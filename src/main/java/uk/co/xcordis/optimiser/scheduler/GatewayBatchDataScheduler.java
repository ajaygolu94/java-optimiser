package uk.co.xcordis.optimiser.scheduler;

import java.util.ArrayList;
import java.util.HashMap;
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
import uk.co.xcordis.optimiser.dto.GatewayBatchDataDto;
import uk.co.xcordis.optimiser.model.MerchantRelatedJobRecord;
import uk.co.xcordis.optimiser.model.MerchantRelatedRequestData;
import uk.co.xcordis.optimiser.model.PaymentGateways;
import uk.co.xcordis.optimiser.util.ApiRequestTypeEnum;
import uk.co.xcordis.optimiser.util.ApplicationConstants;
import uk.co.xcordis.optimiser.util.ApplicationURIConstants;
import uk.co.xcordis.optimiser.util.ApplicationUtils;
import uk.co.xcordis.optimiser.util.BatchDataOperationTypeEnum;
import uk.co.xcordis.optimiser.util.ErrorDataEnum;
import uk.co.xcordis.optimiser.util.RequestResponseStatusEnum;
import uk.co.xcordis.optimiser.util.ServiceRegistry;

/**
 * The <code>GatewayBatchDataScheduler</code> scheduler is used to populate the gateway batch data and store it into gateway table in <b>Optimiser</b>
 * application.
 *
 * @author Rob Atkin
 */
