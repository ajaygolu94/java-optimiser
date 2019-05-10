package uk.co.xcordis.optimiser.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import uk.co.xcordis.optimiser.dto.UIOperationResponse;
import uk.co.xcordis.optimiser.model.Job;
import uk.co.xcordis.optimiser.restcontroller.JobRestController;
import uk.co.xcordis.optimiser.util.ApplicationConstants;
import uk.co.xcordis.optimiser.util.ApplicationURIConstants;
import uk.co.xcordis.optimiser.util.ApplicationUtils;
import uk.co.xcordis.optimiser.util.ErrorDataEnum;

/**
 * The <code>JobController</code> class responsible for handling all Jobs related UI redirections in <b>Optimiser</b> application.
 *
 *
 * @author Rob Atkin
 */
@Controller
public class JobController extends BaseController implements ApplicationURIConstants, ApplicationConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(JobController.class);

	@Autowired
	private JobRestController jobRestController;

	/**
	 * The <code>listJobPage</code> method is used to load List of Jobs Page.
	 *
	 * @param code
	 * @return
	 */
	@GetMapping(JOB_URL)
	public ModelAndView listJobPage(@RequestParam(value = REQUESTPARAM_CODE_LABEL, required = false) final String code) {

		LOGGER.info(" ==> Method : listJobPage ==> Enter");

		final ModelAndView modelAndView = new ModelAndView(ApplicationURIConstants.LIST_JOB_VIEW);

		try {

			if (!ApplicationUtils.isEmpty(code)) {
				modelAndView.addObject(ApplicationConstants.STATUS_SUCCESS, getMessageByCode(code, JOB_LABEL));
			}

		} catch (final Exception e) {

			logError(LOGGER, null, e, " ==> Method ==> listJobPage ==> Exception ==> ");
			modelAndView.addObject(ApplicationConstants.STATUS_ERROR, ApplicationConstants.COMMON_ERRORMESSAGE);
		}

		LOGGER.info(" ==> Method : listJobPage ==> Exit");
		return modelAndView;
	}

	/**
	 * The <code>addJobsPage</code> method is used to redirect to Jobs add Page.
	 *
	 * @return
	 */
	@GetMapping(ADD_JOB_VIEW_URL)
	public ModelAndView addJobsPage() {

		LOGGER.info(" ==> Method ==> addJobsPage ==> Enter");

		final ModelAndView modelAndView = new ModelAndView(ADD_JOB_VIEW);

		try {

			modelAndView.addObject(LIST_OF_SCHEDULER_CLASS, jobRestController.getJobClasses());

		} catch (final Exception e) {

			logError(LOGGER, null, e, " ==> Method ==> addJobsPage ==> Exception ==> ");
			modelAndView.addObject(ApplicationConstants.STATUS_ERROR, ApplicationConstants.COMMON_ERRORMESSAGE);
		}

		LOGGER.info(" ==> Method ==> addJobsPage ==> Exit");
		return modelAndView;
	}

	/**
	 * This <code>editJobPage</code> method is used to redirect to Job Edit page.
	 *
	 * @param jobId
	 * @return
	 */

	@GetMapping(EDIT_JOB_VIEW_URL)
	public ModelAndView editJobPage(@PathVariable(value = ApplicationConstants.JOB_ID_LABEL, required = false) final UUID jobId) {

		LOGGER.info(" ==> Method ==> editJobPage ==> Enter");

		final ModelAndView modelAndView = new ModelAndView(EDIT_JOB_VIEW);

		Boolean isError = Boolean.FALSE;

		try {

			if (jobId != null) {

				final Job job = getServiceRegistry().getJobService().getJobById(jobId);

				if (job != null) {

					modelAndView.addObject(ApplicationConstants.JOB, job);
					modelAndView.addObject(LIST_OF_SCHEDULER_CLASS, jobRestController.getJobClasses());

				} else {
					isError = Boolean.TRUE;
				}

			} else {

				isError = Boolean.TRUE;
			}

			if (isError) {
				modelAndView.addObject(ApplicationConstants.STATUS_ERROR,
						getMessageSource().getMessage(ErrorDataEnum.COMMON_JOB_NOT_FOUND_ERROR_MESSAGE.message(), null, null));
			}

		} catch (final Exception e) {

			logError(LOGGER, String.valueOf(jobId), e, " ==> Method ==> editJobPage ==> Exception ==> ");
			modelAndView.addObject(ApplicationConstants.STATUS_ERROR, ApplicationConstants.COMMON_ERRORMESSAGE);
		}

		LOGGER.info(" ==> Method ==> editJobPage ==> Exit");
		return modelAndView;
	}

	/**
	 * The method <code>viewJobPage</code> is responsible for redirecting to Job view page screen in <b>Optimiser</b> application.
	 *
	 * @param jobId
	 * @return
	 */
	@GetMapping(DETAILS_JOB_VIEW_URL)
	public ModelAndView viewJobPage(@PathVariable(required = false) final UUID jobId) {

		LOGGER.info(" ==> Method : viewJobPage ==> Enter ");

		final ModelAndView modelAndView = new ModelAndView(VIEW_JOB_VIEW);

		try {

			if (jobId != null) {
				modelAndView.addObject(ApplicationConstants.JOB_ID_LABEL, jobId);
			} else {
				modelAndView.addObject(STATUS_ERROR, getMessageSource().getMessage(ErrorDataEnum.COMMON_JOB_NOT_FOUND_ERROR_MESSAGE.message(), null, null));
			}

		} catch (final Exception e) {
			logError(LOGGER, String.valueOf(jobId), e, " ==> Method ==> viewJobPage ==> Exception ==> ");
			commonErrorResponse(new UIOperationResponse(),
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		LOGGER.info(" ==> Method : viewJobPage ==> Exit ");
		return modelAndView;
	}

}
