package uk.co.xcordis.optimiser.restcontroller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
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
import uk.co.xcordis.optimiser.dto.UIOperationResponse;
import uk.co.xcordis.optimiser.model.MerchantRules;
import uk.co.xcordis.optimiser.model.Rules;
import uk.co.xcordis.optimiser.rules.RuleOperationEnum;
import uk.co.xcordis.optimiser.util.ApplicationConstants;
import uk.co.xcordis.optimiser.util.ApplicationURIConstants;
import uk.co.xcordis.optimiser.util.ApplicationUtils;
import uk.co.xcordis.optimiser.util.ErrorDataEnum;
import uk.co.xcordis.optimiser.util.OptimiserUtility;
import uk.co.xcordis.optimiser.util.RequestResponseStatusEnum;
import uk.co.xcordis.optimiser.util.UIResponseCodeEnum;

/**
 * The <code>MerchantRulesRestController</code> class responsible for handling all the Merchant Rules related Rest request in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@RestController
public class MerchantRulesRestController extends BaseController implements ApplicationURIConstants, ApplicationConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(MerchantRulesRestController.class);

	@Autowired
	private OptimiserUtility optimiserUtility;

	/**
	 * The method <code>getMerchantRulesList</code> method is used to get the list of Merchant Rules.
	 *
	 * @param servletRequest
	 * @return
	 */
	@GetMapping(LIST_MERCHANTRULES_URL)
	public ResponseEntity<UIOperationResponse> getMerchantRulesList(@RequestHeader(value = MERCHANTID_LABLE, required = false) final String merchantId) {

		LOGGER.info(" ==> Method : getMerchantRulesList ==> Enter");

		final UIOperationResponse uiOperationResponse = new UIOperationResponse();
		ResponseEntity<UIOperationResponse> responseEntity;
		final List<String> errors = new ArrayList<>();

		try {

			// Authenticate the merchantId.
			optimiserUtility.authenticateMerchantId(merchantId, errors);

			if (!ApplicationUtils.isValid(errors)) {
				final List<MerchantRules> merchantRulesList = getServiceRegistry().getMerchantRulesService()
						.getMerchantRulesByMerchantId(UUID.fromString(merchantId));

				return new ResponseEntity<>(getUiOperationResponse(RequestResponseStatusEnum.SUCCESS.status(), null, merchantRulesList,
						UIResponseCodeEnum.LIST_SUCCESS_MESSAGE.getCode()), HttpStatus.OK);
			} else {
				responseEntity = commonErrorResponse(new UIOperationResponse(), new LinkedHashSet<String>(errors), HttpStatus.BAD_REQUEST);
			}

		} catch (final Exception e) {

			logError(LOGGER, null, e, " ==> Method ==> getMerchantRulesList ==> Exception ==> ");

			responseEntity = commonErrorResponse(uiOperationResponse,
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		LOGGER.info(" ==> Method : getMerchantRulesList ==> Exit");
		return responseEntity;
	}

	/**
	 * The method <code>addMerchantRule</code> is used to store Merchant Rule Details to the Database.
	 *
	 * @return
	 */
	@PostMapping(value = ADD_MERCHANTRULES_URL)
	public ResponseEntity<UIOperationResponse> addMerchantRule(@RequestHeader(value = MERCHANTID_LABLE, required = false) final String merchantId,
			@RequestBody(required = false) final MerchantRules merchantRules) {

		LOGGER.info(" ==> Method : addMerchantRule ==> called ");

		final UIOperationResponse uiOperationResponse = new UIOperationResponse();

		ResponseEntity<UIOperationResponse> responseEntity = new ResponseEntity<>(HttpStatus.OK);

		List<String> errors = new ArrayList<>();

		boolean isError = Boolean.FALSE;

		try {

			if (merchantRules != null) {

				// Authenticate the merchantId.
				optimiserUtility.authenticateMerchantId(merchantId, errors);

				if (!ApplicationUtils.isValid(errors)) {

					merchantRules.setMerchantId(UUID.fromString(merchantId));

					errors = validateMerchantRulesDetails(merchantRules, Boolean.TRUE);

					if (!ApplicationUtils.isValid(errors)) {

						getServiceRegistry().getMerchantRulesService().addMerchantRule(merchantRules);

						return new ResponseEntity<>(getUiOperationResponse(RequestResponseStatusEnum.SUCCESS.status(),
								new HashSet<>(Arrays.asList(ApplicationConstants.MERCHANT_RULES_LABEL
										+ getMessageSource().getMessage(ErrorDataEnum.ADD_SUCCESS_MESSAGE.message(), null, null))),
								merchantRules.getMerchantRuleId(), UIResponseCodeEnum.ADD_SUCCESS_MESSAGE.getCode()), HttpStatus.OK);

					} else {
						isError = Boolean.TRUE;
					}
				} else {
					isError = Boolean.TRUE;
				}
			} else {
				isError = Boolean.TRUE;
			}

			if (isError) {
				responseEntity = commonErrorResponse(uiOperationResponse, new LinkedHashSet<String>(errors), HttpStatus.BAD_REQUEST);
			}

		} catch (final Exception e) {
			e.printStackTrace();
			logError(LOGGER, String.valueOf(merchantRules.getMerchantId()), e, " ==> Method ==> addMerchantRule ==> Exception ==> ");
			commonErrorResponse(new UIOperationResponse(),
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;
	}

	/**
	 * The method <code>addMerchantRulesFromUI</code> is used to add the Merchant Gateway details from UI request.
	 *
	 * @param merchantId
	 * @param merchantRules
	 * @return
	 */
	@PostMapping(ADD_MERCHANTRULES_UI_URL)
	public ResponseEntity<UIOperationResponse> addMerchantRulesFromUI(@RequestHeader(value = MERCHANTID_LABLE, required = false) final String merchantId,
			@ModelAttribute final MerchantRules merchantRules) {

		LOGGER.info(" ==> Method ==> addMerchantRulesFromUI ==> Enter");

		final UIOperationResponse uiOperationResponse = new UIOperationResponse();
		ResponseEntity<UIOperationResponse> responseEntity;

		try {

			responseEntity = addMerchantRule(merchantId, merchantRules);

		} catch (final Exception e) {

			logError(LOGGER, String.valueOf(merchantRules.getMerchantRuleId()), e, " ==> Method ==> addMerchantGatewayFromUI ==> Exception ==> ");
			responseEntity = commonErrorResponse(uiOperationResponse,
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		LOGGER.info(" ==> Method : addMerchantRulesFromUI ==> Exit");
		return responseEntity;
	}

	/**
	 * The <code>editMerchantRules</code> method is responsible for update the Merchant Rules details in the database.
	 *
	 * @return
	 */
	@PostMapping(EDIT_MERCHANTRULES_URL)
	public ResponseEntity<UIOperationResponse> editMerchantRules(@RequestHeader(value = MERCHANTID_LABLE, required = false) final String merchantId,
			@RequestBody(required = false) final MerchantRules merchantRules) {

		LOGGER.info(" ==> Method : editMerchantRules ==> called ");

		ResponseEntity<UIOperationResponse> responseEntity = new ResponseEntity<>(HttpStatus.OK);

		final UIOperationResponse uiOperationResponse = new UIOperationResponse();

		List<String> errors = new ArrayList<>();

		boolean isError = Boolean.FALSE;

		try {

			if (merchantRules != null && merchantRules.getMerchantRuleId() != null) {

				// Authenticate the merchantId.
				optimiserUtility.authenticateMerchantId(merchantId, errors);

				if (!ApplicationUtils.isValid(errors)) {

					merchantRules.setMerchantId(UUID.fromString(merchantId));

					final MerchantRules merchantRulesDetails = getServiceRegistry().getMerchantRulesService().findById(merchantRules.getMerchantRuleId());

					if (merchantRulesDetails == null) {
						responseEntity = commonErrorResponse(uiOperationResponse,
								new HashSet<>(Arrays
										.asList(getMessageSource().getMessage(ErrorDataEnum.MERCHANT_RULES_NOT_FOUND_ERROR_MESSAGE.message(), null, null))),
								HttpStatus.NOT_FOUND);
					} else {
						errors = validateMerchantRulesDetails(merchantRules, Boolean.FALSE);

						if (!ApplicationUtils.isValid(errors)) {

							getServiceRegistry().getMerchantRulesService().updateMerchantRule(merchantRules);

							responseEntity = new ResponseEntity<>(getUiOperationResponse(RequestResponseStatusEnum.SUCCESS.status(),
									new HashSet<>(Arrays.asList(ApplicationConstants.MERCHANT_RULES_LABEL
											+ getMessageSource().getMessage(ErrorDataEnum.EDIT_SUCCESS_MESSAGE.message(), null, null))),
									merchantRules.getMerchantRuleId(), UIResponseCodeEnum.EDIT_SUCCESS_MESSAGE.getCode()), HttpStatus.OK);

						} else {
							isError = Boolean.TRUE;
						}
					}
				} else {
					isError = Boolean.TRUE;
				}
			} else {
				isError = Boolean.TRUE;
			}

			if (isError) {
				responseEntity = commonErrorResponse(uiOperationResponse, new LinkedHashSet<String>(errors), HttpStatus.BAD_REQUEST);
			}

		} catch (final Exception e) {
			logError(LOGGER, String.valueOf(merchantRules.getMerchantRuleId()), e, " ==> Method ==> editMerchantRules ==> Exception ==> ");
			commonErrorResponse(new UIOperationResponse(),
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;

	}

	/**
	 * The method <code>editMerchantRulesFromUI</code> is used to edit the Merchant Gateway details from UI request.
	 *
	 * @param merchantId
	 * @param merchantRules
	 * @return
	 */
	@PostMapping(EDIT_MERCHANTRULES_UI_URL)
	public ResponseEntity<UIOperationResponse> editMerchantRulesFromUI(@RequestHeader(value = MERCHANTID_LABLE, required = false) final String merchantId,
			@ModelAttribute final MerchantRules merchantRules) {

		LOGGER.info(" ==> Method ==> editMerchantRulesFromUI ==> Enter");

		final UIOperationResponse uiOperationResponse = new UIOperationResponse();
		ResponseEntity<UIOperationResponse> responseEntity;

		try {

			responseEntity = editMerchantRules(merchantId, merchantRules);

		} catch (final Exception e) {

			logError(LOGGER, String.valueOf(merchantRules.getMerchantRuleId()), e, " ==> Method ==> editMerchantRulesFromUI ==> Exception ==> ");
			responseEntity = commonErrorResponse(uiOperationResponse,
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		LOGGER.info(" ==> Method : editMerchantRulesFromUI ==> Exit");
		return responseEntity;
	}

	/**
	 * This <code>validateMerchantRulesDetails</code> method is used to validate the merchant rules details.
	 *
	 * @param merchantRules
	 * @param errors
	 */
	@SuppressWarnings("unlikely-arg-type")
	private List<String> validateMerchantRulesDetails(final MerchantRules merchantRules, final Boolean isAdd) {

		final List<String> errors = new ArrayList<>();

		LOGGER.info(" ==> Method ==> validateMerchantGatewaysDetails ==> Enter");

		if (merchantRules.getMerchantId() == null) {
			errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_BLANK.message(), null, null)
					+ getMessageSource().getMessage(ErrorDataEnum.MERCHANT_ID_ERROR.message(), null, null));
		} else {
			if (getServiceRegistry().getMerchantService().getMerchantByMerchantId(merchantRules.getMerchantId()) == null) {
				errors.add(getMessageSource().getMessage(ErrorDataEnum.MERCHANT_COMMON_NOT_FOUND_ERROR_MESSAGE.message(), null, null));
			}
		}

		if (merchantRules.getRuleId() == null) {
			errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_SELECT.message(), null, null)
					+ getMessageSource().getMessage(ErrorDataEnum.MERCHANT_RULES_RULE_ID_ERROR_MESSAGE.message(), null, null));
		} else {

			Rules rules = getServiceRegistry().getRulesService().findById(merchantRules.getRuleId());

			if (rules == null) {
				errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_RULE_NOT_FOUND_ERROR_MESSAGE.message(), null, null));
			} else {
				if (ApplicationUtils.isValid(rules.getSelectorKey())) {
					if (ApplicationUtils.isEmpty(merchantRules.getOperation())) {
						errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_SELECT.message(), null, null)
								+ getMessageSource().getMessage(ErrorDataEnum.MERCHANT_RULES_OPRATION_ERROR.message(), null, null));
					} else {
						if (Arrays.asList(RuleOperationEnum.values()).contains(merchantRules.getOperation())) {
							errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_FIELD_NOT_VALID_ERROR_MESSAGE.message(),
									new Object[] { getMessageSource().getMessage(ErrorDataEnum.MERCHANT_RULES_OPRATION_ERROR.message(), null, null) }, null));
						}
					}
				}
			}
		}

		if (ApplicationUtils.isEmpty(merchantRules.getMerchantRulesSequence())) {
			errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_BLANK.message(), null, null)
					+ getMessageSource().getMessage(ErrorDataEnum.MERCHANT_RULES_SEQUENCE_ERROR.message(), null, null));
		} else {
			if (!ApplicationUtils.isOnlyNumber(String.valueOf(merchantRules.getMerchantRulesSequence()))) {
				errors.add(getMessageSource().getMessage(ErrorDataEnum.ERROR_ONLY_INTEGER.message(), null, null)
						+ getMessageSource().getMessage(ErrorDataEnum.MERCHANT_RULES_SEQUENCE_ERROR.message(), null, null));
			} else {
				if (isAdd && getServiceRegistry().getMerchantRulesService().getMerchantRulesByMerchantId(merchantRules.getMerchantId()).stream()
						.map(MerchantRules::getMerchantRulesSequence).collect(Collectors.toList()).contains(merchantRules.getMerchantRulesSequence())) {
					errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_EXISTS_ERROR_MESSAGE.message(),
							new Object[] { getMessageSource().getMessage(ErrorDataEnum.MERCHANT_RULES_SEQUENCE_ERROR.message(), null, null) }, null));
				} else {
					if (!isAdd
							&& !getServiceRegistry().getMerchantRulesService().findById(merchantRules.getMerchantRuleId()).getMerchantRulesSequence()
									.equals(merchantRules.getMerchantRulesSequence())
							&& getServiceRegistry().getMerchantRulesService().getMerchantRulesByMerchantId(merchantRules.getMerchantId()).stream()
									.map(MerchantRules::getMerchantRulesSequence).collect(Collectors.toList())
									.contains(merchantRules.getMerchantRulesSequence())) {
						errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_EXISTS_ERROR_MESSAGE.message(),
								new Object[] { getMessageSource().getMessage(ErrorDataEnum.MERCHANT_RULES_SEQUENCE_ERROR.message(), null, null) }, null));
					}
				}
			}
		}

		if (merchantRules.getRuleId() != null) {
			Rules rules = getServiceRegistry().getRulesService().findById(merchantRules.getRuleId());

			if (ApplicationUtils.isValid(rules.getSelectorKey()) && !merchantRules.getEntryCriteria().keySet().containsAll(rules.getSelectorKey())) {
				errors.add(getMessageSource().getMessage(ErrorDataEnum.MERCHANT_RULES_ENTRY_CRITERIA_MISMATCH_ERROR.message(), null, null));
			} else {
				if (ApplicationUtils.isValid(merchantRules.getEntryCriteria()) && ApplicationUtils.isValid(merchantRules.getEntryCriteria().values().stream()
						.filter(value -> value.isEmpty() || value.equals(null)).collect(Collectors.toList()))) {
					errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_BLANK.message(), null, null)
							+ getMessageSource().getMessage(ErrorDataEnum.MERCHANT_RULES_ENTRY_CRITERIA_VALUE_ERROR.message(), null, null));
				} else {
					if (ApplicationUtils.isValid(merchantRules.getEntryCriteria())) {
						merchantRules.getEntryCriteria().entrySet().stream().forEach(map -> {

							if (map.getKey().equalsIgnoreCase(ApplicationConstants.AMOUNT_LABEL)) {

								if (!ApplicationUtils.isValidAmountWithSemicolon(map.getValue())) {
									errors.add(getMessageSource().getMessage(ErrorDataEnum.INVALID_AMOUNT_ERROR.message(), null, null) + map.getKey());
								}
							} else {

								if (!ApplicationUtils.isValidValueWithSemicolonAndDash(map.getValue())) {
									errors.add(
											getMessageSource().getMessage(ErrorDataEnum.ERROR_ALPHANUMERICSEMICOLONDASH.message(), null, null) + map.getKey());
								}

							}
						});
					}
				}
			}
		}

		if (ApplicationUtils.isValid(merchantRules.getMerchantRuleParam())) {

			// If merchant rule param key is not empty then value must be mandatory
			merchantRules.getMerchantRuleParam().forEach((key, value) -> {

				if (!ApplicationUtils.isEmpty(key) && ApplicationUtils.isEmpty(value)) {
					errors.add(getMessageSource().getMessage(ErrorDataEnum.MERCHANT_RULES_PARAMETER_VALUE_ERROR_MESSAGE.message(),
							new Object[] { key, null, null }, null));
				} else if (ApplicationUtils.isEmpty(key) && !ApplicationUtils.isEmpty(value)) {
					errors.add(getMessageSource().getMessage(ErrorDataEnum.MERCHANT_RULES_PARAMETER_KEY_ERROR_MESSAGE.message(),
							new Object[] { value, null, null }, null));
				}
			});
		}

		LOGGER.info(" ==> Method ==> validateMerchantGatewaysDetails ==> Exit");

		return errors;
	}

	/**
	 * This <code>viewMerchantRule</code> method is used view the Merchant Rule Details.
	 *
	 * @param merchantId
	 * @param merchantRuleId
	 * @return
	 */
	@GetMapping(VIEW_MERCHANTRULES_URL)
	public ResponseEntity<UIOperationResponse> viewMerchantRule(@PathVariable(required = false) final String merchantRuleId) {

		LOGGER.info(" ==> Method ==> viewMerchantRule ==> Enter");

		final UIOperationResponse uiOperationResponse = new UIOperationResponse();
		ResponseEntity<UIOperationResponse> responseEntity;
		final List<String> errors = new ArrayList<>();

		try {

			if (!ApplicationUtils.isEmpty(merchantRuleId)) {

				if (!ApplicationUtils.isValid(errors)) {

					final MerchantRules merchantRules = getServiceRegistry().getMerchantRulesService().findById(UUID.fromString(merchantRuleId));
					if (merchantRules == null) {

						responseEntity = commonErrorResponse(uiOperationResponse,
								new HashSet<>(Arrays
										.asList(getMessageSource().getMessage(ErrorDataEnum.MERCHANT_RULES_NOT_FOUND_ERROR_MESSAGE.message(), null, null))),
								HttpStatus.NOT_FOUND);
					} else {

						return new ResponseEntity<>(getUiOperationResponse(RequestResponseStatusEnum.SUCCESS.status(), null, merchantRules,
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

			logError(LOGGER, String.valueOf(merchantRuleId), e, " ==> Method ==> viewMerchantRule ==> Exception ==> ");
			responseEntity = commonErrorResponse(uiOperationResponse,
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		LOGGER.info(" ==> Method : viewMerchantRule ==> Exit");
		return responseEntity;
	}

	/**
	 * This <code>deleteMerchantRule</code> method is used delete the Merchant Rule Details.
	 *
	 * @param merchantRuleId
	 * @return
	 */
	@DeleteMapping(DELETE_MERCHANTRULES_URL)
	public ResponseEntity<UIOperationResponse> deleteMerchantRule(@PathVariable(required = false) final String merchantRuleId) {

		LOGGER.info(" ==> Method ==> deleteMerchantRule ==> Enter");

		final UIOperationResponse uiOperationResponse = new UIOperationResponse();
		ResponseEntity<UIOperationResponse> responseEntity;
		final List<String> errors = new ArrayList<>();

		try {

			if (!ApplicationUtils.isEmpty(merchantRuleId)) {

				if (!ApplicationUtils.isValid(errors)) {

					getServiceRegistry().getMerchantRulesService().softDelete(UUID.fromString(merchantRuleId));

					return new ResponseEntity<>(getUiOperationResponse(RequestResponseStatusEnum.SUCCESS.status(), null, merchantRuleId,
							UIResponseCodeEnum.DELETE_SUCCESS_MESSAGE.getCode()), HttpStatus.OK);

				} else {
					responseEntity = commonErrorResponse(uiOperationResponse, new LinkedHashSet<String>(errors), HttpStatus.BAD_REQUEST);
				}

			} else {
				responseEntity = commonErrorResponse(uiOperationResponse,
						new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_BADREQUEST_ERROR_MESSAGE.message(), null, null))),
						HttpStatus.BAD_REQUEST);
			}

		} catch (final Exception e) {

			logError(LOGGER, String.valueOf(merchantRuleId), e, " ==> Method ==> deleteMerchantRule ==> Exception ==> ");
			responseEntity = commonErrorResponse(uiOperationResponse,
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		LOGGER.info(" ==> Method : deleteMerchantRule ==> Exit");
		return responseEntity;
	}

	/**
	 * The <code>changeSequenceMerchantRules</code> method is responsible for update the Merchant Rules sequence in the database.
	 *
	 * @return
	 */
	@PostMapping(SEQUENCE_MERCHANTRULES_URL)
	public ResponseEntity<UIOperationResponse> changeSequenceMerchantRules(@RequestHeader(value = MERCHANTID_LABLE, required = false) final String merchantId,
			@RequestBody(required = false) final List<UUID> merchantRulesIdList) {

		LOGGER.info(" ==> Method : changeSequenceMerchantRules ==> called ");

		final UIOperationResponse uiOperationResponse = new UIOperationResponse();

		ResponseEntity<UIOperationResponse> responseEntity = null;

		final List<String> errors = new ArrayList<>();

		try {

			// Authenticate the merchantId.
			optimiserUtility.authenticateMerchantId(merchantId, errors);

			if (!ApplicationUtils.isValid(errors)) {

				merchantRulesIdList.forEach(merchantRulesId -> {
					final MerchantRules merchantRules = getServiceRegistry().getMerchantRulesService().findById(merchantRulesId);
					if (merchantRules != null) {
						merchantRules.setMerchantRulesSequence(merchantRulesIdList.indexOf(merchantRulesId) + 1);
						getServiceRegistry().getMerchantRulesService().updateMerchantRule(merchantRules);
					}

				});

			} else {

				responseEntity = commonErrorResponse(uiOperationResponse, new LinkedHashSet<String>(errors), HttpStatus.BAD_REQUEST);
			}

		} catch (final Exception e) {
			logError(LOGGER, null, e, " ==> Method ==> changeSequenceMerchantRules ==> Exception ==> ");
			commonErrorResponse(new UIOperationResponse(),
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;

	}

}
