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
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.xcordis.optimiser.model.Rules;
import uk.co.xcordis.optimiser.service.RulesService;
import uk.co.xcordis.optimiser.util.ApplicationConstants;
import uk.co.xcordis.optimiser.util.ApplicationUtils;
import uk.co.xcordis.optimiser.util.DaoFactory;

/**
 * The <code>RulesServiceImpl</code> class responsible for implement the RulesService method in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Service
public class RulesServiceImpl implements RulesService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RulesServiceImpl.class);

	@Autowired
	private DaoFactory daoFactory;

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.RulesService#findById(java.lang.Object[])
	 */
	@Override
	public Rules findById(final Object... objects) {

		LOGGER.info(" ==> Method ==> findById ==> Called");
		return (Rules) daoFactory.getRulesDao().findById(objects);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.RulesService#getRulesList()
	 */
	@Override
	public List<Rules> getRulesList() {

		LOGGER.info(" ==> Method ==> getRulesList ==> Called");

		final List<Rules> rulesList = daoFactory.getRulesDao().list();

		if (ApplicationUtils.isValid(rulesList)) {

			ApplicationUtils.sortListByTimeStamp(rulesList, ApplicationConstants.FIELD_AUDITTIMESTAMP_LABEL, ApplicationConstants.DD_MM_YYYY_HH_MM_SS_AM_PM);
			return rulesList.stream().filter(rules -> rules != null && rules.getActive() != null && rules.getActive()).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.RulesService#addRule(uk.co.xcordis.optimiser.model.Rules)
	 */
	@Override
	public void addRule(final Rules rules) {

		LOGGER.info(" ==> Method ==> addRule ==> Called");
		rules.setRuleid(UUID.randomUUID());
		rules.setActive(Boolean.TRUE);
		rules.setCreateddate(ApplicationUtils.getCurrentTimeStamp());
		rules.setAuditTimeStamp(ApplicationUtils.getCurrentTimeStamp());
		daoFactory.getRulesDao().add(rules);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.RulesService#deleteRule(java.util.UUID)
	 */
	@Override
	public Boolean deleteRule(final UUID ruleId, final Boolean deactivate) {

		LOGGER.info(" ==> Method ==> deleteRule ==> Called");

		final Rules rules = (Rules) daoFactory.getRulesDao().findById(ruleId);

		if (rules != null && rules.getRuleid() != null) {

			rules.setActive(deactivate);
			rules.setAuditTimeStamp(ApplicationUtils.getCurrentTimeStamp());
			daoFactory.getRulesDao().update(rules);
			return Boolean.TRUE;

		} else {
			return Boolean.FALSE;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.RulesService#updateRule(uk.co.xcordis.optimiser.model.Rules)
	 */
	@Override
	public void updateRule(final Rules rules) {

		LOGGER.info(" ==> Method ==> updateRule ==> Called");
		rules.setAuditTimeStamp(ApplicationUtils.getCurrentTimeStamp());
		rules.setActive(Boolean.TRUE);
		daoFactory.getRulesDao().update(rules);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.RulesService#isRuleNameExists(java.lang.String)
	 */
	@Override
	public boolean isRuleNameExists(final String ruleName) {

		LOGGER.info(" ==> Method ==> isRuleNameExists ==> Called");
		final Rules rules = daoFactory.getRulesDao().getRuleByRuleName(ruleName);

		if (rules != null && !ApplicationUtils.isEmpty(rules.getRulename()) && rules.getActive()) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.RulesService#getSelectorKeyListByRuleName(java.lang.String)
	 */
	@Override
	public List<String> getSelectorKeyListByRuleName(final String ruleName) {

		LOGGER.info(" ==> Method ==> getSelectorKeyListByRuleName ==> Called");

		final Rules rules = daoFactory.getRulesDao().getRuleByRuleName(ruleName);

		if (rules != null && ApplicationUtils.isValid(rules.getSelectorKey())) {

			return rules.getSelectorKey();
		} else {

			return Collections.emptyList();
		}
	}
}
