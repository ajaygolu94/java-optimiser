/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.restcontroller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import uk.co.xcordis.optimiser.controller.BaseController;
import uk.co.xcordis.optimiser.dto.ChooseGatewayRequest;
import uk.co.xcordis.optimiser.dto.ChooseGatewayResponse;
import uk.co.xcordis.optimiser.dto.GenerateAccessTokenResponse;
import uk.co.xcordis.optimiser.model.AccessToken;
import uk.co.xcordis.optimiser.model.ApplicationUser;
import uk.co.xcordis.optimiser.model.Merchant;
import uk.co.xcordis.optimiser.model.RequestData;
import uk.co.xcordis.optimiser.rules.ChooseGatewayRuleEngine;
import uk.co.xcordis.optimiser.util.ApiRequestTypeEnum;
import uk.co.xcordis.optimiser.util.ApplicationConstants;
import uk.co.xcordis.optimiser.util.ApplicationURIConstants;
import uk.co.xcordis.optimiser.util.ApplicationUtils;
import uk.co.xcordis.optimiser.util.CardType;
import uk.co.xcordis.optimiser.util.ErrorDataEnum;
import uk.co.xcordis.optimiser.util.RequestResponseCodeEnum;
import uk.co.xcordis.optimiser.util.RequestResponseStatusEnum;
import uk.co.xcordis.optimiser.util.RequestStatusEnum;

