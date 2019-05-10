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

import uk.co.xcordis.optimiser.dao.MerchantDao;
import uk.co.xcordis.optimiser.model.Merchant;
import uk.co.xcordis.optimiser.util.ApplicationTableConstants;

/**
 * The <code>MerchantDaoImpl</code> class responsible for implement the MerchantDao method in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Repository
public class MerchantDaoImpl extends BaseDaoImpl<Merchant> implements MerchantDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(MerchantDaoImpl.class);

	private MerchantDao merchantDao;

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.dao.impl.BaseDaoImpl#setMappper()
	 */
	@Override
	protected void setMappper() {

		mapper = getManager().mapper(Merchant.class);
		tableName = ApplicationTableConstants.TABLENAME_MERCHANT;
		merchantDao = getManager().createAccessor(MerchantDao.class);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.dao.MerchantDao#getMerchantBySourceMerchantId(java.lang.String)
	 */
	@Override
	public Merchant getMerchantBySourceMerchantId(final String sourceMerchantId) {

		LOGGER.info(" ==> Method ==> getMerchantBySourceMerchantId ==> Called");
		return merchantDao.getMerchantBySourceMerchantId(sourceMerchantId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.dao.MerchantDao#getAllMerchantsByUserId(java.util.UUID)
	 */
	@Override
	public Result<Merchant> getAllMerchantsByUserId(UUID userId) {

		LOGGER.info(" ==> Method ==> getAllMerchants ==> Called");
		return merchantDao.getAllMerchantsByUserId(userId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.dao.MerchantDao#inActiveMerchant(java.lang.Boolean, java.lang.String, java.util.UUID)
	 */
	@Override
	public void inActiveMerchant(Boolean status, String currentAuditTimestamp, UUID merchantId) {

		LOGGER.info(" ==> Method ==> inActiveMerchant ==> Called");
		merchantDao.inActiveMerchant(status, currentAuditTimestamp, merchantId);

	}
}
