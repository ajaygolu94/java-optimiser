/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.service;

import java.util.List;

import uk.co.xcordis.optimiser.model.RuleSelectorKeys;

/**
 * The <code>RuleSelectorKeysService</code> interface responsible for provide the rule selector keys related method in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
public interface RuleSelectorKeysService {

	/**
	 * The <code>findById</code> method is use to get RuleSelectorKeys object base on the primary keys or Ids.
	 *
	 * @param objects
	 * @return
	 */
	public RuleSelectorKeys findById(Object... objects);

	/**
	 * The <code>getRuleSelectorKeysList</code> method is use to get List of all RuleSelectorKeys.
	 *
	 * @return
	 */
	public List<RuleSelectorKeys> getRuleSelectorKeysList();

	/**
	 * The <code>addRuleSelectorKey</code> method is used to add RuleSelectorKeys Details.
	 *
	 * @param ruleSelectorKeys
	 */
	public void addRuleSelectorKey(RuleSelectorKeys ruleSelectorKeys);

	/**
	 * The <code>updateRuleSelectorKey</code> method is used to update RuleSelectorKeys Details.
	 *
	 * @param ruleSelectorKeys
	 */
	public void updateRuleSelectorKey(RuleSelectorKeys ruleSelectorKeys);

	/**
	 * The <code>isSelectorKeyExists</code> method is used to check if SelectorKey already exists.
	 *
	 * @param selectorKey
	 */
	public boolean isSelectorKeyExists(String selectorKey);

}
