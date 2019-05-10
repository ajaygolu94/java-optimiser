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

import uk.co.xcordis.optimiser.model.PaymentGateways;

/**
 * The <code>PaymentGatewaysDao</code> interface responsible for provide the payment gateways related method in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Accessor
public interface PaymentGatewaysDao extends BaseDao<PaymentGateways> {

	/**
	 *
	 * This <code>getAllPaymentGateways</code> method is use to get all active payment gateway.
	 *
	 * @return
	 */
	@Query("select * from paymentgateways where active = true")
	Result<PaymentGateways> getAllPaymentGateways();

	/**
	 *
	 * This <code>inActivePaymentGateway</code> method is use to in active the payment gateway by paymentGatewayId.
	 *
	 * @param status
	 * @param currentAuditTimestamp
	 * @param paymentGatewayId
	 */
	@Query("UPDATE paymentgateways SET active = ?, audittimestamp = ? WHERE paymentgatewayid = ?")
	public void inActivePaymentGateway(Boolean status, String currentAuditTimestamp, UUID paymentGatewayId);

	/**
	 * This method <code>getPaymentGatewayByGatewayName</code> is used for get the payment gateway object by gatewayname param.
	 *
	 * @param gatewayName
	 * @return
	 */
	@Query("select * from paymentgateways where paymentgatewayname = ?")
	public PaymentGateways getPaymentGatewayByGatewayName(String gatewayName);
}
