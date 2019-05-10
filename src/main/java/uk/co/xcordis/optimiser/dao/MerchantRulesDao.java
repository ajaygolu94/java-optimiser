/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.dao;

import java.util.UUID;

import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Query;

import uk.co.xcordis.optimiser.model.MerchantRules;

/**
 * The <code>MerchantRules</code> interface responsible for provide the merchant wise rules related method in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Accessor
public interface MerchantRulesDao extends BaseDao<MerchantRules> {

	/**
	 * This <code>getMerchantRulesByMerchantId</code> method is use to get all merchant rules base on merchantId.
	 *
	 * @param merchantId
	 * @return
	 */
	@Query("select * from merchantrules where merchantid = ?")
	public Result<MerchantRules> getMerchantRulesByMerchantId(UUID merchantId);

	/**
	 * The <code>setMerchantRuleInactive</code> method is use to set merchant rules inactive base on merchantRuleId.
	 *
	 * @param status
	 * @param currentAuditTimestamp
	 * @param merchantRuleId
	 */
	@Query("UPDATE merchantrules SET active = ?, audittimestamp = ? WHERE merchantruleid = ?")
	public void setMerchantRuleInactive(Boolean status, String currentAuditTimestamp, UUID merchantRuleId);
}
