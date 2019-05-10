package uk.co.xcordis.optimiser.restcontroller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import uk.co.xcordis.optimiser.dto.MerchantGatewaysDto;
import uk.co.xcordis.optimiser.dto.UIOperationResponse;
import uk.co.xcordis.optimiser.model.MerchantGateways;
import uk.co.xcordis.optimiser.model.PaymentGateways;
import uk.co.xcordis.optimiser.util.ApplicationConstants;
import uk.co.xcordis.optimiser.util.ApplicationURIConstants;
import uk.co.xcordis.optimiser.util.ApplicationUtils;
import uk.co.xcordis.optimiser.util.ErrorDataEnum;
import uk.co.xcordis.optimiser.util.OptimiserUtility;
import uk.co.xcordis.optimiser.util.RequestResponseStatusEnum;
import uk.co.xcordis.optimiser.util.UIResponseCodeEnum;

/**
 * The <code>MerchantGatewaysRestController</code> class responsible for handling all the Merchant Gateway Rest API request in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@RestController
public class MerchantGatewaysRestController extends BaseController implements ApplicationURIConstants, ApplicationConstants {

	@Autowired
	private OptimiserUtility optimiserUtility;

	private static final Logger LOGGER = LoggerFactory.getLogger(MerchantGatewaysRestController.class);

	/**
	 * This <code>merchantGatewayList</code> method is used to get the merchant gateway list based on merchantId.
	 *
	 * @param merchantId
	 * @return
	 */
	@GetMapping(MERCHANT_GATEWAY_LIST_URL)
	public ResponseEntity<UIOperationResponse> merchantGatewayList(@RequestHeader(value = MERCHANTID_LABLE, required = false) final String merchantId) {

		LOGGER.info(" ==> Method : merchantGatewayList ==> Enter");

		final UIOperationResponse uiOperationResponse = new UIOperationResponse();
		ResponseEntity<UIOperationResponse> responseEntity;
		final List<String> errors = new ArrayList<>();

		try {

			// Authenticate the merchantId.
			optimiserUtility.authenticateMerchantId(merchantId, errors);

			if (!ApplicationUtils.isValid(errors)) {

				final List<MerchantGateways> merchantGatewayList = getServiceRegistry().getMerchantGatewaysService()
						.getMerchantGatewaysByMerchantId(UUID.fromString(merchantId));

				if (!ApplicationUtils.isValid(merchantGatewayList)) {

					responseEntity = commonErrorResponse(uiOperationResponse,
							new HashSet<>(Arrays
									.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_MERCHANTGATEWAY_NOT_FOUND_ERROR_MESSAGE.message(), null, null))),
							HttpStatus.NOT_FOUND);
				} else {

					ApplicationUtils.sortListByTimeStamp(merchantGatewayList, FIELD_AUDITTIMESTAMP_LABEL, ApplicationConstants.DD_MM_YYYY_HH_MM_SS_AM_PM);

					return new ResponseEntity<>(getUiOperationResponse(RequestResponseStatusEnum.SUCCESS.status(), null, merchantGatewayList,
							UIResponseCodeEnum.VIEW_SUCCESS_MESSAGE.getCode()), HttpStatus.OK);
				}

			} else {
				responseEntity = commonErrorResponse(uiOperationResponse, new LinkedHashSet<String>(errors), HttpStatus.BAD_REQUEST);
			}

		} catch (final Exception e) {

			logError(LOGGER, null, e, " ==> Method ==> merchantGatewayList ==> Exception ==> ");
			responseEntity = commonErrorResponse(uiOperationResponse,
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		LOGGER.info(" ==> Method : merchantGatewayList ==> Exit");
		return responseEntity;
	}

	/**
	 * This <code>addMerchantGateway</code> method is used to add the Merchant Gateway details.
	 *
	 * @param merchantId
	 * @param merchantGateways
	 * @return
	 */
	@PostMapping(ADD_MERCHANT_GATEWAY_URL)
	public ResponseEntity<UIOperationResponse> addMerchantGateway(@RequestHeader(value = MERCHANTID_LABLE, required = false) final String merchantId,
			@RequestBody(required = false) final MerchantGatewaysDto merchantGateways) {

		LOGGER.info(" ==> Method ==> addMerchantGateway ==> Enter");

		final UIOperationResponse uiOperationResponse = new UIOperationResponse();
		final List<String> errors = new ArrayList<>();
		ResponseEntity<UIOperationResponse> responseEntity;

		try {

			if (merchantGateways != null) {

				setMerchantGatewayParameter(merchantGateways, errors);

				// Authenticate the merchantId.
				optimiserUtility.authenticateMerchantId(merchantId, errors);

				if (!ApplicationUtils.isEmpty(merchantId)) {
					merchantGateways.setMerchantId(UUID.fromString(merchantId));
				}

				validateMerchantGatewaysDetails(merchantGateways, errors, Boolean.TRUE);

				if (!ApplicationUtils.isValid(errors)) {

					// Add Merchant Gateway Data in MerchantGateways Table
					getServiceRegistry().getMerchantGatewaysService().addMerchantGateways(merchantGateways);

					return new ResponseEntity<>(getUiOperationResponse(RequestResponseStatusEnum.SUCCESS.status(),
							new HashSet<>(Arrays.asList(getMessageByCode(UIResponseCodeEnum.ADD_SUCCESS_MESSAGE.getCode(), MERCHANT_GATEWAY_MODULE_LABEL))),
							null, UIResponseCodeEnum.ADD_SUCCESS_MESSAGE.getCode()), HttpStatus.OK);

				} else {
					responseEntity = commonErrorResponse(uiOperationResponse, new LinkedHashSet<String>(errors), HttpStatus.BAD_REQUEST);
				}

			} else {

				responseEntity = commonErrorResponse(uiOperationResponse,
						new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_BADREQUEST_ERROR_MESSAGE.message(), null, null))),
						HttpStatus.BAD_REQUEST);
			}

		} catch (final Exception e) {

			logError(LOGGER,
					merchantGateways != null ? String.valueOf(merchantGateways.getMerchantGatewayId() != null ? merchantGateways.getMerchantGatewayId() : null)
							: null,
					e, " ==> Method ==> addMerchantGateway ==> Exception ==> ");
			responseEntity = commonErrorResponse(uiOperationResponse,
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		LOGGER.info(" ==> Method : addMerchantGateway ==> Exit");
		return responseEntity;
	}

	/**
	 * This <code>addMerchantGatewayFromUI</code> method is used to add the Merchant Gateway details from UI request.
	 *
	 * @param merchantId
	 * @param merchantGateways
	 * @return
	 */
	@PostMapping(ADD_MERCHANT_GATEWAY_UI_URL)
	public ResponseEntity<UIOperationResponse> addMerchantGatewayFromUI(@RequestHeader(value = MERCHANTID_LABLE, required = false) final String merchantId,
			@ModelAttribute final MerchantGatewaysDto merchantGateways) {

		LOGGER.info(" ==> Method ==> addMerchantGatewayFromUI ==> Enter");

		final UIOperationResponse uiOperationResponse = new UIOperationResponse();
		ResponseEntity<UIOperationResponse> responseEntity;

		try {

			responseEntity = addMerchantGateway(merchantId, merchantGateways);

		} catch (final Exception e) {

			logError(LOGGER, String.valueOf(merchantGateways.getMerchantGatewayId()), e, " ==> Method ==> addMerchantGatewayFromUI ==> Exception ==> ");
			responseEntity = commonErrorResponse(uiOperationResponse,
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		LOGGER.info(" ==> Method : addMerchantGatewayFromUI ==> Exit");
		return responseEntity;
	}

	/**
	 * This <code>editMerchantGateway</code> method is used to update the Merchant Gateway details to database.
	 *
	 * @param merchantId
	 * @param merchantGateways
	 * @return
	 */
	@PostMapping(EDIT_MERCHANT_GATEWAY_URL)
	public ResponseEntity<UIOperationResponse> editMerchantGateway(@RequestHeader(value = MERCHANTID_LABLE, required = false) final String merchantId,
			@RequestBody final MerchantGatewaysDto merchantGateways) {

		LOGGER.info(" ==> Method ==> editMerchantGateway ==> Enter");

		final UIOperationResponse uiOperationResponse = new UIOperationResponse();
		final List<String> errors = new ArrayList<>();
		ResponseEntity<UIOperationResponse> responseEntity;

		try {

			if (merchantGateways != null && merchantGateways.getMerchantGatewayId() != null) {

				setMerchantGatewayParameter(merchantGateways, errors);

				// Authenticate the merchantId.
				optimiserUtility.authenticateMerchantId(merchantId, errors);

				if (!ApplicationUtils.isEmpty(merchantId)) {
					merchantGateways.setMerchantId(UUID.fromString(merchantId));
				}

				final MerchantGateways merchantGatewaysDetails = getServiceRegistry().getMerchantGatewaysService()
						.findById(merchantGateways.getMerchantGatewayId());

				if (merchantGatewaysDetails == null) {

					responseEntity = commonErrorResponse(uiOperationResponse,
							new HashSet<>(Arrays
									.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_MERCHANTGATEWAY_NOT_FOUND_ERROR_MESSAGE.message(), null, null))),
							HttpStatus.NOT_FOUND);
				} else {

					validateMerchantGatewaysDetails(merchantGateways, errors, Boolean.FALSE);

					if (!ApplicationUtils.isValid(errors)) {

						// Update Merchant Gateway Data in MerchantGateways Table
						getServiceRegistry().getMerchantGatewaysService().updateMerchantGateways(merchantGateways);

						return new ResponseEntity<>(getUiOperationResponse(RequestResponseStatusEnum.SUCCESS.status(),
								new HashSet<>(
										Arrays.asList(getMessageByCode(UIResponseCodeEnum.EDIT_SUCCESS_MESSAGE.getCode(), MERCHANT_GATEWAY_MODULE_LABEL))),
								null, UIResponseCodeEnum.EDIT_SUCCESS_MESSAGE.getCode()), HttpStatus.OK);

					} else {
						responseEntity = commonErrorResponse(uiOperationResponse, new LinkedHashSet<String>(errors), HttpStatus.BAD_REQUEST);
					}
				}

			} else {

				responseEntity = commonErrorResponse(uiOperationResponse,
						new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_BADREQUEST_ERROR_MESSAGE.message(), null, null))),
						HttpStatus.BAD_REQUEST);
			}

		} catch (final Exception e) {

			logError(LOGGER,
					merchantGateways != null ? String.valueOf(merchantGateways.getMerchantGatewayId() != null ? merchantGateways.getMerchantGatewayId() : null)
							: null,
					e, " ==> Method ==> editMerchantGateway ==> Exception ==> ");
			responseEntity = commonErrorResponse(uiOperationResponse,
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		LOGGER.info(" ==> Method : editMerchantGateway ==> Exit");
		return responseEntity;
	}

	/**
	 * This <code>editMerchantGatewayFromUI</code> method is used to update the Merchant Gateway details from UI request.
	 *
	 * @param merchantId
	 * @param merchantGateways
	 * @return
	 */
	@PostMapping(EDIT_MERCHANT_GATEWAY_UI_URL)
	public ResponseEntity<UIOperationResponse> editMerchantGatewayFromUI(@RequestHeader(value = MERCHANTID_LABLE, required = false) final String merchantId,
			@ModelAttribute final MerchantGatewaysDto merchantGateways) {

		LOGGER.info(" ==> Method ==> editMerchantGatewayFromUI ==> Enter");

		final UIOperationResponse uiOperationResponse = new UIOperationResponse();
		ResponseEntity<UIOperationResponse> responseEntity;

		try {

			responseEntity = editMerchantGateway(merchantId, merchantGateways);

		} catch (final Exception e) {

			logError(LOGGER, String.valueOf(merchantGateways.getMerchantGatewayId()), e, " ==> Method ==> editMerchantGatewayFromUI ==> Exception ==> ");
			responseEntity = commonErrorResponse(uiOperationResponse,
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		LOGGER.info(" ==> Method : editMerchantGatewayFromUI ==> Exit");
		return responseEntity;
	}

	/**
	 * This <code>deleteMerchantGateway</code> method is used to delete the MerchantGateway Details.
	 *
	 * @param merchantId
	 * @param merchantGatewayId
	 * @return
	 */
	@DeleteMapping(DELETE_MERCHANT_GATEWAY_URL)
	public ResponseEntity<UIOperationResponse> deleteMerchantGateway(@RequestHeader(value = MERCHANTID_LABLE, required = false) final String merchantId,
			@PathVariable(required = false) final UUID merchantGatewayId) {

		LOGGER.info(" ==> Method ==> deleteMerchantGateway ==> Enter");

		final UIOperationResponse uiOperationResponse = new UIOperationResponse();
		ResponseEntity<UIOperationResponse> responseEntity;
		final List<String> errors = new ArrayList<>();

		try {

			if (merchantGatewayId == null) {

				responseEntity = commonErrorResponse(uiOperationResponse,
						new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_BADREQUEST_ERROR_MESSAGE.message(), null, null))),
						HttpStatus.BAD_REQUEST);

			} else {

				// Authenticate the merchantId.
				optimiserUtility.authenticateMerchantId(merchantId, errors);

				if (!ApplicationUtils.isValid(errors)) {

					if (getServiceRegistry().getMerchantGatewaysService().deleteMerchantGateways(merchantGatewayId, Boolean.FALSE)) {

						return new ResponseEntity<>(getUiOperationResponse(RequestResponseStatusEnum.SUCCESS.status(),
								new HashSet<>(
										Arrays.asList(getMessageByCode(UIResponseCodeEnum.DELETE_SUCCESS_MESSAGE.getCode(), MERCHANT_GATEWAY_MODULE_LABEL))),
								null, UIResponseCodeEnum.DELETE_SUCCESS_MESSAGE.getCode()), HttpStatus.OK);
					} else {

						responseEntity = commonErrorResponse(uiOperationResponse,
								new HashSet<>(Arrays.asList(
										getMessageSource().getMessage(ErrorDataEnum.COMMON_MERCHANTGATEWAY_NOT_FOUND_ERROR_MESSAGE.message(), null, null))),
								HttpStatus.NOT_FOUND);
					}

				} else {
					responseEntity = commonErrorResponse(uiOperationResponse, new LinkedHashSet<String>(errors), HttpStatus.BAD_REQUEST);
				}

			}

		} catch (final Exception e) {

			logError(LOGGER, String.valueOf(merchantGatewayId), e, " ==> Method ==> deleteMerchantGateway ==> Exception ==> ");
			responseEntity = commonErrorResponse(uiOperationResponse,
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		LOGGER.info(" ==> Method ==> deleteMerchantGateway ==> Exit");
		return responseEntity;
	}

	/**
	 * This <code>viewMerchantGatewayDetails</code> method is used view the MerchantGateway Details.
	 *
	 * @param merchantId
	 * @param merchantGatewayId
	 * @return
	 */
	@GetMapping(DETAILS_MERCHANT_GATEWAY_URL)
	public ResponseEntity<UIOperationResponse> viewMerchantGatewayDetails(@RequestHeader(value = MERCHANTID_LABLE, required = false) final String merchantId,
			@PathVariable(required = false) final UUID merchantGatewayId) {

		LOGGER.info(" ==> Method ==> viewMerchantGatewayDetails ==> Enter");

		final UIOperationResponse uiOperationResponse = new UIOperationResponse();
		ResponseEntity<UIOperationResponse> responseEntity;
		final List<String> errors = new ArrayList<>();

		try {

			if (merchantGatewayId != null) {

				// Authenticate the merchantId.
				optimiserUtility.authenticateMerchantId(merchantId, errors);

				if (!ApplicationUtils.isValid(errors)) {

					final MerchantGateways merchantGateways = getServiceRegistry().getMerchantGatewaysService().findById(merchantGatewayId);

					if (merchantGateways == null) {

						responseEntity = commonErrorResponse(uiOperationResponse,
								new HashSet<>(Arrays.asList(
										getMessageSource().getMessage(ErrorDataEnum.COMMON_MERCHANTGATEWAY_NOT_FOUND_ERROR_MESSAGE.message(), null, null))),
								HttpStatus.NOT_FOUND);
					} else {
						return new ResponseEntity<>(getUiOperationResponse(RequestResponseStatusEnum.SUCCESS.status(), null, merchantGateways,
								UIResponseCodeEnum.VIEW_SUCCESS_MESSAGE.getCode()), HttpStatus.OK);
					}

				} else {
					responseEntity = commonErrorResponse(uiOperationResponse, new LinkedHashSet<String>(errors), HttpStatus.BAD_REQUEST);
				}

			} else {
				responseEntity = commonErrorResponse(uiOperationResponse,
						new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_BADREQUEST_ERROR_MESSAGE.message(), null, null))),
						HttpStatus.BAD_REQUEST);
			}

		} catch (final Exception e) {

			logError(LOGGER, String.valueOf(merchantGatewayId), e, " ==> Method ==> viewMerchantGatewayDetails ==> Exception ==> ");
			responseEntity = commonErrorResponse(uiOperationResponse,
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		LOGGER.info(" ==> Method : viewMerchantGatewayDetails ==> Exit");
		return responseEntity;
	}

	/**
	 * This method <code>setMerchantGatewayParameter</code> is used for set the merchant gateways parameter from key-value pair list.
	 *
	 * @param merchantGatewaysDto
	 * @param errors
	 */
	private void setMerchantGatewayParameter(final MerchantGatewaysDto merchantGatewaysDto, final List<String> errors) {

		LOGGER.info(" ==> Method ==> setMerchantGatewayParameter ==> Enter");

		try {

			if (ApplicationUtils.isValid(merchantGatewaysDto.getMerchantGatewayParameterList())) {

				final List<Map<String, String>> merchantGatewayParameterList = new ArrayList<>();
				final Map<String, String> map = new HashMap<String, String>();

				merchantGatewaysDto.getMerchantGatewayParameterList().forEach(merchantGatewayParam -> {

					if (!ApplicationUtils.isEmpty(merchantGatewayParam)) {

						for (final String keyValuePair : merchantGatewayParam.split(",")) {

							if (!ApplicationUtils.isEmpty(keyValuePair)) {

								final String mapKeyValue[] = keyValuePair.split("=");

								if (!ApplicationUtils.isEmpty(mapKeyValue) && !ApplicationUtils.isEmpty(mapKeyValue[0])
										&& !ApplicationUtils.isEmpty(mapKeyValue[1])) {

									if (ApplicationUtils.isValid(map) && !map.containsKey(mapKeyValue[0].trim())) {

										map.put(mapKeyValue[0].trim(), mapKeyValue[1].trim());
									} else {

										map.put(mapKeyValue[0].trim(), mapKeyValue[1].trim());
									}
								}
							}
						}
					}
				});
				merchantGatewayParameterList.add(map);
				merchantGatewaysDto.setMerchantGatewayParameter(merchantGatewayParameterList);
			}

		} catch (final Exception e) {
			logError(LOGGER, null, e, " ==> Method ==> setMerchantGatewayParameter ==> Exception ==> ");
			errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_FORMAT_INVALID.message(), null, null)
					+ getMessageSource().getMessage(ErrorDataEnum.MERCHANT_GATEWAY_PARAMETER_ERROR.message(), null, null));
		}

		LOGGER.info(" ==> Method ==> setMerchantGatewayParameter ==> Exit");
	}

	/**
	 * This <code>validateMerchantGatewaysDetails</code> method is used to validate the merchant gateways details.
	 *
	 * @param merchantGateways
	 * @param errors
	 * @param isAdd
	 */
	private void validateMerchantGatewaysDetails(final MerchantGateways merchantGateways, final List<String> errors, final Boolean isAdd) {

		LOGGER.info(" ==> Method ==> validateMerchantGatewaysDetails ==> Enter");

		if (merchantGateways.getPaymentGatewayId() == null) {
			errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_SELECT.message(), null, null)
					+ getMessageSource().getMessage(ErrorDataEnum.PAYMENT_GATEWAY_ERROR.message(), null, null));
		} else {

			final PaymentGateways paymentGateways = getServiceRegistry().getPaymentGatewaysService()
					.getPaymentGatewayById(String.valueOf(merchantGateways.getPaymentGatewayId()));

			if (paymentGateways != null && !ApplicationUtils.isEmpty(paymentGateways.getPaymentgatewayname())) {
				merchantGateways.setGatewayName(paymentGateways.getPaymentgatewayname());
			}
		}

		if (ApplicationUtils.isEmpty(merchantGateways.getSourceMerchantGatewayId())) {
			errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_BLANK.message(), null, null)
					+ getMessageSource().getMessage(ErrorDataEnum.SOURCE_MERCHANTGATEWAY_ID_ERROR.message(), null, null));
		} else {
			if (isAdd && getServiceRegistry().getMerchantGatewaysService()
					.getMerchantGatewaysBySourceMerchantGatewayId(merchantGateways.getSourceMerchantGatewayId()) != null) {
				errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_EXISTS_ERROR_MESSAGE.message(),
						new Object[] { getMessageSource().getMessage(ErrorDataEnum.SOURCE_MERCHANTGATEWAY_ID_ERROR.message(), null, null) }, null));
			} else {
				if (!isAdd
						&& !getServiceRegistry().getMerchantGatewaysService().findById(merchantGateways.getMerchantGatewayId()).getSourceMerchantGatewayId()
								.equals(merchantGateways.getSourceMerchantGatewayId())
						&& getServiceRegistry().getMerchantGatewaysService()
								.getMerchantGatewaysBySourceMerchantGatewayId(merchantGateways.getSourceMerchantGatewayId()) != null) {
					errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_EXISTS_ERROR_MESSAGE.message(),
							new Object[] { getMessageSource().getMessage(ErrorDataEnum.SOURCE_MERCHANTGATEWAY_ID_ERROR.message(), null, null) }, null));

				}
			}
		}

		if (ApplicationUtils.isEmpty(merchantGateways.getPaymentGatewayPreference())) {
			errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_BLANK.message(), null, null)
					+ getMessageSource().getMessage(ErrorDataEnum.PAYMENT_GATEWAY_PREFERENCE_ERROR.message(), null, null));
		} else {

			if (!ApplicationUtils.isOnlyNumberWithThreeDigit(String.valueOf(merchantGateways.getPaymentGatewayPreference()))) {
				errors.add(getMessageSource().getMessage(ErrorDataEnum.ERROR_MAX_INTEGER.message(), null, null)
						+ getMessageSource().getMessage(ErrorDataEnum.PAYMENT_GATEWAY_PREFERENCE_ERROR.message(), null, null));
			}

			if (!ApplicationUtils.isOnlyNumber(String.valueOf(merchantGateways.getPaymentGatewayPreference()))) {
				errors.add(getMessageSource().getMessage(ErrorDataEnum.ERROR_ONLY_INTEGER.message(), null, null)
						+ getMessageSource().getMessage(ErrorDataEnum.PAYMENT_GATEWAY_PREFERENCE_ERROR.message(), null, null));
			}
		}

		if (ApplicationUtils.isEmpty(merchantGateways.getPaymentGatewaySequence())) {
			errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_BLANK.message(), null, null)
					+ getMessageSource().getMessage(ErrorDataEnum.PAYMENT_GATEWAY_SEQUENCE_ERROR.message(), null, null));
		} else {

			if (!ApplicationUtils.isOnlyNumberWithThreeDigit(String.valueOf(merchantGateways.getPaymentGatewaySequence()))) {
				errors.add(getMessageSource().getMessage(ErrorDataEnum.ERROR_MAX_INTEGER.message(), null, null)
						+ getMessageSource().getMessage(ErrorDataEnum.PAYMENT_GATEWAY_SEQUENCE_ERROR.message(), null, null));
			}

			if (!ApplicationUtils.isOnlyNumber(String.valueOf(merchantGateways.getPaymentGatewaySequence()))) {
				errors.add(getMessageSource().getMessage(ErrorDataEnum.ERROR_ONLY_INTEGER.message(), null, null)
						+ getMessageSource().getMessage(ErrorDataEnum.PAYMENT_GATEWAY_SEQUENCE_ERROR.message(), null, null));
			} else {

				if (isAdd && getServiceRegistry().getMerchantGatewaysService().getMerchantGatewaysByMerchantId(merchantGateways.getMerchantId()).stream()
						.map(MerchantGateways::getPaymentGatewaySequence).collect(Collectors.toList()).contains(merchantGateways.getPaymentGatewaySequence())) {
					errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_EXISTS_ERROR_MESSAGE.message(),
							new Object[] { getMessageSource().getMessage(ErrorDataEnum.PAYMENT_GATEWAY_SEQUENCE_ERROR.message(), null, null) }, null));
				} else {
					if (!isAdd
							&& !getServiceRegistry().getMerchantGatewaysService().findById(merchantGateways.getMerchantGatewayId()).getPaymentGatewaySequence()
									.equals(merchantGateways.getPaymentGatewaySequence())
							&& getServiceRegistry().getMerchantGatewaysService().getMerchantGatewaysByMerchantId(merchantGateways.getMerchantId()).stream()
									.map(MerchantGateways::getPaymentGatewaySequence).collect(Collectors.toList())
									.contains(merchantGateways.getPaymentGatewaySequence())) {
						errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_EXISTS_ERROR_MESSAGE.message(),
								new Object[] { getMessageSource().getMessage(ErrorDataEnum.PAYMENT_GATEWAY_SEQUENCE_ERROR.message(), null, null) }, null));
					}
				}
			}
		}

		if (ApplicationUtils.isValid(merchantGateways.getSelector())) {

			// check the selector key is valid or not
			merchantGateways.getSelector().forEach((key, value) -> {

				if (!ApplicationUtils.isEmpty(key) && ApplicationUtils.isEmpty(value)) {
					errors.add(getMessageSource().getMessage(ErrorDataEnum.MERCHANGATEWAY_SELECTOR_VALUE_ERROR_MESSAGE.message(),
							new Object[] { key, null, null }, null));
				} else {
					if (ApplicationUtils.isEmpty(key) && !ApplicationUtils.isEmpty(value)) {
						errors.add(getMessageSource().getMessage(ErrorDataEnum.MERCHANGATEWAY_SELECTOR_VALUE_ERROR_MESSAGE.message(),
								new Object[] { value, null, null }, null));
					}
				}

				if (!ApplicationUtils.isEmpty(key) && !getServiceRegistry().getRuleSelectorKeysService().isSelectorKeyExists(key)) {

					errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_FIELD_NOT_VALID_ERROR_MESSAGE.message(),
							new Object[] { getMessageSource().getMessage(ErrorDataEnum.SELECTOR_KEY_ERROR.message(), null, null) }, null));
				}
			});
		}

		LOGGER.info(" ==> Method ==> validateMerchantGatewaysDetails ==> Exit");
	}

	/**
	 * The <code>changeSequenceMerchantGateways</code> method is responsible for updating the Merchant Gateways sequence in the database.
	 *
	 * @return
	 */
	@PostMapping(SEQUENCE_MERCHANTGATEWAYS_URL)
	public ResponseEntity<UIOperationResponse> changeSequenceMerchantGateways(
			@RequestHeader(value = MERCHANTID_LABLE, required = false) final String merchantId,
			@RequestBody(required = false) final List<UUID> merchantGatewayIdList) {

		LOGGER.info(" ==> Method : changeSequenceMerchantGateways ==> Enter ");

		final UIOperationResponse uiOperationResponse = new UIOperationResponse();

		ResponseEntity<UIOperationResponse> responseEntity = null;

		final List<String> errors = new ArrayList<>();

		try {

			// Authenticate the merchantId
			optimiserUtility.authenticateMerchantId(merchantId, errors);

			if (!ApplicationUtils.isValid(errors)) {

				merchantGatewayIdList.forEach(merchantGatewayId -> {

					final MerchantGateways merchantGateway = getServiceRegistry().getMerchantGatewaysService().findById(merchantGatewayId);

					if (merchantGateway != null) {

						merchantGateway.setPaymentGatewaySequence(merchantGatewayIdList.indexOf(merchantGatewayId) + 1);
						getServiceRegistry().getMerchantGatewaysService().updateMerchantGateways(merchantGateway);
					}
				});
			} else {

				responseEntity = commonErrorResponse(uiOperationResponse, new LinkedHashSet<String>(errors), HttpStatus.BAD_REQUEST);
			}

		} catch (final Exception e) {
			logError(LOGGER, null, e, " ==> Method ==> changeSequenceMerchantGateways ==> Exception ==> ");
			commonErrorResponse(new UIOperationResponse(),
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		LOGGER.info(" ==> Method : changeSequenceMerchantGateways ==> Exit ");
		return responseEntity;
	}
}