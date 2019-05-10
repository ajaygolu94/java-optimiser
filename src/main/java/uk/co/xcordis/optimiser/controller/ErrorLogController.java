package uk.co.xcordis.optimiser.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import uk.co.xcordis.optimiser.util.ApplicationConstants;
import uk.co.xcordis.optimiser.util.ApplicationURIConstants;
import uk.co.xcordis.optimiser.util.ErrorDataEnum;

/**
 * Handles requests for the Error Log page.
 */
@Controller
public class ErrorLogController extends BaseController implements ApplicationURIConstants, ApplicationConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(ErrorLogController.class);

	/**
	 * The method <code>loadErrorLogPage</code> is responsible for loading the Error Log screen in Optimiser application.
	 *
	 * @return
	 */
	@GetMapping(LIST_ERROR_LOG_VIEW_URL)
	public ModelAndView loadErrorLogPage() {

		LOGGER.info(" ==> Method : loadErrorLogPage ==> Called");
		return new ModelAndView(LIST_ERROR_LOG_VIEW);
	}

	/**
	 * The method<code>loadViewErrorLogPage</code> is used to view the Error log details.
	 *
	 * @param errorLogId
	 * @return
	 */
	@GetMapping(DETAILS_ERRORLOG_VIEW_URL)
	public ModelAndView loadViewErrorLogPage(@PathVariable(value = ApplicationConstants.ERROR_LOG_ID_KEY, required = false) final String errorLogId) {

		LOGGER.info(" ==> Method : loadViewErrorLogPage ==> Enter");

		final ModelAndView modelAndView = new ModelAndView(VIEW_ERRORLOG_VIEW);

		try {

			if (errorLogId != null) {

				modelAndView.addObject(ApplicationConstants.ERROR_LOG_ID_KEY, errorLogId);
			} else {

				modelAndView.addObject(ApplicationConstants.STATUS_ERROR,
						getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_LOG_NOT_FOUND_ERROR_MESSAGE.message(), null, null));
			}

		} catch (final Exception e) {

			logError(LOGGER, null, e, " ==> Method ==> loadViewErrorLogPage ==> Exception ==> ");
			modelAndView.addObject(ApplicationConstants.STATUS_ERROR, getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null));
		}

		LOGGER.info(" ==> Method : loadViewErrorLogPage ==> Exit");
		return modelAndView;
	}
}