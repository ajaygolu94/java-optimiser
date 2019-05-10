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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.xcordis.optimiser.model.ApplicationConfiguration;
import uk.co.xcordis.optimiser.service.AppConfigService;
import uk.co.xcordis.optimiser.util.ApplicationConstants;
import uk.co.xcordis.optimiser.util.ApplicationUtils;
import uk.co.xcordis.optimiser.util.DaoFactory;

/**
 * The <code>AppConfigServiceImpl</code> class responsible for implement the AppConfigService method in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Service
public class AppConfigServiceImpl implements AppConfigService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AppConfigServiceImpl.class);

	@Autowired
	private DaoFactory daoFactory;

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.AppConfigService#getAppConfigByCode(java.lang.String)
	 */
	@Override
	public ApplicationConfiguration getAppConfigByCode(String code) {

		LOGGER.info(" ==> Method ==> getAppConfigByCode ==> Called");
		return daoFactory.getAppConfigDao().getAppConfigByCode(code);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.AppConfigService#getAppConfigList()
	 */
	@Override
	public List<ApplicationConfiguration> getAppConfigList() {

		LOGGER.info(" ==> Method ==> getAppConfigList ==> Start");

		final List<ApplicationConfiguration> applicationConfigurations = daoFactory.getAppConfigDao().list();

		if (ApplicationUtils.isValid(applicationConfigurations)) {
			ApplicationUtils.sortListByTimeStamp(applicationConfigurations, ApplicationConstants.FIELD_AUDITTIMESTAMP_LABEL,
					ApplicationConstants.DD_MM_YYYY_HH_MM_SS_AM_PM);
			return applicationConfigurations;
		}

		LOGGER.info(" ==> Method ==> getAppConfigList ==> End");

		return Collections.emptyList();

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.AppConfigService#addAppConfig(uk.co.xcordis.optimiser.model.ApplicationConfiguration)
	 */
	@Override
	public void addAppConfig(ApplicationConfiguration applicationConfiguration) {

		LOGGER.info(" ==> Method ==> addAppConfig ==> Enter");

		applicationConfiguration.setAppConfigId(UUID.randomUUID());
		applicationConfiguration.setAuditTimeStamp(ApplicationUtils.getCurrentTimeStamp());

		daoFactory.getAppConfigDao().add(applicationConfiguration);

		LOGGER.info(" ==> Method ==> addAppConfig ==> Exit");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.AppConfigService#updateAppConfig(uk.co.xcordis.optimiser.model.ApplicationConfiguration)
	 */
	@Override
	public void updateAppConfig(ApplicationConfiguration applicationConfiguration) {

		LOGGER.info(" ==> Method ==> updateAppConfig ==> Enter");

		applicationConfiguration.setAuditTimeStamp(ApplicationUtils.getCurrentTimeStamp());

		daoFactory.getAppConfigDao().update(applicationConfiguration);

		LOGGER.info(" ==> Method ==> updateAppConfig ==> Exit");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.AppConfigService#deleteAppConfig(uk.co.xcordis.optimiser.model.ApplicationConfiguration)
	 */
	@Override
	public void deleteAppConfig(ApplicationConfiguration applicationConfiguration) {

		LOGGER.info(" ==> Method ==> deleteAppConfig ==> Enter");
		daoFactory.getAppConfigDao().delete(applicationConfiguration);
		LOGGER.info(" ==> Method ==> deleteAppConfig ==> Exit");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.AppConfigService#getAppConfigById(java.util.UUID)
	 */
	@Override
	public ApplicationConfiguration getAppConfigById(UUID id) {

		LOGGER.info(" ==> Method ==> getAppConfigById ==> called");
		return (ApplicationConfiguration) daoFactory.getAppConfigDao().findById(id);
	}
}
