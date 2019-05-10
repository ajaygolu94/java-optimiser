/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.omg.CORBA.portable.UnknownException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Repository;

import uk.co.xcordis.optimiser.controller.BaseController;
import uk.co.xcordis.optimiser.dto.BaseBatchDataApiRequest;
import uk.co.xcordis.optimiser.dto.BatchDataApiResponse;
import uk.co.xcordis.optimiser.model.AccessToken;

/**
 * The <code>OptimiserBatchDataUtility</code> class contains all the methods that serve as a batch data utility purpose in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Repository
public class OptimiserBatchDataUtility extends BaseController implements ApplicationConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(OptimiserBatchDataUtility.class);

	/**
	 * This <code>authenticateBatchDataRequest</code> method is used to authenticate the batch data request from database.
	 *
	 * @param httpHeaders
	 * @param errors
	 */
	public void authenticateBatchDataRequest(final HttpHeaders httpHeaders, final BatchDataApiResponse response) {

		LOGGER.info(" ==> Method ==> authenticateBatchDataRequest ==> Called");

		final AccessToken accessToken = getServiceRegistry().getAccessTokenService().getAccessTokenByOpenIdAndToken(httpHeaders.get(USERID_LABLE).get(0),
				httpHeaders.get(ACCESSTOKEN_LABEL).get(0));

		if (accessToken == null) {
			response.getErrorMessage().add(getMessageSource().getMessage(ErrorDataEnum.AUTHENTICATION_ERROR.message(), null, null));
		} else if (accessToken != null && !ApplicationUtils.isEmpty(accessToken.getActive()) && !accessToken.getActive()) {
			response.getErrorMessage()
					.add(getMessageSource().getMessage(ErrorDataEnum.REQUEST_HEADER_ACCESSTOKEN_INACTIVE_ERROR_MESSAGE.message(), null, null));
		} else if (accessToken != null && !ApplicationUtils.isEmpty(accessToken.getExpirydate())
				&& getServiceRegistry().getAccessTokenService().isAccessTokenExpired(accessToken.getExpirydate())) {
			response.getErrorMessage().add(getMessageSource().getMessage(ErrorDataEnum.REQUEST_HEADER_ACCESSTOKEN_EXPIRED_ERROR_MESSAGE.message(), null, null));
		}
	}

	/**
	 * This method <code>validateBatchDataRequestHeader</code> is used for validate the batch data request header.
	 *
	 * @param httpHeaders
	 * @param response
	 */
	public void validateBatchDataRequestHeader(final HttpHeaders httpHeaders, final BatchDataApiResponse response) {

		LOGGER.info(" ==> Method ==> validateBatchDataRequestHeader ==> Called");

		if (!ApplicationUtils.isValid(response.getErrorMessage())) {
			response.setErrorMessage(new HashSet<>());
		}

		if (!ApplicationUtils.isValid(httpHeaders.get(USERID_LABLE))) {
			response.getErrorMessage().add(getMessageSource().getMessage(ErrorDataEnum.REQUEST_HEADER_USERID_MISSING_ERROR_MESSAGE.message(), null, null));
		} else {
			response.setUserId(httpHeaders.get(USERID_LABLE).get(0));
		}

		if (!ApplicationUtils.isValid(httpHeaders.get(ACCESSTOKEN_LABEL))) {
			response.getErrorMessage().add(getMessageSource().getMessage(ErrorDataEnum.REQUEST_HEADER_ACCESSTOKEN_MISSING_ERROR_MESSAGE.message(), null, null));
		}
	}

	/**
	 * This method <code>validateRequestBodyForBatchDataApi</code> is used for validate the request body for batch data api.
	 *
	 * @param response
	 * @param object
	 * @param batchData
	 */
	public void validateRequestBodyForBatchDataApi(final BatchDataApiResponse response, final Object object, final List<?> batchData) {

		LOGGER.info(" ==> Method ==> validateRequestBodyForBatchDataApi ==> Enter");

		final BaseBatchDataApiRequest baseBatchDataApiRequest = new BaseBatchDataApiRequest();
		BeanUtils.copyProperties(object, baseBatchDataApiRequest);

		if (ApplicationUtils.isEmpty(baseBatchDataApiRequest.getOperationType())) {

			response.getErrorMessage().add(getMessageSource().getMessage(ErrorDataEnum.COMMON_MISSING_FIELD.message(),
					new Object[] { getMessageSource().getMessage(ErrorDataEnum.BATCH_DATA_API_OPERATIONTYPE_LABEL.message(), null, null) }, null));

		} else {

			response.setOperationType(baseBatchDataApiRequest.getOperationType());

			if (BatchDataOperationTypeEnum.findByOperationType(baseBatchDataApiRequest.getOperationType()) == null) {
				response.getErrorMessage()
						.add(getMessageSource().getMessage(ErrorDataEnum.BATCH_DATA_API_OPERATIONTYPE_INVALID_ERROR_MESSAGE.message(), null, null));
			}
		}

		if (ApplicationUtils.isEmpty(baseBatchDataApiRequest.getReferenceId())) {

			response.getErrorMessage().add(getMessageSource().getMessage(ErrorDataEnum.COMMON_MISSING_FIELD.message(),
					new Object[] { getMessageSource().getMessage(ErrorDataEnum.BATCH_DATA_API_REFERENCEID_LABEL.message(), null, null) }, null));
		} else {
			response.setReferenceId(baseBatchDataApiRequest.getReferenceId());
		}

		if (ApplicationUtils.isEmpty(baseBatchDataApiRequest.getNotificationUrl())) {

			response.getErrorMessage().add(getMessageSource().getMessage(ErrorDataEnum.COMMON_MISSING_FIELD.message(),
					new Object[] { getMessageSource().getMessage(ErrorDataEnum.BATCH_DATA_API_NOTIFICATIONURL_LABEL.message(), null, null) }, null));
		}

		// validate the batch data list from request body
		validateBatchDataList(response, batchData);

		LOGGER.info(" ==> Method ==> validateRequestBodyForBatchDataApi ==> Exit");
	}

	/**
	 * This method <code>validateBatchDataList</code> is used for validate the batch data list from request body.
	 *
	 * @param response
	 * @param batchData
	 */
	private void validateBatchDataList(final BatchDataApiResponse response, final List<?> batchData) {

		if (!ApplicationUtils.isValid(batchData)) {

			response.getErrorMessage().add(getMessageSource().getMessage(ErrorDataEnum.COMMON_MISSING_FIELD.message(),
					new Object[] { getMessageSource().getMessage(ErrorDataEnum.BATCH_DATA_API_BATCHDATA_LABEL.message(), null, null) }, null));
		} else {

			for (final Object batchDataObject : batchData) {

				// get the field name and field value from object in map<k,v> collection
				final Map<String, Object> fieldValueMap = getFieldNamesAndValuesFromObject(batchDataObject);

				fieldValueMap.forEach((field, value) -> {

					if (!GATEWAY_PARAMETER_LABEL.equalsIgnoreCase(field) && (value == null || value == "")) {

						response.getErrorMessage()
								.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_MISSING_FIELD.message(), new Object[] { field }, null));
					}
				});
			}
		}
	}

	/**
	 * This method <code>getFieldNamesAndValuesFromObject</code> is used for return the all field name and field value from object in map<k,v> collection.
	 *
	 * @param contentObject
	 * @return
	 */
	private Map<String, Object> getFieldNamesAndValuesFromObject(final Object contentObject) {

		LOGGER.info(" ==> Method ==> getFieldNamesAndValuesFromObject ==> Enter");

		final Map<String, Object> fieldMap = new HashMap<>();

		try {

			final Class<? extends Object> c1 = contentObject.getClass();

			final Field[] valueObjFields = c1.getDeclaredFields();

			// compare values now
			for (final Field valueObjField : valueObjFields) {

				final String fieldName = valueObjField.getName();

				valueObjField.setAccessible(true);

				final Object fieldValue = valueObjField.get(contentObject);

				fieldMap.put(fieldName, fieldValue);
			}

		} catch (IllegalArgumentException | IllegalAccessException e) {
			LOGGER.error(" ==> Method : getFieldNamesAndValuesFromObject ==> Exception ==> " + e);
			throw new UnknownException(e);
		}

		LOGGER.info(" ==> Method ==> getFieldNamesAndValuesFromObject ==> Exit");
		return fieldMap;
	}

}
