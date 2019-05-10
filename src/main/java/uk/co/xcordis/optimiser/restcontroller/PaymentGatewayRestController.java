/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.restcontroller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import uk.co.xcordis.optimiser.controller.BaseController;
import uk.co.xcordis.optimiser.dto.UIOperationResponse;
import uk.co.xcordis.optimiser.model.PaymentGateways;
import uk.co.xcordis.optimiser.util.ApplicationConstants;
import uk.co.xcordis.optimiser.util.ApplicationURIConstants;
import uk.co.xcordis.optimiser.util.ApplicationUtils;
import uk.co.xcordis.optimiser.util.ErrorDataEnum;
import uk.co.xcordis.optimiser.util.UIResponseCodeEnum;

/**
 * The <code>PaymentGatewayRestController</code> class responsible for expose API for payment gateway in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@RestController
public class PaymentGatewayRestController extends BaseController implements ApplicationConstants, ApplicationURIConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentGatewayRestController.class);

	/**
	 * The method <code>paymentGatewayList</code> is responsible for payment gateway page screen in Optimiser application.
	 *
	 * @return
	 */
	@GetMapping(value = PAYMENT_GATEWAY_LIST_URL)
	public ResponseEntity<UIOperationResponse> paymentGatewayList() {

		LOGGER.info(" ==> Method : paymentGatewayList ==> Called");

		UIOperationResponse uiOperationResponse = null;
		try {

			uiOperationResponse = getUiOperationResponse(ApplicationConstants.STATUS_SUCCESS, null,
					getServiceRegistry().getPaymentGatewaysService().getAllPaymentGateways(), UIResponseCodeEnum.LIST_SUCCESS_MESSAGE.getCode());

		} catch (final Exception e) {

			logError(LOGGER, null, e, " ==> Method ==> paymentGatewayList ==> Exception ==> ");
			commonErrorResponse(uiOperationResponse,
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(uiOperationResponse, HttpStatus.OK);
	}

	/**
	 * The method <code>addPaymentGateway</code> is responsible for store the payment gateway details in the database.
	 *
	 * @return
	 */
	@PostMapping(value = PAYMENT_GATEWAY_ADD_URL)
	public ResponseEntity<UIOperationResponse> addPaymentGateway(@RequestBody(required = false) final PaymentGateways paymentGateways) {

		LOGGER.info(" ==> Method : addPaymentGateway ==> called ");

		ResponseEntity<UIOperationResponse> responseEntity = null;
		final UIOperationResponse uiOperationResponse = new UIOperationResponse();
		Set<String> errors = null;

		try {

			if (paymentGateways != null) {

				errors = validatePaymentGateway(paymentGateways, Boolean.TRUE);

				if (!ApplicationUtils.isValid(errors)) {

					getServiceRegistry().getPaymentGatewaysService().savePaymentGatewayDetails(paymentGateways);

					responseEntity = new ResponseEntity<>(getUiOperationResponse(ApplicationConstants.STATUS_SUCCESS,
							new HashSet<>(Arrays.asList(ApplicationConstants.PAYMENT_GATEWAY_MODULE_LABEL
									+ getMessageSource().getMessage(ErrorDataEnum.ADD_SUCCESS_MESSAGE.message(), null, null))),
							paymentGateways.getPaymentgatewayid(), UIResponseCodeEnum.ADD_SUCCESS_MESSAGE.getCode()), HttpStatus.OK);

				} else {
					responseEntity = new ResponseEntity<>(getUiOperationResponse(ApplicationConstants.STATUS_ERROR, new LinkedHashSet<String>(errors), null,
							UIResponseCodeEnum.FAILED_MESSAGE.getCode()), HttpStatus.BAD_REQUEST);
				}

			} else {
				responseEntity = commonErrorResponse(uiOperationResponse,
						new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_BADREQUEST_ERROR_MESSAGE.message(), null, null))),
						HttpStatus.BAD_REQUEST);
			}

		} catch (final Exception e) {
			logError(LOGGER, null, e, " ==> Method ==> addPaymentGateway ==> Exception ==> ");
			commonErrorResponse(new UIOperationResponse(),
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;

	}

	/**
	 * The method <code>editPaymentGateway</code> is responsible for update the payment gateway details in the database.
	 *
	 * @return
	 */
	@PostMapping(value = PAYMENT_GATEWAY_EDIT_URL)
	public ResponseEntity<UIOperationResponse> editPaymentGateway(@RequestBody(required = false) final PaymentGateways paymentGateways) {

		LOGGER.info(" ==> Method : editPaymentGateway ==> called ");

		ResponseEntity<UIOperationResponse> responseEntity = null;
		final UIOperationResponse uiOperationResponse = new UIOperationResponse();

		Set<String> errors = null;

		try {

			if (paymentGateways != null) {

				errors = validatePaymentGateway(paymentGateways, Boolean.FALSE);

				if (!ApplicationUtils.isValid(errors)) {

					responseEntity = new ResponseEntity<>(getUiOperationResponse(ApplicationConstants.STATUS_SUCCESS,
							new HashSet<>(Arrays.asList(ApplicationConstants.PAYMENT_GATEWAY_MODULE_LABEL
									+ getMessageSource().getMessage(ErrorDataEnum.EDIT_SUCCESS_MESSAGE.message(), null, null))),
							getServiceRegistry().getPaymentGatewaysService().savePaymentGatewayDetails(paymentGateways),
							UIResponseCodeEnum.EDIT_SUCCESS_MESSAGE.getCode()), HttpStatus.OK);

				} else {
					responseEntity = new ResponseEntity<>(getUiOperationResponse(ApplicationConstants.STATUS_ERROR, new LinkedHashSet<String>(errors), null,
							UIResponseCodeEnum.FAILED_MESSAGE.getCode()), HttpStatus.BAD_REQUEST);
				}

			} else {
				responseEntity = commonErrorResponse(uiOperationResponse,
						new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_BADREQUEST_ERROR_MESSAGE.message(), null, null))),
						HttpStatus.BAD_REQUEST);
			}

		} catch (final Exception e) {
			logError(LOGGER, String.valueOf(paymentGateways.getPaymentgatewayid()), e, " ==> Method ==> editPaymentGateway ==> Exception ==> ");
			commonErrorResponse(new UIOperationResponse(),
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;

	}

	/**
	 * The method <code>viewPaymentGateway</code> is responsible for load payment gateway data by payment gateway id.
	 *
	 * @param paymentGatewayId
	 * @return
	 */
	@GetMapping(value = PAYMENT_GATEWAY_VIEW_URL)
	public ResponseEntity<UIOperationResponse> viewPaymentGateway(@RequestHeader(value = USERID_LABLE, required = false) final String userId,
			@RequestHeader(value = ACCESSTOKEN_LABEL, required = false) final String accessToken,
			@PathVariable(ApplicationConstants.PAYMENT_GATEWAY_ID) final String paymentGatewayId) {

		LOGGER.info(" ==> Method : viewPaymentGateway ==> Called");

		PaymentGateways paymentGateways = null;
		ResponseEntity<UIOperationResponse> responseEntity = null;
		try {
			if (!ApplicationUtils.isEmpty(paymentGatewayId)) {
				paymentGateways = getServiceRegistry().getPaymentGatewaysService().getPaymentGatewayById(paymentGatewayId);
				if (paymentGateways != null) {
					responseEntity = new ResponseEntity<>(getUiOperationResponse(ApplicationConstants.STATUS_SUCCESS, null, paymentGateways,
							UIResponseCodeEnum.VIEW_SUCCESS_MESSAGE.getCode()), HttpStatus.OK);
				} else {
					responseEntity = commonErrorResponse(getUiOperationResponse(null, null, null, null),
							new HashSet<>(
									Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.PAYMENT_GATEWAY_COMMON_ERROR_MESSAGE.message(), null, null))),
							HttpStatus.NOT_FOUND);
				}
			} else {
				responseEntity = commonErrorResponse(new UIOperationResponse(),
						new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_BADREQUEST_ERROR_MESSAGE.message(), null, null))),
						HttpStatus.BAD_REQUEST);
			}
		} catch (final Exception e) {

			logError(LOGGER, paymentGatewayId, e, " ==> Method ==> viewPaymentGateway ==> Exception ==> ");
			commonErrorResponse(new UIOperationResponse(),
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	/**
	 * The method <code>paymentGatewayDelete</code> is responsible for delete/inactive the payment gateway data in the database.
	 *
	 * @param paymentGatewayId
	 * @param userId
	 * @param accessToken
	 * @param model
	 * @return
	 */
	@DeleteMapping(value = PAYMENT_GATEWAY_DELETE_URL)
	public ResponseEntity<UIOperationResponse>
			paymentGatewayDelete(@PathVariable(value = ApplicationConstants.PAYMENT_GATEWAY_ID) final String paymentGatewayId) {

		LOGGER.info(" ==> Method : paymentGatewayDelete ==> called ");

		ResponseEntity<UIOperationResponse> responseEntity = null;
		try {
			if (!ApplicationUtils.isEmpty(paymentGatewayId)) {

				getServiceRegistry().getPaymentGatewaysService().inActivePaymentGateway(Boolean.FALSE, UUID.fromString(paymentGatewayId));

				responseEntity = new ResponseEntity<>(getUiOperationResponse(ApplicationConstants.STATUS_SUCCESS,
						new HashSet<>(Arrays.asList(ApplicationConstants.PAYMENT_GATEWAY_MODULE_LABEL
								+ getMessageSource().getMessage(ErrorDataEnum.DELETE_SUCCESS_MESSAGE.message(), null, null))),
						null, UIResponseCodeEnum.DELETE_SUCCESS_MESSAGE.getCode()), HttpStatus.OK);
			} else {
				responseEntity = commonErrorResponse(new UIOperationResponse(),
						new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_BADREQUEST_ERROR_MESSAGE.message(), null, null))),
						HttpStatus.BAD_REQUEST);
			}
		} catch (final Exception e) {
			logError(LOGGER, paymentGatewayId, e, " ==> Method ==> paymentGatewayDelete ==> Exception ==> ");
			commonErrorResponse(new UIOperationResponse(),
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	/**
	 *
	 * The method <code>redirectToAddPaymentGatewayApi</code> is responsible for save the payment gateway data in the database.
	 *
	 * @param paymentGateways
	 * @return
	 */
	@PostMapping(value = ADD_PAYMENT_GATEWAY_URL)
	public ResponseEntity<UIOperationResponse> redirectToAddPaymentGatewayApi(@ModelAttribute final PaymentGateways paymentGateways) {

		LOGGER.info(" ==> Method : redirectToAddPaymentGatewayApi ==> called ");

		ResponseEntity<UIOperationResponse> storePaymentGatewayDetails = null;

		try {
			storePaymentGatewayDetails = addPaymentGateway(paymentGateways);

		} catch (final Exception e) {

			logError(LOGGER, null, e, " ==> Method ==> redirectToAddPaymentGatewayApi ==> Exception ==> ");
			commonErrorResponse(new UIOperationResponse(),
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);

		}
		return storePaymentGatewayDetails;
	}

	/**
	 *
	 * The method <code>redirectToEditPaymentGatewayApi</code> is responsible to send the payment gateway data for edit to the payment gateway edit api.
	 *
	 * @return
	 */
	@PostMapping(value = UPDATE_PAYMENT_GATEWAY_URL)
	public ResponseEntity<UIOperationResponse> redirectToEditPaymentGatewayApi(@ModelAttribute final PaymentGateways paymentGateways) {

		LOGGER.info(" ==> Method : redirectToEditPaymentGatewayApi ==> called ");

		ResponseEntity<UIOperationResponse> responseEntity = null;

		try {

			responseEntity = editPaymentGateway(paymentGateways);

		} catch (final Exception e) {

			logError(LOGGER, String.valueOf(paymentGateways.getPaymentgatewayid()), e, " ==> Method ==> redirectToEditPaymentGatewayApi ==> Exception ==> ");
			commonErrorResponse(new UIOperationResponse(),
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	/**
	 * The method <code>validatePaymentGateway</code> is responsible for validate the payment gateway object.
	 *
	 * @param paymentGateways
	 * @return
	 */
	private Set<String> validatePaymentGateway(final PaymentGateways paymentGateways, final Boolean isAdd) {

		LOGGER.info(" ==> Method : validatePaymentGateway ==> Enter ");

		final Set<String> errors = new HashSet<>();

		if (ApplicationUtils.isEmpty(paymentGateways.getPaymentgatewayname())) {
			errors.add(getMessageSource().getMessage(ErrorDataEnum.PAYMENT_GATEWAY_NAME_ERROR_MESSAGE.message(), null, null));
		} else {

			if (!ApplicationUtils.isOnlyAlphaAndSpace(paymentGateways.getPaymentgatewayname())) {
				errors.add(getMessageSource().getMessage(ErrorDataEnum.ERROR_ALPHAANDSPACE.message(), null, null)
						+ getMessageSource().getMessage(ErrorDataEnum.PAYMENT_GATEWAY_NAME_ERROR.message(), null, null));
			} else {
				if (isAdd && ApplicationUtils.isValid(getServiceRegistry().getPaymentGatewaysService().getAllPaymentGateways().stream()
						.map(PaymentGateways::getPaymentgatewayname).collect(Collectors.toList()).stream()
						.filter(a -> a.equalsIgnoreCase(paymentGateways.getPaymentgatewayname())).collect(Collectors.toList()))) {
					errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_EXISTS_ERROR_MESSAGE.message(),
							new Object[] { getMessageSource().getMessage(ErrorDataEnum.PAYMENT_GATEWAY_NAME_ERROR.message(), null, null) }, null));
				} else {
					if (!isAdd
							&& !getServiceRegistry().getPaymentGatewaysService().getPaymentGatewayById(String.valueOf(paymentGateways.getPaymentgatewayid()))
									.getPaymentgatewayname().equals(paymentGateways.getPaymentgatewayname())
							&& ApplicationUtils.isValid(getServiceRegistry().getPaymentGatewaysService().getAllPaymentGateways().stream()
									.map(PaymentGateways::getPaymentgatewayname).collect(Collectors.toList()).stream()
									.filter(a -> a.equalsIgnoreCase(paymentGateways.getPaymentgatewayname())).collect(Collectors.toList()))) {
						errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_EXISTS_ERROR_MESSAGE.message(),
								new Object[] { getMessageSource().getMessage(ErrorDataEnum.PAYMENT_GATEWAY_NAME_ERROR.message(), null, null) }, null));
					}
				}
			}
		}

		if (ApplicationUtils.isValid(paymentGateways.getGatewayParameters())) {

			// If gateway parameter key is not empty then value must be mandatory
			paymentGateways.getGatewayParameters().forEach((key, value) -> {
				// START_WITH_ALPHA
				if (!ApplicationUtils.isEmpty(key) && ApplicationUtils.isEmpty(value)) {
					if (!ApplicationUtils.isOnlyAlphaNumericDashUnderscoreStartWithAlpha(key)) {
						errors.add(getMessageSource().getMessage(ErrorDataEnum.ERROR_ALPHANUMERICDASHUNDERSCORE.message(), null, null)
								+ getMessageSource().getMessage(ErrorDataEnum.PAYMENT_GATEWAY_PARAMETER_KEY.message(), null, null)
								+ getMessageSource().getMessage(ErrorDataEnum.START_WITH_ALPHA.message(), null, null));
					} else if (!ApplicationUtils.isOnlyAlphaAndSpace(value)) {
						errors.add(getMessageSource().getMessage(ErrorDataEnum.ERROR_ALPHAANDSPACE.message(), null, null)
								+ getMessageSource().getMessage(ErrorDataEnum.PAYMENT_GATEWAY_PARAMETER_VALUE.message(), null, null));
					} else {
						errors.add(getMessageSource().getMessage(ErrorDataEnum.PAYMENT_GATEWAY_PARAMETER_VALUE_ERROR_MESSAGE.message(),
								new Object[] { key, null, null }, null));
					}
				} else {
					if (ApplicationUtils.isEmpty(key) && !ApplicationUtils.isEmpty(value)) {
						errors.add(getMessageSource().getMessage(ErrorDataEnum.PAYMENT_GATEWAY_PARAMETER_KEY_ERROR_MESSAGE.message(),
								new Object[] { value, null, null }, null));
					}
					if (!ApplicationUtils.isEmpty(key) && !ApplicationUtils.isOnlyAlphaNumericDashUnderscoreStartWithAlpha(key)) {
						errors.add(getMessageSource().getMessage(ErrorDataEnum.ERROR_ALPHANUMERICDASHUNDERSCORE.message(), null, null)
								+ getMessageSource().getMessage(ErrorDataEnum.PAYMENT_GATEWAY_PARAMETER_KEY.message(), null, null)
								+ getMessageSource().getMessage(ErrorDataEnum.START_WITH_ALPHA.message(), null, null));
					}
				}
			});
		}

		LOGGER.info(" ==> Method : validatePaymentGateway ==> Exit ");
		return errors;
	}

}
