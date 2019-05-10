/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.dao.impl;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.datastax.driver.mapping.Result;

import uk.co.xcordis.optimiser.dao.PaymentGatewaysDao;
import uk.co.xcordis.optimiser.model.PaymentGateways;
import uk.co.xcordis.optimiser.util.ApplicationTableConstants;

/**
 * The <code>PaymentGatewaysDaoImpl</code> class responsible for implement the PaymentGatewaysDao method in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Repository
public class PaymentGatewaysDaoImpl extends BaseDaoImpl<PaymentGateways> implements PaymentGatewaysDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentGatewaysDaoImpl.class);

	private PaymentGatewaysDao paymentGatewaysDao;

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.dao.impl.BaseDaoImpl#setMappper()
	 */
	@Override
	protected void setMappper() {

		mapper = getManager().mapper(PaymentGateways.class);
		tableName = ApplicationTableConstants.TABLENAME_PAYMENTGATEWAYS;
		paymentGatewaysDao = getManager().createAccessor(PaymentGatewaysDao.class);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.dao.PaymentGatewaysDao#getAllPaymentGateways()
	 */
	@Override
	public Result<PaymentGateways> getAllPaymentGateways() {

		LOGGER.info(" ==> Method ==> getAllPaymentGateways ==> Called : ");
		return paymentGatewaysDao.getAllPaymentGateways();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.dao.PaymentGatewaysDao#inActivePaymentGateway(java.lang.Boolean, java.lang.String, java.util.UUID)
	 */
	@Override
	public void inActivePaymentGateway(final Boolean status, final String currentAuditTimestamp, final UUID paymentGatewayId) {

		LOGGER.info(" ==> Method ==> inActivePaymentGateway ==> Called ");
		paymentGatewaysDao.inActivePaymentGateway(status, currentAuditTimestamp, paymentGatewayId);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.co.xcordis.optimiser.dao.PaymentGatewaysDao#getPaymentGatewayByGatewayName(java.lang.String)
	 */
	@Override
	public PaymentGateways getPaymentGatewayByGatewayName(final String gatewayName) {

		LOGGER.info(" ==> Method ==> getPaymentGatewayByGatewayName ==> Called ");
		return paymentGatewaysDao.getPaymentGatewayByGatewayName(gatewayName);
	}
}
