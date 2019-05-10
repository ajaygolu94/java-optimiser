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

import uk.co.xcordis.optimiser.dao.RulesDao;
import uk.co.xcordis.optimiser.model.Rules;
import uk.co.xcordis.optimiser.util.ApplicationTableConstants;

/**
 * The <code>RulesDaoImpl</code> class responsible for implement the RulesDao method in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Repository
public class RulesDaoImpl extends BaseDaoImpl<Rules> implements RulesDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(RulesDaoImpl.class);

	private RulesDao rulesDao;

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.dao.impl.BaseDaoImpl#setMappper()
	 */
	@Override
	protected void setMappper() {

		mapper = getManager().mapper(Rules.class);
		tableName = ApplicationTableConstants.TABLENAME_RULES;
		rulesDao = getManager().createAccessor(RulesDao.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.co.xcordis.optimiser.dao.RulesDao#getRuleByRuleName(java.lang.String)
	 */
	@Override
	public Rules getRuleByRuleName(final String ruleName) {

		LOGGER.info("==> Method ==> getRuleByRuleName ==> Called");
		return rulesDao.getRuleByRuleName(ruleName);
	}
}
