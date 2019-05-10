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

import uk.co.xcordis.optimiser.model.MerchantGateways;

/**
 * The <code>MerchantGatewaysDao</code> interface responsible for provide the merchant gateways related method in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Accessor
public interface MerchantGatewaysDao extends BaseDao<MerchantGateways> {

	/**
	 * This <code>getMerchantGatewaysByMerchantId</code> method is use to get merchant gateways base on merchantId.
	 *
	 * @param merchantId
	 * @return
	 */
	@Query("select * from merchantgateways where merchantid = ?")
	public Result<MerchantGateways> getMerchantGatewaysByMerchantId(UUID merchantId);

	/**
	 * This <code>getMerchantGatewaysBySourceMerchantGatewayId</code> method is use to get merchant gateways base on sourceMerchantGatewayId.
	 *
	 * @param sourceMerchantGatewayId
	 * @return
	 */
	@Query("select * from merchantgateways where sourcemerchantgatewayid = ?")
	public MerchantGateways getMerchantGatewaysBySourceMerchantGatewayId(String sourceMerchantGatewayId);
}
