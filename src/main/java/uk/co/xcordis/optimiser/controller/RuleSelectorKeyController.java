package uk.co.xcordis.optimiser.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import uk.co.xcordis.optimiser.model.RuleSelectorKeys;
import uk.co.xcordis.optimiser.util.ApplicationConstants;
import uk.co.xcordis.optimiser.util.ApplicationURIConstants;
import uk.co.xcordis.optimiser.util.ApplicationUtils;
import uk.co.xcordis.optimiser.util.ErrorDataEnum;

/**
 * The <code>RuleSelectorKeyController</code> class responsible for handling all the Rule selector key related configuration in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Controller
public class RuleSelectorKeyController extends BaseController implements ApplicationURIConstants, ApplicationConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(RuleSelectorKeyController.class);

	/**
	 * The <code>listRuleSelectorKey</code> method is used to load List of RuleSelectorKey Page.
	 *
	 * @param model
	 * @return
	 */
	@GetMapping(RULESELECTORKEY_URL)
	public ModelAndView listRuleSelectorKey(@RequestParam(value = REQUESTPARAM_CODE_LABEL, required = false) final String code) {

		LOGGER.info(" ==> Method : listRuleSelectorKey ==> Enter");

		final ModelAndView modelAndView = new ModelAndView(ApplicationURIConstants.LIST_RULESELECTORKEYS_VIEW);

		try {

			if (!ApplicationUtils.isEmpty(code)) {
				modelAndView.addObject(ApplicationConstants.STATUS_SUCCESS, getMessageByCode(code, RULE_SELECTOR_KEY_LABEL));
			} else {
				return modelAndView;
			}

		} catch (final Exception e) {

			logError(LOGGER, null, e, " ==> Method ==> listRuleSelectorKey ==> Exception ==> ");
			modelAndView.addObject(ApplicationConstants.STATUS_ERROR, ApplicationConstants.COMMON_ERRORMESSAGE);
		}

		LOGGER.info(" ==> Method : listRuleSelectorKey ==> Exit");
		return modelAndView;
	}

	/**
	 * The <code>loadAddRuleSelectorKeysPage</code> method is used to load Rule Selector Key add Page.
	 *
	 * @return
	 */
	@GetMapping(ADD_RULESELECTORKEY_VIEW_URL)
	public String loadAddRuleSelectorKeysPage() {

		LOGGER.info(" ==> Method ==> loadAddRuleSelectorKeysPage ==> Called");

		return ApplicationURIConstants.ADD_RULESELECTORKEY_VIEW;
	}

	/**
	 * This <code>loadViewRuleSelectorKeysPage</code> method is used to view a selectorKey details.
	 *
	 * @param selectorKey
	 * @return
	 */
	@GetMapping(DETAILS_RULESELECTORKEY_VIEW_URL)
	public ModelAndView loadViewRuleSelectorKeysPage(@PathVariable(value = ApplicationConstants.SELECTOR_KEY, required = false) final String selectorKey) {

		LOGGER.info(" ==> Method : loadViewRuleSelectorKeysPage ==> Enter");

		final ModelAndView modelAndView = new ModelAndView(VIEW_RULESELECTORKEY_VIEW);

		try {

			if (selectorKey != null) {
				modelAndView.addObject(ApplicationConstants.SELECTOR_KEY, selectorKey);
			} else {
				modelAndView.addObject(ApplicationConstants.STATUS_ERROR,
						getMessageSource().getMessage(ErrorDataEnum.COMMON_RULE_NOT_FOUND_ERROR_MESSAGE.message(), null, null));
				return modelAndView;
			}

		} catch (final Exception e) {

			logError(LOGGER, null, e, " ==> Method ==> loadViewRuleSelectorKeysPage ==> Exception ==> ");
			modelAndView.addObject(ApplicationConstants.STATUS_ERROR, getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null));
		}

		LOGGER.info(" ==> Method : loadViewRuleSelectorKeysPage ==> Exit");
		return modelAndView;
	}

	/**
	 * This <code>loadEditRuleSelectorKeysPage</code> method is used to view a selectorKey edit page.
	 *
	 * @param selectorKey
	 * @return
	 */
	@GetMapping(EDIT_RULESELECTORKEY_VIEW_URL)
	public ModelAndView loadEditRuleSelectorKeysPage(@PathVariable(value = ApplicationConstants.SELECTOR_KEY, required = false) final String selectorKey) {

		LOGGER.info(" ==> Method ==> loadEditRuleSelectorKeysPage ==> Called");

		final ModelAndView modelAndView = new ModelAndView();

		boolean isError = Boolean.FALSE;

		try {

			if (selectorKey != null) {

				final RuleSelectorKeys ruleSelectorKeys = getServiceRegistry().getRuleSelectorKeysService().findById(selectorKey);

				if (ruleSelectorKeys != null) {

					modelAndView.addObject(ApplicationConstants.RULE_SELECTOR_KEY, ruleSelectorKeys);
					modelAndView.setViewName(ApplicationURIConstants.EDIT_RULESELECTORKEY_VIEW);

				} else {
					isError = Boolean.TRUE;
				}

			} else {

				isError = Boolean.TRUE;
			}

			if (isError) {
				modelAndView.addObject(ApplicationConstants.STATUS_ERROR,
						getMessageSource().getMessage(ErrorDataEnum.COMMON_RULE_NOT_FOUND_ERROR_MESSAGE.message(), null, null));
			}

		} catch (final Exception e) {

			logError(LOGGER, selectorKey, e, " ==> Method ==> loadEditRuleSelectorKeysPage ==> Exception ==> ");
			modelAndView.addObject(ApplicationConstants.STATUS_ERROR, ApplicationConstants.COMMON_ERRORMESSAGE);
		}

		LOGGER.info(" ==> Method ==> loadEditRuleSelectorKeysPage ==> Exit");
		return modelAndView;
	}

}
