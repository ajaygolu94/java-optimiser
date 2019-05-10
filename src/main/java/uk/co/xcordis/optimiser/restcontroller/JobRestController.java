package uk.co.xcordis.optimiser.restcontroller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import uk.co.xcordis.optimiser.controller.BaseController;
import uk.co.xcordis.optimiser.dto.UIOperationResponse;
import uk.co.xcordis.optimiser.model.Job;
import uk.co.xcordis.optimiser.util.ApplicationConstants;
import uk.co.xcordis.optimiser.util.ApplicationURIConstants;
import uk.co.xcordis.optimiser.util.ApplicationUtils;
import uk.co.xcordis.optimiser.util.ErrorDataEnum;
import uk.co.xcordis.optimiser.util.RequestResponseStatusEnum;
import uk.co.xcordis.optimiser.util.UIResponseCodeEnum;

/**
 * The <code>JobRestController</code> class responsible for handling all the request related to Job Module in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@RestController
public class JobRestController extends BaseController implements ApplicationURIConstants, ApplicationConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(JobRestController.class);

	/**
	 * This <code>listJobs</code> method is used to get the list of all Jobs from DB.
	 *
	 * @return
	 */
	@GetMapping(LIST_JOB_URL)
	public ResponseEntity<UIOperationResponse> listJobs() {

		LOGGER.info(" ==> Method : listJobs ==> Enter");

		final UIOperationResponse uiOperationResponse = new UIOperationResponse();
		ResponseEntity<UIOperationResponse> responseEntity;

		try {

			return new ResponseEntity<>(getUiOperationResponse(RequestResponseStatusEnum.SUCCESS.status(), null,
					getServiceRegistry().getJobService().getJobList(), UIResponseCodeEnum.LIST_SUCCESS_MESSAGE.getCode()), HttpStatus.OK);

		} catch (final Exception e) {

			logError(LOGGER, null, e, " ==> Method ==> listJobs ==> Exception ==> ");

			responseEntity = commonErrorResponse(uiOperationResponse,
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		LOGGER.info(" ==> Method : listJobs ==> Exit");
		return responseEntity;
	}

	/**
	 * This <code>deleteJob</code> method is used to delete the Job Details.
	 *
	 * @param jobId
	 * @return
	 */
	@DeleteMapping(DELETE_JOB_URL)
	public ResponseEntity<UIOperationResponse> deleteJob(@PathVariable(value = JOB_ID_LABEL, required = false) final UUID jobId) {

		LOGGER.info(" ==> Method ==> deleteJob ==> Enter");

		final UIOperationResponse uiOperationResponse = new UIOperationResponse();
		ResponseEntity<UIOperationResponse> responseEntity;

		try {

			if (jobId == null) {
				responseEntity = commonErrorResponse(uiOperationResponse,
						new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_BADREQUEST_ERROR_MESSAGE.message(), null, null))),
						HttpStatus.BAD_REQUEST);
			} else {

				final Job job = getServiceRegistry().getJobService().getJobById(jobId);

				if (job != null) {
					getServiceRegistry().getJobService().deleteJob(job);
					return new ResponseEntity<>(getUiOperationResponse(RequestResponseStatusEnum.SUCCESS.status(),
							new HashSet<>(Arrays.asList(getMessageByCode(UIResponseCodeEnum.DELETE_SUCCESS_MESSAGE.getCode(), JOB_LABEL))), null,
							UIResponseCodeEnum.DELETE_SUCCESS_MESSAGE.getCode()), HttpStatus.OK);
				} else {
					responseEntity = commonErrorResponse(uiOperationResponse,
							new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_JOB_NOT_FOUND_ERROR_MESSAGE.message(), null, null))),
							HttpStatus.NOT_FOUND);
				}
			}

		} catch (final Exception e) {

			logError(LOGGER, String.valueOf(jobId), e, " ==> Method ==> deleteJob ==> Exception ==> ");
			responseEntity = commonErrorResponse(uiOperationResponse,
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		LOGGER.info(" ==> Method ==> deleteJob ==> Exit");
		return responseEntity;
	}

	/**
	 * This <code>addJob</code> method is used to add the Job details.
	 *
	 * @param job
	 * @return
	 */
	@PostMapping(ADD_JOB_URL)
	public ResponseEntity<UIOperationResponse> addJob(@RequestBody(required = false) final Job job) {

		LOGGER.info(" ==> Method ==> addJob ==> Enter");

		final UIOperationResponse uiOperationResponse = new UIOperationResponse();
		List<String> errors = new ArrayList<>();
		ResponseEntity<UIOperationResponse> responseEntity;

		try {

			if (job != null) {

				errors = validateJobDetails(job, Boolean.TRUE);

				if (!ApplicationUtils.isValid(errors)) {

					// Add Job Details in job Table
					getServiceRegistry().getJobService().addJob(job);

					return new ResponseEntity<>(getUiOperationResponse(RequestResponseStatusEnum.SUCCESS.status(),
							new HashSet<>(Arrays.asList(getMessageByCode(UIResponseCodeEnum.ADD_SUCCESS_MESSAGE.getCode(), JOB_LABEL))), job.getJobId(),
							UIResponseCodeEnum.ADD_SUCCESS_MESSAGE.getCode()), HttpStatus.OK);

				} else {
					responseEntity = commonErrorResponse(uiOperationResponse, new LinkedHashSet<String>(errors), HttpStatus.BAD_REQUEST);
				}

			} else {

				responseEntity = commonErrorResponse(uiOperationResponse,
						new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_BADREQUEST_ERROR_MESSAGE.message(), null, null))),
						HttpStatus.BAD_REQUEST);
			}

		} catch (final Exception e) {

			logError(LOGGER, job != null ? String.valueOf(job.getJobId() != null ? job.getJobId() : null) : null, e,
					" ==> Method ==> addJob ==> Exception ==> ");
			responseEntity = commonErrorResponse(uiOperationResponse,
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		LOGGER.info(" ==> Method : addJob ==> Exit");
		return responseEntity;
	}

	/**
	 * This <code>validateJobDetails</code> method is used to validate the Job details.
	 *
	 * @param job
	 * @return
	 */
	private List<String> validateJobDetails(final Job job, final Boolean isAdd) {

		LOGGER.info(" ==> Method ==> validateJobDetails ==> Enter");

		final List<String> errors = new ArrayList<>();
		Job jobDetails = null;

		if (job.getJobId() != null) {
			jobDetails = getServiceRegistry().getJobService().getJobById(job.getJobId());
		}

		if (ApplicationUtils.isEmpty(job.getJobName())) {
			errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_BLANK.message(), null, null)
					+ getMessageSource().getMessage(ErrorDataEnum.JOB_NAME_ERROR.message(), null, null));
		} else {
			if (!ApplicationUtils.isOnlyAlphaAndSpace(job.getJobName())) {
				errors.add(getMessageSource().getMessage(ErrorDataEnum.ERROR_ALPHAANDSPACE.message(), null, null)
						+ getMessageSource().getMessage(ErrorDataEnum.JOB_NAME_ERROR.message(), null, null));
			} else {
				if (isAdd && ApplicationUtils.isValid(getServiceRegistry().getJobService().getJobList().stream().map(Job::getJobName)
						.collect(Collectors.toList()).stream().filter(a -> a.equalsIgnoreCase(job.getJobName())).collect(Collectors.toList()))) {
					errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_EXISTS_ERROR_MESSAGE.message(),
							new Object[] { getMessageSource().getMessage(ErrorDataEnum.JOB_NAME_ERROR.message(), null, null) }, null));
				} else {
					if (!isAdd && jobDetails != null && !jobDetails.getJobName().equals(job.getJobName())
							&& ApplicationUtils.isValid(getServiceRegistry().getJobService().getJobList().stream().map(Job::getJobName)
									.collect(Collectors.toList()).stream().filter(a -> a.equalsIgnoreCase(job.getJobName())).collect(Collectors.toList()))) {
						errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_EXISTS_ERROR_MESSAGE.message(),
								new Object[] { getMessageSource().getMessage(ErrorDataEnum.JOB_NAME_ERROR.message(), null, null) }, null));
					}
				}
			}
		}

		if (ApplicationUtils.isEmpty(job.getJobClassName())) {
			errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_SELECT.message(), null, null)
					+ getMessageSource().getMessage(ErrorDataEnum.JOB_CLASS_NAME_ERROR.message(), null, null));
		} else {
			if (!getJobClasses().contains(job.getJobClassName())) {
				errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_FIELD_NOT_VALID_ERROR_MESSAGE.message(),
						new Object[] { getMessageSource().getMessage(ErrorDataEnum.JOB_CLASS_NAME_ERROR.message(), null, null) }, null));
			} else {
				if (isAdd && ApplicationUtils.isValid(getServiceRegistry().getJobService().getJobList().stream().map(Job::getJobClassName)
						.collect(Collectors.toList()).stream().filter(a -> a.equalsIgnoreCase(job.getJobClassName())).collect(Collectors.toList()))) {
					errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_EXISTS_ERROR_MESSAGE.message(),
							new Object[] { getMessageSource().getMessage(ErrorDataEnum.JOB_CLASS_NAME_ERROR.message(), null, null) }, null));
				} else {
					if (!isAdd && jobDetails != null && !jobDetails.getJobClassName().equals(job.getJobClassName())
							&& ApplicationUtils
									.isValid(getServiceRegistry().getJobService().getJobList().stream().map(Job::getJobClassName).collect(Collectors.toList())
											.stream().filter(a -> a.equalsIgnoreCase(job.getJobClassName())).collect(Collectors.toList()))) {
						errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_EXISTS_ERROR_MESSAGE.message(),
								new Object[] { getMessageSource().getMessage(ErrorDataEnum.JOB_CLASS_NAME_ERROR.message(), null, null) }, null));
					}
				}
			}
		}

		if (ApplicationUtils.isEmpty(job.getJobDuration())) {
			errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_BLANK.message(), null, null)
					+ getMessageSource().getMessage(ErrorDataEnum.JOB_DURATON_ERROR.message(), null, null));
		} else {
			if (!ApplicationUtils.isValidCronJobExpression(job.getJobDuration())) {
				errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_FIELD_NOT_VALID_ERROR_MESSAGE.message(),
						new Object[] { getMessageSource().getMessage(ErrorDataEnum.JOB_DURATON_ERROR.message(), null, null) }, null));
			}
		}

		if (!ApplicationUtils.isEmpty(job.getStatus())) {
			errors.add(getMessageSource().getMessage(ErrorDataEnum.JOB_STATUS_EDIT_ERROR.message(), null, null));
		}

		LOGGER.info(" ==> Method ==> validateJobDetails ==> Exit");
		return errors;
	}

	/**
	 * This <code>editJob</code> method is used to edit/update a Job Details.
	 *
	 * @param job
	 * @return
	 */
	@PostMapping(EDIT_JOB_URL)
	public ResponseEntity<UIOperationResponse> editJob(@RequestBody(required = false) final Job job) {

		LOGGER.info(" ==> Method ==> editJob ==> Enter");

		final UIOperationResponse uiOperationResponse = new UIOperationResponse();
		List<String> errors = new ArrayList<>();
		ResponseEntity<UIOperationResponse> responseEntity;

		try {

			if (job != null && job.getJobId() != null) {

				final Job jobDetails = getServiceRegistry().getJobService().getJobById(job.getJobId());

				if (jobDetails == null) {
					responseEntity = commonErrorResponse(uiOperationResponse,
							new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_JOB_NOT_FOUND_ERROR_MESSAGE.message(), null, null))),
							HttpStatus.NOT_FOUND);
				} else {

					errors = validateJobDetails(job, Boolean.FALSE);

					if (!ApplicationUtils.isValid(errors)) {

						job.setStatus(jobDetails.getStatus());

						// Update Job Details in job Table
						getServiceRegistry().getJobService().updateJob(job);

						return new ResponseEntity<>(getUiOperationResponse(RequestResponseStatusEnum.SUCCESS.status(),
								new HashSet<>(Arrays.asList(getMessageByCode(UIResponseCodeEnum.EDIT_SUCCESS_MESSAGE.getCode(), JOB_LABEL))), job.getJobId(),
								UIResponseCodeEnum.EDIT_SUCCESS_MESSAGE.getCode()), HttpStatus.OK);

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

			logError(LOGGER, job != null ? String.valueOf(job.getJobId() != null ? job.getJobId() : null) : null, e,
					" ==> Method ==> editJob ==> Exception ==> ");
			responseEntity = commonErrorResponse(uiOperationResponse,
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		LOGGER.info(" ==> Method : editJob ==> Exit");
		return responseEntity;
	}

	/**
	 * This <code>getJobClasses</code> method is a helper method, used to get List of Scheduler Class.
	 *
	 * @return
	 */
	public List<String> getJobClasses() {

		try {

			LOGGER.info(" ==> Method ==> getJobClasses ==> Called");

			final ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
			scanner.addIncludeFilter(new AssignableTypeFilter(org.quartz.Job.class));

			final Set<BeanDefinition> setOfRulesClass = scanner.findCandidateComponents(JOB_SCHEDULER_PACKAGE);

			return setOfRulesClass.stream().map(BeanDefinition::getBeanClassName).collect(Collectors.toList());
		} catch (final Exception e) {

			logError(LOGGER, null, e, " ==> Method ==> getJobClasses ==> Exception ==> ");
		}

		return Collections.emptyList();
	}

	/**
	 * This <code>viewJob</code> method is used view the Job Details.
	 *
	 * @param jobId
	 * @return
	 */
	@GetMapping(VIEW_JOB_URL)
	public ResponseEntity<UIOperationResponse> viewJob(@PathVariable(value = JOB_ID_LABEL, required = false) final UUID jobId) {

		LOGGER.info(" ==> Method ==> viewJob ==> Enter");

		final UIOperationResponse uiOperationResponse = new UIOperationResponse();
		ResponseEntity<UIOperationResponse> responseEntity;
		final List<String> errors = new ArrayList<>();

		try {

			if (jobId != null) {

				if (!ApplicationUtils.isValid(errors)) {

					final Job job = getServiceRegistry().getJobService().getJobById(jobId);
					if (job == null) {

						responseEntity = commonErrorResponse(uiOperationResponse,
								new HashSet<>(
										Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_JOB_NOT_FOUND_ERROR_MESSAGE.message(), null, null))),
								HttpStatus.NOT_FOUND);
					} else {
						return new ResponseEntity<>(getUiOperationResponse(RequestResponseStatusEnum.SUCCESS.status(), null, job,
								UIResponseCodeEnum.VIEW_SUCCESS_MESSAGE.getCode()), HttpStatus.OK);
					}

				} else {
					responseEntity = commonErrorResponse(uiOperationResponse, new HashSet<>(errors), HttpStatus.BAD_REQUEST);
				}

			} else {
				responseEntity = commonErrorResponse(uiOperationResponse,
						new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_BADREQUEST_ERROR_MESSAGE.message(), null, null))),
						HttpStatus.BAD_REQUEST);
			}

		} catch (final Exception e) {

			logError(LOGGER, String.valueOf(jobId), e, " ==> Method ==> viewJob ==> Exception ==> ");
			responseEntity = commonErrorResponse(uiOperationResponse,
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		LOGGER.info(" ==> Method : viewJob ==> Exit");
		return responseEntity;
	}

	/**
	 * This <code>changeJobStatus</code> method is used to change status of job by its JobId.
	 *
	 * @param jobId
	 * @param jobStatus
	 * @return
	 */
	@GetMapping(STATUS_JOB_URL)
	public ResponseEntity<UIOperationResponse> changeJobStatus(@PathVariable(value = JOB_ID_LABEL, required = false) final UUID jobId,
			@PathVariable(value = JOB_STATUS_LABEL, required = false) final Boolean jobStatus) {

		LOGGER.info(" ==> Method : changeJobStatus ==> Enter");

		final UIOperationResponse uiOperationResponse = new UIOperationResponse();
		ResponseEntity<UIOperationResponse> responseEntity = new ResponseEntity<>(HttpStatus.OK);
		Boolean isError = Boolean.FALSE;

		try {

			if (jobId != null) {

				if (!ApplicationUtils.isEmpty(jobStatus)) {

					// Called service to get the job details form DB.
					final Job job = getServiceRegistry().getJobService().getJobById(jobId);

					if (job != null) {

						// Changed the status of Job.
						job.setStatus(jobStatus);

						// Updated the DB with the updated status of Job.
						getServiceRegistry().getJobService().updateJob(job);

						// Scheduler for the job.
						changeScheduleJob(job);

						return new ResponseEntity<>(
								getUiOperationResponse(RequestResponseStatusEnum.SUCCESS.status(), null, jobId, UIResponseCodeEnum.SUCCESS_MESSAGE.getCode()),
								HttpStatus.OK);
					} else {
						responseEntity = commonErrorResponse(uiOperationResponse,
								new HashSet<>(
										Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_JOB_NOT_FOUND_ERROR_MESSAGE.message(), null, null))),
								HttpStatus.NOT_FOUND);
					}

				} else {
					isError = Boolean.TRUE;
				}
			} else {
				isError = Boolean.TRUE;
			}

			if (isError) {
				responseEntity = commonErrorResponse(uiOperationResponse,
						new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_BADREQUEST_ERROR_MESSAGE.message(), null, null))),
						HttpStatus.BAD_REQUEST);
			}

		} catch (final Exception e) {

			logError(LOGGER, String.valueOf(jobId), e, " ==> Method ==> changeJobStatus ==> Exception ==> ");

			responseEntity = commonErrorResponse(uiOperationResponse,
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		LOGGER.info(" ==> Method : changeJobStatus ==> Exit");
		return responseEntity;
	}

	/**
	 * This <code>executeJob</code> method is used to schedule and execute the job at the time of request come.
	 *
	 * @param jobId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@GetMapping(EXECUTE_JOB_URL)
	public ResponseEntity<UIOperationResponse> executeJob(@PathVariable(value = JOB_ID_LABEL, required = false) final UUID jobId) {

		LOGGER.info(" ==> Method : executeJob ==> Enter");

		final UIOperationResponse uiOperationResponse = new UIOperationResponse();
		ResponseEntity<UIOperationResponse> responseEntity = new ResponseEntity<>(HttpStatus.OK);

		try {

			if (jobId != null) {

				final Job job = getServiceRegistry().getJobService().getJobById(jobId);

				if (job != null) {

					final JobDetail jobDetail = JobBuilder.newJob((Class<? extends org.quartz.Job>) Class.forName(job.getJobClassName()))
							.withIdentity(job.getJobName() + KEY, job.getJobName() + GROUP).build();

					final Trigger trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName() + KEY, job.getJobName() + GROUP).startNow()
							.forJob(jobDetail).build();

					jobDetail.getJobDataMap().put(SERVICEREGISTRY_LABEL, getServiceRegistry());
					jobDetail.getJobDataMap().put(MESSAGESOURCE_LABEL, getMessageSource());
					jobDetail.getJobDataMap().put(JOB_ID_LABEL, job.getJobId());

					StdSchedulerFactory.getDefaultScheduler().scheduleJob(jobDetail, trigger);

					return new ResponseEntity<>(getUiOperationResponse(RequestResponseStatusEnum.SUCCESS.status(),
							new HashSet<>(Arrays.asList(job.getJobName().concat(" ")
									+ getMessageSource().getMessage(ErrorDataEnum.JOB_EXECUTE_SUCCESS_MESSAGE.message(), null, null))),
							jobId, UIResponseCodeEnum.SUCCESS_MESSAGE.getCode()), HttpStatus.OK);

				} else {
					responseEntity = commonErrorResponse(uiOperationResponse,
							new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_JOB_NOT_FOUND_ERROR_MESSAGE.message(), null, null))),
							HttpStatus.NOT_FOUND);
				}
			} else {
				responseEntity = commonErrorResponse(uiOperationResponse,
						new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_BADREQUEST_ERROR_MESSAGE.message(), null, null))),
						HttpStatus.BAD_REQUEST);
			}

		} catch (final Exception e) {

			logError(LOGGER, null, e, " ==> Method ==> executeJob ==> Exception ==> ");

			responseEntity = commonErrorResponse(uiOperationResponse,
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		LOGGER.info(" ==> Method : executeJob ==> Exit");
		return responseEntity;
	}

	/**
	 * This <code>changeScheduleJob</code> is used to start and stop the scheduler.
	 *
	 * @param job
	 */
	@SuppressWarnings("unchecked")
	private void changeScheduleJob(final Job job) {

		LOGGER.info(" ==> Method : changeScheduleJob ==> Enter");

		try {

			final JobDetail jobDetail = JobBuilder.newJob((Class<? extends org.quartz.Job>) Class.forName(job.getJobClassName()))
					.withIdentity(job.getJobName() + KEY, job.getJobName() + GROUP).build();

			if (job.getStatus()) {

				final Trigger trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName() + KEY, job.getJobName() + GROUP)
						.withSchedule(CronScheduleBuilder.cronSchedule(job.getJobDuration())).forJob(jobDetail).build();

				jobDetail.getJobDataMap().put(SERVICEREGISTRY_LABEL, getServiceRegistry());
				jobDetail.getJobDataMap().put(MESSAGESOURCE_LABEL, getMessageSource());
				jobDetail.getJobDataMap().put(JOB_ID_LABEL, job.getJobId());

				// Schedule(start) the job
				StdSchedulerFactory.getDefaultScheduler().scheduleJob(jobDetail, trigger);

			} else {

				// Stop the job
				StdSchedulerFactory.getDefaultScheduler().deleteJob(jobDetail.getKey());
			}

		} catch (final Exception e) {

			logError(LOGGER, null, e, " ==> Method ==> changeScheduleJob ==> Exception ==> ");
		}
		LOGGER.info(" ==> Method : changeScheduleJob ==> Exit");
	}
}
