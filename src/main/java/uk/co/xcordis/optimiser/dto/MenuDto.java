/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.dto;

import java.io.Serializable;
import java.util.List;

import uk.co.xcordis.optimiser.model.Menu;

/**
 * The <code>MenuDto</code> model is used to transfer the Menu details in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 *
 */
public class MenuDto extends Menu implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private List<Menu> menuList;

	/**
	 * @return the menuList
	 */
	public List<Menu> getMenuList() {

		return menuList;
	}

	/**
	 * @param menuList
	 *            the menuList to set
	 */
	public void setMenuList(List<Menu> menuList) {

		this.menuList = menuList;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.model.Menu#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {

		// TODO Auto-generated method stub
		return super.equals(obj);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.co.xcordis.optimiser.model.Menu#hashCode()
	 */
	@Override
	public int hashCode() {

		// TODO Auto-generated method stub
		return super.hashCode();
	}

}
