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

import uk.co.xcordis.optimiser.model.MerchantRules;

/**
 * The <code>MerchantRulesService</code> interface responsible for provide the merchant rules related method in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
public interface MerchantRulesService {

	/**
	 * This <code>getMerchantRulesByMerchantId</code> method is use to get all merchant rules base on merchantId.
	 *
	 * @param merchantId
	 * @return
	 */
	public List<MerchantRules> getMerchantRulesByMerchantId(UUID merchantId);

	/**
	 * The <code>getMerchantRulesList</code> method is use to get all merchant rules.
	 *
	 * @return
	 */
	public List<MerchantRules> getMerchantRulesList();

	/**
	 * The <code>addMerchantRule</code> method is use to add merchant rules to DB.
	 *
	 * @param merchantRules
	 */
	public void addMerchantRule(MerchantRules merchantRules);

	/**
	 * The <code>findById</code> method is get merchant rules by Id from DB.
	 *
	 * @param merchantRules
	 */
	public MerchantRules findById(UUID merchantRuleId);

	/**
	 * The <code>softDelete</code> method is soft delete(set status to in active) merchant rules by Id to DB.
	 *
	 * @param merchantRuleId
	 */
	public void softDelete(UUID merchantRuleId);

	/**
	 * The <code>updateMerchantRule</code> method is use to edit existing merchant rules to DB.
	 *
	 * @param merchantRules
	 */
	public void updateMerchantRule(MerchantRules merchantRules);

}
