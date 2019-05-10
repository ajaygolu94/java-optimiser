package uk.co.xcordis.optimiser.scheduler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;

import uk.co.xcordis.optimiser.dto.BatchDataApiResponse;
import uk.co.xcordis.optimiser.dto.MerchantBatchDataDto;
import uk.co.xcordis.optimiser.model.ApplicationUser;
import uk.co.xcordis.optimiser.model.Merchant;
import uk.co.xcordis.optimiser.model.MerchantRelatedJobRecord;
import uk.co.xcordis.optimiser.model.MerchantRelatedRequestData;
import uk.co.xcordis.optimiser.util.ApiRequestTypeEnum;
import uk.co.xcordis.optimiser.util.ApplicationConstants;
import uk.co.xcordis.optimiser.util.ApplicationURIConstants;
import uk.co.xcordis.optimiser.util.ApplicationUtils;
import uk.co.xcordis.optimiser.util.BatchDataOperationTypeEnum;
import uk.co.xcordis.optimiser.util.ErrorDataEnum;
import uk.co.xcordis.optimiser.util.RequestResponseStatusEnum;
import uk.co.xcordis.optimiser.util.ServiceRegistry;

/**
 * The <code>MerchantBatchDataScheduler</code> scheduler is used to populate the merchant batch data and store it into merchant table in <b>Optimiser</b>
 * application.
 *
 * @author Rob Atkin
 */
