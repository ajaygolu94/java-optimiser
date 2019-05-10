/* 
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without
 * prior written consent of XCordis FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the
 * recipient, and its use is subject to the terms of that license.
 */
package uk.co.xcordis.optimiser.service;

import java.util.List;
import java.util.UUID;

import uk.co.xcordis.optimiser.model.ApplicationConfiguration;

/**
 * The <code>AppConfigService</code> interface responsible for provide the application configuration related method in <b>Optimiser</b> application.
 * 
 * @author Rob Atkin
 */
public interface AppConfigService {
	
	/**
	 * This <code>getAppConfigByCode</code> method is use to get the application configuration value base on given code. 
	 * 
	 * @param code
	 * @return
	 */
	public ApplicationConfiguration getAppConfigByCode(String code);
	
	/**
	 * This <code>getAppConfigList</code> method is use to get the all application configuration list.
	 * 
	 * @return
	 */
	public List<ApplicationConfiguration> getAppConfigList();
	
	/**
	 * This <code>addAppConfig</code> method is used for add the application configuration value.
	 * 
	 * @param applicationConfiguration
	 */
	public void addAppConfig(ApplicationConfiguration applicationConfiguration);
	
	/**
	 * This <code>updateAppConfig</code> method is used for update the application configuration value.
	 * 
	 * @param applicationConfiguration
	 */
	public void updateAppConfig(ApplicationConfiguration applicationConfiguration);
	
	/**
	 * This <code>deleteAppConfig</code> method is used for deleting the application configuration value.
	 * 
	 * @param applicationConfiguration
	 */
	public void deleteAppConfig(ApplicationConfiguration applicationConfiguration);

	/**
	 * This <code>getAppConfigById</code> method is used to get Application configuration details by ID.
	 * 
	 * @param id
	 * @return
	 */
	public ApplicationConfiguration getAppConfigById(UUID id);
}
