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

import uk.co.xcordis.optimiser.model.Merchant;

/**
 * The <code>MerchantService</code> interface responsible for provide the merchant related method in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
public interface MerchantService {

	/**
	 * This <code>getMerchantBySourceMerchantId</code> method is use to get merchant base on source merchantId.
	 *
	 * @param sourceMerchantId
	 * @return
	 */
	public Merchant getMerchantBySourceMerchantId(String sourceMerchantId);

	/**
	 * This <code>getAllMerchantsByUserId</code> method is used to get all active merchants by user id from the database.
	 *
	 * @param userId
	 *
	 * @return
	 */
	public List<Merchant> getAllMerchantsByUserId(String userId);

	/**
	 * This <code>getMerchantByMerchantId</code> method is used to get merchant by merchant id.
	 *
	 * @param merchantId
	 * @return
	 */
	public Merchant getMerchantByMerchantId(UUID merchantId);

	/**
	 * This <code>saveMerchant</code> method is used to save the merchant data.
	 *
	 * @param merchant
	 */
	public Merchant saveMerchant(Merchant merchant);

	/**
	 * This <code>inActiveMerchant</code> method is used to inactive the merchant by merchant id.
	 *
	 * @param status
	 *
	 * @param merchantId
	 */
	public void inActiveMerchant(Boolean status, UUID merchantId);

	/**
	 * This <code>getAllMerchants</code> method is used to get all active merchants by user id from the database.
	 *
	 *
	 * @return
	 */
	public List<Merchant> getAllMerchants();
}
