package uk.co.xcordis.optimiser.scheduler;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.xcordis.optimiser.dto.BatchDataApiResponse;
import uk.co.xcordis.optimiser.dto.GatewayBatchDataApiRequest;
import uk.co.xcordis.optimiser.dto.GatewayBatchDataDto;
import uk.co.xcordis.optimiser.dto.MerchantBatchDataApiRequest;
import uk.co.xcordis.optimiser.dto.MerchantBatchDataDto;
import uk.co.xcordis.optimiser.dto.MerchantGatewayBatchDataApiRequest;
import uk.co.xcordis.optimiser.dto.MerchantGatewayBatchDataDto;
import uk.co.xcordis.optimiser.model.MerchantRelatedJobRecord;
import uk.co.xcordis.optimiser.model.MerchantRelatedRequestData;
import uk.co.xcordis.optimiser.util.ApiRequestTypeEnum;
import uk.co.xcordis.optimiser.util.ApplicationConstants;
import uk.co.xcordis.optimiser.util.ApplicationURIConstants;
import uk.co.xcordis.optimiser.util.ApplicationUtils;
import uk.co.xcordis.optimiser.util.RequestResponseStatusEnum;
import uk.co.xcordis.optimiser.util.ServiceRegistry;

/**
 * The <code>GeneralBatchDataScheduler</code> scheduler is used to bifurcation the request data and store data into merchantrelatedjobrecord in <b>Optimiser</b>
 * application.
 *
 * @author Rob Atkin
 */
