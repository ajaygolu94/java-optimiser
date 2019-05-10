/* 
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without
 * prior written consent of XCordis FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the
 * recipient, and its use is subject to the terms of that license.
 */
package uk.co.xcordis.optimiser.dao;

import uk.co.xcordis.optimiser.model.ApplicationConfiguration;

import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Query;

/**
 * The <code>AppConfigDao</code> interface responsible for provide the application configuration related method in <b>Optimiser</b> application.
 * 
 * @author Rob Atkin
 */
@Accessor
public interface AppConfigDao extends BaseDao<ApplicationConfiguration> {
	
	/**
	 * This method <code>getAppConfigByCode</code> is used for get the ApplicationConfiguration based on code params.
	 * 
	 * @param code
	 * @return
	 */
	@Query("select * from appconfig where code = ?")
	public ApplicationConfiguration getAppConfigByCode(String code);
}