public class GatewayBatchDataScheduler implements Job, ApplicationURIConstants, ApplicationConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(GatewayBatchDataScheduler.class);

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

			// Process the gateway batch data job
			processGatewayBatchDataJob();

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
	 * This method <code>processGatewayBatchDataJob</code> is used to process the gateway batch data job and store data to paymentgateway table.
	 *
	 */
	private void processGatewayBatchDataJob() {

		LOGGER.info(" ==> Method : processGatewayBatchDataJob ==> Enter");

		try {

			final Set<MerchantRelatedJobRecord> merchantRelatedJobRecordList = serviceRegistry.getMerchantRelatedJobRecordService()
					.getListByRequestTypeAndStatus(ApiRequestTypeEnum.GATEWAY_BATCH_DATA.requestType(), RequestResponseStatusEnum.PENDING.status());

			if (ApplicationUtils.isValid(merchantRelatedJobRecordList)) {

				merchantRelatedJobRecordList.forEach(merchantRelatedJobRecord -> {

					List<String> errors = new ArrayList<>();

					if (merchantRelatedJobRecord != null && !ApplicationUtils.isEmpty(merchantRelatedJobRecord.getOperationType())) {

						if (BatchDataOperationTypeEnum.ADD.operationType().equals(merchantRelatedJobRecord.getOperationType())) {

							// Add the Gateway Batch Data
							errors = addGatewayDetails(merchantRelatedJobRecord, errors);

						} else if (BatchDataOperationTypeEnum.UPDATE.operationType().equals(merchantRelatedJobRecord.getOperationType())) {

							// Update the Gateway Batch Data
							errors = updateGatewayDetails(merchantRelatedJobRecord, errors);

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
						" ==> Method : processGatewayBatchDataJob ==> No record found for RequestType = GATEWAY_BATCH_DATA and Status = PENDING in MerchantRelatedJobRecord");
			}

		} catch (final Exception e) {
			LOGGER.error(" ==> Method : processGatewayBatchDataJob ==> Exception : " + e);
		}

		LOGGER.info(" ==> Method : processGatewayBatchDataJob ==> Exit");
	}

	/**
	 * This method <code>addGatewayDetails</code> is used for add the gateway details to database.
	 *
	 * @param merchantRelatedJobRecord
	 * @param errors
	 * @return
	 */
	private List<String> addGatewayDetails(final MerchantRelatedJobRecord merchantRelatedJobRecord, List<String> errors) {

		LOGGER.info(" ==> Method : addGatewayDetails ==> Enter");

		try {

			if (!ApplicationUtils.isEmpty(merchantRelatedJobRecord.getRequestData())) {

				final GatewayBatchDataDto gatewayBatchDataDto = ApplicationUtils.generateObjectFromJSON(merchantRelatedJobRecord.getRequestData(),
						GatewayBatchDataDto.class, Boolean.FALSE);

				if (gatewayBatchDataDto != null) {

					errors = validateGateway(gatewayBatchDataDto, errors, Boolean.TRUE);

					if (!ApplicationUtils.isValid(errors)) {

						final PaymentGateways paymentGateways = new PaymentGateways();
						paymentGateways.setPaymentgatewayname(gatewayBatchDataDto.getGatewayName());

						if (gatewayBatchDataDto.getGatewayParameter() != null) {
							paymentGateways.setGatewayParameters(new HashMap(gatewayBatchDataDto.getGatewayParameter()));
						}

						// add the gateway details.
						serviceRegistry.getPaymentGatewaysService().savePaymentGatewayDetails(paymentGateways);
					}

				} else {
					errors.add(messageSource.getMessage(ErrorDataEnum.JOB_SCHEDULER_BATCH_DATA_INVALID_ERROR_MESSAGE.message(), null, null));
				}

			} else {
				errors.add(messageSource.getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null));
			}

		} catch (final Exception e) {
			errors.add(messageSource.getMessage(ErrorDataEnum.JOB_SCHEDULER_BATCH_DATA_INVALID_ERROR_MESSAGE.message(), null, null));
			LOGGER.error(" ==> Method : addGatewayDetails ==> Exception : " + e);
		}

		LOGGER.info(" ==> Method : addGatewayDetails ==> Exit");
		return errors;
	}

	/**
	 * This method <code>updateGatewayDetails</code> is used for update the gateway details to database.
	 *
	 * @param merchantRelatedJobRecord
	 * @param errors
	 * @return
	 */
	private List<String> updateGatewayDetails(final MerchantRelatedJobRecord merchantRelatedJobRecord, List<String> errors) {

		LOGGER.info(" ==> Method : updateGatewayDetails ==> Enter");

		try {

			if (!ApplicationUtils.isEmpty(merchantRelatedJobRecord.getRequestData())) {

				final GatewayBatchDataDto gatewayBatchDataDto = ApplicationUtils.generateObjectFromJSON(merchantRelatedJobRecord.getRequestData(),
						GatewayBatchDataDto.class, Boolean.FALSE);

				if (gatewayBatchDataDto != null) {

					errors = validateGateway(gatewayBatchDataDto, errors, Boolean.FALSE);

					if (!ApplicationUtils.isValid(errors)) {

						final PaymentGateways paymentGateways = serviceRegistry.getPaymentGatewaysService()
								.getPaymentGatewayByGatewayName(gatewayBatchDataDto.getGatewayName());

						if (paymentGateways != null) {
							paymentGateways.setPaymentgatewayname(gatewayBatchDataDto.getGatewayName());

							if (gatewayBatchDataDto.getGatewayParameter() != null) {
								paymentGateways.setGatewayParameters(new HashMap(gatewayBatchDataDto.getGatewayParameter()));
							}

							// update the gateway details.
							serviceRegistry.getPaymentGatewaysService().savePaymentGatewayDetails(paymentGateways);

						} else {
							errors.add(messageSource.getMessage(ErrorDataEnum.JOB_SCHEDULER_GATEWAY_BATCH_DATA_UPDATE_OPERATION_FAILED_MESSAGE.message(), null,
									null) + gatewayBatchDataDto.getGatewayName());
						}
					}

				} else {
					errors.add(messageSource.getMessage(ErrorDataEnum.JOB_SCHEDULER_BATCH_DATA_INVALID_ERROR_MESSAGE.message(), null, null));
				}
			} else {
				errors.add(messageSource.getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null));
			}

		} catch (final Exception e) {
			errors.add(messageSource.getMessage(ErrorDataEnum.JOB_SCHEDULER_BATCH_DATA_INVALID_ERROR_MESSAGE.message(), null, null));
			LOGGER.error(" ==> Method : updateGatewayDetails ==> Exception : " + e);
		}

		LOGGER.info(" ==> Method : updateGatewayDetails ==> Exit");
		return errors;
	}

	/**
	 * The method <code>validateGateway</code> is responsible for validate the payment gateway object.
	 *
	 * @param gatewayBatchDataDto
	 * @param errors
	 * @param isAddOrUpdate
	 * @return
	 */
	private List<String> validateGateway(final GatewayBatchDataDto gatewayBatchDataDto, final List<String> errors, final Boolean isAddOrUpdate) {

		LOGGER.info(" ==> Method : validateGateway ==> Enter ");

		if (ApplicationUtils.isEmpty(gatewayBatchDataDto.getGatewayName())) {

			errors.add(messageSource.getMessage(ErrorDataEnum.JOB_SCHEDULER_BATCH_DATA_MISSING_FIELD_ERROR.message(),
					new Object[] { messageSource.getMessage(ErrorDataEnum.GATEWAYNAME_FIELD_LABEL.message(), null, null) }, null));
		} else {

			if (isAddOrUpdate && serviceRegistry.getPaymentGatewaysService().getPaymentGatewayByGatewayName(gatewayBatchDataDto.getGatewayName()) != null) {
				errors.add(messageSource.getMessage(ErrorDataEnum.COMMON_EXISTS_ERROR_MESSAGE.message(),
						new Object[] { messageSource.getMessage(ErrorDataEnum.GATEWAYNAME_FIELD_LABEL.message(), null, null) }, null));
			}

			if (!ApplicationUtils.isOnlyAlphaAndSpace(gatewayBatchDataDto.getGatewayName())) {
				errors.add(messageSource.getMessage(ErrorDataEnum.JOB_SCHEDULER_BATCH_DATA_ERROR_ALPHAANDSPACE.message(), null, null)
						+ messageSource.getMessage(ErrorDataEnum.GATEWAYNAME_FIELD_LABEL.message(), null, null));
			}
		}

		LOGGER.info(" ==> Method : validateGateway ==> Exit ");

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
