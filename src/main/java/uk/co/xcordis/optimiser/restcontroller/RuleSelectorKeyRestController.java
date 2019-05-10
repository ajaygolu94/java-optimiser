package uk.co.xcordis.optimiser.restcontroller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import uk.co.xcordis.optimiser.controller.BaseController;
import uk.co.xcordis.optimiser.dto.UIOperationResponse;
import uk.co.xcordis.optimiser.model.RuleSelectorKeys;
import uk.co.xcordis.optimiser.util.ApplicationConstants;
import uk.co.xcordis.optimiser.util.ApplicationURIConstants;
import uk.co.xcordis.optimiser.util.ApplicationUtils;
import uk.co.xcordis.optimiser.util.ErrorDataEnum;
import uk.co.xcordis.optimiser.util.RequestResponseStatusEnum;
import uk.co.xcordis.optimiser.util.UIResponseCodeEnum;

/**
 * The <code>RuleSelectorKeyController</code> class responsible for handling all the Rule selector key related configuration in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@RestController
public class RuleSelectorKeyRestController extends BaseController implements ApplicationURIConstants, ApplicationConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(RuleSelectorKeyRestController.class);

	/**
	 * This <code>getRuleSelectorKeysList</code> method is used to get the list of all Rule Selector Keys.
	 *
	 * @param servletRequest
	 * @return
	 */
	@GetMapping(LIST_RULESELECTORKEYS_URL)
	public ResponseEntity<UIOperationResponse> getRuleSelectorKeysList(final HttpServletRequest servletRequest) {

		LOGGER.info(" ==> Method : getRuleSelectorKeysList ==> Enter");

		final UIOperationResponse uiOperationResponse = new UIOperationResponse();
		ResponseEntity<UIOperationResponse> responseEntity;

		try {

			return new ResponseEntity<>(
					getUiOperationResponse(RequestResponseStatusEnum.SUCCESS.status(), null,
							getServiceRegistry().getRuleSelectorKeysService().getRuleSelectorKeysList(), UIResponseCodeEnum.LIST_SUCCESS_MESSAGE.getCode()),
					HttpStatus.OK);

		} catch (Exception e) {

			logError(LOGGER, null, e, " ==> Method ==> getRuleSelectorKeysList ==> Exception ==> ");

			responseEntity = commonErrorResponse(uiOperationResponse,
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		LOGGER.info(" ==> Method : getRuleSelectorKeysList ==> Exit");
		return responseEntity;
	}

	/**
	 * This <code>addRuleSelectorKeys</code> method is used to add the addRuleSelectorKeys details.
	 *
	 * @param userId
	 * @param accessToken
	 * @param ruleSelectorKeys
	 * @return
	 */
	@PostMapping(ADD_RULESELECTORKEY_URL)
	public ResponseEntity<UIOperationResponse> addRuleSelectorKeys(@RequestBody(required = false) final RuleSelectorKeys ruleSelectorKeys) {

		LOGGER.info(" ==> Method ==> addRuleSelectorKeys ==> Enter");

		final UIOperationResponse uiOperationResponse = new UIOperationResponse();
		List<String> errors = new ArrayList<>();
		ResponseEntity<UIOperationResponse> responseEntity;

		try {

			if (ruleSelectorKeys != null) {

				errors = validateRuleSelectorKeysDetails(ruleSelectorKeys, Boolean.TRUE);

				if (!ApplicationUtils.isValid(errors)) {

					// Add Rule Data in Rules Table
					getServiceRegistry().getRuleSelectorKeysService().addRuleSelectorKey(ruleSelectorKeys);
					return new ResponseEntity<>(getUiOperationResponse(RequestResponseStatusEnum.SUCCESS.status(),
							new HashSet<>(Arrays.asList(getMessageByCode(UIResponseCodeEnum.ADD_SUCCESS_MESSAGE.getCode(), RULE_SELECTOR_KEY_LABEL))),
							ruleSelectorKeys.getSelectorKey(), UIResponseCodeEnum.ADD_SUCCESS_MESSAGE.getCode()), HttpStatus.OK);

				} else {
					responseEntity = commonErrorResponse(uiOperationResponse, new LinkedHashSet<String>(errors), HttpStatus.BAD_REQUEST);
				}

			} else {

				responseEntity = commonErrorResponse(uiOperationResponse,
						new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_BADREQUEST_ERROR_MESSAGE.message(), null, null))),
						HttpStatus.BAD_REQUEST);
			}

		} catch (final Exception e) {

			logError(LOGGER, ruleSelectorKeys.getSelectorKey(), e, " ==> Method ==> addRuleSelectorKeys ==> Exception ==> ");
			responseEntity = commonErrorResponse(uiOperationResponse,
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		LOGGER.info(" ==> Method : addRuleSelectorKeys ==> Exit");
		return responseEntity;
	}

	/**
	 * This <code>viewRuleSelectorKeyDetails</code> method is used to get the details of a selectorKey.
	 *
	 * @param selectorKey
	 * @return
	 */
	@GetMapping(VIEW_RULESELECTORKEY_URL)
	public ResponseEntity<UIOperationResponse>
			viewRuleSelectorKeyDetails(@PathVariable(value = ApplicationConstants.SELECTOR_KEY, required = false) final String selectorKey) {

		LOGGER.info(" ==> Method ==> viewRuleSelectorKeyDetails ==> Enter");

		final UIOperationResponse uiOperationResponse = new UIOperationResponse();
		ResponseEntity<UIOperationResponse> responseEntity;

		try {

			if (!ApplicationUtils.isEmpty(selectorKey)) {

				final RuleSelectorKeys ruleSelectorKeys = getServiceRegistry().getRuleSelectorKeysService().findById(selectorKey);

				if (ruleSelectorKeys == null) {
					responseEntity = commonErrorResponse(uiOperationResponse,
							new HashSet<>(Arrays.asList(
									getMessageSource().getMessage(ErrorDataEnum.COMMON_RULE_SELECTOR_KEY_NOT_FOUND_ERROR_MESSAGE.message(), null, null))),
							HttpStatus.NOT_FOUND);
				} else {
					return new ResponseEntity<>(getUiOperationResponse(RequestResponseStatusEnum.SUCCESS.status(), null, ruleSelectorKeys,
							UIResponseCodeEnum.VIEW_SUCCESS_MESSAGE.getCode()), HttpStatus.OK);
				}

			} else {
				responseEntity = commonErrorResponse(uiOperationResponse,
						new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_BADREQUEST_ERROR_MESSAGE.message(), null, null))),
						HttpStatus.BAD_REQUEST);
			}

		} catch (final Exception e) {

			logError(LOGGER, selectorKey, e, " ==> Method ==> viewRuleSelectorKeyDetails ==> Exception ==> ");
			responseEntity = commonErrorResponse(uiOperationResponse,
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		LOGGER.info(" ==> Method : viewRuleSelectorKeyDetails ==> Exit");
		return responseEntity;
	}

	/**
	 * This <code>viewRuleSelectorKeyDetails</code> method is used to edit/update a Rule Selector Key by it's Selector Key.
	 *
	 * @param ruleSelectorKeys
	 * @return
	 */
	@PostMapping(EDIT_RULESELECTORKEY_URL)
	public ResponseEntity<UIOperationResponse> editRuleSelectorKeys(@RequestBody(required = false) final RuleSelectorKeys ruleSelectorKeys) {

		LOGGER.info(" ==> Method ==> editRuleSelectorKeys ==> Called");

		final UIOperationResponse uiOperationResponse = new UIOperationResponse();
		List<String> errors = new ArrayList<>();
		ResponseEntity<UIOperationResponse> responseEntity;

		try {

			if (ruleSelectorKeys != null && ruleSelectorKeys.getSelectorKey() != null) {

				RuleSelectorKeys ruleSelectorKeysDetails = getServiceRegistry().getRuleSelectorKeysService().findById(ruleSelectorKeys.getSelectorKey());

				if (ruleSelectorKeysDetails == null) {

					responseEntity = commonErrorResponse(uiOperationResponse,
							new HashSet<>(Arrays.asList(
									getMessageSource().getMessage(ErrorDataEnum.COMMON_RULE_SELECTOR_KEY_NOT_FOUND_ERROR_MESSAGE.message(), null, null))),
							HttpStatus.NOT_FOUND);
				} else {
					errors = validateRuleSelectorKeysDetails(ruleSelectorKeys, Boolean.FALSE);

					if (!ApplicationUtils.isValid(errors)) {

						// Update Rule Selector Key Data in Rule Table
						getServiceRegistry().getRuleSelectorKeysService().updateRuleSelectorKey(ruleSelectorKeys);
						return new ResponseEntity<>(getUiOperationResponse(RequestResponseStatusEnum.SUCCESS.status(),
								new HashSet<>(Arrays.asList(getMessageByCode(UIResponseCodeEnum.EDIT_SUCCESS_MESSAGE.getCode(), RULE_MODULE_LABEL))),
								ruleSelectorKeys.getSelectorKey(), UIResponseCodeEnum.EDIT_SUCCESS_MESSAGE.getCode()), HttpStatus.OK);

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

			logError(LOGGER, ruleSelectorKeys.getSelectorKey(), e, " ==> Method ==> editRuleSelectorKeys ==> Exception ==> ");
			responseEntity = commonErrorResponse(uiOperationResponse,
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		LOGGER.info(" ==> Method : editRuleSelectorKeys ==> Exit");
		return responseEntity;
	}

	/**
	 * This <code>validateRulesDetails</code> method is used to validate the rule details.
	 *
	 * @param ruleSelectorKeys
	 * @param isAdd
	 * @return
	 */
	private List<String> validateRuleSelectorKeysDetails(final RuleSelectorKeys ruleSelectorKeys, final Boolean isAdd) {

		LOGGER.info(" ==> Method ==> validateRuleSelectorKeysDetails ==> Enter");

		final List<String> errors = new ArrayList<>();

		if (ApplicationUtils.isEmpty(ruleSelectorKeys.getSelectorKey())) {
			errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_BLANK.message(), null, null)
					+ getMessageSource().getMessage(ErrorDataEnum.SELECTOR_KEY_ERROR.message(), null, null));
		} else {
			if (!ApplicationUtils.isAlphaNumericAndUnderscore(ruleSelectorKeys.getSelectorKey())) {
				errors.add(getMessageSource().getMessage(ErrorDataEnum.ERROR_ALPHANUMERIC_UNDERSCORE_ONLY.message(), null, null)
						+ getMessageSource().getMessage(ErrorDataEnum.SELECTOR_KEY_ERROR.message(), null, null)
						+ getMessageSource().getMessage(ErrorDataEnum.START_WITH_ALPHA.message(), null, null));
			} else {
				if (isAdd && ApplicationUtils.isValid(getServiceRegistry().getRuleSelectorKeysService().getRuleSelectorKeysList().stream()
						.map(RuleSelectorKeys::getSelectorKey).collect(Collectors.toList()).stream()
						.filter(a -> a.equalsIgnoreCase(ruleSelectorKeys.getSelectorKey())).collect(Collectors.toList()))) {
					errors.add(getMessageSource().getMessage(ErrorDataEnum.SELECTOR_KEY_EXISTS_ERROR.message(), null, null));
				} else {
					if (!isAdd
							&& !getServiceRegistry().getRuleSelectorKeysService().findById(ruleSelectorKeys.getSelectorKey()).getSelectorKey()
									.equalsIgnoreCase(ruleSelectorKeys.getSelectorKey())
							&& ApplicationUtils.isValid(getServiceRegistry().getRuleSelectorKeysService().getRuleSelectorKeysList().stream()
									.map(RuleSelectorKeys::getSelectorKey).collect(Collectors.toList()).stream()
									.filter(a -> a.equalsIgnoreCase(ruleSelectorKeys.getSelectorKey())).collect(Collectors.toList()))) {
						errors.add(getMessageSource().getMessage(ErrorDataEnum.SELECTOR_KEY_EXISTS_ERROR.message(), null, null));
					}
				}
			}
		}

		if (ApplicationUtils.isEmpty(ruleSelectorKeys.getSelectorKeyClass())) {
			errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_BLANK.message(), null, null)
					+ getMessageSource().getMessage(ErrorDataEnum.SELECTOR_KEY_CLASS_ERROR.message(), null, null));
		} else {
			if (!ApplicationUtils.isClassName(ruleSelectorKeys.getSelectorKeyClass())) {
				errors.add(getMessageSource().getMessage(ErrorDataEnum.ERROR_SELECTOR_KEY_CLASS_INVALID.message(), null, null));
			}
		}

		if (ApplicationUtils.isEmpty(ruleSelectorKeys.getOptimiserParam())) {
			errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_BLANK.message(), null, null)
					+ getMessageSource().getMessage(ErrorDataEnum.OPTIMISER_PARAM_ERROR.message(), null, null));
		} else {
			if (!ApplicationUtils.isAlphaNumericAndUnderscore(ruleSelectorKeys.getOptimiserParam())) {
				errors.add(getMessageSource().getMessage(ErrorDataEnum.ERROR_ALPHANUMERIC_UNDERSCORE_ONLY.message(), null, null)
						+ getMessageSource().getMessage(ErrorDataEnum.OPTIMISER_PARAM_ERROR.message(), null, null)
						+ getMessageSource().getMessage(ErrorDataEnum.START_WITH_ALPHA.message(), null, null));
			}
		}

		LOGGER.info(" ==> Method ==> validateRuleSelectorKeysDetails ==> Exit");
		return errors;
	}
}
