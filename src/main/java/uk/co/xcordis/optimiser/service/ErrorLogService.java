/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.service;

import java.util.List;

import uk.co.xcordis.optimiser.model.ErrorLog;

/**
 * The <code>ErrorLogService</code> interface responsible for provide the error log related method in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
public interface ErrorLogService {

	/**
	 * This <code>addErrorLog</code> method is used for add the error log value.
	 *
	 * @param errorLog
	 */
	public void addErrorLog(ErrorLog errorLog);

	/**
	 * The <code>getErrorLogList</code> method is used to get all the error logs list.
	 *
	 * @return
	 */
	public List<ErrorLog> getErrorLogList();

	/**
	 * The <code>findById</code> method is use to get ErrorLog object base on the primary keys or ID's.
	 *
	 * @param objects
	 * @return
	 */
	public ErrorLog findById(Object... objects);
}
