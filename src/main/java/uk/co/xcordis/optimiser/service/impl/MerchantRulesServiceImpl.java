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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.xcordis.optimiser.model.Merchant;
import uk.co.xcordis.optimiser.model.MerchantRules;
import uk.co.xcordis.optimiser.model.Rules;
import uk.co.xcordis.optimiser.service.MerchantRulesService;
import uk.co.xcordis.optimiser.util.ApplicationConstants;
import uk.co.xcordis.optimiser.util.ApplicationUtils;
import uk.co.xcordis.optimiser.util.DaoFactory;

/**
 * The <code>MerchantRulesServiceImpl</code> class responsible for implement the MerchantRulesService method in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Service
public class MerchantRulesServiceImpl implements MerchantRulesService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MerchantRulesServiceImpl.class);

	@Autowired
	private DaoFactory daoFactory;

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.MerchantRulesService#getMerchantRulesByMerchantId(java.util.UUID)
	 */
	@Override
	public List<MerchantRules> getMerchantRulesByMerchantId(final UUID merchantId) {

		LOGGER.info(" ==> Method ==> getMerchantRulesByMerchantId ==> Start");

		final List<MerchantRules> merchantRules = daoFactory.getMerchantRulesDao().getMerchantRulesByMerchantId(merchantId).all();

		if (ApplicationUtils.isValid(merchantRules)) {
			ApplicationUtils.sortListByTimeStamp(merchantRules, ApplicationConstants.FIELD_AUDITTIMESTAMP_LABEL,
					ApplicationConstants.DD_MM_YYYY_HH_MM_SS_AM_PM);

			return merchantRules.stream().filter(merchantRule -> merchantRule != null && merchantRule.getActive() != null && merchantRule.getActive())
					.collect(Collectors.toList());
		}
		LOGGER.info(" ==> Method ==> getMerchantRulesByMerchantId ==> End");
		return Collections.emptyList();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.MerchantRulesService#getMerchantRulesList()
	 */
	@Override
	public List<MerchantRules> getMerchantRulesList() {

		LOGGER.info(" ==> Method ==> getMerchantRulesList ==> Called");

		return daoFactory.getMerchantRulesDao().list();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.MerchantRulesService#addMerchantRule(uk.co.xcordis.optimiser.model.MerchantRules)
	 */
	@Override
	public void addMerchantRule(MerchantRules merchantRules) {

		LOGGER.info(" ==> Method ==> add ==> Called");

		Merchant merchant = (Merchant) daoFactory.getMerchantDao().findById(merchantRules.getMerchantId());
		if (merchant != null && !ApplicationUtils.isEmpty(merchant.getMerchantname())) {
			merchantRules.setMerchantname(merchant.getMerchantname());
		}

		Rules rules = (Rules) daoFactory.getRulesDao().findById(merchantRules.getRuleId());
		if (rules != null) {
			BeanUtils.copyProperties(rules, merchantRules);
		}
		merchantRules.setMerchantRuleId(UUID.randomUUID());
		merchantRules.setAuditTimeStamp(ApplicationUtils.getCurrentTimeStamp());
		merchantRules.setCreatedDate(ApplicationUtils.getCurrentTimeStamp());
		merchantRules.setActive(Boolean.TRUE);

		daoFactory.getMerchantRulesDao().add(merchantRules);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.MerchantRulesService#findById(java.util.UUID)
	 */
	@Override
	public MerchantRules findById(UUID merchantRuleId) {

		LOGGER.info(" ==> Method ==> findById ==> Called");
		return (MerchantRules) daoFactory.getMerchantRulesDao().findById(merchantRuleId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.MerchantRulesService#softDelete(java.util.UUID)
	 */
	@Override
	public void softDelete(UUID merchantRuleId) {

		LOGGER.info(" ==> Method ==> softDelete ==> Called");
		daoFactory.getMerchantRulesDao().setMerchantRuleInactive(Boolean.FALSE, ApplicationUtils.getCurrentTimeStamp(), merchantRuleId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.MerchantRulesService#updateMerchantRule(uk.co.xcordis.optimiser.model.MerchantRules)
	 */
	@Override
	public void updateMerchantRule(MerchantRules merchantRules) {

		LOGGER.info(" ==> Method ==> edit ==> Called");

		Merchant merchant = (Merchant) daoFactory.getMerchantDao().findById(merchantRules.getMerchantId());
		if (merchant != null && !ApplicationUtils.isEmpty(merchant.getMerchantname())) {
			merchantRules.setMerchantname(merchant.getMerchantname());
		}

		Rules rules = (Rules) daoFactory.getRulesDao().findById(merchantRules.getRuleId());
		if (rules != null) {
			BeanUtils.copyProperties(rules, merchantRules);
		}
		merchantRules.setAuditTimeStamp(ApplicationUtils.getCurrentTimeStamp());

		daoFactory.getMerchantRulesDao().update(merchantRules);

	}

}
