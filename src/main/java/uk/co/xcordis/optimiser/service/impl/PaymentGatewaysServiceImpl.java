/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.xcordis.optimiser.model.PaymentGateways;
import uk.co.xcordis.optimiser.service.PaymentGatewaysService;
import uk.co.xcordis.optimiser.util.ApplicationConstants;
import uk.co.xcordis.optimiser.util.ApplicationUtils;
import uk.co.xcordis.optimiser.util.DaoFactory;

/**
 * The <code>PaymentGatewaysServiceImpl</code> class responsible for implement the PaymentGatewaysService method in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Service
public class PaymentGatewaysServiceImpl implements PaymentGatewaysService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentGatewaysServiceImpl.class);

	@Autowired
	private DaoFactory daoFactory;

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.PaymentGatewaysService#getAllPaymentGateways()
	 */
	@Override
	public List<PaymentGateways> getAllPaymentGateways() {

		LOGGER.info(" ==> Method ==> getAllPaymentGateways ==> Called");

		final List<PaymentGateways> allPaymentGateways = daoFactory.getPaymentGatewaysDao().getAllPaymentGateways().all();

		if (ApplicationUtils.isValid(allPaymentGateways)) {
			ApplicationUtils.sortListByTimeStamp(allPaymentGateways, ApplicationConstants.FIELD_AUDITTIMESTAMP_LABEL,
					ApplicationConstants.DD_MM_YYYY_HH_MM_SS_AM_PM);
			return allPaymentGateways;
		}

		return Collections.emptyList();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.PaymentGatewaysService#getPaymentGatewayById(java.lang.String)
	 */
	@Override
	public PaymentGateways getPaymentGatewayById(final String paymentGatewayId) {

		LOGGER.info(" ==> Method ==> getPaymentGatewayById ==> Called");

		return (PaymentGateways) daoFactory.getPaymentGatewaysDao().findById(UUID.fromString(paymentGatewayId));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.PaymentGatewaysService#savePaymentGatewayDetails(uk.co.xcordis.optimiser.model.PaymentGateways)
	 */
	@Override
	public PaymentGateways savePaymentGatewayDetails(final PaymentGateways paymentGateways) {

		LOGGER.info(" ==> Method ==> savePaymentGatewayDetails ==> Called");

		if (paymentGateways.getPaymentgatewayid() == null) {
			paymentGateways.setPaymentgatewayid(UUID.randomUUID());
			paymentGateways.setCreatedDate(ApplicationUtils.getCurrentTimeStamp());
		}

		paymentGateways.setActive(Boolean.TRUE);
		paymentGateways.setAuditTimeStamp(ApplicationUtils.getCurrentTimeStamp());
		daoFactory.getPaymentGatewaysDao().add(paymentGateways);

		return paymentGateways;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.PaymentGatewaysService#inActivePaymentGateway(java.lang.Boolean, java.util.UUID)
	 */
	@Override
	public void inActivePaymentGateway(final Boolean status, final UUID paymentGatewayId) {

		LOGGER.info(" ==> Method ==> inActivePaymentGateway ==> Called");

		daoFactory.getPaymentGatewaysDao().inActivePaymentGateway(status, ApplicationUtils.getCurrentTimeStamp(), paymentGatewayId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.PaymentGatewaysService#getPaymentGatewayByGatewayName(java.lang.String)
	 */
	@Override
	public PaymentGateways getPaymentGatewayByGatewayName(final String gatewayName) {

		LOGGER.info(" ==> Method ==> getPaymentGatewayByGatewayName ==> Called");
		return daoFactory.getPaymentGatewaysDao().getPaymentGatewayByGatewayName(gatewayName);
	}

}
