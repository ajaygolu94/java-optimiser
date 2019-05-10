/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.datastax.driver.mapping.Result;

import uk.co.xcordis.optimiser.dao.MenuDao;
import uk.co.xcordis.optimiser.model.Menu;
import uk.co.xcordis.optimiser.util.ApplicationTableConstants;

/**
 * The <code>MenuDaoImpl</code> class responsible for implement the MenuDao method in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Repository
public class MenuDaoImpl extends BaseDaoImpl<Menu> implements MenuDao {

	private static final Logger logger = LoggerFactory.getLogger(MenuDaoImpl.class);

	private MenuDao menuDao;

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.dao.impl.BaseDaoImpl#setMappper()
	 */
	@Override
	protected void setMappper() {

		mapper = getManager().mapper(Menu.class);
		tableName = ApplicationTableConstants.TABLENAME_MENU;
		menuDao = getManager().createAccessor(MenuDao.class);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.dao.MenuDao#getMenuListByToken(java.lang.String)
	 */
	@Override
	public Result<Menu> getMenuListByToken(String token) {

		logger.info(" ==> Method ==> getMenuListByToken ==> Called");
		return menuDao.getMenuListByToken(token);
	}
}
