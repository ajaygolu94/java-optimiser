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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import uk.co.xcordis.optimiser.controller.BaseController;
import uk.co.xcordis.optimiser.dto.UIOperationResponse;
import uk.co.xcordis.optimiser.model.Rules;
import uk.co.xcordis.optimiser.util.ApplicationConstants;
import uk.co.xcordis.optimiser.util.ApplicationURIConstants;
import uk.co.xcordis.optimiser.util.ApplicationUtils;
import uk.co.xcordis.optimiser.util.ErrorDataEnum;
import uk.co.xcordis.optimiser.util.RequestResponseStatusEnum;
import uk.co.xcordis.optimiser.util.UIResponseCodeEnum;

/**
 * The <code>RulesRestController</code> class responsible for handling all the Rules related requests by Rest API in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@RestController
public class RulesRestController extends BaseController implements ApplicationURIConstants, ApplicationConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(RulesRestController.class);

	/**
	 * This <code>rulesList</code> method is used to get the list of Rules.
	 *
	 * @return
	 */
	@GetMapping(LIST_RULES_URL)
	public ResponseEntity<UIOperationResponse> rulesList() {

		LOGGER.info(" ==> Method : rulesList ==> Enter");

		final UIOperationResponse uiOperationResponse = new UIOperationResponse();
		final ResponseEntity<UIOperationResponse> responseEntity;

		try {

			return new ResponseEntity<>(getUiOperationResponse(RequestResponseStatusEnum.SUCCESS.status(), null,
					getServiceRegistry().getRulesService().getRulesList(), UIResponseCodeEnum.LIST_SUCCESS_MESSAGE.getCode()), HttpStatus.OK);

		} catch (final Exception e) {

			logError(LOGGER, null, e, " ==> Method ==> rulesList ==> Exception ==> ");
			responseEntity = commonErrorResponse(uiOperationResponse,
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		LOGGER.info(" ==> Method : rulesList ==> Exit");
		return responseEntity;
	}

	/**
	 * This <code>viewRuleDetails</code> method is used view the Rule Details.
	 *
	 * @param userId
	 * @param accessToken
	 * @param ruleId
	 * @return
	 */
	@GetMapping(VIEW_RULE_URL)
	public ResponseEntity<UIOperationResponse> viewRuleDetails(@PathVariable(value = ApplicationConstants.RULEID_LABEL, required = false) final UUID ruleId) {

		LOGGER.info(" ==> Method ==> viewRuleDetails ==> Enter");

		final UIOperationResponse uiOperationResponse = new UIOperationResponse();
		ResponseEntity<UIOperationResponse> responseEntity;

		try {

			if (ruleId != null) {

				final Rules rules = getServiceRegistry().getRulesService().findById(ruleId);

				if (rules == null) {
					responseEntity = commonErrorResponse(uiOperationResponse,
							new HashSet<>(
									Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_RULE_NOT_FOUND_ERROR_MESSAGE.message(), null, null))),
							HttpStatus.NOT_FOUND);
				} else {
					return new ResponseEntity<>(
							getUiOperationResponse(RequestResponseStatusEnum.SUCCESS.status(), null, rules, UIResponseCodeEnum.VIEW_SUCCESS_MESSAGE.getCode()),
							HttpStatus.OK);
				}

			} else {
				responseEntity = commonErrorResponse(uiOperationResponse,
						new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_BADREQUEST_ERROR_MESSAGE.message(), null, null))),
						HttpStatus.BAD_REQUEST);
			}

		} catch (final Exception e) {

			logError(LOGGER, String.valueOf(ruleId), e, " ==> Method ==> viewRuleDetails ==> Exception ==> ");
			responseEntity = commonErrorResponse(uiOperationResponse,
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		LOGGER.info(" ==> Method : viewRuleDetails ==> Exit");
		return responseEntity;
	}

	/**
	 * This <code>addRule</code> method is used to add the Rule details.
	 *
	 * @param userId
	 * @param accessToken
	 * @param ruleDetails
	 * @return
	 */
	@PostMapping(ADD_RULE_URL)
	public ResponseEntity<UIOperationResponse> addRule(@RequestBody(required = false) final Rules ruleDetails) {

		LOGGER.info(" ==> Method ==> addRule ==> Called");

		final UIOperationResponse uiOperationResponse = new UIOperationResponse();
		List<String> errors = new ArrayList<>();
		ResponseEntity<UIOperationResponse> responseEntity;

		try {

			if (ruleDetails != null) {

				errors = validateRulesDetails(ruleDetails, Boolean.TRUE);

				if (!ApplicationUtils.isValid(errors)) {

					// Add Rule Data in Rules Table
					getServiceRegistry().getRulesService().addRule(ruleDetails);
					return new ResponseEntity<>(getUiOperationResponse(RequestResponseStatusEnum.SUCCESS.status(),
							new HashSet<>(Arrays.asList(getMessageByCode(UIResponseCodeEnum.ADD_SUCCESS_MESSAGE.getCode(), RULE_MODULE_LABEL))), ruleDetails,
							UIResponseCodeEnum.ADD_SUCCESS_MESSAGE.getCode()), HttpStatus.OK);

				} else {
					responseEntity = commonErrorResponse(uiOperationResponse, new LinkedHashSet<String>(errors), HttpStatus.BAD_REQUEST);
				}

			} else {

				responseEntity = commonErrorResponse(uiOperationResponse,
						new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_BADREQUEST_ERROR_MESSAGE.message(), null, null))),
						HttpStatus.BAD_REQUEST);
			}

		} catch (final Exception e) {

			logError(LOGGER, String.valueOf(ruleDetails.getRuleid()), e, " ==> Method ==> addRule ==> Exception ==> ");
			responseEntity = commonErrorResponse(uiOperationResponse,
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		LOGGER.info(" ==> Method : addRule ==> Exit");
		return responseEntity;
	}

	/**
	 * This <code>editRule</code> method is used to edit/update the Rules Details.
	 *
	 * @param userId
	 * @param accessToken
	 * @param rules
	 * @return
	 */
	@PostMapping(EDIT_RULE_URL)
	public ResponseEntity<UIOperationResponse> editRule(@RequestBody(required = false) final Rules rules) {

		LOGGER.info(" ==> Method ==> editRule ==> Called");

		final UIOperationResponse uiOperationResponse = new UIOperationResponse();
		List<String> errors = new ArrayList<>();
		ResponseEntity<UIOperationResponse> responseEntity;

		try {

			if (rules != null && rules.getRuleid() != null) {

				final Rules rulesDetails = getServiceRegistry().getRulesService().findById(rules.getRuleid());

				if (rulesDetails == null) {
					responseEntity = commonErrorResponse(uiOperationResponse,
							new HashSet<>(
									Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_RULE_NOT_FOUND_ERROR_MESSAGE.message(), null, null))),
							HttpStatus.NOT_FOUND);
				} else {
					errors = validateRulesDetails(rules, Boolean.FALSE);

					if (!ApplicationUtils.isValid(errors)) {

						// Update Rule Data in Rule Table
						getServiceRegistry().getRulesService().updateRule(rules);
						return new ResponseEntity<>(getUiOperationResponse(RequestResponseStatusEnum.SUCCESS.status(),
								new HashSet<>(Arrays.asList(getMessageByCode(UIResponseCodeEnum.EDIT_SUCCESS_MESSAGE.getCode(), RULE_MODULE_LABEL))), rules,
								UIResponseCodeEnum.EDIT_SUCCESS_MESSAGE.getCode()), HttpStatus.OK);

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

			logError(LOGGER, String.valueOf(rules.getRuleid()), e, " ==> Method ==> editRule ==> Exception ==> ");
			responseEntity = commonErrorResponse(uiOperationResponse,
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		LOGGER.info(" ==> Method : editRule ==> Exit");
		return responseEntity;
	}

	/**
	 * This <code>deleteRule</code> method is used to delete the Rule Details.
	 *
	 * @param userId
	 * @param accessToken
	 * @param ruleId
	 * @return
	 */
	@DeleteMapping(DELETE_RULE_URL)
	public ResponseEntity<UIOperationResponse> deleteRule(@PathVariable(value = ApplicationConstants.RULEID_LABEL, required = false) final UUID ruleId) {

		LOGGER.info(" ==> Method ==> deleteRule ==> Enter");

		final UIOperationResponse uiOperationResponse = new UIOperationResponse();
		ResponseEntity<UIOperationResponse> responseEntity;

		try {

			if (ruleId == null) {
				responseEntity = commonErrorResponse(uiOperationResponse,
						new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_BADREQUEST_ERROR_MESSAGE.message(), null, null))),
						HttpStatus.BAD_REQUEST);

			} else {

				getServiceRegistry().getRulesService().deleteRule(ruleId, Boolean.FALSE);
				return new ResponseEntity<>(getUiOperationResponse(RequestResponseStatusEnum.SUCCESS.status(),
						new HashSet<>(Arrays.asList(getMessageByCode(UIResponseCodeEnum.DELETE_SUCCESS_MESSAGE.getCode(), RULE_MODULE_LABEL))), null,
						UIResponseCodeEnum.DELETE_SUCCESS_MESSAGE.getCode()), HttpStatus.OK);
			}

		} catch (final Exception e) {

			logError(LOGGER, String.valueOf(ruleId), e, " ==> Method ==> deleteRule ==> Exception ==> ");
			responseEntity = commonErrorResponse(uiOperationResponse,
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		LOGGER.info(" ==> Method ==> deleteRule ==> Exit");
		return responseEntity;
	}

	/**
	 * This <code>validateRulesDetails</code> method is used to validate the rule details.
	 *
	 * @param ruleDetails
	 * @param isAdd
	 * @return
	 */
	private List<String> validateRulesDetails(final Rules ruleDetails, final Boolean isAdd) {

		LOGGER.info(" ==> Method ==> validateRulesDetails ==> Enter");

		final List<String> errors = new ArrayList<>();

		if (ApplicationUtils.isEmpty(ruleDetails.getRulename())) {
			errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_BLANK.message(), null, null)
					+ getMessageSource().getMessage(ErrorDataEnum.RULENAME_ERROR.message(), null, null));
		} else {

			if (!ApplicationUtils.isOnlyAlphaAndSpace(ruleDetails.getRulename())) {
				errors.add(getMessageSource().getMessage(ErrorDataEnum.ERROR_ALPHAANDSPACE.message(), null, null)
						+ getMessageSource().getMessage(ErrorDataEnum.RULENAME_ERROR.message(), null, null));
			} else {

				if (isAdd && ApplicationUtils.isValid(getServiceRegistry().getRulesService().getRulesList().stream().map(Rules::getRulename)
						.collect(Collectors.toList()).stream().filter(a -> a.equalsIgnoreCase(ruleDetails.getRulename())).collect(Collectors.toList()))) {
					errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_EXISTS_ERROR_MESSAGE.message(),
							new Object[] { getMessageSource().getMessage(ErrorDataEnum.RULENAME_ERROR.message(), null, null) }, null));
				}
			}
		}

		if (ApplicationUtils.isEmpty(ruleDetails.getRuleclass())) {
			errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_SELECT.message(), null, null)
					+ getMessageSource().getMessage(ErrorDataEnum.RULECLASS_ERROR.message(), null, null));
		} else {

			if (!ApplicationUtils.isClassName(ruleDetails.getRuleclass())) {
				errors.add(getMessageSource().getMessage(ErrorDataEnum.ERROR_RULECLASS_INVALID.message(), null, null));
			}
		}

		if (ApplicationUtils.isEmpty(ruleDetails.getDefaultsequence())) {
			errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_BLANK.message(), null, null)
					+ getMessageSource().getMessage(ErrorDataEnum.DEFAULT_SEQUENCE_ERROR.message(), null, null));
		} else {
			if (!ApplicationUtils.isOnlyNumber(String.valueOf(ruleDetails.getDefaultsequence()))) {
				errors.add(getMessageSource().getMessage(ErrorDataEnum.ERROR_ONLY_INTEGER.message(), null, null)
						+ getMessageSource().getMessage(ErrorDataEnum.DEFAULT_SEQUENCE_ERROR.message(), null, null));
			} else {
				if (isAdd && getServiceRegistry().getRulesService().getRulesList().stream().map(Rules::getDefaultsequence).collect(Collectors.toList())
						.contains(ruleDetails.getDefaultsequence())) {
					errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_EXISTS_ERROR_MESSAGE.message(),
							new Object[] { getMessageSource().getMessage(ErrorDataEnum.DEFAULT_SEQUENCE_ERROR.message(), null, null) }, null));
				} else {
					if (!isAdd
							&& !getServiceRegistry().getRulesService().findById(ruleDetails.getRuleid()).getDefaultsequence()
									.equals(ruleDetails.getDefaultsequence())
							&& getServiceRegistry().getRulesService().getRulesList().stream().map(Rules::getDefaultsequence).collect(Collectors.toList())
									.contains(ruleDetails.getDefaultsequence())) {
						errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_EXISTS_ERROR_MESSAGE.message(),
								new Object[] { getMessageSource().getMessage(ErrorDataEnum.DEFAULT_SEQUENCE_ERROR.message(), null, null) }, null));

					}
				}
			}
		}

		LOGGER.info(" ==> Method ==> validateRulesDetails ==> Exit");
		return errors;
	}

	/**
	 * This <code>getSelectorKeysListByRuleName</code> method is used to get the list of SelectorKeys by the rule name assigned to the particular rule.
	 *
	 * @return
	 */
	@GetMapping(LIST_RULES_SELECTORKEYS_URL)
	public ResponseEntity<UIOperationResponse>
			getSelectorKeysListByRuleName(@PathVariable(value = ApplicationConstants.RULE_NAME, required = false) final String ruleName) {

		LOGGER.info(" ==> Method : getSelectorKeysListByRuleName ==> Enter");

		final UIOperationResponse uiOperationResponse = new UIOperationResponse();
		ResponseEntity<UIOperationResponse> responseEntity;

		try {

			final List<String> rulesList = getServiceRegistry().getRulesService().getSelectorKeyListByRuleName(ruleName);

			if (ApplicationUtils.isValid(rulesList)) {
				return new ResponseEntity<>(
						getUiOperationResponse(RequestResponseStatusEnum.SUCCESS.status(), null, rulesList, UIResponseCodeEnum.LIST_SUCCESS_MESSAGE.getCode()),
						HttpStatus.OK);
			} else {
				responseEntity = commonErrorResponse(uiOperationResponse,
						new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_RULE_NOT_FOUND_ERROR_MESSAGE.message(), null, null))),
						HttpStatus.BAD_REQUEST);
			}

		} catch (final Exception e) {

			logError(LOGGER, null, e, " ==> Method ==> getSelectorKeysListByRuleName ==> Exception ==> ");
			responseEntity = commonErrorResponse(uiOperationResponse,
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		LOGGER.info(" ==> Method : getSelectorKeysListByRuleName ==> Exit");
		return responseEntity;
	}

}
