/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.service;

import java.util.List;
import java.util.UUID;

import uk.co.xcordis.optimiser.model.Rules;

/**
 * The <code>RulesService</code> interface responsible for provide the rules related method in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
public interface RulesService {

	/**
	 * This <code>findById</code> method is use to get rule object base on primary key or id.
	 *
	 * @param objects
	 * @return
	 */
	public Rules findById(Object... objects);

	/**
	 * The <code>getRulesList</code> method is used for get the list of Rules.
	 *
	 * @return
	 */
	public List<Rules> getRulesList();

	/**
	 * The <code>addRule</code> method is used to add Rule Details.
	 *
	 * @param rules
	 */
	public void addRule(Rules rules);

	/**
	 * The <code>deleteRule</code> method is used to delete the rule based on Rule Id.
	 *
	 * @param ruleId
	 * @param deactivate
	 * @return
	 */
	public Boolean deleteRule(UUID ruleId, Boolean deactivate);

	/**
	 * The <code>updateRule</code> method is used update Rule Details.
	 *
	 * @param rules
	 */
	public void updateRule(Rules rules);

	/**
	 * The <code>isRuleNameExists</code> method is used to check if Rule Name already exists based on Rule Name.
	 *
	 * @param ruleName
	 * @return
	 */
	public boolean isRuleNameExists(String ruleName);

	/**
	 * The <code>getSelectorKeyListByRuleName</code> method is used to get the list of SelectorKeys by the rule name assigned to the particular rule.
	 *
	 * @param ruleName
	 * @return
	 */
	public List<String> getSelectorKeyListByRuleName(String ruleName);

}
