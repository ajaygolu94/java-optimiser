/*
 * Copyright (c) XCordis FinTech Ltd 2010-2018.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.restcontroller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import uk.co.xcordis.optimiser.controller.BaseController;
import uk.co.xcordis.optimiser.dto.BatchDataApiResponse;
import uk.co.xcordis.optimiser.dto.GatewayBatchDataApiRequest;
import uk.co.xcordis.optimiser.dto.MerchantBatchDataApiRequest;
import uk.co.xcordis.optimiser.dto.MerchantGatewayBatchDataApiRequest;
import uk.co.xcordis.optimiser.model.MerchantRelatedRequestData;
import uk.co.xcordis.optimiser.util.ApiRequestTypeEnum;
import uk.co.xcordis.optimiser.util.ApplicationConstants;
import uk.co.xcordis.optimiser.util.ApplicationURIConstants;
import uk.co.xcordis.optimiser.util.ApplicationUtils;
import uk.co.xcordis.optimiser.util.ErrorDataEnum;
import uk.co.xcordis.optimiser.util.OptimiserBatchDataUtility;
import uk.co.xcordis.optimiser.util.RequestResponseStatusEnum;
import uk.co.xcordis.optimiser.util.RequestStatusEnum;

/**
 * The <code>BatchDataRestApiController</code> class responsible for expose an API for batch data which is receive the data from external application and push
 * in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@RestController
public class BatchDataRestApiController extends BaseController implements ApplicationConstants, ApplicationURIConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(BatchDataRestApiController.class);

	@Autowired
	private OptimiserBatchDataUtility optimiserBatchDataUtility;

	/**
	 * This <code>gatewayBatchDataApi</code> method is used to expose the gateway batch data API.
	 *
	 * @param httpHeaders
	 * @param gatewayBatchDataApiRequest
	 * @param servletRequest
	 * @return
	 */
	@PostMapping(BATCH_DATA_GATEWAY)
	public ResponseEntity<BatchDataApiResponse> gatewayBatchDataApi(@RequestHeader final HttpHeaders httpHeaders,
			@RequestBody(required = false) final GatewayBatchDataApiRequest gatewayBatchDataApiRequest, final HttpServletRequest servletRequest) {

		LOGGER.info(" ==> Method ==> gatewayBatchDataApi ==> Enter");

		final BatchDataApiResponse response = new BatchDataApiResponse();
		Boolean isCommonError = Boolean.FALSE;
		HttpStatus httpStatus = HttpStatus.OK;

		try {

			if (gatewayBatchDataApiRequest != null) {

				final MerchantRelatedRequestData merchantRelatedRequestData = new MerchantRelatedRequestData();
				merchantRelatedRequestData.setRequestType(ApiRequestTypeEnum.GATEWAY_BATCH_DATA.requestType());
				merchantRelatedRequestData.setRequestClass(GatewayBatchDataApiRequest.class.getName());
				merchantRelatedRequestData.setRequestData(ApplicationUtils.generateJSONFromObject(gatewayBatchDataApiRequest));

				// Validate the request header
				optimiserBatchDataUtility.validateBatchDataRequestHeader(httpHeaders, response);

				// Validate the request body
				optimiserBatchDataUtility.validateRequestBodyForBatchDataApi(response, gatewayBatchDataApiRequest, gatewayBatchDataApiRequest.getData());

				// Add the request data to MerchantRelatedRequestData table
				addMerchantRelatedRequestData(response, merchantRelatedRequestData, servletRequest);

				if (!ApplicationUtils.isValid(response.getErrorMessage())) {

					// Authenticate the batch data request
					optimiserBatchDataUtility.authenticateBatchDataRequest(httpHeaders, response);

					if (!ApplicationUtils.isValid(response.getErrorMessage())) {

						// If above all the scenario get valid means data received successfully and set status = RECEIVED
						response.setStatus(RequestResponseStatusEnum.RECEIVED.status());

					} else {
						httpStatus = HttpStatus.UNAUTHORIZED;
					}

				} else {
					httpStatus = HttpStatus.BAD_REQUEST;
				}

			} else {
				httpStatus = HttpStatus.BAD_REQUEST;
				response.setErrorMessage(
						new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_BADREQUEST_ERROR_MESSAGE.message(), null, null))));
			}

		} catch (final Exception e) {
			logError(LOGGER, null, e, " ==> Method ==> gatewayBatchDataApi ==> Exception ==> ");
			LOGGER.info(" ==> Method ==> gatewayBatchDataApi ==> Exception : " + e);
			isCommonError = Boolean.TRUE;
		}

		if (isCommonError) {
			response.setErrorMessage(new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))));
		}

		// Update and set the response of request
		updateAndSetResponseOfRequest(response);

		LOGGER.info(" ==> Method ==> gatewayBatchDataApi ==> Exit");
		return new ResponseEntity<>(response, httpStatus);
	}

	/**
	 * This <code>merchantBatchDataApi</code> method is used to expose the merchant batch data API.
	 *
	 * @param httpHeaders
	 * @param merchantBatchDataApiRequest
	 * @param servletRequest
	 * @return
	 */
	@PostMapping(BATCH_DATA_MERCHANT)
	public ResponseEntity<BatchDataApiResponse> merchantBatchDataApi(@RequestHeader final HttpHeaders httpHeaders,
			@RequestBody(required = false) final MerchantBatchDataApiRequest merchantBatchDataApiRequest, final HttpServletRequest servletRequest) {

		LOGGER.info(" ==> Method ==> merchantBatchDataApi ==> Enter");

		final BatchDataApiResponse response = new BatchDataApiResponse();
		Boolean isCommonError = Boolean.FALSE;
		HttpStatus httpStatus = HttpStatus.OK;

		try {

			if (merchantBatchDataApiRequest != null) {

				final MerchantRelatedRequestData merchantRelatedRequestData = new MerchantRelatedRequestData();
				merchantRelatedRequestData.setRequestType(ApiRequestTypeEnum.MERCHANT_BATCH_DATA.requestType());
				merchantRelatedRequestData.setRequestClass(MerchantBatchDataApiRequest.class.getName());
				merchantRelatedRequestData.setRequestData(ApplicationUtils.generateJSONFromObject(merchantBatchDataApiRequest));

				// Validate the request header
				optimiserBatchDataUtility.validateBatchDataRequestHeader(httpHeaders, response);

				// Validate the request body
				optimiserBatchDataUtility.validateRequestBodyForBatchDataApi(response, merchantBatchDataApiRequest, merchantBatchDataApiRequest.getData());

				// Add the request data to MerchantRelatedRequestData table
				addMerchantRelatedRequestData(response, merchantRelatedRequestData, servletRequest);

				if (!ApplicationUtils.isValid(response.getErrorMessage())) {

					// Authenticate the batch data request
					optimiserBatchDataUtility.authenticateBatchDataRequest(httpHeaders, response);

					if (!ApplicationUtils.isValid(response.getErrorMessage())) {

						// If above all the scenario get valid means data received successfully and set status = RECEIVED
						response.setStatus(RequestResponseStatusEnum.RECEIVED.status());

					} else {
						httpStatus = HttpStatus.UNAUTHORIZED;
					}

				} else {
					httpStatus = HttpStatus.BAD_REQUEST;
				}

			} else {
				httpStatus = HttpStatus.BAD_REQUEST;
				response.setErrorMessage(
						new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_BADREQUEST_ERROR_MESSAGE.message(), null, null))));
			}

		} catch (final Exception e) {
			logError(LOGGER, null, e, " ==> Method ==> getMerchantBatchData ==> Exception ==> ");
			LOGGER.info(" ==> Method ==> getMerchantBatchData ==> Exception : " + e);
			isCommonError = Boolean.TRUE;
		}

		if (isCommonError) {
			response.setErrorMessage(new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))));
		}

		// Update and set the response of request
		updateAndSetResponseOfRequest(response);

		LOGGER.info(" ==> Method ==> merchantBatchDataApi ==> Exit");
		return new ResponseEntity<>(response, httpStatus);
	}

	/**
	 * This <code>merchantGatewayBatchDataApi</code> method is used to expose the merchant gateway batch data API.
	 *
	 * @param httpHeaders
	 * @param merchantGatewayBatchDataApiRequest
	 * @param servletRequest
	 * @return
	 */
	@PostMapping(BATCH_DATA_MERCHANTGATEWAY)
	public ResponseEntity<BatchDataApiResponse> merchantGatewayBatchDataApi(@RequestHeader final HttpHeaders httpHeaders,
			@RequestBody(required = false) final MerchantGatewayBatchDataApiRequest merchantGatewayBatchDataApiRequest,
			final HttpServletRequest servletRequest) {

		LOGGER.info(" ==> Method ==> merchantGatewayBatchDataApi ==> Enter");

		final BatchDataApiResponse response = new BatchDataApiResponse();
		Boolean isCommonError = Boolean.FALSE;
		HttpStatus httpStatus = HttpStatus.OK;

		try {

			if (merchantGatewayBatchDataApiRequest != null) {

				final MerchantRelatedRequestData merchantRelatedRequestData = new MerchantRelatedRequestData();
				merchantRelatedRequestData.setRequestType(ApiRequestTypeEnum.MERCHANT_GATEWAY_BATCH_DATA.requestType());
				merchantRelatedRequestData.setRequestClass(MerchantGatewayBatchDataApiRequest.class.getName());
				merchantRelatedRequestData.setRequestData(ApplicationUtils.generateJSONFromObject(merchantGatewayBatchDataApiRequest));

				// Validate the request header
				optimiserBatchDataUtility.validateBatchDataRequestHeader(httpHeaders, response);

				// Validate the request body
				optimiserBatchDataUtility.validateRequestBodyForBatchDataApi(response, merchantGatewayBatchDataApiRequest,
						merchantGatewayBatchDataApiRequest.getData());

				// Add the request data to MerchantRelatedRequestData table
				addMerchantRelatedRequestData(response, merchantRelatedRequestData, servletRequest);

				if (!ApplicationUtils.isValid(response.getErrorMessage())) {

					// Authenticate the batch data request
					optimiserBatchDataUtility.authenticateBatchDataRequest(httpHeaders, response);

					if (!ApplicationUtils.isValid(response.getErrorMessage())) {

						// If above all the scenario get valid means data received successfully and set status = RECEIVED
						response.setStatus(RequestResponseStatusEnum.RECEIVED.status());

					} else {
						httpStatus = HttpStatus.UNAUTHORIZED;
					}

				} else {
					httpStatus = HttpStatus.BAD_REQUEST;
				}

			} else {
				httpStatus = HttpStatus.BAD_REQUEST;
				response.setErrorMessage(
						new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_BADREQUEST_ERROR_MESSAGE.message(), null, null))));
			}

		} catch (final Exception e) {
			logError(LOGGER, null, e, " ==> Method ==> merchantGatewayBatchDataApi ==> Exception ==> ");
			LOGGER.info(" ==> Method ==> merchantGatewayBatchDataApi ==> Exception : " + e);
			isCommonError = Boolean.TRUE;
		}

		if (isCommonError) {
			response.setErrorMessage(new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))));
		}

		// Update and set the response of request
		updateAndSetResponseOfRequest(response);

		LOGGER.info(" ==> Method ==> merchantGatewayBatchDataApi ==> Exit");
		return new ResponseEntity<>(response, httpStatus);
	}

	/**
	 * This method <code>addMerchantRelatedRequestData</code> is used for add the merchant related request data details into database.
	 *
	 * @param response
	 * @param merchantRelatedRequestData
	 * @param servletRequest
	 */
	private void addMerchantRelatedRequestData(final BatchDataApiResponse response, final MerchantRelatedRequestData merchantRelatedRequestData,
			final HttpServletRequest servletRequest) {

		LOGGER.info(" ==> Method ==> addMerchantRelatedRequestData ==> Enter");

		merchantRelatedRequestData.setStatus(RequestStatusEnum.PROCESSING.status());

		if (ApplicationUtils.isEmpty(servletRequest.getHeader(REQUEST_GETHEADER_LABEL))) {
			merchantRelatedRequestData.setIpAddress(servletRequest.getRemoteAddr());
		} else {
			merchantRelatedRequestData.setIpAddress(servletRequest.getHeader(REQUEST_GETHEADER_LABEL));
		}

		merchantRelatedRequestData.setMerchantRequestDataId(UUID.randomUUID());
		merchantRelatedRequestData.setUserId(response.getUserId());
		merchantRelatedRequestData.setOperationType(response.getOperationType());
		merchantRelatedRequestData.setRequestDate(ApplicationUtils.getCurrentTimeStamp());

		response.setId(String.valueOf(merchantRelatedRequestData.getMerchantRequestDataId()));
		response.setRequestDate(merchantRelatedRequestData.getRequestDate());

		getServiceRegistry().getMerchantRelatedRequestDataService().addMerchantRelatedRequestData(merchantRelatedRequestData);

		LOGGER.info(" ==> Method ==> addMerchantRelatedRequestData ==> Exit");
	}

	/**
	 * This method <code>updateAndSetResponseOfRequest</code> is used for update and set the response of request to database.
	 *
	 * @param response
	 */
	private void updateAndSetResponseOfRequest(final BatchDataApiResponse response) {

		LOGGER.info(" ==> Method ==> updateAndSetResponseOfRequest ==> Enter");

		if (RequestResponseStatusEnum.RECEIVED.status().equalsIgnoreCase(response.getStatus())) {
			response.setStatus(RequestResponseStatusEnum.RECEIVED.status());
		} else {
			response.setStatus(RequestResponseStatusEnum.FAILED.status());
		}

		if (response != null && !ApplicationUtils.isEmpty(response.getId())) {

			final MerchantRelatedRequestData merchantRelatedRequestData = getServiceRegistry().getMerchantRelatedRequestDataService()
					.findById(UUID.fromString(response.getId()));

			if (merchantRelatedRequestData != null) {

				merchantRelatedRequestData.setUserId(response.getUserId());
				merchantRelatedRequestData.setStatus(response.getStatus());
				merchantRelatedRequestData.setOperationType(response.getOperationType());
				merchantRelatedRequestData.setResponseClass(BatchDataApiResponse.class.getName());
				merchantRelatedRequestData.setResponseData(ApplicationUtils.generateJSONFromObject(response));
				merchantRelatedRequestData.setResponseDate(ApplicationUtils.getCurrentTimeStamp());

				getServiceRegistry().getMerchantRelatedRequestDataService().updateMerchantRelatedRequestData(merchantRelatedRequestData);
			}
		}

		LOGGER.info(" ==> Method ==> updateAndSetResponseOfRequest ==> Exit");
	}
}