public class MerchantBatchDataScheduler implements Job, ApplicationURIConstants, ApplicationConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(MerchantBatchDataScheduler.class);

	private ServiceRegistry serviceRegistry = null;

	private MessageSource messageSource = null;

	/*
	 * (non-Javadoc)
	 *
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execute(final JobExecutionContext context) throws JobExecutionException {

		LOGGER.info(" ==> Method : execute ==> Enter");

		final Map<String, Object> jobDataMap = context.getJobDetail().getJobDataMap();

		if (validateJobDataMap(jobDataMap)) {

			serviceRegistry = (ServiceRegistry) jobDataMap.get(SERVICEREGISTRY_LABEL);
			messageSource = (MessageSource) jobDataMap.get(MESSAGESOURCE_LABEL);
			final UUID jobId = (UUID) jobDataMap.get(JOB_ID_LABEL);

			// Update the Job
			updateJob(jobId);

			// Process the merchant batch data job
			processMerchantBatchDataJob();

		} else {
			LOGGER.info(" ==> Method : execute ==> jobDataMap is empty");
		}

		LOGGER.info(" ==> Method : execute ==> Exit");
	}

	/**
	 * This method <code>validateJobDataMap</code> is used for validate the job data map.
	 *
	 * @param jobDataMap
	 * @return
	 */
	private Boolean validateJobDataMap(final Map<String, Object> jobDataMap) {

		LOGGER.info(" ==> Method : validateJobDataMap ==> Called");

		if (!jobDataMap.isEmpty() && jobDataMap.get(SERVICEREGISTRY_LABEL) != null && jobDataMap.get(MESSAGESOURCE_LABEL) != null
				&& jobDataMap.get(JOB_ID_LABEL) != null) {

			return Boolean.TRUE;
		}

		return Boolean.FALSE;
	}

	/**
	 * This method <code>updateJob</code> is used for update the last run time to job table.
	 *
	 * @param jobId
	 */
	private void updateJob(final UUID jobId) {

		LOGGER.info(" ==> Method : updateJob ==> Enter");

		try {

			final uk.co.xcordis.optimiser.model.Job jobDetails = serviceRegistry.getJobService().getJobById(jobId);

			if (jobDetails != null) {

				jobDetails.setLastRunTime(ApplicationUtils.getCurrentTimeStamp());

				serviceRegistry.getJobService().updateJob(jobDetails);
			}

		} catch (final Exception e) {
			LOGGER.error(" ==> Method : updateJob ==> Exception : " + e);
		}

		LOGGER.info(" ==> Method : updateJob ==> Exit");
	}

	/**
	 * This method <code>processMerchantBatchDataJob</code> is used to process the merchant batch data job and store data to merchant table.
	 *
	 */
	private void processMerchantBatchDataJob() {

		LOGGER.info(" ==> Method : processMerchantBatchDataJob ==> Enter");

		try {

			final Set<MerchantRelatedJobRecord> merchantRelatedJobRecordList = serviceRegistry.getMerchantRelatedJobRecordService()
					.getListByRequestTypeAndStatus(ApiRequestTypeEnum.MERCHANT_BATCH_DATA.requestType(), RequestResponseStatusEnum.PENDING.status());

			if (ApplicationUtils.isValid(merchantRelatedJobRecordList)) {

				merchantRelatedJobRecordList.forEach(merchantRelatedJobRecord -> {

					List<String> errors = new ArrayList<>();

					if (merchantRelatedJobRecord != null && !ApplicationUtils.isEmpty(merchantRelatedJobRecord.getOperationType())) {

						if (BatchDataOperationTypeEnum.ADD.operationType().equals(merchantRelatedJobRecord.getOperationType())) {

							// Add the Merchant Batch Data
							errors = addMerchantDetails(merchantRelatedJobRecord, errors);

						} else if (BatchDataOperationTypeEnum.UPDATE.operationType().equals(merchantRelatedJobRecord.getOperationType())) {

							// Update the Merchant Batch Data
							errors = updateMerchantDetails(merchantRelatedJobRecord, errors);

						} else {
							errors.add(messageSource.getMessage(ErrorDataEnum.BATCH_DATA_API_OPERATIONTYPE_INVALID_ERROR_MESSAGE.message(), null, null));
						}
					} else {
						errors.add(messageSource.getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null));
					}

					// Update the final status of merchant related job record
					updateMerchantRelatedJobRecordStatus(merchantRelatedJobRecord, errors);
				});

			} else {
				LOGGER.info(
						" ==> Method : processMerchantBatchDataJob ==> No record found for RequestType = MERCHANT_BATCH_DATA and Status = PENDING in MerchantRelatedJobRecord");
			}

		} catch (final Exception e) {
			LOGGER.error(" ==> Method : processMerchantBatchDataJob ==> Exception : " + e);
		}

		LOGGER.info(" ==> Method : processMerchantBatchDataJob ==> Exit");
	}

	/**
	 * This method <code>addMerchantDetails</code> is used for add the merchant details to database.
	 *
	 * @param merchantRelatedJobRecord
	 * @param errors
	 * @return
	 */
	private List<String> addMerchantDetails(final MerchantRelatedJobRecord merchantRelatedJobRecord, final List<String> errors) {

		LOGGER.info(" ==> Method : addMerchantDetails ==> Enter");

		try {

			if (!ApplicationUtils.isEmpty(merchantRelatedJobRecord.getRequestData())) {

				final MerchantBatchDataDto merchantBatchDataDto = ApplicationUtils.generateObjectFromJSON(merchantRelatedJobRecord.getRequestData(),
						MerchantBatchDataDto.class, Boolean.FALSE);

				if (merchantBatchDataDto != null && !ApplicationUtils.isEmpty(merchantBatchDataDto.getMerchantId())
						&& !ApplicationUtils.isEmpty(merchantBatchDataDto.getMerchantName())) {

					final Merchant merchant = new Merchant();
					merchant.setSourcemerchantid(merchantBatchDataDto.getMerchantId());
					merchant.setMerchantname(merchantBatchDataDto.getMerchantName());
					merchant.setUserId(getUserId(merchantRelatedJobRecord.getUserId()));

					// add the merchant details.
					serviceRegistry.getMerchantService().saveMerchant(merchant);

				} else {
					errors.add(messageSource.getMessage(ErrorDataEnum.JOB_SCHEDULER_BATCH_DATA_INVALID_ERROR_MESSAGE.message(), null, null));
				}
			} else {
				errors.add(messageSource.getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null));
			}

		} catch (final Exception e) {
			errors.add(messageSource.getMessage(ErrorDataEnum.JOB_SCHEDULER_BATCH_DATA_INVALID_ERROR_MESSAGE.message(), null, null));
			LOGGER.error(" ==> Method : addMerchantDetails ==> Exception : " + e);
		}

		LOGGER.info(" ==> Method : addMerchantDetails ==> Exit");
		return errors;
	}

	/**
	 * This method <code>updateMerchantDetails</code> is used for update the merchant details to database.
	 *
	 * @param merchantRelatedJobRecord
	 * @param errors
	 * @return
	 */
	private List<String> updateMerchantDetails(final MerchantRelatedJobRecord merchantRelatedJobRecord, final List<String> errors) {

		LOGGER.info(" ==> Method : updateMerchantDetails ==> Enter");

		try {

			if (!ApplicationUtils.isEmpty(merchantRelatedJobRecord.getRequestData())) {

				final MerchantBatchDataDto merchantBatchDataDto = ApplicationUtils.generateObjectFromJSON(merchantRelatedJobRecord.getRequestData(),
						MerchantBatchDataDto.class, Boolean.FALSE);

				if (merchantBatchDataDto != null && !ApplicationUtils.isEmpty(merchantBatchDataDto.getMerchantId())
						&& !ApplicationUtils.isEmpty(merchantBatchDataDto.getMerchantName())
						&& !ApplicationUtils.isEmpty(merchantRelatedJobRecord.getSourceMerchantId())) {

					final Merchant merchant = serviceRegistry.getMerchantService()
							.getMerchantBySourceMerchantId(merchantRelatedJobRecord.getSourceMerchantId());

					if (merchant != null) {
						merchant.setSourcemerchantid(merchantBatchDataDto.getMerchantId());
						merchant.setMerchantname(merchantBatchDataDto.getMerchantName());
						merchant.setUserId(getUserId(merchantRelatedJobRecord.getUserId()));

						// update the merchant details.
						serviceRegistry.getMerchantService().saveMerchant(merchant);

					} else {
						errors.add(
								messageSource.getMessage(ErrorDataEnum.JOB_SCHEDULER_MERCHANT_BATCH_DATA_UPDATE_OPERATION_FAILED_MESSAGE.message(), null, null)
										+ merchantRelatedJobRecord.getSourceMerchantId());
					}

				} else {
					errors.add(messageSource.getMessage(ErrorDataEnum.JOB_SCHEDULER_BATCH_DATA_INVALID_ERROR_MESSAGE.message(), null, null));
				}
			} else {
				errors.add(messageSource.getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null));
			}

		} catch (final Exception e) {
			errors.add(messageSource.getMessage(ErrorDataEnum.JOB_SCHEDULER_BATCH_DATA_INVALID_ERROR_MESSAGE.message(), null, null));
			LOGGER.error(" ==> Method : updateMerchantDetails ==> Exception : " + e);
		}

		LOGGER.info(" ==> Method : updateMerchantDetails ==> Exit");
		return errors;
	}

	/**
	 * This method <code>updateMerchantRelatedJobRecordStatus</code> is used for update the merchant related job record status to database.
	 *
	 * @param merchantRelatedJobRecord
	 * @param errors
	 */
	private void updateMerchantRelatedJobRecordStatus(final MerchantRelatedJobRecord merchantRelatedJobRecord, final List<String> errors) {

		LOGGER.info(" ==> Method : updateMerchantRelatedJobRecordStatus ==> Enter");

		try {

			if (ApplicationUtils.isValid(errors)) {
				merchantRelatedJobRecord.setStatus(RequestResponseStatusEnum.FAILED.status());
			} else {
				merchantRelatedJobRecord.setStatus(RequestResponseStatusEnum.SUCCESS.status());
			}

			merchantRelatedJobRecord.setErrorMessage(new HashSet<>(errors));
			serviceRegistry.getMerchantRelatedJobRecordService().updateMerchantRelatedJobRecord(merchantRelatedJobRecord);

			// If status = FAILED then notify to end user with error message
			if (RequestResponseStatusEnum.FAILED.status().equals(merchantRelatedJobRecord.getStatus())) {

				final MerchantRelatedRequestData merchantRelatedRequestData = serviceRegistry.getMerchantRelatedRequestDataService()
						.findById(merchantRelatedJobRecord.getMerchantRequestDataId());

				final String referenceId = ApplicationUtils.getKeyValueFromJsonObject(
						ApplicationUtils.getJsonObjectFromJsonString(merchantRelatedRequestData.getRequestData()), REFERENCE_ID_LABEL);
				final String notificationUrl = ApplicationUtils.getKeyValueFromJsonObject(
						ApplicationUtils.getJsonObjectFromJsonString(merchantRelatedRequestData.getRequestData()), NOTIFICATION_URL_LABEL);

				// Send response to end user
				ApplicationUtils.sendResponseToClient(setNotificationResponse(merchantRelatedJobRecord, referenceId), notificationUrl);
			}

		} catch (final Exception e) {
			LOGGER.error(" ==> Method : updateMerchantRelatedJobRecordStatus ==> Exception : " + e);
		}

		LOGGER.info(" ==> Method : updateMerchantRelatedJobRecordStatus ==> Exit");
	}

	/**
	 * This method <code>getUserId</code> is used for get the userId from application user table based on openid param.
	 *
	 * @param userId
	 * @return
	 */
	private UUID getUserId(final String userId) {

		LOGGER.info(" ==> Method : getUserId ==> Called");

		final ApplicationUser applicationUser = serviceRegistry.getUserService().getUserByOpenId(userId);

		if (applicationUser != null && applicationUser.getUserId() != null) {
			return applicationUser.getUserId();
		}

		return null;
	}

	/**
	 * This method <code>setNotificationResponse</code> is used for set the base response of notify to end user for batch data is invalid.
	 *
	 * @param merchantJobRecordId
	 * @param referenceId
	 * @return
	 */
	private BatchDataApiResponse setNotificationResponse(final MerchantRelatedJobRecord merchantRelatedJobRecord, final String referenceId) {

		LOGGER.info(" ==> Method : setNotificationResponse ==> Enter");

		final BatchDataApiResponse batchDataApiResponse = new BatchDataApiResponse();

		batchDataApiResponse.setId(String.valueOf(merchantRelatedJobRecord.getMerchantJobRecordId()));
		batchDataApiResponse.setUserId(merchantRelatedJobRecord.getUserId());
		batchDataApiResponse.setOperationType(merchantRelatedJobRecord.getOperationType());
		batchDataApiResponse.setReferenceId(referenceId);
		batchDataApiResponse.setStatus(RequestResponseStatusEnum.FAILED.status());
		batchDataApiResponse.setErrorMessage(merchantRelatedJobRecord.getErrorMessage());

		LOGGER.info(" ==> Method : setNotificationResponse ==> Exit");
		return batchDataApiResponse;
	}
}
