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

import uk.co.xcordis.optimiser.dao.MerchantGatewaysDao;
import uk.co.xcordis.optimiser.model.MerchantGateways;
import uk.co.xcordis.optimiser.util.ApplicationTableConstants;

/**
 * The <code>MerchantGatewaysDaoImpl</code> class responsible for implement the MerchantGatewaysDao method in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Repository
public class MerchantGatewaysDaoImpl extends BaseDaoImpl<MerchantGateways> implements MerchantGatewaysDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(MerchantGatewaysDaoImpl.class);

	private MerchantGatewaysDao merchantGatewaysDao;

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.dao.impl.BaseDaoImpl#setMappper()
	 */
	@Override
	protected void setMappper() {

		mapper = getManager().mapper(MerchantGateways.class);
		tableName = ApplicationTableConstants.TABLENAME_MERCHANTGATEWAYS;
		merchantGatewaysDao = getManager().createAccessor(MerchantGatewaysDao.class);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.dao.MerchantGatewaysDao#getMerchantGatewaysByMerchantId(java.util.UUID)
	 */
	@Override
	public Result<MerchantGateways> getMerchantGatewaysByMerchantId(final UUID merchantId) {

		LOGGER.info(" ==> Method ==> getMerchantGatewaysByMerchantId ==> Called");
		return merchantGatewaysDao.getMerchantGatewaysByMerchantId(merchantId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.dao.MerchantGatewaysDao#getMerchantGatewaysBySourceMerchantGatewayId(java.lang.String)
	 */
	@Override
	public MerchantGateways getMerchantGatewaysBySourceMerchantGatewayId(final String sourceMerchantGatewayId) {

		LOGGER.info(" ==> Method ==> getMerchantGatewaysBySourceMerchantGatewayId ==> Called");
		return merchantGatewaysDao.getMerchantGatewaysBySourceMerchantGatewayId(sourceMerchantGatewayId);
	}

}
