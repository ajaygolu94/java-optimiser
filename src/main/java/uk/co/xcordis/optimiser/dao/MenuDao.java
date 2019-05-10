/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.dao;

import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Query;

import uk.co.xcordis.optimiser.model.Menu;

/**
 * The <code>MenuDao</code> interface responsible for provide the Menu related method in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Accessor
public interface MenuDao extends BaseDao<Menu> {

	/**
	 * This <code>getMenuListByToken</code> method is used for get menu list based on token params.
	 *
	 * @param token
	 * @return
	 */
	@Query("select * from menu where accessToken = ?")
	public Result<Menu> getMenuListByToken(String token);
}
