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

import uk.co.xcordis.optimiser.dao.RuleSelectorKeysDao;
import uk.co.xcordis.optimiser.model.RuleSelectorKeys;
import uk.co.xcordis.optimiser.util.ApplicationTableConstants;

/**
 * The <code>RuleSelectorKeysDaoImpl</code> class responsible for implement the RuleSelectorKeysDao method in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Repository
public class RuleSelectorKeysDaoImpl extends BaseDaoImpl<RuleSelectorKeys> implements RuleSelectorKeysDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(RuleSelectorKeysDaoImpl.class);

	private RuleSelectorKeysDao ruleSelectorKeysDao;

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.dao.impl.BaseDaoImpl#setMappper()
	 */
	@Override
	protected void setMappper() {

		mapper = getManager().mapper(RuleSelectorKeys.class);
		tableName = ApplicationTableConstants.TABLENAME_RULESELECTORKEYS;
		ruleSelectorKeysDao = getManager().createAccessor(RuleSelectorKeysDao.class);
	}
}
