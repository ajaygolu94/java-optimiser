/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import uk.co.xcordis.optimiser.util.ApplicationTableConstants;

/**
 * The <code>RuleSelectorKeys</code> model is used to store the Rule Selector Keys details into database in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 *
 */
@Table(name = ApplicationTableConstants.TABLENAME_RULESELECTORKEYS)
public class RuleSelectorKeys implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@PartitionKey(0)
	private String selectorKey;

	private String selectorKeyClass;

	private String optimiserParam;

	private String auditTimeStamp;

	/**
	 * @return the selectorKey
	 */
	public String getSelectorKey() {

		return selectorKey;
	}

	/**
	 * @param selectorKey
	 *            the selectorKey to set
	 */
	public void setSelectorKey(final String selectorKey) {

		this.selectorKey = selectorKey;
	}

	/**
	 * @return the selectorKeyClass
	 */
	public String getSelectorKeyClass() {

		return selectorKeyClass;
	}

	/**
	 * @param selectorKeyClass
	 *            the selectorKeyClass to set
	 */
	public void setSelectorKeyClass(final String selectorKeyClass) {

		this.selectorKeyClass = selectorKeyClass;
	}

	/**
	 * @return the optimiserParam
	 */
	public String getOptimiserParam() {

		return optimiserParam;
	}

	/**
	 * @param optimiserParam
	 *            the optimiserParam to set
	 */
	public void setOptimiserParam(final String optimiserParam) {

		this.optimiserParam = optimiserParam;
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
