/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import uk.co.xcordis.optimiser.controller.BaseController;
import uk.co.xcordis.optimiser.dto.UIOperationResponse;
import uk.co.xcordis.optimiser.model.AccessToken;
import uk.co.xcordis.optimiser.model.ApplicationUser;

/**
 * The <code>OptimiserUtility</code> class contains all the methods that serve as a Utility purpose in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Repository
public class OptimiserUtility extends BaseController implements ApplicationURIConstants, ApplicationConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(OptimiserUtility.class);

	@Autowired
	private CommonConfigParam commonConfigParam;

	/**
	 * This method <code>authenticateRequest</code> is used for authenticate the rest api request.
	 *
	 * @param httpServletRequest
	 * @param uiOperationResponse
	 * @return
	 */
	public Boolean authenticateRequest(final HttpServletRequest httpServletRequest, final UIOperationResponse uiOperationResponse) {

		LOGGER.info(" ==> Method ==> authenticateRequest ==> Called");

		try {

			// Validate the request header field
			final List<String> errors = validateRequestHeader(httpServletRequest);

			if (!ApplicationUtils.isValid(errors)) {

				// Authenticate the header request
				validateAuthenticateRequest(httpServletRequest, errors);

				if (!ApplicationUtils.isValid(errors)) {

					// If error is empty means authentication success then authenticate the request url based on role
					return authenticateApiUrlByRole(httpServletRequest, uiOperationResponse);

				} else {
					// If error is not empty means authentication failed
					uiOperationResponse.setStatus(RequestResponseStatusEnum.FAILED.status());
					uiOperationResponse.setCode(RequestResponseCodeEnum.AUTHENTICATIONERROR.code());
					uiOperationResponse.setMessages(new HashSet<>(errors));
					return Boolean.FALSE;
				}

			} else {
				uiOperationResponse.setStatus(RequestResponseStatusEnum.FAILED.status());
				uiOperationResponse.setCode(RequestResponseCodeEnum.VALIDATIONERROR.code());
				uiOperationResponse.setMessages(new HashSet<>(errors));
				return Boolean.FALSE;
			}

		} catch (final Exception e) {
			uiOperationResponse.setStatus(RequestResponseStatusEnum.FAILED.status());
			uiOperationResponse.setCode(RequestResponseCodeEnum.INTERNALSERVERERROR.code());
			uiOperationResponse
					.setMessages(new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))));
			return Boolean.FALSE;
		}
	}

	/**
	 * This method <code>authenticateApiUrlByRole</code> is used for authenticate request url based on user role.
	 *
	 * @param httpServletRequest
	 * @param uiOperationResponse
	 * @return
	 */
	private Boolean authenticateApiUrlByRole(final HttpServletRequest httpServletRequest, final UIOperationResponse uiOperationResponse) {

		LOGGER.info(" ==> Method ==> authenticateApiUrlByRole ==> Called");

		Boolean result = Boolean.FALSE;

		final String requestURI = httpServletRequest.getRequestURI();

		// if authentication success then authenticate request url based on role
		final ApplicationUser applicationUser = getServiceRegistry().getUserService().getUserByOpenId(httpServletRequest.getHeader(USERID_LABLE));

		if (applicationUser != null && !ApplicationUtils.isEmpty(applicationUser.getRole())) {

			if (ApplicationConstants.ROLE_ADMIN.equalsIgnoreCase(applicationUser.getRole())) {

				if (authenticateRequestUrl(requestURI, commonConfigParam.getAdminRoleUrls())) {
					// authentication success
					result = Boolean.TRUE;
				}

			} else if (ApplicationConstants.ROLE_MERCHANT.equalsIgnoreCase(applicationUser.getRole())) {

				if (authenticateRequestUrl(requestURI, commonConfigParam.getMerchantRoleUrls())) {
					// authentication success
					result = Boolean.TRUE;
				}

			} else if (ApplicationConstants.ROLE_MERCHANT_MANAGER.equalsIgnoreCase(applicationUser.getRole())) {

				if (authenticateRequestUrl(requestURI, commonConfigParam.getMerchantManagerRoleUrls())) {
					// authentication success
					result = Boolean.TRUE;
				}

			} else {
				result = Boolean.FALSE;
			}

			if (!result) {
				// authentication failed - access denied response
				result = accessDeniedResponse(uiOperationResponse);
			}

		} else {
			// application user not found
			uiOperationResponse.setStatus(RequestResponseStatusEnum.FAILED.status());
			uiOperationResponse.setCode(RequestResponseCodeEnum.AUTHENTICATIONERROR.code());
			uiOperationResponse.setMessages(new HashSet<>(
					Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.REQUEST_HEADER_USER_NOT_FOUND_ERROR_MESSAGE.message(), null, null))));
			result = Boolean.FALSE;
		}

		return result;
	}

	/**
	 * This method <code>accessDeniedResponse</code> is used for return the access denied generic response.
	 *
	 * @param uiOperationResponse
	 * @return
	 */
	private Boolean accessDeniedResponse(final UIOperationResponse uiOperationResponse) {

		LOGGER.info(" ==> Method ==> accessDeniedResponse ==> Called");

		uiOperationResponse.setStatus(RequestResponseStatusEnum.FAILED.status());
		uiOperationResponse.setCode(RequestResponseCodeEnum.AUTHENTICATIONERROR.code());
		uiOperationResponse.setMessages(new HashSet<>(
				Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.REQUEST_URL_USER_NOT_ACCESS_RIGHTS_ERROR_MESSAGE.message(), null, null))));
		return Boolean.FALSE;
	}

	/**
	 * This method <code>authenticateRequestUrl</code> is used for check the whether request url is exists or not.
	 *
	 * @param requestURI
	 * @return
	 */
	private Boolean authenticateRequestUrl(final String requestURI, final List<String> restApiUrlList) {

		Boolean allowedRequest = Boolean.FALSE;

		restApiUrlLabel: for (String restApiUrl : restApiUrlList) {

			if (restApiUrl.contains("{")) {
				restApiUrl = restApiUrl.substring(0, restApiUrl.indexOf("{"));
			}

			if (requestURI.contains(restApiUrl)) {
				allowedRequest = true;
				break restApiUrlLabel;
			}
		}
		return allowedRequest;
	}

	/**
	 * This <code>validateAuthenticateRequest</code> method is used to authenticate the request header from database.
	 *
	 * @param httpServletRequest
	 * @param errors
	 */
	private void validateAuthenticateRequest(final HttpServletRequest httpServletRequest, final List<String> errors) {

		LOGGER.info(" ==> Method ==> validateAuthenticateRequest ==> Called");

		final AccessToken accessToken = getServiceRegistry().getAccessTokenService().getAccessTokenByOpenIdAndToken(httpServletRequest.getHeader(USERID_LABLE),
				httpServletRequest.getHeader(ACCESSTOKEN_LABEL));

		if (accessToken == null) {
			errors.add(getMessageSource().getMessage(ErrorDataEnum.AUTHENTICATION_ERROR.message(), null, null));
		} else if (accessToken != null && !ApplicationUtils.isEmpty(accessToken.getActive()) && !accessToken.getActive()) {
			errors.add(getMessageSource().getMessage(ErrorDataEnum.REQUEST_HEADER_ACCESSTOKEN_INACTIVE_ERROR_MESSAGE.message(), null, null));
		} else if (accessToken != null && !ApplicationUtils.isEmpty(accessToken.getExpirydate())
				&& getServiceRegistry().getAccessTokenService().isAccessTokenExpired(accessToken.getExpirydate())) {
			errors.add(getMessageSource().getMessage(ErrorDataEnum.REQUEST_HEADER_ACCESSTOKEN_EXPIRED_ERROR_MESSAGE.message(), null, null));
		}
	}

	/**
	 * This method <code>validateRequestHeader</code> is used for validate the request header field.
	 *
	 * @param httpServletRequest
	 * @return
	 */
	private List<String> validateRequestHeader(final HttpServletRequest httpServletRequest) {

		LOGGER.info(" ==> Method ==> validateRequestHeader ==> Enter");

		final List<String> errors = new ArrayList<>();

		if (httpServletRequest.getHeader(USERID_LABLE) == null) {
			errors.add(getMessageSource().getMessage(ErrorDataEnum.REQUEST_HEADER_USERID_MISSING_ERROR_MESSAGE.message(), null, null));
		}

		if (httpServletRequest.getHeader(ACCESSTOKEN_LABEL) == null) {
			errors.add(getMessageSource().getMessage(ErrorDataEnum.REQUEST_HEADER_ACCESSTOKEN_MISSING_ERROR_MESSAGE.message(), null, null));
		}

		LOGGER.info(" ==> Method ==> validateRequestHeader ==> Exit");
		return errors;
	}

	/**
	 * This method <code>authenticateMerchantId</code> is used for authenticate the merchant whether merchant is exist or not in database.
	 *
	 * @param merchantId
	 * @param errors
	 * @return
	 */
	public List<String> authenticateMerchantId(final String merchantId, final List<String> errors) {

		LOGGER.info(" ==> Method ==> authenticateMerchantId ==> Called");

		if (ApplicationUtils.isEmpty(merchantId)) {
			errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_BLANK.message(), null, null)
					+ getMessageSource().getMessage(ErrorDataEnum.MERCHANT_ID_ERROR.message(), null, null));
		} else {
			if (getServiceRegistry().getMerchantService().getMerchantByMerchantId(UUID.fromString(merchantId)) == null) {
				errors.add(getMessageSource().getMessage(ErrorDataEnum.MERCHANT_ID_INVALID_ERROR_MESSAGE.message(), null, null));
			}
		}

		return errors;
	}

}
