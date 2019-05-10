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

import uk.co.xcordis.optimiser.model.Merchant;
import uk.co.xcordis.optimiser.service.MerchantService;
import uk.co.xcordis.optimiser.util.ApplicationConstants;
import uk.co.xcordis.optimiser.util.ApplicationUtils;
import uk.co.xcordis.optimiser.util.DaoFactory;

/**
 * The <code>MerchantServiceImpl</code> class responsible for implement the MerchantService method in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Service
public class MerchantServiceImpl implements MerchantService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MerchantServiceImpl.class);

	@Autowired
	private DaoFactory daoFactory;

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.MerchantService#getMerchantBySourceMerchantId(java.lang.String)
	 */
	@Override
	public Merchant getMerchantBySourceMerchantId(final String sourceMerchantId) {

		LOGGER.info(" ==> Method ==> getMerchantBySourceMerchantId ==> Called");
		return daoFactory.getMerchantDao().getMerchantBySourceMerchantId(sourceMerchantId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.MerchantService#getAllMerchantsByUserId(java.lang.String)
	 */
	@Override
	public List<Merchant> getAllMerchantsByUserId(final String userId) {

		LOGGER.info(" ==> Method ==> getAllMerchantsByUserId ==> Start");

		final List<Merchant> merchants = daoFactory.getMerchantDao().getAllMerchantsByUserId(UUID.fromString(userId)).all();

		if (ApplicationUtils.isValid(merchants)) {

			ApplicationUtils.sortListByTimeStamp(merchants, ApplicationConstants.FIELD_AUDITTIMESTAMP_LABEL, ApplicationConstants.DD_MM_YYYY_HH_MM_SS_AM_PM);
			return merchants.stream().filter(merchant -> merchant != null && merchant.getActive() != null && merchant.getActive()).collect(Collectors.toList());
		}
		LOGGER.info(" ==> Method ==> getAllMerchantsByUserId ==> End");
		return Collections.emptyList();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.MerchantService#getMerchantByMerchantId(java.lang.String)
	 */
	@Override
	public Merchant getMerchantByMerchantId(final UUID merchantId) {

		LOGGER.info(" ==> Method ==> getMerchantByMerchantId ==> Called");
		return (Merchant) daoFactory.getMerchantDao().findById(merchantId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.MerchantService#saveMerchant(uk.co.xcordis.optimiser.model.Merchant)
	 */
	@Override
	public Merchant saveMerchant(final Merchant merchant) {

		LOGGER.info(" ==> Method ==> saveMerchant ==> Called");

		if (merchant.getMerchantId() == null) {
			merchant.setMerchantId(UUID.randomUUID());
			merchant.setCreateddate(ApplicationUtils.getCurrentTimeStamp());
		}
		merchant.setActive(Boolean.TRUE);
		merchant.setAuditTimeStamp(ApplicationUtils.getCurrentTimeStamp());
		daoFactory.getMerchantDao().add(merchant);

		return merchant;

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.MerchantService#inActiveMerchant(java.util.UUID)
	 */
	@Override
	public void inActiveMerchant(final Boolean status, final UUID merchantId) {

		LOGGER.info(" ==> Method ==> inActiveMerchant ==> Called");

		daoFactory.getMerchantDao().inActiveMerchant(status, ApplicationUtils.getCurrentTimeStamp(), merchantId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.MerchantService#getAllMerchants()
	 */
	@Override
	public List<Merchant> getAllMerchants() {

		LOGGER.info(" ==> Method ==> getAllMerchants ==> Called");

		return daoFactory.getMerchantDao().list();
	}
}
