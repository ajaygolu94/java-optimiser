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
 * The <code>Merchant</code> model is used to store the Merchant details into database in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 *
 */
@Table(name = ApplicationTableConstants.TABLENAME_MERCHANT)
public class Merchant implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@PartitionKey(0)
	private UUID merchantId;

	private UUID userId;

	private String createddate;

	private String merchantname;

	private String sourcemerchantid;

	private Boolean active;

	private String auditTimeStamp;

	/**
	 * @return the merchantId
	 */
	public UUID getMerchantId() {

		return merchantId;
	}

	/**
	 * @param merchantId
	 *            the merchantId to set
	 */
	public void setMerchantId(final UUID merchantId) {

		this.merchantId = merchantId;
	}

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
	 * @return the createddate
	 */
	public String getCreateddate() {

		return createddate;
	}

	/**
	 * @param createddate
	 *            the createddate to set
	 */
	public void setCreateddate(final String createddate) {

		this.createddate = createddate;
	}

	/**
	 * @return the merchantname
	 */
	public String getMerchantname() {

		return merchantname;
	}

	/**
	 * @param merchantname
	 *            the merchantname to set
	 */
	public void setMerchantname(final String merchantname) {

		this.merchantname = merchantname;
	}

	/**
	 * @return the sourcemerchantid
	 */
	public String getSourcemerchantid() {

		return sourcemerchantid;
	}

	/**
	 * @param sourcemerchantid
	 *            the sourcemerchantid to set
	 */
	public void setSourcemerchantid(final String sourcemerchantid) {

		this.sourcemerchantid = sourcemerchantid;
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
