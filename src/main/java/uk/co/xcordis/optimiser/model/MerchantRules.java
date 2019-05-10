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
 * The <code>MerchantRules</code> model is used to store the Merchant wise rules details into database in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 *
 */
@Table(name = ApplicationTableConstants.TABLENAME_MERCHANTRULES)
public class MerchantRules implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@PartitionKey(0)
	private UUID merchantRuleId;

	private UUID merchantId;

	private String merchantname;

	private UUID ruleId;

	private Map<String, String> entryCriteria;

	private Map<String, String> merchantRuleParam;

	private String createdDate;

	private Integer merchantRulesSequence;

	private String auditTimeStamp;

	private Boolean active;

	private String ruleclass;

	private String rulename;

	private String operation;

	/**
	 * @return the merchantruleid
	 */
	public UUID getMerchantRuleId() {

		return merchantRuleId;
	}

	/**
	 * @param merchantruleId
	 *            the merchantruleid to set
	 */
	public void setMerchantRuleId(final UUID merchantruleId) {

		merchantRuleId = merchantruleId;
	}

	/**
	 * @return the merchantid
	 */
	public UUID getMerchantId() {

		return merchantId;
	}

	/**
	 * @param merchantId
	 *            the merchantid to set
	 */
	public void setMerchantId(final UUID merchantId) {

		this.merchantId = merchantId;
	}

	/**
	 * @return the merchantName
	 */
	public String getMerchantname() {

		return merchantname;
	}

	/**
	 * @param merchantname
	 *            the merchantName to set
	 */
	public void setMerchantname(String merchantname) {

		this.merchantname = merchantname;
	}

	/**
	 * @return the ruleid
	 */
	public UUID getRuleId() {

		return ruleId;
	}

	/**
	 * @param ruleId
	 *            the ruleid to set
	 */
	public void setRuleId(final UUID ruleId) {

		this.ruleId = ruleId;
	}

	/**
	 * @return the selector
	 */
	public Map<String, String> getEntryCriteria() {

		return entryCriteria;
	}

	/**
	 * @param selector
	 *            the selector to set
	 */
	public void setEntryCriteria(final Map<String, String> entryCriteria) {

		this.entryCriteria = entryCriteria;
	}

	/**
	 * @return the merchantRuleParam
	 */
	public Map<String, String> getMerchantRuleParam() {

		return merchantRuleParam;
	}

	/**
	 * @param merchantRuleParam
	 *            the merchantRuleParam to set
	 */
	public void setMerchantRuleParam(final Map<String, String> merchantRuleParam) {

		this.merchantRuleParam = merchantRuleParam;
	}

	/**
	 * @return the createddate
	 */
	public String getCreatedDate() {

		return createdDate;
	}

	/**
	 * @param createdDate
	 *            the createddate to set
	 */
	public void setCreatedDate(final String createdDate) {

		this.createdDate = createdDate;
	}

	/**
	 * @return the merchantrulessequence
	 */
	public Integer getMerchantRulesSequence() {

		return merchantRulesSequence;
	}

	/**
	 * @param merchantRulesSequence
	 *            the merchantrulessequence to set
	 */
	public void setMerchantRulesSequence(final Integer merchantRulesSequence) {

		this.merchantRulesSequence = merchantRulesSequence;
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
	 * @return the ruleClass
	 */
	public String getRuleclass() {

		return ruleclass;
	}

	/**
	 * @param ruleclass
	 *            the ruleClass to set
	 */
	public void setRuleclass(final String ruleclass) {

		this.ruleclass = ruleclass;
	}

	/**
	 * @return the ruleName
	 */
	public String getRulename() {

		return rulename;
	}

	/**
	 * @param rulename
	 *            the ruleName to set
	 */
	public void setRulename(final String rulename) {

		this.rulename = rulename;
	}

	/**
	 * @return the operation
	 */
	public String getOperation() {

		return operation;
	}

	/**
	 * @param operation
	 *            the operation to set
	 */
	public void setOperation(final String operation) {

		this.operation = operation;
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
