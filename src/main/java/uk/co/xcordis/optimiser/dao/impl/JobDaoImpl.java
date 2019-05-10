/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import uk.co.xcordis.optimiser.dao.JobDao;
import uk.co.xcordis.optimiser.model.Job;
import uk.co.xcordis.optimiser.util.ApplicationTableConstants;

/**
 * The <code>JobDaoImpl</code> class responsible for implement the JobDao method in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Repository
public class JobDaoImpl extends BaseDaoImpl<Job> implements JobDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(JobDaoImpl.class);

	private JobDao jobDao;

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.dao.impl.BaseDaoImpl#setMappper()
	 */
	@Override
	protected void setMappper() {

		mapper = getManager().mapper(Job.class);
		tableName = ApplicationTableConstants.TABLENAME_JOB;
		jobDao = getManager().createAccessor(JobDao.class);
	}

}
