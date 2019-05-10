/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datastax.driver.core.utils.UUIDs;

import uk.co.xcordis.optimiser.model.ErrorLog;
import uk.co.xcordis.optimiser.service.ErrorLogService;
import uk.co.xcordis.optimiser.util.ApplicationConstants;
import uk.co.xcordis.optimiser.util.ApplicationUtils;
import uk.co.xcordis.optimiser.util.DaoFactory;

/**
 * The <code>ErrorLogServiceImpl</code> class responsible for implement the ErrorLogService method in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Service
public class ErrorLogServiceImpl implements ErrorLogService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ErrorLogServiceImpl.class);

	@Autowired
	private DaoFactory daoFactory;

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.ftl.service.ErrorLogService#addErrorLog(uk.co.xcordis.ftl.model.ErrorLog)
	 */
	@Override
	public void addErrorLog(final ErrorLog errorLog) {

		LOGGER.info(" ==> Method ==> addErrorLog ==> Enter");

		errorLog.setErrorLogId(UUIDs.timeBased());
		errorLog.setAuditTimeStamp(ApplicationUtils.getCurrentTimeStamp());

		daoFactory.getErrorLogDao().add(errorLog);

		LOGGER.info(" ==> Method ==> addErrorLog ==> Exit");

	}

	/*
	 * (non-Javadoc)
	 * @see uk.co.xcordis.optimiser.service.ErrorLogService#getErrorLogList()
	 */
	@Override
	public List<ErrorLog> getErrorLogList() {

		LOGGER.info(" ==> Method ==> getErrorLogList ==> Enter");

		final List<ErrorLog> errorLogs = daoFactory.getErrorLogDao().list();

		if (ApplicationUtils.isValid(errorLogs)) {

			ApplicationUtils.sortListByTimeStamp(errorLogs, ApplicationConstants.FIELD_AUDITTIMESTAMP_LABEL,
					ApplicationConstants.DD_MM_YYYY_HH_MM_SS_AM_PM);
		}

		LOGGER.info(" ==> Method ==> getErrorLogList ==> Exit");
		return errorLogs;
	}

	/*
	 * (non-Javadoc)
	 * @see uk.co.xcordis.optimiser.service.ErrorLogService#findById(java.lang.Object[])
	 */
	@Override
	public ErrorLog findById(final Object... objects) {

		LOGGER.info(" ==> Method ==> findById ==> Called");
		return (ErrorLog) daoFactory.getErrorLogDao().findById(objects);
	}
}