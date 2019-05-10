package uk.co.xcordis.optimiser.controller;

import java.util.UUID;

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
 * The <code>RequestDataController</code> class responsible for handling all the request data related information in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Controller
public class RequestDataController extends BaseController implements ApplicationURIConstants, ApplicationConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(RequestDataController.class);

	/**
	 * This <code>loadRequestDataListPage</code> method is used to load request data list page.
	 *
	 * @return
	 */
	@GetMapping(REQUEST_DATA)
	public ModelAndView loadRequestDataListPage() {

		LOGGER.info(" ==> Method : loadRequestDataListPage ==> Called");
		return new ModelAndView(LIST_REQUESTDATA_VIEW);
	}

	/**
	 * This <code>loadViewRequestDataPage</code> method is used to load the view Request Data Details Page.
	 *
	 * @param requestDataId
	 * @return
	 */
	@GetMapping(DETAILS_REQUESTDATA_VIEW_URL)
	public ModelAndView loadViewRequestDataPage(@PathVariable(required = false) final UUID requestDataId) {

		LOGGER.info(" ==> Method : loadViewRequestDataPage ==> Enter");

		final ModelAndView modelAndView = new ModelAndView(VIEW_REQUESTDATA_VIEW);

		try {

			if (requestDataId != null) {
				modelAndView.addObject(REQUEST_DATA_LABEL, getServiceRegistry().getRequestDataService().findById(requestDataId));
			} else {
				modelAndView.addObject(STATUS_ERROR, getMessageSource().getMessage(ErrorDataEnum.COMMON_BADREQUEST_ERROR_MESSAGE.message(), null, null));
			}

		} catch (final Exception e) {

			logError(LOGGER, null, e, " ==> Method ==> loadViewRequestDataPage ==> Exception ==> ");
			modelAndView.addObject(ApplicationConstants.STATUS_ERROR, getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null));
		}

		LOGGER.info(" ==> Method : loadViewRequestDataPage ==> Exit");
		return modelAndView;
	}
}
