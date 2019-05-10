/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.service;

import java.util.List;

import uk.co.xcordis.optimiser.model.RequestData;

/**
 * The <code>RequestDataService</code> interface responsible for provide the request data related method in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
public interface RequestDataService {

	/**
	 * This <code>addRequestData</code> method is use to add request details into database.
	 *
	 * @param requestData
	 */
	public void addRequestData(RequestData requestData);

	/**
	 * This <code>updateRequestData</code> method is use to update request details into database.
	 *
	 * @param requestData
	 */
	public void updateRequestData(RequestData requestData);

	/**
	 * This <code>findById</code> method is use to get request data object by primary key.
	 *
	 * @param objects
	 * @return
	 */
	public RequestData findById(Object... objects);

	/**
	 * This method <code>getRequestDataList</code> is used for get the list of request data from database.
	 *
	 * @return
	 */
	public List<RequestData> getRequestDataList();
}
