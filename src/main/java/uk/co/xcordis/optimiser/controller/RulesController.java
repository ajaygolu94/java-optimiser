package uk.co.xcordis.optimiser.controller;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.jeasy.rules.annotation.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import uk.co.xcordis.optimiser.model.RuleSelectorKeys;
import uk.co.xcordis.optimiser.model.Rules;
import uk.co.xcordis.optimiser.util.ApplicationConstants;
import uk.co.xcordis.optimiser.util.ApplicationURIConstants;
import uk.co.xcordis.optimiser.util.ApplicationUtils;
import uk.co.xcordis.optimiser.util.ErrorDataEnum;

/**
 * The <code>RulesController</code> class responsible for handling all the Rules related UI configuration in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Controller
public class RulesController extends BaseController implements ApplicationURIConstants, ApplicationConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(RulesController.class);

	/**
	 * This <code>loadRulesPage</code> method is used to load Rules Page.
	 *
	 * @param model
	 * @return
	 */
	@GetMapping(RULE)
	public ModelAndView loadRulesPage(@RequestParam(value = REQUESTPARAM_CODE_LABEL, required = false) final String code) {

		LOGGER.info(" ==> Method : loadRulesPage ==> Enter");

		final ModelAndView modelAndView = new ModelAndView(ApplicationURIConstants.LIST_RULES_VIEW);

		try {

			if (!ApplicationUtils.isEmpty(code)) {
				modelAndView.addObject(ApplicationConstants.STATUS_SUCCESS, getMessageByCode(code, RULE_MODULE_LABEL));
			} else {
				return modelAndView;
			}

		} catch (final Exception e) {

			logError(LOGGER, null, e, " ==> Method ==> loadRulesPage ==> Exception ==> ");
			modelAndView.addObject(ApplicationConstants.STATUS_ERROR, ApplicationConstants.COMMON_ERRORMESSAGE);
		}

		LOGGER.info(" ==> Method : loadRulesPage ==> Exit");
		return modelAndView;
	}

	/**
	 * This <code>loadViewRulesPage</code> method is used to load the view Rules Details Page.
	 *
	 * @param model
	 * @return
	 */
	@GetMapping(DETAILS_RULE_VIEW_URL)
	public ModelAndView loadViewRulesPage(@PathVariable(value = ApplicationConstants.RULEID_LABEL, required = false) final UUID ruleId) {

		LOGGER.info(" ==> Method : loadViewRulesPage ==> Enter");

		final ModelAndView modelAndView = new ModelAndView(ApplicationURIConstants.VIEW_RULE_VIEW);

		try {

			if (ruleId != null) {
				modelAndView.addObject(RULEID_LABEL, ruleId);
			} else {
				modelAndView.addObject(ApplicationConstants.STATUS_ERROR,
						getMessageSource().getMessage(ErrorDataEnum.COMMON_RULE_NOT_FOUND_ERROR_MESSAGE.message(), null, null));
				return modelAndView;
			}

		} catch (final Exception e) {

			logError(LOGGER, null, e, " ==> Method ==> loadViewRulesPage ==> Exception ==> ");
			modelAndView.addObject(ApplicationConstants.STATUS_ERROR, getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null));
		}

		LOGGER.info(" ==> Method : loadViewRulesPage ==> Exit");
		return modelAndView;
	}

	/**
	 * This <code>loadAddRulePage</code> method is used to load the add Rules Details Page.
	 *
	 * @return
	 */
	@GetMapping(ADD_RULE_VIEW_URL)
	public ModelAndView loadAddRulePage() {

		LOGGER.info(" ==> Method ==> loadAddRulePage ==> Enter");

		final ModelAndView modelAndView = new ModelAndView(ADD_RULE_VIEW);

		try {

			modelAndView.addObject(SELECTOR_KEY_LABEL, getServiceRegistry().getRuleSelectorKeysService().getRuleSelectorKeysList().stream()
					.map(RuleSelectorKeys::getSelectorKey).collect(Collectors.toList()));
			modelAndView.addObject(LIST_OF_RULES_CLASS, getRulesClass());

		} catch (final Exception e) {

			logError(LOGGER, null, e, " ==> Method ==> loadAddRulePage ==> Exception ==> ");
			modelAndView.addObject(ApplicationConstants.STATUS_ERROR, ApplicationConstants.COMMON_ERRORMESSAGE);
		}

		LOGGER.info(" ==> Method ==> loadAddRulePage ==> Exit");
		return modelAndView;
	}

	/**
	 * This <code>loadEditRulePage</code> method is used to load the edit Rules Details Page.
	 *
	 * @param ruleId
	 * @return
	 */
	@GetMapping(EDIT_RULE_VIEW_URL)
	public ModelAndView loadEditRulePage(@PathVariable(value = ApplicationConstants.RULEID_LABEL, required = false) final UUID ruleId) {

		LOGGER.info(" ==> Method ==> loadEditRulePage ==> Called");

		final ModelAndView modelAndView = new ModelAndView();

		try {

			if (ruleId != null) {

				final Rules rules = getServiceRegistry().getRulesService().findById(ruleId);

				if (rules != null) {

					modelAndView.addObject(RULES_LABEL, rules);
					modelAndView.addObject(SELECTOR_KEY_LABEL, getServiceRegistry().getRuleSelectorKeysService().getRuleSelectorKeysList().stream()
							.map(RuleSelectorKeys::getSelectorKey).collect(Collectors.toList()));
					modelAndView.setViewName(ApplicationURIConstants.EDIT_RULE_VIEW);
					modelAndView.addObject(LIST_OF_RULES_CLASS, getRulesClass());

				} else {
					modelAndView.addObject(ApplicationConstants.STATUS_ERROR,
							getMessageSource().getMessage(ErrorDataEnum.COMMON_RULE_NOT_FOUND_ERROR_MESSAGE.message(), null, null));
				}

			} else {

				modelAndView.addObject(ApplicationConstants.STATUS_ERROR,
						getMessageSource().getMessage(ErrorDataEnum.COMMON_RULE_NOT_FOUND_ERROR_MESSAGE.message(), null, null));
			}

		} catch (final Exception e) {

			logError(LOGGER, String.valueOf(ruleId), e, " ==> Method ==> loadEditRulePage ==> Exception ==> ");
			modelAndView.addObject(ApplicationConstants.STATUS_ERROR, ApplicationConstants.COMMON_ERRORMESSAGE);
		}

		LOGGER.info(" ==> Method ==> loadEditRulePage ==> Exit");
		return modelAndView;
	}

	/**
	 * This <code>getRulesClass</code> method is a helper method, used to get List of Rules Class.
	 *
	 * @return
	 */
	private List<String> getRulesClass() {

		try {

			LOGGER.info(" ==> Method ==> getRulesClass ==> Called");

			final ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
			scanner.addIncludeFilter(new AnnotationTypeFilter(Rule.class));

			final Set<BeanDefinition> setOfRulesClass = scanner.findCandidateComponents(BASE_PACKAGE);

			return setOfRulesClass.stream().map(BeanDefinition::getBeanClassName).collect(Collectors.toList());
		} catch (final Exception e) {

			logError(LOGGER, null, e, " ==> Method ==> loadAddRulePage ==> Exception ==> ");
		}

		return Collections.emptyList();
	}
}
