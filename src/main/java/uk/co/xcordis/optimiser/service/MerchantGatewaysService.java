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

import uk.co.xcordis.optimiser.model.MerchantGateways;

/**
 * The <code>MerchantGatewaysService</code> interface responsible for provide the merchant gateways related method in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
public interface MerchantGatewaysService {

	/**
	 * This method <code>findById</code> is used for get the merchant gateway by merchantGatewayId param.
	 *
	 * @param merchantGatewayId
	 * @return
	 */
	public MerchantGateways findById(final Object... objects);

	/**
	 * This <code>getMerchantGatewaysByMerchantId</code> method is use to get the merchant gateways base on merchantId.
	 *
	 * @param merchantId
	 * @return
	 */
	public List<MerchantGateways> getMerchantGatewaysByMerchantId(UUID merchantId);

	/**
	 * This <code>addMerchantGateways</code> is used for add the merchant gateways details to database.
	 *
	 * @param merchantGateways
	 */
	public void addMerchantGateways(MerchantGateways merchantGateways);

	/**
	 * This <code>updateMerchantGateways</code> is used for update the merchant gateways details to database.
	 *
	 * @param merchantGateways
	 */
	public void updateMerchantGateways(MerchantGateways merchantGateways);

	/**
	 * The <code>deleteMerchantGateways</code> method is used to delete the merchant gateway based on merchantGatewayId.
	 *
	 * @param merchantGatewayId
	 * @param deactivate
	 * @return
	 */
	public Boolean deleteMerchantGateways(UUID merchantGatewayId, Boolean deactivate);

	/**
	 * This method <code>getMerchantGatewaysBySourceMerchantGatewayId</code> is used to get the merchant gateways based on sourceMerchantGatewayId param.
	 *
	 * @param sourceMerchantGatewayId
	 * @return
	 */
	public MerchantGateways getMerchantGatewaysBySourceMerchantGatewayId(String sourceMerchantGatewayId);
}
