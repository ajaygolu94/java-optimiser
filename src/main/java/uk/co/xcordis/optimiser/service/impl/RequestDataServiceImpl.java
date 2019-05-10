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

import uk.co.xcordis.optimiser.model.RequestData;
import uk.co.xcordis.optimiser.service.RequestDataService;
import uk.co.xcordis.optimiser.util.ApplicationUtils;
import uk.co.xcordis.optimiser.util.DaoFactory;

/**
 * The <code>RequestDataServiceImpl</code> class responsible for implement the RequestDataService method in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Service
public class RequestDataServiceImpl implements RequestDataService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RequestDataServiceImpl.class);

	@Autowired
	private DaoFactory daoFactory;

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.RequestDataService#addRequestData(uk.co.xcordis.optimiser.model.RequestData)
	 */
	@Override
	public void addRequestData(final RequestData requestData) {

		LOGGER.info(" ==> Method ==> addRequestData ==> Called");

		requestData.setAuditTimeStamp(ApplicationUtils.getCurrentTimeStamp());
		daoFactory.getRequestDataDao().add(requestData);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.RequestDataService#updateRequestData(uk.co.xcordis.optimiser.model.RequestData)
	 */
	@Override
	public void updateRequestData(final RequestData requestData) {

		LOGGER.info(" ==> Method ==> updateRequestData ==> Called");

		requestData.setAuditTimeStamp(ApplicationUtils.getCurrentTimeStamp());
		daoFactory.getRequestDataDao().update(requestData);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.RequestDataService#findById(java.lang.Object[])
	 */
	@Override
	public RequestData findById(final Object... objects) {

		LOGGER.info(" ==> Method ==> findById ==> Called");
		return (RequestData) daoFactory.getRequestDataDao().findById(objects);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.co.xcordis.optimiser.service.RequestDataService#getRequestDataList()
	 */
	@Override
	public List<RequestData> getRequestDataList() {

		LOGGER.info(" ==> Method ==> getRequestDataList ==> Called");
		return daoFactory.getRequestDataDao().list();
	}
}
