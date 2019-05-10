/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.model;

import java.io.Serializable;
import java.util.UUID;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import uk.co.xcordis.optimiser.util.ApplicationTableConstants;

/**
 * The <code>Menu</code> model is used to store the Menu details into database in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 *
 */
@Table(name = ApplicationTableConstants.TABLENAME_MENU)
public class Menu implements Serializable, Comparable<Menu> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@PartitionKey(0)
	private UUID menuId;

	private UUID parentMenuId;

	private Boolean isParent;

	private String menuItem;

	private String accessToken;

	private String url;

	private int menuOrder;

	/**
	 * @return the menuId
	 */
	public UUID getMenuId() {

		return menuId;
	}

	/**
	 * @param menuId
	 *            the menuId to set
	 */
	public void setMenuId(final UUID menuId) {

		this.menuId = menuId;
	}

	/**
	 * @return the parentMenuId
	 */
	public UUID getParentMenuId() {

		return parentMenuId;
	}

	/**
	 * @param parentMenuId
	 *            the parentMenuId to set
	 */
	public void setParentMenuId(final UUID parentMenuId) {

		this.parentMenuId = parentMenuId;
	}

	/**
	 * @return the isParent
	 */
	public Boolean getIsParent() {

		return isParent;
	}

	/**
	 * @param isParent
	 *            the isParent to set
	 */
	public void setIsParent(final Boolean isParent) {

		this.isParent = isParent;
	}

	/**
	 * @param menuOrder
	 *            the menuOrder to set
	 */
	public void setMenuOrder(final int menuOrder) {

		this.menuOrder = menuOrder;
	}

	/**
	 * @return the menuItem
	 */
	public String getMenuItem() {

		return menuItem;
	}

	/**
	 * @param menuItem
	 *            the menuItem to set
	 */
	public void setMenuItem(final String menuItem) {

		this.menuItem = menuItem;
	}

	/**
	 * @return the accessToken
	 */
	public String getAccessToken() {

		return accessToken;
	}

	/**
	 * @param accessToken
	 *            the accessToken to set
	 */
	public void setAccessToken(final String accessToken) {

		this.accessToken = accessToken;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {

		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(final String url) {

		this.url = url;
	}

	/**
	 * @return the menuOrder
	 */
	public Integer getMenuOrder() {

		return menuOrder;
	}

	/**
	 * @param menuOrder
	 *            the menuOrder to set
	 */
	public void setMenuOrder(final Integer menuOrder) {

		this.menuOrder = menuOrder;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(final Menu m) {

		return Integer.compare(getMenuOrder(), m.getMenuOrder());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {

		return this == obj;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {

		return 0;
	}
}
