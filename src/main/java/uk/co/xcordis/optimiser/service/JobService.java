/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.service;

import java.util.List;
import java.util.UUID;

import uk.co.xcordis.optimiser.model.Job;

/**
 * The <code>JobService</code> interface responsible for provide the Job related method in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
public interface JobService {

	/**
	 * This <code>getJobList</code> method is use to get the all Jobs list.
	 *
	 * @return
	 */
	public List<Job> getJobList();

	/**
	 * This <code>addJob</code> method is used for add the Job details to DB.
	 *
	 * @param job
	 */
	public void addJob(Job job);

	/**
	 * This <code>updateJob</code> method is used to update the Job details to DB.
	 *
	 * @param job
	 */
	public void updateJob(Job job);

	/**
	 * This <code>deleteJob</code> method is used for deleting(set active to false) the Job details from DB.
	 *
	 * @param job
	 */
	public void deleteJob(Job job);

	/**
	 * This <code>getJobById</code> method is used to get Job details by ID.
	 *
	 * @param jobId
	 * @return
	 */
	public Job getJobById(UUID jobId);

	/**
	 * This method <code>getActiveJobList</code> is used for get the all active job list from database.
	 *
	 * @return
	 */
	public List<Job> getActiveJobList();

}
