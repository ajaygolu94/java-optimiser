/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.service;

import java.util.List;

import uk.co.xcordis.optimiser.model.Menu;

/**
 * The <code>MenuService</code> interface responsible for provide the Menu Service related method in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
public interface MenuService {

	/**
	 * The <code>getMenuList</code> method is used for get the list of menu from database.
	 *
	 * @return
	 */
	public List<Menu> getMenuList();

	/**
	 * The <code>getMenuListByAccessToken</code> is used for get menu list based on comma separated accessToken of specific merchant.
	 *
	 * @param accessTokenList
	 * @return
	 */
	public List<Menu> getMenuListByAccessToken(String accessTokenList);

}
