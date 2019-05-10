/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.util;

/**
 * The <code>UIResponseCodeEnum</code> class contains all Constants related to ui level code for <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
public enum UIResponseCodeEnum {

	// Common error message properties
	ADD_SUCCESS_MESSAGE("201"), EDIT_SUCCESS_MESSAGE("202"), DELETE_SUCCESS_MESSAGE("203"), VIEW_SUCCESS_MESSAGE("204"), LIST_SUCCESS_MESSAGE("205"),
	FAILED_MESSAGE("206"), SUCCESS_MESSAGE("207");

	private String code;

	UIResponseCodeEnum(final String code) {

		this.code = code;
	}

	/**
	 * @return the code
	 */
	public String getCode() {

		return code;
	}

}
