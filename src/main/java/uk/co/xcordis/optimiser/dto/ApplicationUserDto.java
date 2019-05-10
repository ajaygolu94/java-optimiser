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

import uk.co.xcordis.optimiser.model.ApplicationUser;

/**
 * The <code>ApplicationUserDto</code> model is used to transfer the User data with some extra fields in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 *
 */
public class ApplicationUserDto extends ApplicationUser implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private List<String> merchants;

	/**
	 * @return the merchants
	 */
	public List<String> getMerchants() {

		return merchants;
	}

	/**
	 * @param merchants
	 *            the merchants to set
	 */
	public void setMerchants(final List<String> merchants) {

		this.merchants = merchants;
	}
}
