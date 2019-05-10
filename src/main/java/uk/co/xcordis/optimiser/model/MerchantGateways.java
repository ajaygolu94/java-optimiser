/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import uk.co.xcordis.optimiser.util.ApplicationTableConstants;

/**
 * The <code>MerchantGateways</code> model is used to store the Merchant Gateways details into database in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 *
 */
@Table(name = ApplicationTableConstants.TABLENAME_MERCHANTGATEWAYS)
public class MerchantGateways implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@PartitionKey(0)
	private UUID merchantGatewayId;

	private UUID merchantId;

	private UUID paymentGatewayId;

	private List<Map<String, String>> merchantGatewayParameter;

	private Map<String, String> selector;

	private String sourceMerchantGatewayId;

	private Integer paymentGatewayPreference;

	private Integer paymentGatewaySequence;

	private Boolean active;

	private String auditTimeStamp;

	private String gatewayName;

	private String hostedPaymentPage;

	/**
	 * @return the merchantGatewayId
	 */
	public UUID getMerchantGatewayId() {

		return merchantGatewayId;
	}

	/**
	 * @param merchantGatewayId
	 *            the merchantGatewayId to set
	 */
	public void setMerchantGatewayId(final UUID merchantGatewayId) {

		this.merchantGatewayId = merchantGatewayId;
	}

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
	 * @return the paymentGatewayId
	 */
	public UUID getPaymentGatewayId() {

		return paymentGatewayId;
	}

	/**
	 * @param paymentGatewayId
	 *            the paymentGatewayId to set
	 */
	public void setPaymentGatewayId(final UUID paymentGatewayId) {

		this.paymentGatewayId = paymentGatewayId;
	}

	/**
	 * @return the merchantGatewayParameter
	 */
	public List<Map<String, String>> getMerchantGatewayParameter() {

		return merchantGatewayParameter;
	}

	/**
	 * @param merchantGatewayParameter
	 *            the merchantGatewayParameter to set
	 */
	public void setMerchantGatewayParameter(final List<Map<String, String>> merchantGatewayParameter) {

		this.merchantGatewayParameter = merchantGatewayParameter;
	}

	/**
	 * @return the selector
	 */
	public Map<String, String> getSelector() {

		return selector;
	}

	/**
	 * @param selector
	 *            the selector to set
	 */
	public void setSelector(final Map<String, String> selector) {

		this.selector = selector;
	}

	/**
	 * @return the sourceMerchantGatewayId
	 */
	public String getSourceMerchantGatewayId() {

		return sourceMerchantGatewayId;
	}

	/**
	 * @param sourceMerchantGatewayId
	 *            the sourceMerchantGatewayId to set
	 */
	public void setSourceMerchantGatewayId(final String sourceMerchantGatewayId) {

		this.sourceMerchantGatewayId = sourceMerchantGatewayId;
	}

	/**
	 * @return the paymentGatewayPreference
	 */
	public Integer getPaymentGatewayPreference() {

		return paymentGatewayPreference;
	}

	/**
	 * @param paymentGatewayPreference
	 *            the paymentGatewayPreference to set
	 */
	public void setPaymentGatewayPreference(final Integer paymentGatewayPreference) {

		this.paymentGatewayPreference = paymentGatewayPreference;
	}

	/**
	 * @return the paymentGatewaySequence
	 */
	public Integer getPaymentGatewaySequence() {

		return paymentGatewaySequence;
	}

	/**
	 * @param paymentGatewaySequence
	 *            the paymentGatewaySequence to set
	 */
	public void setPaymentGatewaySequence(final Integer paymentGatewaySequence) {

		this.paymentGatewaySequence = paymentGatewaySequence;
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
	 * @return the gatewayName
	 */
	public String getGatewayName() {

		return gatewayName;
	}

	/**
	 * @param gatewayName
	 *            the gatewayName to set
	 */
	public void setGatewayName(final String gatewayName) {

		this.gatewayName = gatewayName;
	}

	/**
	 * @return the hostedPaymentPage
	 */
	public String getHostedPaymentPage() {

		return hostedPaymentPage;
	}

	/**
	 * @param hostedPaymentPage
	 *            the hostedPaymentPage to set
	 */
	public void setHostedPaymentPage(final String hostedPaymentPage) {

		this.hostedPaymentPage = hostedPaymentPage;
	}

	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
