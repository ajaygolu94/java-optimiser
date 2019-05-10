/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.xcordis.optimiser.model.Job;
import uk.co.xcordis.optimiser.service.JobService;
import uk.co.xcordis.optimiser.util.ApplicationConstants;
import uk.co.xcordis.optimiser.util.ApplicationUtils;
import uk.co.xcordis.optimiser.util.DaoFactory;

/**
 * The <code>JobServiceImpl</code> class responsible for implement the JobService methods in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Service
public class JobServiceImpl implements JobService {

	private static final Logger LOGGER = LoggerFactory.getLogger(JobServiceImpl.class);

	@Autowired
	private DaoFactory daoFactory;

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.JobService#getJobList()
	 */
	@Override
	public List<Job> getJobList() {

		LOGGER.info(" ==> Method ==> getJobList ==> Enter");

		final List<Job> listOfJobs = daoFactory.getJobDao().list();

		if (ApplicationUtils.isValid(listOfJobs)) {

			ApplicationUtils.sortListByTimeStamp(listOfJobs, ApplicationConstants.FIELD_AUDITTIMESTAMP_LABEL, ApplicationConstants.DD_MM_YYYY_HH_MM_SS_AM_PM);

			return listOfJobs.stream().filter(job -> job != null && job.getActive() != null && job.getActive()).collect(Collectors.toList());
		}

		LOGGER.info(" ==> Method ==> getJobList ==> Exit");

		return Collections.emptyList();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.JobService#addJob(uk.co.xcordis.optimiser.model.Job)
	 */
	@Override
	public void addJob(final Job job) {

		LOGGER.info(" ==> Method ==> addJob ==> Enter");

		job.setJobId(UUID.randomUUID());
		job.setActive(Boolean.TRUE);
		job.setStatus(Boolean.FALSE);
		job.setCreatedDate(ApplicationUtils.getCurrentTimeStamp());
		job.setAuditTimeStamp(ApplicationUtils.getCurrentTimeStamp());

		daoFactory.getJobDao().add(job);

		LOGGER.info(" ==> Method ==> addJob ==> Exit");

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.JobService#updateJob(uk.co.xcordis.optimiser.model.Job)
	 */
	@Override
	public void updateJob(final Job job) {

		LOGGER.info(" ==> Method ==> updateJob ==> called");
		job.setAuditTimeStamp(ApplicationUtils.getCurrentTimeStamp());
		daoFactory.getJobDao().update(job);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.JobService#deleteJob(uk.co.xcordis.optimiser.model.Job)
	 */
	@Override
	public void deleteJob(final Job job) {

		LOGGER.info(" ==> Method ==> deleteJob ==> Enter");
		job.setActive(Boolean.FALSE);
		job.setStatus(Boolean.FALSE);
		job.setAuditTimeStamp(ApplicationUtils.getCurrentTimeStamp());
		daoFactory.getJobDao().update(job);
		LOGGER.info(" ==> Method ==> deleteJob ==> Exit");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.JobService#getJobById(java.util.UUID)
	 */
	@Override
	public Job getJobById(final UUID jobId) {

		LOGGER.info(" ==> Method ==> getJobById ==> called");
		return (Job) daoFactory.getJobDao().findById(jobId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.JobService#getActiveJobList()
	 */
	@Override
	public List<Job> getActiveJobList() {

		LOGGER.info(" ==> Method ==> getActiveJobList ==> Enter");

		final List<Job> jobList = getJobList();

		if (ApplicationUtils.isValid(jobList)) {
			return jobList.stream().filter(job -> job != null && job.getStatus() != null && job.getStatus()).collect(Collectors.toList());
		}

		LOGGER.info(" ==> Method ==> getActiveJobList ==> Exit");
		return Collections.emptyList();
	}

}
