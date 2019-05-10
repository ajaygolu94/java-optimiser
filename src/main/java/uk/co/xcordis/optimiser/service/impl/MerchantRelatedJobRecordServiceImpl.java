/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.xcordis.optimiser.model.MerchantRelatedJobRecord;
import uk.co.xcordis.optimiser.service.MerchantRelatedJobRecordService;
import uk.co.xcordis.optimiser.util.ApplicationUtils;
import uk.co.xcordis.optimiser.util.DaoFactory;

/**
 * The <code>MerchantRelatedJobRecordServiceImpl</code> class responsible for implement the MerchantRelatedJobRecordService method in <b>Optimiser</b>
 * application.
 *
 * @author Rob Atkin
 */
@Service
public class MerchantRelatedJobRecordServiceImpl implements MerchantRelatedJobRecordService {

	@Autowired
	private DaoFactory daoFactory;

	private static final Logger LOGGER = LoggerFactory.getLogger(MerchantRelatedJobRecordServiceImpl.class);

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.MerchantRelatedJobRecordService#addMerchantRelatedJobRecord(uk.co.xcordis.optimiser.model.MerchantRelatedJobRecord)
	 */
	@Override
	public void addMerchantRelatedJobRecord(final MerchantRelatedJobRecord merchantRelatedJobRecord) {

		LOGGER.info(" ==> Method ==> addMerchantRelatedJobRecord ==> Called");
		daoFactory.getMerchantRelatedJobRecordDao().add(merchantRelatedJobRecord);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.co.xcordis.optimiser.service.MerchantRelatedJobRecordService#updateMerchantRelatedJobRecord(uk.co.xcordis.optimiser.model.MerchantRelatedJobRecord)
	 */
	@Override
	public void updateMerchantRelatedJobRecord(final MerchantRelatedJobRecord merchantRelatedJobRecord) {

		LOGGER.info(" ==> Method ==> updateMerchantRelatedJobRecord ==> Called");
		daoFactory.getMerchantRelatedJobRecordDao().update(merchantRelatedJobRecord);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.MerchantRelatedJobRecordService#findById(java.util.UUID)
	 */
	@Override
	public MerchantRelatedJobRecord findById(final UUID merchantJobRecordId) {

		LOGGER.info(" ==> Method ==> findById ==> Called");
		return (MerchantRelatedJobRecord) daoFactory.getMerchantRelatedJobRecordDao().findById(merchantJobRecordId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.MerchantRelatedJobRecordService#getListByRequestTypeAndStatus(java.lang.String, java.lang.String)
	 */
	@Override
	public Set<MerchantRelatedJobRecord> getListByRequestTypeAndStatus(final String requestType, final String status) {

		LOGGER.info(" ==> Method ==> getListByRequestTypeAndStatus ==> Enter");

		final List<List<MerchantRelatedJobRecord>> merchantRelatedJobRecordList = new ArrayList<>();

		try {

			final List<MerchantRelatedJobRecord> requestTypeList = daoFactory.getMerchantRelatedJobRecordDao().getListByRequestType(requestType).all();
			final List<MerchantRelatedJobRecord> statusList = daoFactory.getMerchantRelatedJobRecordDao().getListByStatus(status).all();

			merchantRelatedJobRecordList.add(requestTypeList);
			merchantRelatedJobRecordList.add(statusList);

		} catch (final Exception e) {
			LOGGER.error(" ==> Method ==> getListByRequestTypeAndStatus ==> Exception : " + e);
		}

		LOGGER.info(" ==> Method ==> getListByRequestTypeAndStatus ==> Exit");

		return ApplicationUtils.getCommonElements(merchantRelatedJobRecordList);
	}
}
