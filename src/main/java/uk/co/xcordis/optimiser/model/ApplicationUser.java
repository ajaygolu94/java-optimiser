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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import uk.co.xcordis.optimiser.util.ApplicationTableConstants;

/**
 * The <code>User</code> model is used to store the User details into database in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 *
 */
@Table(name = ApplicationTableConstants.TABLENAME_APPLICATIONUSER)
public class ApplicationUser implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@PartitionKey(0)
	private UUID userId;

	private UUID managerId;

	private String email;

	private String name;

	private String contactNumber;

	private String openId;

	private String role;

	private Boolean firstTimeLogin;

	private String createdDate;

	private Boolean active;

	private String auditTimeStamp;

	private String secretKey;

	/**
	 * @return the userId
	 */
	public UUID getUserId() {

		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(final UUID userId) {

		this.userId = userId;
	}

	/**
	 * @return the name
	 */
	public String getName() {

		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(final String name) {

		this.name = name;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {

		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(final String email) {

		this.email = email;
	}

	/**
	 * @return the createdDate
	 */
	public String getCreatedDate() {

		return createdDate;
	}

	/**
	 * @param createdDate
	 *            the createdDate to set
	 */
	public void setCreatedDate(final String createdDate) {

		this.createdDate = createdDate;
	}

	/**
	 * @return the managerId
	 */
	public UUID getManagerId() {

		return managerId;
	}

	/**
	 * @param managerId
	 *            the managerId to set
	 */
	public void setManagerId(final UUID managerId) {

		this.managerId = managerId;
	}

	/**
	 * @return the contactNumber
	 */
	public String getContactNumber() {

		return contactNumber;
	}

	/**
	 * @param contactNumber
	 *            the contactNumber to set
	 */
	public void setContactNumber(final String contactNumber) {

		this.contactNumber = contactNumber;
	}

	/**
	 * @return the openId
	 */
	public String getOpenId() {

		return openId;
	}

	/**
	 * @param openId
	 *            the openId to set
	 */
	public void setOpenId(final String openId) {

		this.openId = openId;
	}

	/**
	 * @return the role
	 */
	public String getRole() {

		return role;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRole(final String role) {

		this.role = role;
	}

	/**
	 * @return the firstTimeLogin
	 */
	public Boolean getFirstTimeLogin() {

		return firstTimeLogin;
	}

	/**
	 * @param firstTimeLogin
	 *            the firstTimeLogin to set
	 */
	public void setFirstTimeLogin(final Boolean firstTimeLogin) {

		this.firstTimeLogin = firstTimeLogin;
	}

	/**
	 * @return the active
	 */
	public Boolean getActive() {

		return active;
	}

	/**
	 * @param active
	 *            the active to set
	 */
	public void setActive(final Boolean active) {

		this.active = active;
	}

	/**
	 * @return the auditTimeStamp
	 */
	public String getAuditTimeStamp() {

		return auditTimeStamp;
	}

	/**
	 * @param auditTimeStamp
	 *            the auditTimeStamp to set
	 */
	public void setAuditTimeStamp(final String auditTimeStamp) {

		this.auditTimeStamp = auditTimeStamp;
	}

	/**
	 * @return the secretKey
	 */
	public String getSecretKey() {

		return secretKey;
	}

	/**
	 * @param secretKey
	 *            the secretKey to set
	 */
	public void setSecretKey(final String secretKey) {

		this.secretKey = secretKey;
	}

	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
