package uk.co.xcordis.optimiser.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import uk.co.xcordis.optimiser.model.ApplicationConfiguration;
import uk.co.xcordis.optimiser.util.ApplicationConstants;
import uk.co.xcordis.optimiser.util.ApplicationURIConstants;
import uk.co.xcordis.optimiser.util.ApplicationUtils;
import uk.co.xcordis.optimiser.util.ErrorDataEnum;

/**
 * The <code>ApplicationConfigurationController</code> class responsible for handling all Application configuration related UI redirections in <b>Optimiser</b>
 * application.
 *
 *
 * @author Rob Atkin
 */
@Controller
public class ApplicationConfigurationController extends BaseController implements ApplicationURIConstants, ApplicationConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConfigurationController.class);

	/**
	 * The <code>listAppConfigPage</code> method is used to load List of Application Configurations Page.
	 *
	 * @param code
	 * @return
	 */
	@GetMapping(APP_CONFIG_URL)
	public ModelAndView listAppConfigPage(@RequestParam(value = REQUESTPARAM_CODE_LABEL, required = false) final String code) {

		LOGGER.info(" ==> Method : listAppConfigPage ==> Enter");

		final ModelAndView modelAndView = new ModelAndView(ApplicationURIConstants.LIST_APP_CONFIG_VIEW);

		try {

			if (!ApplicationUtils.isEmpty(code)) {
				modelAndView.addObject(ApplicationConstants.STATUS_SUCCESS, getMessageByCode(code, APP_CONFIG_LABEL));
			}

		} catch (final Exception e) {

			logError(LOGGER, null, e, " ==> Method ==> listAppConfigPage ==> Exception ==> ");
			modelAndView.addObject(ApplicationConstants.STATUS_ERROR, ApplicationConstants.COMMON_ERRORMESSAGE);
		}

		LOGGER.info(" ==> Method : listAppConfigPage ==> Exit");
		return modelAndView;
	}

	/**
	 * The <code>addAppConfigsPage</code> method is used to redirect to Application configuration add Page.
	 *
	 * @return
	 */
	@GetMapping(ADD_APP_CONFIG_VIEW_URL)
	public String addAppConfigsPage() {

		LOGGER.info(" ==> Method ==> addAppConfigsPage ==> Called");

		return ApplicationURIConstants.ADD_APP_CONFIG_VIEW;
	}

	/**
	 * This <code>editAppConfigPage</code> method is used to redirect to Application Configuration Edit page.
	 *
	 * @param appConfigId
	 * @return
	 */
	@GetMapping(EDIT_APP_CONFIG_VIEW_URL)
	public ModelAndView editAppConfigPage(@PathVariable(value = ApplicationConstants.APP_CONFIG_ID_LABEL, required = false) final UUID appConfigId) {

		LOGGER.info(" ==> Method ==> editAppConfigPage ==> Enter");

		final ModelAndView modelAndView = new ModelAndView(EDIT_APP_CONFIG_VIEW);

		boolean isError = Boolean.FALSE;

		try {

			if (appConfigId != null) {

				final ApplicationConfiguration applicationConfiguration = getServiceRegistry().getAppConfigService().getAppConfigById(appConfigId);

				if (applicationConfiguration != null) {

					modelAndView.addObject(ApplicationConstants.APP_CONFIG, applicationConfiguration);

				} else {
					isError = Boolean.TRUE;
				}

			} else {

				isError = Boolean.TRUE;
			}

			if (isError) {
				modelAndView.addObject(ApplicationConstants.STATUS_ERROR,
						getMessageSource().getMessage(ErrorDataEnum.COMMON_APP_CONFIG_NOT_FOUND_ERROR_MESSAGE.message(), null, null));
			}

		} catch (final Exception e) {

			logError(LOGGER, String.valueOf(appConfigId), e, " ==> Method ==> editAppConfigPage ==> Exception ==> ");
			modelAndView.addObject(ApplicationConstants.STATUS_ERROR, ApplicationConstants.COMMON_ERRORMESSAGE);
		}

		LOGGER.info(" ==> Method ==> editAppConfigPage ==> Exit");
		return modelAndView;
	}
}
