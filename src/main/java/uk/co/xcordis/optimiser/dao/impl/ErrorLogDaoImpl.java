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

import uk.co.xcordis.optimiser.dao.ErrorLogDao;
import uk.co.xcordis.optimiser.model.ErrorLog;
import uk.co.xcordis.optimiser.util.ApplicationTableConstants;

/**
 * The <code>ErrorLogDaoImpl</code> class responsible for implement the ErrorLogDao method in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Repository
public class ErrorLogDaoImpl extends BaseDaoImpl<ErrorLog> implements ErrorLogDao {

	private static final Logger logger = LoggerFactory.getLogger(ErrorLogDaoImpl.class);

	private ErrorLogDao errorLogDao;

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.ftl.dao.BaseDaoImpl#setMappper()
	 */
	@Override
	protected void setMappper() {

		mapper = getManager().mapper(ErrorLog.class);
		tableName = ApplicationTableConstants.TABLENAME_ERROR_LOG;
		errorLogDao = getManager().createAccessor(ErrorLogDao.class);
	}

}
