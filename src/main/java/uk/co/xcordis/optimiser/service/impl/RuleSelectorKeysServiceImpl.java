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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.xcordis.optimiser.model.RuleSelectorKeys;
import uk.co.xcordis.optimiser.service.RuleSelectorKeysService;
import uk.co.xcordis.optimiser.util.ApplicationConstants;
import uk.co.xcordis.optimiser.util.ApplicationUtils;
import uk.co.xcordis.optimiser.util.DaoFactory;

/**
 * The <code>RuleSelectorKeysServiceImpl</code> class responsible for implement the RulesSelectorKeysService method in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Service
public class RuleSelectorKeysServiceImpl implements RuleSelectorKeysService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RuleSelectorKeysServiceImpl.class);

	@Autowired
	private DaoFactory daoFactory;

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.RuleSelectorKeysService#findById(java.lang.Object[])
	 */
	@Override
	public RuleSelectorKeys findById(final Object... objects) {

		LOGGER.info(" ==> Method ==> findById ==> Called");
		return (RuleSelectorKeys) daoFactory.getRuleSelectorKeysDao().findById(objects);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.RuleSelectorKeysService#getRuleSelectorKeysList()
	 */
	@Override
	public List<RuleSelectorKeys> getRuleSelectorKeysList() {

		LOGGER.info(" ==> Method ==> getRuleSelectorKeysList ==> Called");
		final List<RuleSelectorKeys> ruleSelectorKeysList = daoFactory.getRuleSelectorKeysDao().list();

		if (ApplicationUtils.isValid(ruleSelectorKeysList)) {

			ApplicationUtils.sortListByTimeStamp(ruleSelectorKeysList, ApplicationConstants.FIELD_AUDITTIMESTAMP_LABEL,
					ApplicationConstants.DD_MM_YYYY_HH_MM_SS_AM_PM);
			return ruleSelectorKeysList;
		}

		return Collections.emptyList();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.RuleSelectorKeysService#addRuleSelectorKeys(uk.co.xcordis.optimiser.model.RuleSelectorKeys)
	 */
	@Override
	public void addRuleSelectorKey(RuleSelectorKeys ruleSelectorKeys) {

		LOGGER.info(" ==> Method ==> addRuleSelectorKeys ==> Called");

		try {
			ruleSelectorKeys.setAuditTimeStamp(ApplicationUtils.getCurrentTimeStamp());
			daoFactory.getRuleSelectorKeysDao().add(ruleSelectorKeys);
		} catch (Exception e) {
			LOGGER.error(" ==> Method ==> addRuleSelectorKeys ==> Exception ==> ", e);
		}

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.RuleSelectorKeysService#updateRuleSelectorKeys(uk.co.xcordis.optimiser.model.RuleSelectorKeys)
	 */
	@Override
	public void updateRuleSelectorKey(RuleSelectorKeys ruleSelectorKeys) {

		LOGGER.info(" ==> Method ==> updateRuleSelectorKeys ==> Called");

		try {
			ruleSelectorKeys.setAuditTimeStamp(ApplicationUtils.getCurrentTimeStamp());
			daoFactory.getRuleSelectorKeysDao().update(ruleSelectorKeys);
		} catch (Exception e) {
			LOGGER.error(" ==> Method ==> updateRuleSelectorKeys ==> Exception ==> ", e);
		}

	}

	@Override
	public boolean isSelectorKeyExists(String selectorKey) {

		LOGGER.info(" ==> Method ==> isSelectorKeyExists ==> Called");

		final RuleSelectorKeys ruleSelectorKeys = findById(selectorKey);

		if (ruleSelectorKeys != null && !ApplicationUtils.isEmpty(ruleSelectorKeys.getSelectorKey())) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}

	}
}
