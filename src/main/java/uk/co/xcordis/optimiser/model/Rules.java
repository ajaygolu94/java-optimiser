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
import java.util.UUID;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import uk.co.xcordis.optimiser.util.ApplicationTableConstants;

/**
 * The <code>Rules</code> model is used to store the Rules details into database in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 *
 */
@Table(name = ApplicationTableConstants.TABLENAME_RULES)
public class Rules implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@PartitionKey(0)
	private UUID ruleid;

	private List<String> selectorKey;

	private String ruleclass;

	private String ruledescription;

	private String rulename;

	private Integer defaultsequence;

	private Boolean active;

	private String auditTimeStamp;

	private String createddate;

	/**
	 * @return the ruleid
	 */
	public UUID getRuleid() {

		return ruleid;
	}

	/**
	 * @param ruleid
	 *            the ruleid to set
	 */
	public void setRuleid(final UUID ruleid) {

		this.ruleid = ruleid;
	}

	/**
	 * @return the selectorKey
	 */
	public List<String> getSelectorKey() {

		return selectorKey;
	}

	/**
	 * @param selectorKey
	 *            the selectorKey to set
	 */
	public void setSelectorKey(final List<String> selectorKey) {

		this.selectorKey = selectorKey;
	}

	/**
	 * @return the ruleclass
	 */
	public String getRuleclass() {

		return ruleclass;
	}

	/**
	 * @param ruleclass
	 *            the ruleclass to set
	 */
	public void setRuleclass(final String ruleclass) {

		this.ruleclass = ruleclass;
	}

	/**
	 * @return the ruledescription
	 */
	public String getRuledescription() {

		return ruledescription;
	}

	/**
	 * @param ruledescription
	 *            the ruledescription to set
	 */
	public void setRuledescription(final String ruledescription) {

		this.ruledescription = ruledescription;
	}

	/**
	 * @return the rulename
	 */
	public String getRulename() {

		return rulename;
	}

	/**
	 * @param rulename
	 *            the rulename to set
	 */
	public void setRulename(final String rulename) {

		this.rulename = rulename;
	}

	/**
	 * @return the defaultsequence
	 */
	public Integer getDefaultsequence() {

		return defaultsequence;
	}

	/**
	 * @param defaultsequence
	 *            the defaultsequence to set
	 */
	public void setDefaultsequence(final Integer defaultsequence) {

		this.defaultsequence = defaultsequence;
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

}