public class GeneralBatchDataScheduler implements Job, ApplicationURIConstants, ApplicationConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(GeneralBatchDataScheduler.class);

	private ServiceRegistry serviceRegistry = null;

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
			final UUID jobId = (UUID) jobDataMap.get(JOB_ID_LABEL);

			// Update the Job
			updateJob(jobId);

			// Process the general job
			processGeneralJob();

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
	 * This method <code>processGeneralJob</code> is used to process the general job for batch data bifurcation process.
	 *
	 */
	private void processGeneralJob() {

		LOGGER.info(" ==> Method : processGeneralJob ==> Enter");

		try {

			final List<MerchantRelatedRequestData> merchantRelatedRequestDataList = serviceRegistry.getMerchantRelatedRequestDataService()
					.getListByStatus(RequestResponseStatusEnum.RECEIVED.status());

			if (ApplicationUtils.isValid(merchantRelatedRequestDataList)) {

				merchantRelatedRequestDataList.forEach(merchantRelatedRequestData -> {

					Boolean isError = Boolean.FALSE;

					// Change the MerchantRelatedRequestData status = STEP1_START
					updateMerchantRelatedRequestDataStatus(merchantRelatedRequestData, RequestResponseStatusEnum.STEP1_START.status());

					// populate and process the batch data
					isError = processBatchData(merchantRelatedRequestData, isError);

					if (!isError) {
						// If no any error then finally change the MerchantRelatedRequestData status = STEP1_COMPLETED
						updateMerchantRelatedRequestDataStatus(merchantRelatedRequestData, RequestResponseStatusEnum.STEP1_COMPLETED.status());
					}
				});

			} else {
				LOGGER.info(" ==> Method : processGeneralJob ==> No record found for RECEIVED status in MerchantRelatedRequestData");
			}

		} catch (final Exception e) {
			LOGGER.error(" ==> Method : processGeneralJob ==> Exception : " + e);
		}

		LOGGER.info(" ==> Method : processGeneralJob ==> Exit");
	}

	/**
	 * This method <code>updateMerchantRelatedRequestDataStatus</code> is used to update the status in MerchantRelatedRequestData table.
	 *
	 * @param merchantRelatedRequestData
	 * @param status
	 */
	private void updateMerchantRelatedRequestDataStatus(final MerchantRelatedRequestData merchantRelatedRequestData, final String status) {

		LOGGER.info(" ==> Method : updateMerchantRelatedRequestDataStatus ==> Called");

		merchantRelatedRequestData.setStatus(status);
		serviceRegistry.getMerchantRelatedRequestDataService().updateMerchantRelatedRequestData(merchantRelatedRequestData);
	}

	/**
	 * This method <code>processBatchData</code> is used for process the batch data for all request types.
	 *
	 * @param merchantRelatedRequestData
	 * @param isError
	 * @return
	 */
	private Boolean processBatchData(final MerchantRelatedRequestData merchantRelatedRequestData, Boolean isError) {

		LOGGER.info(" ==> Method : processBatchData ==> Enter");

		try {

			if (ApiRequestTypeEnum.GATEWAY_BATCH_DATA.requestType().equals(merchantRelatedRequestData.getRequestType())) {

				// populate the gateway batch data and save to MerchantRelatedJobRecord table.
				isError = addGatewayBatchDataToMerchantJobRecord(merchantRelatedRequestData, isError);

			} else if (ApiRequestTypeEnum.MERCHANT_BATCH_DATA.requestType().equals(merchantRelatedRequestData.getRequestType())) {

				// populate the merchant batch data and save to MerchantRelatedJobRecord table.
				isError = addMerchantBatchDataToMerchantJobRecord(merchantRelatedRequestData, isError);

			} else if (ApiRequestTypeEnum.MERCHANT_GATEWAY_BATCH_DATA.requestType().equals(merchantRelatedRequestData.getRequestType())) {

				// populate the merchant gateway batch data and save to MerchantRelatedJobRecord table.
				isError = addMerchantGatewayBatchDataToMerchantJobRecord(merchantRelatedRequestData, isError);
			}

		} catch (final Exception e) {
			isError = Boolean.TRUE;
			LOGGER.error(" ==> Method : processBatchData ==> Exception : " + e);
		}

		LOGGER.info(" ==> Method : processBatchData ==> Exit");
		return isError;
	}

	/**
	 * This method <code>addGatewayBatchDataToMerchantJobRecord</code> is use for populate and add the gateway batch data to merchant related job record table.
	 *
	 * @param merchantRelatedRequestData
	 * @param isError
	 */
	private Boolean addGatewayBatchDataToMerchantJobRecord(final MerchantRelatedRequestData merchantRelatedRequestData, Boolean isError) {

		LOGGER.info(" ==> Method : addGatewayBatchDataToMerchantJobRecord ==> Enter");

		try {

			final GatewayBatchDataApiRequest gatewayBatchData = ApplicationUtils.generateObjectFromJSON(merchantRelatedRequestData.getRequestData(),
					GatewayBatchDataApiRequest.class, Boolean.FALSE);

			if (gatewayBatchData != null && ApplicationUtils.isValid(gatewayBatchData.getData())) {

				final List<GatewayBatchDataDto> gatewayBatchDataDtoList = gatewayBatchData.getData();

				gatewayBatchDataDtoList.forEach(batchData -> {

					// store gateway batch data
					addMerchantRelatedJobRecordDetails(merchantRelatedRequestData, batchData, null, Boolean.FALSE);

				});

			} else {
				isError = Boolean.TRUE;
			}

		} catch (final Exception e) {
			isError = Boolean.TRUE;
			LOGGER.error(" ==> Method : addGatewayBatchDataToMerchantJobRecord ==> Exception : " + e);
		}

		if (isError) {
			// store data to MerchantRelatedJobRecord for error
			final UUID merchantJobRecordId = addMerchantRelatedJobRecordDetails(merchantRelatedRequestData, merchantRelatedRequestData.getRequestData(), null,
					Boolean.TRUE);

			final String referenceId = ApplicationUtils
					.getKeyValueFromJsonObject(ApplicationUtils.getJsonObjectFromJsonString(merchantRelatedRequestData.getRequestData()), REFERENCE_ID_LABEL);
			final String notificationUrl = ApplicationUtils.getKeyValueFromJsonObject(
					ApplicationUtils.getJsonObjectFromJsonString(merchantRelatedRequestData.getRequestData()), NOTIFICATION_URL_LABEL);

			// Send response to end user for data is not valid
			ApplicationUtils.sendResponseToClient(setNotificationResponse(merchantJobRecordId, referenceId), notificationUrl);
		}

		LOGGER.info(" ==> Method : addGatewayBatchDataToMerchantJobRecord ==> Exit");
		return isError;
	}

	/**
	 * This method <code>addMerchantBatchDataToMerchantJobRecord</code> is use for populate and add the merchant batch data to merchant related job record
	 * table.
	 *
	 * @param merchantRelatedRequestData
	 * @param isError
	 */
	private Boolean addMerchantBatchDataToMerchantJobRecord(final MerchantRelatedRequestData merchantRelatedRequestData, Boolean isError) {

		LOGGER.info(" ==> Method : addMerchantBatchDataToMerchantJobRecord ==> Enter");

		try {

			final MerchantBatchDataApiRequest merchantBatchData = ApplicationUtils.generateObjectFromJSON(merchantRelatedRequestData.getRequestData(),
					MerchantBatchDataApiRequest.class, Boolean.FALSE);

			if (merchantBatchData != null && ApplicationUtils.isValid(merchantBatchData.getData())) {

				final List<MerchantBatchDataDto> merchantBatchDataDtoList = merchantBatchData.getData();

				merchantBatchDataDtoList.forEach(batchData -> {

					// store merchant batch data
					addMerchantRelatedJobRecordDetails(merchantRelatedRequestData, batchData, batchData.getMerchantId(), Boolean.FALSE);

				});

			} else {
				isError = Boolean.TRUE;
			}

		} catch (final Exception e) {
			isError = Boolean.TRUE;
			LOGGER.error(" ==> Method : addMerchantBatchDataToMerchantJobRecord ==> Exception : " + e);
		}

		if (isError) {
			// store data to MerchantRelatedJobRecord for error
			final UUID merchantJobRecordId = addMerchantRelatedJobRecordDetails(merchantRelatedRequestData, merchantRelatedRequestData.getRequestData(), null,
					Boolean.TRUE);

			final String referenceId = ApplicationUtils
					.getKeyValueFromJsonObject(ApplicationUtils.getJsonObjectFromJsonString(merchantRelatedRequestData.getRequestData()), REFERENCE_ID_LABEL);
			final String notificationUrl = ApplicationUtils.getKeyValueFromJsonObject(
					ApplicationUtils.getJsonObjectFromJsonString(merchantRelatedRequestData.getRequestData()), NOTIFICATION_URL_LABEL);

			// Send response to end user for data is not valid
			ApplicationUtils.sendResponseToClient(setNotificationResponse(merchantJobRecordId, referenceId), notificationUrl);
		}

		LOGGER.info(" ==> Method : addMerchantBatchDataToMerchantJobRecord ==> Exit");
		return isError;
	}

	/**
	 * This method <code>addMerchantGatewayBatchDataToMerchantJobRecord</code> is use for populate and add the merchant gateway batch data to merchant related
	 * job record table.
	 *
	 * @param merchantRelatedRequestData
	 * @param isError
	 */
	private Boolean addMerchantGatewayBatchDataToMerchantJobRecord(final MerchantRelatedRequestData merchantRelatedRequestData, Boolean isError) {

		LOGGER.info(" ==> Method : addMerchantGatewayBatchDataToMerchantJobRecord ==> Enter");

		try {

			final MerchantGatewayBatchDataApiRequest merchantGatewayBatchData = ApplicationUtils
					.generateObjectFromJSON(merchantRelatedRequestData.getRequestData(), MerchantGatewayBatchDataApiRequest.class, Boolean.FALSE);

			if (merchantGatewayBatchData != null && ApplicationUtils.isValid(merchantGatewayBatchData.getData())) {

				final List<MerchantGatewayBatchDataDto> merchantGatewayBatchDataDtoList = merchantGatewayBatchData.getData();

				merchantGatewayBatchDataDtoList.forEach(batchData -> {

					// store merchant gateway batch data
					addMerchantRelatedJobRecordDetails(merchantRelatedRequestData, batchData, batchData.getMerchantId(), Boolean.FALSE);

				});

			} else {
				isError = Boolean.TRUE;
			}

		} catch (final Exception e) {
			isError = Boolean.TRUE;
			LOGGER.info(" ==> Method : addMerchantGatewayBatchDataToMerchantJobRecord ==> Exception : " + e);
		}

		if (isError) {
			// store data to MerchantRelatedJobRecord for error
			final UUID merchantJobRecordId = addMerchantRelatedJobRecordDetails(merchantRelatedRequestData, merchantRelatedRequestData.getRequestData(), null,
					Boolean.TRUE);

			final String referenceId = ApplicationUtils
					.getKeyValueFromJsonObject(ApplicationUtils.getJsonObjectFromJsonString(merchantRelatedRequestData.getRequestData()), REFERENCE_ID_LABEL);
			final String notificationUrl = ApplicationUtils.getKeyValueFromJsonObject(
					ApplicationUtils.getJsonObjectFromJsonString(merchantRelatedRequestData.getRequestData()), NOTIFICATION_URL_LABEL);

			// Send response to end user for data is not valid
			ApplicationUtils.sendResponseToClient(setNotificationResponse(merchantJobRecordId, referenceId), notificationUrl);
		}

		LOGGER.info(" ==> Method : addMerchantGatewayBatchDataToMerchantJobRecord ==> Exit");
		return isError;
	}

	/**
	 * This method <code>addMerchantRelatedJobRecordDetails</code> is used for add the merchant related job record details to database.
	 *
	 * @param merchantRelatedRequestData
	 * @param batchData
	 * @param sourceMerchantId
	 * @param isError
	 */
	private UUID addMerchantRelatedJobRecordDetails(final MerchantRelatedRequestData merchantRelatedRequestData, final Object batchData,
			final String sourceMerchantId, final Boolean isError) {

		LOGGER.info(" ==> Method : addMerchantRelatedJobRecordDetails ==> Enter");

		final MerchantRelatedJobRecord merchantRelatedJobRecord = new MerchantRelatedJobRecord();
		merchantRelatedJobRecord.setMerchantJobRecordId(UUID.randomUUID());
		merchantRelatedJobRecord.setUserId(merchantRelatedRequestData.getUserId());
		merchantRelatedJobRecord.setMerchantRequestDataId(merchantRelatedRequestData.getMerchantRequestDataId());
		merchantRelatedJobRecord.setAuditTimeStamp(ApplicationUtils.getCurrentTimeStamp());
		merchantRelatedJobRecord.setCreatedDate(ApplicationUtils.getCurrentTimeStamp());
		merchantRelatedJobRecord.setOperationType(merchantRelatedRequestData.getOperationType());
		merchantRelatedJobRecord.setRequestType(merchantRelatedRequestData.getRequestType());

		if (batchData instanceof String) {
			merchantRelatedJobRecord.setRequestData(String.valueOf(batchData));
		} else {
			merchantRelatedJobRecord.setRequestData(ApplicationUtils.generateJSONFromObject(batchData));
		}

		merchantRelatedJobRecord.setSourceMerchantId(sourceMerchantId);
		merchantRelatedJobRecord.setStatus(RequestResponseStatusEnum.PENDING.status());

		if (isError) {
			merchantRelatedJobRecord.setStatus(RequestResponseStatusEnum.FAILED.status());
			merchantRelatedJobRecord.setErrorMessage(new HashSet<>(Arrays.asList(ApplicationConstants.BATCH_DATA_NOT_VALID_ERROR_MESSAGE)));
		}

		serviceRegistry.getMerchantRelatedJobRecordService().addMerchantRelatedJobRecord(merchantRelatedJobRecord);

		LOGGER.info(" ==> Method : addMerchantRelatedJobRecordDetails ==> Exit");

		return merchantRelatedJobRecord.getMerchantJobRecordId();
	}

	/**
	 * This method <code>setNotificationResponse</code> is used for set the base response of notify to end user for batch data is invalid.
	 *
	 * @param merchantJobRecordId
	 * @param referenceId
	 * @return
	 */
	private BatchDataApiResponse setNotificationResponse(final UUID merchantJobRecordId, final String referenceId) {

		LOGGER.info(" ==> Method : setNotificationResponse ==> Enter");

		final BatchDataApiResponse batchDataApiResponse = new BatchDataApiResponse();

		if (merchantJobRecordId != null) {

			final MerchantRelatedJobRecord merchantRelatedJobRecord = serviceRegistry.getMerchantRelatedJobRecordService().findById(merchantJobRecordId);

			if (merchantRelatedJobRecord != null) {

				batchDataApiResponse.setId(String.valueOf(merchantRelatedJobRecord.getMerchantJobRecordId()));
				batchDataApiResponse.setUserId(merchantRelatedJobRecord.getUserId());
				batchDataApiResponse.setOperationType(merchantRelatedJobRecord.getOperationType());
				batchDataApiResponse.setReferenceId(referenceId);
				batchDataApiResponse.setStatus(RequestResponseStatusEnum.FAILED.status());
				batchDataApiResponse.setErrorMessage(merchantRelatedJobRecord.getErrorMessage());
			} else {
				LOGGER.info(" ==> Method : setNotificationResponse ==> merchantRelatedJobRecord Object is NULL");
			}

		} else {
			LOGGER.info(" ==> Method : setNotificationResponse ==> merchantJobRecordId is NULL");
		}

		LOGGER.info(" ==> Method : setNotificationResponse ==> Exit");
		return batchDataApiResponse;
	}

}
