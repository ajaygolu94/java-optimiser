/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.model;

import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import uk.co.xcordis.optimiser.util.ApplicationTableConstants;

/**
 * The <code>ApplicationConfiguration</code> model is used to store the application configuration level details into database in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 *
 */
@Table(name = ApplicationTableConstants.TABLENAME_APPCONFIG)
public class ApplicationConfiguration {

	private String code;

	private String value;

	private String auditTimeStamp;

	@PartitionKey(0)
	private UUID appConfigId;

	/**
	 * @return the code
	 */
	public String getCode() {

		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(final String code) {

		this.code = code;
	}

	/**
	 * @return the value
	 */
	public String getValue() {

		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(final String value) {

		this.value = value;
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
	 * @return the appConfigId
	 */
	public UUID getAppConfigId() {

		return appConfigId;
	}

	/**
	 * @param appConfigId
	 *            the appConfigId to set
	 */
	public void setAppConfigId(final UUID appConfigId) {

		this.appConfigId = appConfigId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
