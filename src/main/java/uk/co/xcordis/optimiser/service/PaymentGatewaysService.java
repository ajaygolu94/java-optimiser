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

import uk.co.xcordis.optimiser.model.PaymentGateways;

/**
 * The <code>PaymentGatewayService</code> interface responsible for provide the payment gateways related method in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
public interface PaymentGatewaysService {

	/**
	 *
	 * @return
	 */
	List<PaymentGateways> getAllPaymentGateways();

	/**
	 *
	 * @param paymentGatewayId
	 * @return
	 */
	PaymentGateways getPaymentGatewayById(String paymentGatewayId);

	/**
	 *
	 * @param paymentGateways
	 * @return
	 */
	PaymentGateways savePaymentGatewayDetails(PaymentGateways paymentGateways);

	/**
	 *
	 * @param status
	 * @param paymentGatewayId
	 */
	public void inActivePaymentGateway(Boolean status, UUID paymentGatewayId);

	/**
	 * This method <code>getPaymentGatewayByGatewayName</code> is used for get the payment gateway object by gatewayname param.
	 *
	 * @param gatewayName
	 * @return
	 */
	public PaymentGateways getPaymentGatewayByGatewayName(String gatewayName);

}
