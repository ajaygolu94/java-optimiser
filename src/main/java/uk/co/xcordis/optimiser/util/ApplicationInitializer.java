/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.util;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.ServletContext;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.ServletContextAware;

import uk.co.xcordis.optimiser.controller.BaseController;
import uk.co.xcordis.optimiser.model.ApplicationConfiguration;
import uk.co.xcordis.optimiser.model.Job;

/**
 * The <code>ApplicationInitializer</code> class is called during server startup. It is responsible to Cassandra connection session maintain in <b>Optimiser</b>
 * application.
 *
 * @author Rob Atkin
 */
@Repository
@DependsOn(ApplicationConstants.CASSANDRACONNECTOR_LABEL)
public class ApplicationInitializer extends BaseController implements ServletContextAware, ApplicationConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationInitializer.class);

	@Autowired
	private CassandraConnector connector;

	/**
	 * The method <code>initializer</code> is used to initialize application level value or perform some task on application start up level.
	 *
	 */
	@PostConstruct
	public void initializer() {

		LOGGER.info(" ==> Method: initializer ==> Enter");

		// Schedule the quartz job
		scheduleQuartzJob();

		LOGGER.info(" ==> Method: initializer ==> Exit");
	}

	/**
	 * The method <code>destroy</code> is used to perform some task when application destroy/stop.
	 *
	 */
	@PreDestroy
	public void destroy() {

		LOGGER.info(" ==> Method: destroy ==> Called");

		connector.close();

		try {

			// Stop the scheduler
			StdSchedulerFactory.getDefaultScheduler().shutdown();

		} catch (final Exception e) {
			LOGGER.error(" ==> Method: destroy ==> Exception ==> " + e);
		}
	}

	/**
	 * This <code>scheduleQuartzJob</code> method is used for schedule the all Active Job and ready it for run when application start.
	 *
	 */
	@SuppressWarnings("unchecked")
	private void scheduleQuartzJob() {

		LOGGER.info(" ==> Method: scheduleQuartzJob ==> Enter");

		try {

			// Get the Scheduler instance from the Factory
			final Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

			// start the scheduler
			scheduler.start();

			final List<Job> activeJobList = getServiceRegistry().getJobService().getActiveJobList();

			for (final Job job : activeJobList) {

				final JobDetail jobDetail = JobBuilder.newJob((Class<? extends org.quartz.Job>) Class.forName(job.getJobClassName()))
						.withIdentity(job.getJobName() + KEY, job.getJobName() + GROUP).build();

				final Trigger trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName() + KEY, job.getJobName() + GROUP)
						.withSchedule(CronScheduleBuilder.cronSchedule(job.getJobDuration())).forJob(jobDetail).build();

				jobDetail.getJobDataMap().put(SERVICEREGISTRY_LABEL, getServiceRegistry());
				jobDetail.getJobDataMap().put(MESSAGESOURCE_LABEL, getMessageSource());
				jobDetail.getJobDataMap().put(JOB_ID_LABEL, job.getJobId());

				// Scheduler job
				scheduler.scheduleJob(jobDetail, trigger);
			}

		} catch (final Exception e) {
			logError(LOGGER, null, e, " ==> Method ==> scheduleQuartzJob ==> Exception ==> ");
			LOGGER.error(" ==> Method: scheduleQuartzJob ==> Exception : " + e);
		}

		LOGGER.info(" ==> Method: scheduleQuartzJob ==> Exit");
	}

	@Override
	public void setServletContext(final ServletContext servletContext) {

		LOGGER.info(" ==> Method: setServletContext ==> Enter");

		final ApplicationConfiguration logoRelativePath = getServiceRegistry().getAppConfigService().getAppConfigByCode(APPLICATION_LOGO_RELATIVE_PATH);

		if (logoRelativePath != null && logoRelativePath.getValue() != null) {
			servletContext.setAttribute(APPLICATION_LOGO_RELATIVE_PATH, logoRelativePath.getValue());
		} else {
			servletContext.setAttribute(APPLICATION_LOGO_RELATIVE_PATH, OPTIMISER_APP_LOGO_RELATIVE);
		}

		final ApplicationConfiguration footerText = getServiceRegistry().getAppConfigService().getAppConfigByCode(APPLICATION_FOOTER_TEXT);

		if (footerText != null && footerText.getValue() != null) {
			servletContext.setAttribute(APPLICATION_FOOTER_TEXT, footerText.getValue());
		} else {
			servletContext.setAttribute(APPLICATION_FOOTER_TEXT, OPTIMISER_FOOTER);
		}

		final ApplicationConfiguration headerText = getServiceRegistry().getAppConfigService().getAppConfigByCode(APPLICATION_HEADER_TEXT);

		if (headerText != null && headerText.getValue() != null) {
			servletContext.setAttribute(APPLICATION_HEADER_TEXT, headerText.getValue());
		} else {
			servletContext.setAttribute(APPLICATION_HEADER_TEXT, OPTIMISER_HEADER);
		}

		final ApplicationConfiguration cssChange = getServiceRegistry().getAppConfigService().getAppConfigByCode(APPLICATION_CSS);

		if (cssChange != null && cssChange.getValue() != null) {
			servletContext.setAttribute(APPLICATION_CSS, cssChange.getValue());
		} else {
			servletContext.setAttribute(APPLICATION_CSS, OPTIMISER_APPLICATION_CSS);
		}

		final ApplicationConfiguration favicon = getServiceRegistry().getAppConfigService().getAppConfigByCode(FAVICON_IMAGE);

		if (favicon != null && favicon.getValue() != null) {
			servletContext.setAttribute(FAVICON_IMAGE, favicon.getValue());
		} else {
			servletContext.setAttribute(FAVICON_IMAGE, OPTIMISER_FAVICON);
		}

		final ApplicationConfiguration background = getServiceRegistry().getAppConfigService().getAppConfigByCode(BACKGROUND_IMAGE);

		if (background != null && background.getValue() != null) {
			servletContext.setAttribute(BACKGROUND_IMAGE, background.getValue());
		} else {
			servletContext.setAttribute(BACKGROUND_IMAGE, OPTIMISER_HOME_JPG);
		}

		final ApplicationConfiguration ftlLogo = getServiceRegistry().getAppConfigService().getAppConfigByCode(OPTIMISER_LOGO_IMAGE);

		if (ftlLogo != null && ftlLogo.getValue() != null) {
			servletContext.setAttribute(OPTIMISER_LOGO_IMAGE, ftlLogo.getValue());
		} else {
			servletContext.setAttribute(OPTIMISER_LOGO_IMAGE, OPTIMISER_IMG_PNG);
		}

		final ApplicationConfiguration loginLogo = getServiceRegistry().getAppConfigService().getAppConfigByCode(LOGIN_PAGE_LOGO);

		if (loginLogo != null && loginLogo.getValue() != null) {
			servletContext.setAttribute(LOGIN_PAGE_LOGO, loginLogo.getValue());
		} else {
			servletContext.setAttribute(LOGIN_PAGE_LOGO, OPTIMISER_PNG);
		}

		final ApplicationConfiguration loginHeader = getServiceRegistry().getAppConfigService().getAppConfigByCode(LOGIN_PAGE_HEADER);

		if (loginHeader != null && loginHeader.getValue() != null) {
			servletContext.setAttribute(LOGIN_PAGE_HEADER, loginHeader.getValue());
		} else {
			servletContext.setAttribute(LOGIN_PAGE_HEADER, OPTIMISER_LOGIN);
		}

		final ApplicationConfiguration registrationHeader = getServiceRegistry().getAppConfigService().getAppConfigByCode(REGISTRATION_PAGE_HEADER);

		if (registrationHeader != null && registrationHeader.getValue() != null) {
			servletContext.setAttribute(REGISTRATION_PAGE_HEADER, registrationHeader.getValue());
		} else {
			servletContext.setAttribute(REGISTRATION_PAGE_HEADER, OPTIMISER_SIGNUP);
		}

		final ApplicationConfiguration dashboardBackground = getServiceRegistry().getAppConfigService().getAppConfigByCode(DASHBOARD_BACKGROUND_IMAGE);

		if (dashboardBackground != null && dashboardBackground.getValue() != null) {
			servletContext.setAttribute(DASHBOARD_BACKGROUND_IMAGE, dashboardBackground.getValue());
		} else {
			servletContext.setAttribute(DASHBOARD_BACKGROUND_IMAGE, OPTIMISER_BACKGROUND_IMAGE);
		}

		final ApplicationConfiguration adminEmail = getServiceRegistry().getAppConfigService().getAppConfigByCode(ADMIN_EMAIL);

		if (adminEmail != null && adminEmail.getValue() != null) {
			servletContext.setAttribute(ADMIN_EMAIL, adminEmail.getValue());
		} else {
			servletContext.setAttribute(ADMIN_EMAIL, OPTIMISER_ADMIN_EMAIL_VALUE);
		}
		LOGGER.info(" ==> Method: setServletContext ==> Exit");
	}
}