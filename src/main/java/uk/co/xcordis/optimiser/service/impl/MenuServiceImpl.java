/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.service.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.xcordis.optimiser.model.Menu;
import uk.co.xcordis.optimiser.service.MenuService;
import uk.co.xcordis.optimiser.util.ApplicationUtils;
import uk.co.xcordis.optimiser.util.DaoFactory;

/**
 * The <code>MenuServiceImpl</code> class responsible for implement the MenuService method in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private DaoFactory daoFactory;

	private static final Logger LOGGER = LoggerFactory.getLogger(MenuServiceImpl.class);

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.MenuService#getMenuList()
	 */
	@Override
	public List<Menu> getMenuList() {

		LOGGER.info(" ==> Method ==> getMenuList ==> Called");

		List<Menu> menuList = daoFactory.getMenuDao().list();
		Collections.sort(menuList);

		return menuList;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.MenuService#getMenuListByAccessToken(java.lang.String)
	 */
	@Override
	public List<Menu> getMenuListByAccessToken(String accessTokenList) {

		LOGGER.info(" ==> Method : getMenuListByAccessToken ==> Enter");

		List<Menu> menuList = getMenuList();

		if (ApplicationUtils.isValid(menuList) && !ApplicationUtils.isEmpty(accessTokenList)) {

			LOGGER.info(" ==> Method : getMenuListByAccessToken ==> Exit");
			return menuList.stream().filter(menu -> menu != null && Arrays.asList(accessTokenList.split(";")).contains(menu.getAccessToken()))
					.collect(Collectors.toList());

		}

		LOGGER.info(" ==> Method : getMenuListByAccessToken ==> Exit");
		return Collections.emptyList();
	}

}
