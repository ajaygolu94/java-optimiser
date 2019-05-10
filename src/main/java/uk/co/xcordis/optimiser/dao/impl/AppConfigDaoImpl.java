/* 
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without
 * prior written consent of XCordis FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the
 * recipient, and its use is subject to the terms of that license.
 */
package uk.co.xcordis.optimiser.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import uk.co.xcordis.optimiser.dao.AppConfigDao;
import uk.co.xcordis.optimiser.dao.impl.BaseDaoImpl;
import uk.co.xcordis.optimiser.model.ApplicationConfiguration;
import uk.co.xcordis.optimiser.util.ApplicationTableConstants;

/**
 * The <code>AppConfigDaoImpl</code> class responsible for implement the AppConfigDao method in <b>Optimiser</b> application.
 * 
 * @author Rob Atkin
 */
@Repository
public class AppConfigDaoImpl extends BaseDaoImpl<ApplicationConfiguration> implements AppConfigDao {

	private static final Logger logger = LoggerFactory.getLogger(AppConfigDaoImpl.class);
	
	private AppConfigDao appConfigDao;
	
	/*
	 * (non-Javadoc)
	 * @see uk.co.xcordis.boson.dao.BaseDaoImpl#setMappper()
	 */
	@Override
	protected void setMappper() {
		this.mapper = getManager().mapper(ApplicationConfiguration.class);
		this.tableName = ApplicationTableConstants.TABLENAME_APPCONFIG;
		this.appConfigDao = getManager().createAccessor(AppConfigDao.class);
	}
	
	/*
	 * (non-Javadoc)
	 * @see uk.co.xcordis.optimiser.dao.AppConfigDao#getAppConfigByCode(java.lang.String)
	 */
	@Override
	public ApplicationConfiguration getAppConfigByCode(String code) {
		
		logger.info(" ==> Method ==> getAppConfigByCode ==> called");
		return appConfigDao.getAppConfigByCode(code);
	}
}
