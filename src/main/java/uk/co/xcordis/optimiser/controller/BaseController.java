/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Set;

import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import uk.co.xcordis.optimiser.dto.UIOperationResponse;
import uk.co.xcordis.optimiser.util.ApplicationConstants;
import uk.co.xcordis.optimiser.util.ApplicationUtils;
import uk.co.xcordis.optimiser.util.ErrorDataEnum;
import uk.co.xcordis.optimiser.util.RequestResponseStatusEnum;
import uk.co.xcordis.optimiser.util.ServiceRegistry;
import uk.co.xcordis.optimiser.util.UIResponseCodeEnum;

/**
 * The <code>BaseController</code> class responsible for extended by all controller for get serviceRegistry reference in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Controller
@DependsOn(ApplicationConstants.APPLICATIONINITIALIZER_LABEL)
public class BaseController {

	@Autowired
	private ServiceRegistry serviceRegistry;

	@Autowired
	private MessageSource messageSource;

	/**
	 * @return the serviceRegistry
	 */
	public ServiceRegistry getServiceRegistry() {

		return serviceRegistry;
	}

	/**
	 * @return the messageSource
	 */
	public MessageSource getMessageSource() {

		return messageSource;
	}

	/**
	 * This <code>logError</code> method is use to log error into database.
	 *
	 * @param LOGGER
	 * @param identifier
	 * @param exception
	 * @param message
	 */
	protected void logError(final Logger LOGGER, final String identifier, final Exception exception, String message) {

		ThreadContext.put(ApplicationConstants.LOGGER_THREAD_CONTEXT_IDENTIFIER_LABEL, identifier);

		if (exception != null) {

			final StringWriter errors = new StringWriter();
			exception.printStackTrace(new PrintWriter(errors));
			message += errors.toString();
		}

		LOGGER.error(ApplicationConstants.MARKER, message);
		ThreadContext.clearAll();
	}

	/**
	 * This <code>getUiOperationResponse<code> is used for send the generic OR common ui response.
	 *
	 * @param status
	 * @param message
	 * @param data
	 * @return
	 */
	public UIOperationResponse getUiOperationResponse(final String status, final Set<String> message, final Object data, final String code) {

		final UIOperationResponse uiOperationResponse = new UIOperationResponse();

		uiOperationResponse.setMessages(message);
		uiOperationResponse.setRequestDate(ApplicationUtils.getCurrentTimeStamp());
		uiOperationResponse.setStatus(status);
		uiOperationResponse.setResponsedata(data);
		uiOperationResponse.setCode(code);

		return uiOperationResponse;
	}

	/**
	 * This <code>getMessageByCode</code> method is use to get the operation message by code.
	 *
	 * @param code
	 * @param moduleName
	 * @return
	 */
	public String getMessageByCode(final String code, final String moduleName) {

		String message = null;

		try {

			switch (code) {

				case "201":
					message = moduleName + getMessageSource().getMessage(ErrorDataEnum.ADD_SUCCESS_MESSAGE.message(), null, null);
					break;

				case "202":
					message = moduleName + getMessageSource().getMessage(ErrorDataEnum.EDIT_SUCCESS_MESSAGE.message(), null, null);
					break;

				case "203":
					message = moduleName + getMessageSource().getMessage(ErrorDataEnum.DELETE_SUCCESS_MESSAGE.message(), null, null);
					break;

				default:
					message = getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null);
					break;
			}

		} catch (final Exception e) {
			message = getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null);
		}

		return message;
	}

	/**
	 * This <code>commonErrorResponse</code> method is used to set common error response details.
	 *
	 * @param uiOperationResponse
	 * @param errorMessage
	 * @param httpStatus
	 * @return
	 */
	public ResponseEntity<UIOperationResponse> commonErrorResponse(final UIOperationResponse uiOperationResponse, final Set<String> errorMessage,
			final HttpStatus httpStatus) {

		uiOperationResponse.setMessages(errorMessage);
		uiOperationResponse.setCode(UIResponseCodeEnum.FAILED_MESSAGE.getCode());
		uiOperationResponse.setStatus(RequestResponseStatusEnum.FAILED.status());
		return new ResponseEntity<>(uiOperationResponse, httpStatus);

	}

}