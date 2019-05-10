package uk.co.xcordis.optimiser.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import uk.co.xcordis.optimiser.model.ApplicationUser;
import uk.co.xcordis.optimiser.util.ApplicationConstants;
import uk.co.xcordis.optimiser.util.ApplicationTableConstants;
import uk.co.xcordis.optimiser.util.ApplicationURIConstants;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController extends BaseController implements ApplicationURIConstants, ApplicationConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@GetMapping(value = "/")
	public ModelAndView home(final Locale locale, final Model model, final HttpServletRequest request) {

		LOGGER.info(" ==> Method : home ==> called");

		ModelAndView modelAndView = null;

		try {

			ApplicationUser applicationUser = (ApplicationUser) request.getSession().getAttribute(ApplicationTableConstants.TABLENAME_APPLICATIONUSER);

			if (applicationUser != null && applicationUser.getFirstTimeLogin() != null) {
				if (!applicationUser.getFirstTimeLogin()) {

					modelAndView = new ModelAndView(REDIRECT_LABEL + HOME);
				} else {

					modelAndView = new ModelAndView(REDIRECT_LABEL + SIGNUP_OPTIMISER);
				}

			} else {
				modelAndView = new ModelAndView(LOGIN_VIEW);
			}

		} catch (final Exception e) {
			logError(LOGGER, null, e, " ==> Method ==> home ==> Exception ==> ");
			modelAndView.addObject(ApplicationConstants.STATUS_ERROR, ApplicationConstants.COMMON_ERRORMESSAGE);
		}

		return modelAndView;
	}

	/**
	 * The method <code>homePage</code> is responsible for home page screen in Optimiser application.
	 *
	 * @param request
	 * @param model
	 * @return
	 */
	@GetMapping(value = HOME)
	public ModelAndView homePage(final HttpServletRequest request, final Model model) {

		LOGGER.info(" ==> Method : homePage ==> called");

		ModelAndView modelAndView = null;

		ApplicationUser applicationUser = getApplicationUser(request);

		if (applicationUser != null && applicationUser.getFirstTimeLogin() != null) {
			if (!applicationUser.getFirstTimeLogin()) {

				modelAndView = new ModelAndView(HOME_VIEW);
			} else {

				modelAndView = new ModelAndView(LOGIN_VIEW);
			}
		} else {
			modelAndView = new ModelAndView(LOGIN_VIEW);
		}

		return modelAndView;
	}

	/**
	 * The method <code>getApplicationUser</code> is responsible for get application user object from session of request.
	 *
	 * @param request
	 * @return
	 */
	private ApplicationUser getApplicationUser(final HttpServletRequest request) {

		ApplicationUser applicationUser = null;

		if (ApplicationTableConstants.TABLENAME_APPLICATIONUSER != null
				&& request.getSession().getAttribute(ApplicationTableConstants.TABLENAME_APPLICATIONUSER) != null) {

			applicationUser = (ApplicationUser) request.getSession().getAttribute(ApplicationTableConstants.TABLENAME_APPLICATIONUSER);
		}
		return applicationUser;
	}

}
