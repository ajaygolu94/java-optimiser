/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.service.impl;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.xcordis.optimiser.model.MerchantRelatedRequestData;
import uk.co.xcordis.optimiser.service.MerchantRelatedRequestDataService;
import uk.co.xcordis.optimiser.util.DaoFactory;

/**
 * The <code>MerchantRelatedRequestDataServiceImpl</code> class responsible for implement the MerchantRelatedRequestDataService method in <b>Optimiser</b>
 * application.
 *
 * @author Rob Atkin
 */
@Service
public class MerchantRelatedRequestDataServiceImpl implements MerchantRelatedRequestDataService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MerchantRelatedRequestDataServiceImpl.class);

	@Autowired
	private DaoFactory daoFactory;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * uk.co.xcordis.optimiser.service.MerchantRelatedRequestDataService#addMerchantRelatedRequestData(uk.co.xcordis.optimiser.model.MerchantRelatedRequestData)
	 */
	@Override
	public void addMerchantRelatedRequestData(final MerchantRelatedRequestData merchantRelatedRequestData) {

		LOGGER.info(" ==> Method ==> addMerchantRelatedRequestData ==> called");
		daoFactory.getMerchantRelatedRequestDataDao().add(merchantRelatedRequestData);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.MerchantRelatedRequestDataService#findById(java.lang.Object[])
	 */
	@Override
	public MerchantRelatedRequestData findById(final UUID merchantRequestDataId) {

		LOGGER.info(" ==> Method ==> findById ==> called");
		return (MerchantRelatedRequestData) daoFactory.getMerchantRelatedRequestDataDao().findById(merchantRequestDataId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.MerchantRelatedRequestDataService#updateMerchantRelatedRequestData(uk.co.xcordis.optimiser.model.
	 * MerchantRelatedRequestData)
	 */
	@Override
	public void updateMerchantRelatedRequestData(final MerchantRelatedRequestData merchantRelatedRequestData) {

		LOGGER.info(" ==> Method ==> updateMerchantRelatedRequestData ==> called");
		daoFactory.getMerchantRelatedRequestDataDao().update(merchantRelatedRequestData);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.MerchantRelatedRequestDataService#getListByStatus(java.lang.String)
	 */
	@Override
	public List<MerchantRelatedRequestData> getListByStatus(final String status) {

		LOGGER.info(" ==> Method ==> getListByStatus ==> called");
		return daoFactory.getMerchantRelatedRequestDataDao().getListByStatus(status).all();
	}
}
