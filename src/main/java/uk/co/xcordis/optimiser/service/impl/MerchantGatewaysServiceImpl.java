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

import uk.co.xcordis.optimiser.model.MerchantGateways;
import uk.co.xcordis.optimiser.service.MerchantGatewaysService;
import uk.co.xcordis.optimiser.util.ApplicationConstants;
import uk.co.xcordis.optimiser.util.ApplicationUtils;
import uk.co.xcordis.optimiser.util.DaoFactory;

/**
 * The <code>MerchantGatewaysServiceImpl</code> class responsible for implement the MerchantGatewaysService method in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Service
public class MerchantGatewaysServiceImpl implements MerchantGatewaysService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MerchantGatewaysServiceImpl.class);

	@Autowired
	private DaoFactory daoFactory;

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.MerchantGatewaysService#findById(java.lang.Object[])
	 */
	@Override
	public MerchantGateways findById(final Object... objects) {

		LOGGER.info(" ==> Method ==> findById ==> Called");
		return (MerchantGateways) daoFactory.getMerchantGatewaysDao().findById(objects);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.MerchantGatewaysService#getMerchantGatewaysByMerchantId(java.util.UUID)
	 */
	@Override
	public List<MerchantGateways> getMerchantGatewaysByMerchantId(final UUID merchantId) {

		LOGGER.info(" ==> Method ==> getMerchantGatewaysByMerchantId ==> Start");

		final List<MerchantGateways> merchantGateways = daoFactory.getMerchantGatewaysDao().getMerchantGatewaysByMerchantId(merchantId).all();

		if (ApplicationUtils.isValid(merchantGateways)) {
			ApplicationUtils.sortListByTimeStamp(merchantGateways, ApplicationConstants.FIELD_AUDITTIMESTAMP_LABEL,
					ApplicationConstants.DD_MM_YYYY_HH_MM_SS_AM_PM);

			return merchantGateways.stream()
					.filter(merchantGateway -> merchantGateway != null && merchantGateway.getActive() != null && merchantGateway.getActive())
					.collect(Collectors.toList());
		}
		LOGGER.info(" ==> Method ==> getMerchantGatewaysByMerchantId ==> End");
		return Collections.emptyList();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.MerchantGatewaysService#addMerchantGateways(uk.co.xcordis.optimiser.model.MerchantGateways)
	 */
	@Override
	public void addMerchantGateways(final MerchantGateways merchantGateways) {

		LOGGER.info(" ==> Method ==> addMerchantGateways ==> Called");

		merchantGateways.setMerchantGatewayId(UUID.randomUUID());
		merchantGateways.setAuditTimeStamp(ApplicationUtils.getCurrentTimeStamp());
		merchantGateways.setActive(Boolean.TRUE);
		daoFactory.getMerchantGatewaysDao().add(merchantGateways);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.MerchantGatewaysService#updateMerchantGateways(uk.co.xcordis.optimiser.model.MerchantGateways)
	 */
	@Override
	public void updateMerchantGateways(final MerchantGateways merchantGateways) {

		LOGGER.info(" ==> Method ==> updateMerchantGateways ==> Called");

		merchantGateways.setAuditTimeStamp(ApplicationUtils.getCurrentTimeStamp());
		daoFactory.getMerchantGatewaysDao().add(merchantGateways);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.MerchantGatewaysService#deleteMerchantGateways(java.util.UUID, java.lang.Boolean)
	 */
	@Override
	public Boolean deleteMerchantGateways(final UUID merchantGatewayId, final Boolean deactivate) {

		LOGGER.info(" ==> Method ==> deleteMerchantGateways ==> Called");

		final MerchantGateways merchantGateways = findById(merchantGatewayId);

		if (merchantGateways != null && merchantGateways.getMerchantGatewayId() != null) {

			merchantGateways.setActive(deactivate);
			updateMerchantGateways(merchantGateways);

			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.MerchantGatewaysService#getMerchantGatewaysBySourceMerchantGatewayId(java.lang.String)
	 */
	@Override
	public MerchantGateways getMerchantGatewaysBySourceMerchantGatewayId(final String sourceMerchantGatewayId) {

		LOGGER.info(" ==> Method ==> getMerchantGatewaysBySourceMerchantGatewayId ==> Called");
		return daoFactory.getMerchantGatewaysDao().getMerchantGatewaysBySourceMerchantGatewayId(sourceMerchantGatewayId);
	}

}
