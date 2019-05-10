/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.datastax.driver.mapping.Result;

import uk.co.xcordis.optimiser.dao.MerchantRelatedJobRecordDao;
import uk.co.xcordis.optimiser.model.MerchantRelatedJobRecord;
import uk.co.xcordis.optimiser.util.ApplicationTableConstants;

/**
 * The <code>MenuDaoImpl</code> class responsible for implement the MenuDao method in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Repository
public class MerchantRelatedJobRecordDaoImpl extends BaseDaoImpl<MerchantRelatedJobRecord> implements MerchantRelatedJobRecordDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(MerchantRelatedJobRecordDaoImpl.class);

	private MerchantRelatedJobRecordDao merchantRelatedJobRecordDao;

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.dao.impl.BaseDaoImpl#setMappper()
	 */
	@Override
	protected void setMappper() {

		mapper = getManager().mapper(MerchantRelatedJobRecord.class);
		tableName = ApplicationTableConstants.TABLENAME_MERCHANTRELATEDJOBRECORD;
		merchantRelatedJobRecordDao = getManager().createAccessor(MerchantRelatedJobRecordDao.class);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.dao.MerchantRelatedJobRecordDao#getListByRequestType(java.lang.String)
	 */
	@Override
	public Result<MerchantRelatedJobRecord> getListByRequestType(final String requestType) {

		LOGGER.info(" ==> Method ==> getListByRequestType ==> Called");
		return merchantRelatedJobRecordDao.getListByRequestType(requestType);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.dao.MerchantRelatedJobRecordDao#getListByStatus(java.lang.String)
	 */
	@Override
	public Result<MerchantRelatedJobRecord> getListByStatus(final String status) {

		LOGGER.info(" ==> Method ==> getListByStatus ==> Called");
		return merchantRelatedJobRecordDao.getListByStatus(status);
	}

}
