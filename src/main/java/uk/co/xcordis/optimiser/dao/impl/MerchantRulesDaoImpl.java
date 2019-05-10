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

import uk.co.xcordis.optimiser.dao.MerchantRulesDao;
import uk.co.xcordis.optimiser.model.MerchantRules;
import uk.co.xcordis.optimiser.util.ApplicationTableConstants;

/**
 * The <code>MerchantRulesDaoImpl</code> class responsible for implement the MerchantRulesDao method in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Repository
public class MerchantRulesDaoImpl extends BaseDaoImpl<MerchantRules> implements MerchantRulesDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(MerchantRulesDaoImpl.class);

	private MerchantRulesDao merchantRulesDao;

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.dao.impl.BaseDaoImpl#setMappper()
	 */
	@Override
	protected void setMappper() {

		mapper = getManager().mapper(MerchantRules.class);
		tableName = ApplicationTableConstants.TABLENAME_MERCHANTRULES;
		merchantRulesDao = getManager().createAccessor(MerchantRulesDao.class);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.dao.MerchantRulesDao#getMerchantRulesByMerchantId(java.util.UUID)
	 */
	@Override
	public Result<MerchantRules> getMerchantRulesByMerchantId(final UUID merchantId) {

		LOGGER.info(" ==> Method ==> getMerchantRulesByMerchantId ==> Called");
		return merchantRulesDao.getMerchantRulesByMerchantId(merchantId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.dao.MerchantRulesDao#setMerchantRuleInactive(java.lang.Boolean, java.lang.String, java.util.UUID)
	 */
	@Override
	public void setMerchantRuleInactive(Boolean status, String currentAuditTimestamp, UUID merchantRuleId) {

		LOGGER.info(" ==> Method ==> setMerchantRuleInactive ==> Called");
		merchantRulesDao.setMerchantRuleInactive(status, currentAuditTimestamp, merchantRuleId);
	}
}
