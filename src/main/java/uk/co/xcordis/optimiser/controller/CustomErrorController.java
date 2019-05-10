package uk.co.xcordis.optimiser.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import uk.co.xcordis.optimiser.util.ApplicationConstants;
import uk.co.xcordis.optimiser.util.ApplicationURIConstants;
import uk.co.xcordis.optimiser.util.ErrorDataEnum;

/**
 * The <code>ApplicationFilter</code> class responsible to verify every request coming in <b>Optimiser</b> application for session or authentication.
 *
 * @author Rob Atkin
 */
@Controller
public class CustomErrorController extends BaseController implements ErrorController, ApplicationURIConstants, ApplicationConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomErrorController.class);

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.boot.autoconfigure.web.ErrorController#getErrorPath()
	 */
	@Override
	public String getErrorPath() {

		return ERROR_PAGE_URL;
	}

	/**
	 * This <code>error</code> is use to load application error page when any type of error occurs into application.
	 *
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = ERROR_PAGE_URL, method = { RequestMethod.GET, RequestMethod.POST })
	public String error(final HttpServletResponse response, final ModelMap modelMap) {

		LOGGER.info(" ==> Method : error ==> Enter");

		try {

			String errorMessage = "";

			switch (response.getStatus()) {
				case 400:
					errorMessage = getMessageSource().getMessage(ErrorDataEnum.HTTP_ERROR_400.message(), null, null);
					break;
				case 401:
					errorMessage = getMessageSource().getMessage(ErrorDataEnum.HTTP_ERROR_401.message(), null, null);
					break;
				case 403:
					errorMessage = getMessageSource().getMessage(ErrorDataEnum.HTTP_ERROR_403.message(), null, null);
					break;
				case 404:
					errorMessage = getMessageSource().getMessage(ErrorDataEnum.HTTP_ERROR_404.message(), null, null);
					break;
				case 405:
					errorMessage = getMessageSource().getMessage(ErrorDataEnum.HTTP_ERROR_405.message(), null, null);
					break;
				case 503:
					errorMessage = getMessageSource().getMessage(ErrorDataEnum.HTTP_ERROR_503.message(), null, null);
					break;

				default:
					errorMessage = getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null);;
					break;
			}

			modelMap.addAttribute(STATUS_LABLE, response.getStatus());
			modelMap.addAttribute(MESSAGE_LABEL, errorMessage);

		} catch (final Exception e) {
			LOGGER.error(" ==> Method : error ==> Exception ==> " + e);
		}

		LOGGER.info(" ==> Method : error ==> Exit");
		return ERROR_PAGE_VIEW;
	}
}