/**
 * The <code>OptimiserCoreController</code> class responsible for expose API for choose gateway in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@RestController
public class OptimiserCoreController extends BaseController implements ApplicationConstants, ApplicationURIConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(OptimiserCoreController.class);

	@Autowired
	private ChooseGatewayRuleEngine chooseGatewayRuleEngine;

	/**
	 * This <code>chooseGateway</code> method is use to choose gateway base on the parameters.
	 *
	 * @param merchantId
	 * @param accessToken
	 * @param gatewayRequest
	 * @param servletRequest
	 * @return
	 */
	@PostMapping(CHOOSE_GATEWAY_API_URL)
	public ResponseEntity<ChooseGatewayResponse> chooseGateway(@RequestHeader(value = MERCHANTID_LABLE, required = false) final String merchantId,
			@RequestHeader(value = ACCESSTOKEN_LABEL, required = false) final String accessToken,
			@RequestBody(required = false) final ChooseGatewayRequest gatewayRequest, final HttpServletRequest servletRequest) {

		LOGGER.info(" ==> Method ==> chooseGateway ==> Enter");

		final ChooseGatewayResponse response = new ChooseGatewayResponse();
		ResponseEntity<ChooseGatewayResponse> responseEntity;
		Boolean isCommonError = Boolean.FALSE;

		try {

			if (gatewayRequest != null) {

				gatewayRequest.setAccessToken(accessToken);
				gatewayRequest.setMerchantId(merchantId);
				response.setMerchantId(merchantId);

				final RequestData requestData = new RequestData();
				requestData.setRequestType(ApiRequestTypeEnum.CHOOSE_GATEWAY_API.requestType());
				requestData.setStatus(RequestStatusEnum.PROCESSING.status());
				requestData.setSourceMerchantId(merchantId);
				requestData.setRequestDataId(UUID.randomUUID());

				addRequestDetails(gatewayRequest, servletRequest, requestData);
				authenticateRequest(response, merchantId, accessToken);

				if (!ApplicationUtils.isValid(response.getMessages())) {
					validateAndProcessRequest(response, gatewayRequest);
				}

			} else {
				response.setCode(RequestResponseCodeEnum.VALIDATIONERROR.code());
				response.setMessages(
						new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_BADREQUEST_ERROR_MESSAGE.message(), null, null))));
			}

		} catch (final Exception e) {
			logError(LOGGER, merchantId, e, " ==> Method ==> chooseGateway ==> Exception ==> ");
			isCommonError = Boolean.TRUE;
		}

		if (isCommonError) {

			response.setCode(RequestResponseCodeEnum.INTERNALSERVERERROR.code());
			response.setMessages(new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))));
		}

		if (RequestResponseCodeEnum.SUCCESS.code().equalsIgnoreCase(response.getCode())) {
			response.setStatus(RequestResponseStatusEnum.SUCCESS.status());
		} else {
			response.setStatus(RequestResponseStatusEnum.FAILED.status());
		}

		updateAndSetResponseOfRequest(response, gatewayRequest, servletRequest);
		responseEntity = setHTTPStatusForChooseGateway(response, gatewayRequest, servletRequest);

		LOGGER.info(" ==> Method ==> chooseGateway ==> Exit");
		return responseEntity;
	}

	/**
	 * This <code>generateAccessTokenApi</code> method is used to generate the access token for accessing other API.
	 *
	 * @param httpHeaders
	 * @param servletRequest
	 * @return
	 */
	@PostMapping(GENERATE_ACCESSTOKEN_API_URL)
	public ResponseEntity<GenerateAccessTokenResponse> generateAccessTokenApi(@RequestHeader final HttpHeaders httpHeaders,
			final HttpServletRequest servletRequest) {

		LOGGER.info(" ==> Method ==> generateAccessTokenApi ==> Enter");

		final GenerateAccessTokenResponse response = new GenerateAccessTokenResponse();
		final RequestData requestData = new RequestData();
		HttpStatus httpStatus = HttpStatus.OK;

		try {

			requestData.setRequestDataId(UUID.randomUUID());
			requestData.setRequestType(ApiRequestTypeEnum.GENERATE_ACCESSTOKEN_API.requestType());
			requestData.setStatus(RequestStatusEnum.PROCESSING.status());

			if (ApplicationUtils.isEmpty(servletRequest.getHeader(REQUEST_GETHEADER_LABEL))) {
				requestData.setIpaddress(servletRequest.getRemoteAddr());
			} else {
				requestData.setIpaddress(servletRequest.getHeader(REQUEST_GETHEADER_LABEL));
			}

			final Map<String, String> requestHeaderMap = new HashMap<>();

			// Validate the Generate access token request header
			validateGenerateAccessTokenRequest(httpHeaders, response, requestHeaderMap);

			requestData.setRequestDate(ApplicationUtils.getCurrentTimeStamp());
			requestData.setRequestData(ApplicationUtils.generateJSONFromObject(requestHeaderMap));
			getServiceRegistry().getRequestDataService().addRequestData(requestData);

			if (!ApplicationUtils.isValid(response.getMessages()) && !requestHeaderMap.isEmpty()) {

				// Authenticate the generate access token request
				if (getServiceRegistry().getAccessTokenService().authenticateGenerateAccessTokenRequest(requestHeaderMap.get(USERID_LABLE),
						requestHeaderMap.get(EMAIL_LABEL), requestHeaderMap.get(SECRET_KEY_LABEL))) {

					// Generate Access Token process
					if (getServiceRegistry().getAccessTokenService().generateAccessToken(response.getUserId())) {

						final AccessToken accessToken = getServiceRegistry().getAccessTokenService().findById(response.getUserId());

						if (accessToken != null) {
							response.setStatus(RequestResponseStatusEnum.SUCCESS.status());
							response.setAccessToken(accessToken.getAccesstoken());
							response.setExpiryDate(accessToken.getExpirydate());
						}
					}

				} else {
					httpStatus = HttpStatus.UNAUTHORIZED;
					response.getMessages().add(getMessageSource().getMessage(ErrorDataEnum.AUTHENTICATION_ERROR.message(), null, null));
				}

			} else {
				httpStatus = HttpStatus.BAD_REQUEST;
			}

		} catch (final Exception e) {
			logError(LOGGER, response.getUserId(), e, " ==> Method ==> generateAccessTokenApi ==> Exception ==> ");
			response.setMessages(new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))));
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		if (RequestResponseStatusEnum.SUCCESS.status().equalsIgnoreCase(response.getStatus())) {
			response.setStatus(RequestResponseStatusEnum.SUCCESS.status());
		} else {
			response.setStatus(RequestResponseStatusEnum.FAILED.status());
		}

		updateRequestData(response, requestData);

		LOGGER.info(" ==> Method ==> generateAccessTokenApi ==> Exit");
		return new ResponseEntity<>(response, httpStatus);
	}

	/**
	 * This method <code>validateGenerateAccessTokenRequest</code> is used for validate the generate access token api request.
	 *
	 * @param httpHeaders
	 * @param response
	 * @param requestHeaderMap
	 */
	private void validateGenerateAccessTokenRequest(final HttpHeaders httpHeaders, final GenerateAccessTokenResponse response,
			final Map<String, String> requestHeaderMap) {

		if (!ApplicationUtils.isValid(response.getMessages())) {
			response.setMessages(new HashSet<>());
		}

		if (!ApplicationUtils.isValid(httpHeaders.get(USERID_LABLE))) {
			response.getMessages().add(getMessageSource().getMessage(ErrorDataEnum.REQUEST_HEADER_USERID_MISSING_ERROR_MESSAGE.message(), null, null));
		} else {
			response.setUserId(httpHeaders.get(USERID_LABLE).get(0));
			requestHeaderMap.put(USERID_LABLE, httpHeaders.get(USERID_LABLE).get(0));
		}

		if (!ApplicationUtils.isValid(httpHeaders.get(SECRET_KEY_LABEL))) {
			response.getMessages().add(getMessageSource().getMessage(ErrorDataEnum.REQUEST_HEADER_SECRETKEY_MISSING_ERROR_MESSAGE.message(), null, null));
		} else {
			requestHeaderMap.put(SECRET_KEY_LABEL, httpHeaders.get(SECRET_KEY_LABEL).get(0));
		}

		if (!ApplicationUtils.isValid(httpHeaders.get(EMAIL_LABEL))) {
			response.getMessages().add(getMessageSource().getMessage(ErrorDataEnum.REQUEST_HEADER_EMAIL_MISSING_ERROR_MESSAGE.message(), null, null));
		} else {
			requestHeaderMap.put(EMAIL_LABEL, httpHeaders.get(EMAIL_LABEL).get(0));
		}
	}

	private void updateRequestData(final GenerateAccessTokenResponse response, final RequestData requestData) {

		response.setId(String.valueOf(requestData.getRequestDataId()));
		response.setRequestDate(requestData.getRequestDate());

		requestData.setResponseStatus(response.getStatus());
		requestData.setStatus(RequestStatusEnum.SENT.status());
		requestData.setResponseDate(ApplicationUtils.getCurrentTimeStamp());
		requestData.setResponseData(ApplicationUtils.generateJSONFromObject(response));

		getServiceRegistry().getRequestDataService().updateRequestData(requestData);
	}

	/**
	 * This <code>addRequestDetails</code> method is use to add choose gateway request details into database.
	 *
	 * @param gatewayRequest
	 * @param servletRequest
	 * @param requestData
	 */
	private void addRequestDetails(final ChooseGatewayRequest gatewayRequest, final HttpServletRequest servletRequest, final RequestData requestData) {

		LOGGER.info(" ==> Method ==> addRequestDetails ==> Enter");

		if (ApplicationUtils.isEmpty(servletRequest.getHeader(REQUEST_GETHEADER_LABEL))) {
			requestData.setIpaddress(servletRequest.getRemoteAddr());
		} else {
			requestData.setIpaddress(servletRequest.getHeader(REQUEST_GETHEADER_LABEL));
		}

		requestData.setRequestDate(ApplicationUtils.getCurrentTimeStamp());
		requestData.setRequestData(ApplicationUtils.generateJSONFromObject(gatewayRequest));
		gatewayRequest.setOptimiserRequestDataId(requestData.getRequestDataId());
		getServiceRegistry().getRequestDataService().addRequestData(requestData);

		LOGGER.info(" ==> Method ==> addRequestDetails ==> Exit");
	}

	/**
	 * This <code>authenticateRequest</code> method is use to authenticate choose gateway request.
	 *
	 * @param response
	 * @param merchantId
	 * @param accessToken
	 */
	private void authenticateRequest(final ChooseGatewayResponse response, final String merchantId, final String accessToken) {

		LOGGER.info(" ==> Method ==> authenticateRequest ==> Enter");

		if (!ApplicationUtils.isEmpty(merchantId) && !ApplicationUtils.isEmpty(accessToken)) {

			final Set<String> errors = authenticateMerchant(merchantId, accessToken);

			if (ApplicationUtils.isValid(errors)) {
				response.setCode(RequestResponseCodeEnum.AUTHENTICATIONERROR.code());
				response.setMessages(errors);
			}

		} else {

			response.setCode(RequestResponseCodeEnum.AUTHENTICATIONERROR.code());
			response.setMessages(new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.AUTHENTICATION_PARAM_MISSING.message(), null, null))));
		}

		LOGGER.info(" ==> Method ==> authenticateRequest ==> Exit");
	}

	/**
	 * This <code>authenticateMerchant</code> method is used to authenticateMerchant the request header from database.
	 *
	 * @param sourceMerchantId
	 * @param token
	 * @return
	 */
	private Set<String> authenticateMerchant(final String sourceMerchantId, final String token) {

		LOGGER.info(" ==> Method ==> authenticateMerchant ==> Enter");

		Boolean isError = Boolean.FALSE;
		final Set<String> errors = new HashSet<>();

		final Merchant merchant = getServiceRegistry().getMerchantService().getMerchantBySourceMerchantId(sourceMerchantId);

		if (merchant != null && merchant.getUserId() != null) {

			final ApplicationUser user = getServiceRegistry().getUserService().getUserById(merchant.getUserId());

			if (user != null && !ApplicationUtils.isEmpty(user.getOpenId())) {

				final AccessToken accessToken = getServiceRegistry().getAccessTokenService().getAccessTokenByOpenIdAndToken(user.getOpenId(), token);

				if (accessToken == null) {
					isError = Boolean.TRUE;
				} else if (accessToken != null && !ApplicationUtils.isEmpty(accessToken.getActive()) && !accessToken.getActive()) {
					errors.add(getMessageSource().getMessage(ErrorDataEnum.REQUEST_HEADER_ACCESSTOKEN_INACTIVE_ERROR_MESSAGE.message(), null, null));
				} else if (accessToken != null && !ApplicationUtils.isEmpty(accessToken.getExpirydate())
						&& getServiceRegistry().getAccessTokenService().isAccessTokenExpired(accessToken.getExpirydate())) {
					errors.add(getMessageSource().getMessage(ErrorDataEnum.REQUEST_HEADER_ACCESSTOKEN_EXPIRED_ERROR_MESSAGE.message(), null, null));
				}

			} else {
				isError = Boolean.TRUE;
			}

		} else {
			isError = Boolean.TRUE;
		}

		if (isError) {
			errors.add(getMessageSource().getMessage(ErrorDataEnum.AUTHENTICATION_ERROR.message(), null, null));
		}

		LOGGER.info(" ==> Method ==> authenticateMerchant ==> Exit");
		return errors;
	}

	/**
	 * This <code>validateAndProcessRequest</code> method is use to validate and process request.
	 *
	 * @param response
	 * @param gatewayRequest
	 */
	private void validateAndProcessRequest(final ChooseGatewayResponse response, final ChooseGatewayRequest gatewayRequest) {

		LOGGER.info(" ==> Method ==> validateAndProcessRequest ==> Enter");

		validateRequestData(response, gatewayRequest);

		if (!ApplicationUtils.isValid(response.getMessages())) {

			BeanUtils.copyProperties(chooseGatewayRuleEngine.setupRuleEngine(gatewayRequest), response, CHOOSEGATEAY_IGNOREPROPERTIES);

		} else {
			response.setCode(RequestResponseCodeEnum.VALIDATIONERROR.code());
		}

		LOGGER.info(" ==> Method ==> validateAndProcessRequest ==> Exit");

	}

	/**
	 * This <code>validateRequestData</code> method is use to validate request data.
	 *
	 * @param response
	 * @param gatewayRequest
	 */
	private void validateRequestData(final ChooseGatewayResponse response, final ChooseGatewayRequest gatewayRequest) {

		LOGGER.info(" ==> Method ==> validateRequestData ==> Enter");

		if (!ApplicationUtils.isValid(response.getMessages())) {
			response.setMessages(new HashSet<>());
		}

		validateCardDetails(response, gatewayRequest);

		if (ApplicationUtils.isEmpty(gatewayRequest.getAmount())) {
			response.getMessages().add(getMessageSource().getMessage(ErrorDataEnum.AMOUNT_MISSING_ERROR.message(), null, null));
		} else {

			if (!ApplicationUtils.isDoubleNumber(gatewayRequest.getAmount())) {
				response.getMessages().add(getMessageSource().getMessage(ErrorDataEnum.INVALID_AMOUNT_ERROR.message(), null, null));
			}
		}

		if (ApplicationUtils.isEmpty(gatewayRequest.getCountryCode())) {
			response.getMessages().add(getMessageSource().getMessage(ErrorDataEnum.COUNTRYCODE_MISSING_ERROR.message(), null, null));
		} else {

			if (!ApplicationUtils.isValidCountryCode(gatewayRequest.getCountryCode())) {
				response.getMessages().add(getMessageSource().getMessage(ErrorDataEnum.INVALID_COUNTRYCODE_ERROR.message(), null, null));
			}
		}

		if (ApplicationUtils.isEmpty(gatewayRequest.getCurrencyCode())) {
			response.getMessages().add(getMessageSource().getMessage(ErrorDataEnum.CURRENCYCODE_MISSING_ERROR.message(), null, null));
		} else {

			if (!ApplicationUtils.isValidCurrencyOrCountryCode(gatewayRequest.getCurrencyCode())) {
				response.getMessages().add(getMessageSource().getMessage(ErrorDataEnum.INVALID_CURRENCYCODE_ERROR.message(), null, null));
			}
		}

		if (ApplicationUtils.isEmpty(gatewayRequest.getHostedPaymentPage())) {
			response.getMessages().add(getMessageSource().getMessage(ErrorDataEnum.HOSTEDPAYMENTPAGE_MISSING_ERROR.message(), null, null));
		} else {

			if (!ApplicationUtils.isValidHostedPaymentPage(gatewayRequest.getHostedPaymentPage())) {
				response.getMessages().add(getMessageSource().getMessage(ErrorDataEnum.INVALID_HOSTEDPAYMENTPAGE_ERROR.message(), null, null));
			}
		}

		LOGGER.info(" ==> Method ==> validateRequestData ==> Exit");
	}

	/**
	 * This <code>validateCardDetails</code> method is use to validate card details.
	 *
	 * @param response
	 * @param gatewayRequest
	 */
	private void validateCardDetails(final ChooseGatewayResponse response, final ChooseGatewayRequest gatewayRequest) {

		LOGGER.info(" ==> Method ==> validateCardDetails ==> Enter");

		if (!ApplicationUtils.isEmpty(gatewayRequest.getCardType()) && !ApplicationUtils.isValidCardType(gatewayRequest.getCardType())) {
			response.getMessages().add(getMessageSource().getMessage(ErrorDataEnum.INVALID_CARDTYPE_ERROR.message(), null, null));
		}

		if (!ApplicationUtils.isEmpty(gatewayRequest.getCardNumber()) && ApplicationUtils.isValidCardNumber(gatewayRequest.getCardNumber())) {

			if (ApplicationUtils.isValidCardNumber(gatewayRequest.getCardNumber())) {

				if (ApplicationUtils.isEmpty(gatewayRequest.getCardType())) {

					if (!CardType.UNKNOWN.name().equalsIgnoreCase(CardType.detect(gatewayRequest.getCardNumber()).name())) {
						gatewayRequest.setCardType(CardType.detect(gatewayRequest.getCardNumber()).name());
					} else {
						response.getMessages().add(getMessageSource().getMessage(ErrorDataEnum.INVALID_CARDTYPEBYCARDNUMBER_ERROR.message(), null, null));
					}
				}
			} else {
				response.getMessages().add(getMessageSource().getMessage(ErrorDataEnum.INVALID_CARDNUMBER_ERROR.message(), null, null));
			}
		}

		if (!ApplicationUtils.isEmpty(gatewayRequest.getCardIssueCountry()) && !ApplicationUtils.isValidCountryCode(gatewayRequest.getCardIssueCountry())) {
			response.getMessages().add(getMessageSource().getMessage(ErrorDataEnum.INVALID_CARDISSUECOUNTRY_ERROR.message(), null, null));
		}

		LOGGER.info(" ==> Method ==> validateCardDetails ==> Exit");
	}

	/**
	 * This <code>updateAndSetResponseOfRequest</code> method is use to update and set response value for request.
	 *
	 * @param response
	 * @param gatewayRequest
	 * @param servletRequest
	 */
	private void updateAndSetResponseOfRequest(final ChooseGatewayResponse response, final ChooseGatewayRequest gatewayRequest,
			final HttpServletRequest servletRequest) {

		LOGGER.info(" ==> Method ==> updateAndSetResponseOfRequest ==> Enter");

		final String currentTimeStamp = ApplicationUtils.getCurrentTimeStamp();
		String merchantId = "";

		try {

			if (gatewayRequest != null && gatewayRequest.getOptimiserRequestDataId() != null) {

				merchantId = gatewayRequest.getMerchantId();
				final RequestData requestData = getServiceRegistry().getRequestDataService().findById(gatewayRequest.getOptimiserRequestDataId());

				if (requestData != null) {

					requestData.setResponseDate(currentTimeStamp);
					requestData.setResponseStatus(response.getStatus());
					requestData.setStatus(RequestStatusEnum.SENT.status());
					response.setReferenceId(gatewayRequest.getReferenceId());
					response.setId(String.valueOf(requestData.getRequestDataId()));
					response.setRequestDate(currentTimeStamp);
					requestData.setResponseData(ApplicationUtils.generateJSONFromObject(response));
					getServiceRegistry().getRequestDataService().updateRequestData(requestData);
				}
			} else {

				final RequestData requestData = new RequestData();
				final UUID requestDataId = UUID.randomUUID();
				requestData.setRequestDataId(requestDataId);
				requestData.setResponseData(ApplicationUtils.generateJSONFromObject(response));
				requestData.setResponseDate(currentTimeStamp);
				requestData.setResponseStatus(response.getStatus());
				requestData.setStatus(RequestStatusEnum.SENT.status());
				response.setId(String.valueOf(requestDataId));
				response.setRequestDate(currentTimeStamp);
				addRequestDetails(gatewayRequest, servletRequest, requestData);
			}

		} catch (final Exception e) {

			logError(LOGGER, merchantId, e, " ==> Method ==> updateAndSetResponseOfRequest ==> Exception ==> ");
			response.setCode(RequestResponseCodeEnum.INTERNALSERVERERROR.code());
			response.setMessages(new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))));
		}

		LOGGER.info(" ==> Method ==> updateAndSetResponseOfRequest ==> Exit");
	}

	/**
	 * This <code>setResponseForChooseGateway</code> method is use to set the HTTP status for choose gateway.
	 *
	 * @param response
	 * @param gatewayRequest
	 * @param servletRequest
	 * @return
	 */
	private ResponseEntity<ChooseGatewayResponse> setHTTPStatusForChooseGateway(final ChooseGatewayResponse response, final ChooseGatewayRequest gatewayRequest,
			final HttpServletRequest servletRequest) {

		LOGGER.info(" ==> Method ==> setResponseForChooseGateway ==> Enter");

		ResponseEntity<ChooseGatewayResponse> responseEntity = null;
		Boolean isCommonError = Boolean.FALSE;

		try {

			if (!ApplicationUtils.isEmpty(response.getCode())) {

				switch (response.getCode()) {

					case "00":
						responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
						break;

					case "02":
						responseEntity = new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
						break;

					case "03":
						responseEntity = new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
						break;

					case "04":
						responseEntity = new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
						break;

					default:
						responseEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
						break;
				}
			} else {
				isCommonError = Boolean.TRUE;
			}

		} catch (final Exception e) {
			logError(LOGGER, null, e, " ==> Method ==> setResponseForChooseGateway ==> Exception ==> ");
			isCommonError = Boolean.TRUE;
		}

		if (isCommonError) {

			response.setCode(RequestResponseCodeEnum.INTERNALSERVERERROR.code());
			response.setMessages(new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))));
			responseEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			updateAndSetResponseOfRequest(response, gatewayRequest, servletRequest);
		}

		LOGGER.info(" ==> Method ==> setResponseForChooseGateway ==> Exit");
		return responseEntity;
	}
}
