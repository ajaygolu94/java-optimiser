/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.model;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import uk.co.xcordis.optimiser.util.ApplicationTableConstants;

/**
 * The <code>PaymentGateways</code> model is used to store the payment gateways details into database in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 *
 */
@Table(name = ApplicationTableConstants.TABLENAME_PAYMENTGATEWAYS)
public class PaymentGateways implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@PartitionKey(0)
	private UUID paymentgatewayid;

	private String paymentgatewayname;

	private String description;

	private Map<String, String> gatewayParameters;

	private String createdDate;

	private Boolean active;

	private String auditTimeStamp;

	/**
	 * @return the paymentgatewayid
	 */
	public UUID getPaymentgatewayid() {

		return paymentgatewayid;
	}

	/**
	 * @param paymentgatewayid
	 *            the paymentgatewayid to set
	 */
	public void setPaymentgatewayid(final UUID paymentgatewayid) {

		this.paymentgatewayid = paymentgatewayid;
	}

	/**
	 * @return the paymentgatewayname
	 */
	public String getPaymentgatewayname() {

		return paymentgatewayname;
	}

	/**
	 * @param paymentgatewayname
	 *            the paymentgatewayname to set
	 */
	public void setPaymentgatewayname(final String paymentgatewayname) {

		this.paymentgatewayname = paymentgatewayname;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {

		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(final String description) {

		this.description = description;
	}

	/**
	 * @return the gatewayParameters
	 */
	public Map<String, String> getGatewayParameters() {

		return gatewayParameters;
	}

	/**
	 * @param gatewayParameters
	 *            the gatewayParameters to set
	 */
	public void setGatewayParameters(final Map<String, String> gatewayParameters) {

		this.gatewayParameters = gatewayParameters;
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

	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
