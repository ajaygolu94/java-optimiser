/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.dto;

import java.io.Serializable;

import uk.co.xcordis.optimiser.model.Merchant;

/**
 * The <code>MerchantDto</code> model is used to transfer the Merchant details in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 *
 */
public class MerchantDto extends Merchant implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String userName;

	/**
	 * @return the userName
	 */
	public String getUserName() {

		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {

		this.userName = userName;
	}

}
