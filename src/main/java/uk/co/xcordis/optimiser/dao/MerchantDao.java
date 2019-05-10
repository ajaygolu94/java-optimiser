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

import uk.co.xcordis.optimiser.model.Merchant;

/**
 * The <code>MerchantDao</code> interface responsible for provide the merchant related method in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Accessor
public interface MerchantDao extends BaseDao<Merchant> {

	/**
	 * This <code>getMerchantBySourceMerchantId</code> method is use to get merchant base on source merchantId.
	 *
	 * @param sourceMerchantId
	 * @return
	 */
	@Query("select * from merchant where sourcemerchantid = ?")
	public Merchant getMerchantBySourceMerchantId(String sourceMerchantId);

	/**
	 * This <code>getAllMerchantsByUserId</code> method is use to get all active merchants by user id.
	 *
	 * @param userId
	 *
	 * @return
	 */
	@Query("select * from merchant where userid=?")
	public Result<Merchant> getAllMerchantsByUserId(UUID userId);

	/**
	 * This <code>inActiveMerchant</code> method is used to inactive the merchant by merchant id.
	 *
	 * @param status
	 * @param currentAuditTimestamp
	 * @param merchantId
	 */
	@Query("UPDATE merchant SET active = ?, audittimestamp = ? WHERE merchantid = ?")
	public void inActiveMerchant(Boolean status, String currentAuditTimestamp, UUID merchantId);
}
