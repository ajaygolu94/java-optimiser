package uk.co.xcordis.optimiser.controller;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import uk.co.xcordis.optimiser.dto.UIOperationResponse;
import uk.co.xcordis.optimiser.model.MerchantRules;
import uk.co.xcordis.optimiser.rules.RuleOperationEnum;
import uk.co.xcordis.optimiser.util.ApplicationConstants;
import uk.co.xcordis.optimiser.util.ApplicationURIConstants;
import uk.co.xcordis.optimiser.util.ApplicationUtils;
import uk.co.xcordis.optimiser.util.ErrorDataEnum;

/**
 * The <code>MerchantRulesController</code> class responsible for handling all the Merchant Rules related UI configuration in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Controller
public class MerchantRulesController extends BaseController implements ApplicationURIConstants, ApplicationConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(MerchantRulesController.class);

	/**
	 * This <code>loadMerchantRulesPage</code> method is used to load Merchant Rules Page.
	 *
	 * @param model
	 * @return
	 */
	@GetMapping(LIST_MERCHANTRULES_VIEW_URL)
	public ModelAndView loadMerchantRulesPage(@PathVariable(value = MERCHANTID_LABLE, required = false) final String merchantId,
			@RequestParam(value = REQUESTPARAM_CODE_LABEL, required = false) final String code) {

		LOGGER.info(" ==> Method : loadMerchantRulesPage ==> Enter");

		final ModelAndView modelAndView = new ModelAndView(ApplicationURIConstants.LIST_MERCHANTRULES_VIEW);

		try {

			if (!ApplicationUtils.isEmpty(merchantId)) {

				modelAndView.addObject(ApplicationConstants.MERCHANTID_LABLE, merchantId);

				if (!ApplicationUtils.isEmpty(code)) {

					modelAndView.addObject(ApplicationConstants.STATUS_SUCCESS, getMessageByCode(code, MERCHANT_RULES_LABEL));

				}

			} else {

				modelAndView.addObject(ApplicationConstants.STATUS_ERROR,
						getMessageSource().getMessage(ErrorDataEnum.COMMON_BADREQUEST_ERROR_MESSAGE.message(), null, null));
				return modelAndView;
			}

		} catch (final Exception e) {

			logError(LOGGER, null, e, " ==> Method ==> loadMerchantRulesPage ==> Exception ==> ");
			modelAndView.addObject(ApplicationConstants.STATUS_ERROR, ApplicationConstants.COMMON_ERRORMESSAGE);
		}

		LOGGER.info(" ==> Method : loadMerchantRulesPage ==> Exit");
		return modelAndView;
	}

	/**
	 * The method <code>loadMerchantRulesAddPage</code> is used to load merchant rules add page screen in <b>Optimiser</b> application.
	 *
	 * @param request
	 * @param model
	 * @return
	 */
	@GetMapping(ADD_MERCHANTRULES_VIEW_URL)
	public ModelAndView loadMerchantRulesAddPage(@PathVariable(value = MERCHANTID_LABLE, required = false) final String merchantId) {

		LOGGER.info(" ==> Method : loadMerchantRulesAddPage ==> called");

		ModelAndView modelAndView = new ModelAndView(ADD_MERCHANTRULES_VIEW);

		try {

			if (!ApplicationUtils.isEmpty(merchantId)) {

				modelAndView.addObject(ApplicationConstants.MERCHANT_RULES_OBJECT_LABEL, new MerchantRules());
				modelAndView.addObject(ApplicationConstants.MERCHANT_RULES_MERCHANT_LIST_OBJECT_LABEL,
						getServiceRegistry().getMerchantService().getAllMerchants());
				modelAndView.addObject(ApplicationConstants.MERCHANT_RULES_RULES_LIST_OBJECT_LABEL, getServiceRegistry().getRulesService().getRulesList());
				modelAndView.addObject(ApplicationConstants.MERCHANT_RULES_OPERATION_LIST_OBJECT_LABEL, Arrays.asList(RuleOperationEnum.values()));

				modelAndView.addObject(ApplicationConstants.MERCHANTID_LABLE, merchantId);

			} else {

				modelAndView.addObject(ApplicationConstants.STATUS_ERROR,
						getMessageSource().getMessage(ErrorDataEnum.COMMON_BADREQUEST_ERROR_MESSAGE.message(), null, null));
				return modelAndView;
			}

		} catch (Exception e) {

			logError(LOGGER, null, e, " ==> Method ==> loadMerchantRulesAddPage ==> Exception ==> ");
			commonErrorResponse(new UIOperationResponse(),
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return modelAndView;
	}

	/**
	 * The method <code>viewMerchantRulePage</code> is responsible for Merchant Rule view page screen in <b>Optimiser</b> application.
	 *
	 * @param merchantRuleId
	 * @param model
	 * @return
	 */
	@GetMapping(DETAILS_MERCHANTRULES_VIEW_URL)
	public ModelAndView viewMerchantRulePage(@PathVariable(required = false) String merchantRuleId) {

		LOGGER.info(" ==> Method : viewMerchantRulePage ==> called ");

		ModelAndView modelAndView = new ModelAndView(VIEW_MERCHANTRULES_VIEW);

		try {

			if (!ApplicationUtils.isEmpty(merchantRuleId)) {
				modelAndView.addObject(ApplicationConstants.MERCHANTRULE_ID, merchantRuleId);

			} else {
				modelAndView.addObject(ApplicationConstants.STATUS_ERROR,
						getMessageSource().getMessage(ErrorDataEnum.MERCHANT_RULES_NOT_FOUND_ERROR_MESSAGE.message(), null, null));
				return modelAndView;
			}
		} catch (Exception e) {
			logError(LOGGER, merchantRuleId, e, " ==> Method ==> viewMerchantRulePage ==> Exception ==> ");
			commonErrorResponse(new UIOperationResponse(),
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return modelAndView;
	}

	/**
	 * This <code>editMerchantRulesPage</code> method is used to load the edit Merchant Rules Page.
	 *
	 * @param merchantRuleId
	 * @return
	 */
	@GetMapping(EDIT_MERCHANTRULES_VIEW_URL)
	public ModelAndView editMerchantRulesPage(@PathVariable(value = MERCHANTRULE_ID, required = false) final UUID merchantRuleId) {

		LOGGER.info(" ==> Method ==> editMerchantRulesPage ==> Enter");

		final ModelAndView modelAndView = new ModelAndView(EDIT_MERCHANTRULES_VIEW);

		try {

			if (merchantRuleId != null) {

				modelAndView.addObject(MERCHANT_RULES_OBJECT_LABEL, getServiceRegistry().getMerchantRulesService().findById(merchantRuleId));
				modelAndView.addObject(ApplicationConstants.MERCHANT_RULES_MERCHANT_LIST_OBJECT_LABEL,
						getServiceRegistry().getMerchantService().getAllMerchants());
				modelAndView.addObject(ApplicationConstants.MERCHANT_RULES_RULES_LIST_OBJECT_LABEL, getServiceRegistry().getRulesService().getRulesList());
				modelAndView.addObject(ApplicationConstants.MERCHANT_RULES_OPERATION_LIST_OBJECT_LABEL, Arrays.asList(RuleOperationEnum.values()));

			} else {
				modelAndView.addObject(ApplicationConstants.STATUS_ERROR,
						getMessageSource().getMessage(ErrorDataEnum.COMMON_BADREQUEST_ERROR_MESSAGE.message(), null, null));
				return modelAndView;
			}

		} catch (final Exception e) {

			logError(LOGGER, null, e, " ==> Method ==> editMerchantRulesPage ==> Exception ==> ");
			modelAndView.addObject(ApplicationConstants.STATUS_ERROR, ApplicationConstants.COMMON_ERRORMESSAGE);
		}

		LOGGER.info(" ==> Method ==> editMerchantRulesPage ==> Exit");
		return modelAndView;
	}

	/**
	 * This <code>loadViewSequencePage</code> method is used to load the view Request Data Details Page.
	 *
	 * @param merchantId
	 * @return
	 */
	@GetMapping(SEQUENCE_MERCHANTRULES_VIEW_URL)
	public ModelAndView loadViewSequencePage(@PathVariable(value = MERCHANTID_LABLE, required = false) final String merchantId) {

		LOGGER.info(" ==> Method : loadViewSequencePage ==> Enter");

		final ModelAndView modelAndView = new ModelAndView(SEQUENCE_MERCHANTRULES_VIEW);

		try {

			List<MerchantRules> merchantRulesList = getServiceRegistry().getMerchantRulesService().getMerchantRulesByMerchantId(UUID.fromString(merchantId));

			// sorting the merchant rule by sequence.
			merchantRulesList.sort(Comparator.comparing(MerchantRules::getMerchantRulesSequence));

			modelAndView.addObject(MERCHANT_RULES_LIST_OBJECT_LABEL, merchantRulesList);

		} catch (final Exception e) {

			logError(LOGGER, null, e, " ==> Method ==> loadViewRequestDataPage ==> Exception ==> ");
			modelAndView.addObject(ApplicationConstants.STATUS_ERROR, getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null));
		}

		LOGGER.info(" ==> Method : loadViewSequencePage ==> Exit");
		return modelAndView;
	}
}
