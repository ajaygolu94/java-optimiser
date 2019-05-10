/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.service;

import java.util.Set;
import java.util.UUID;

import uk.co.xcordis.optimiser.model.MerchantRelatedJobRecord;

/**
 * The <code>MerchantRelatedJobRecordService</code> interface responsible for provide the MerchantRelatedJobRecord Service related method in <b>Optimiser</b>
 * application.
 *
 * @author Rob Atkin
 */
public interface MerchantRelatedJobRecordService {

	/**
	 * The <code>addMerchantRelatedJobRecord</code> method is used to store the MerchantRelatedJobRecord to database.
	 *
	 * @param merchantRelatedJobRecord
	 */
	public void addMerchantRelatedJobRecord(MerchantRelatedJobRecord merchantRelatedJobRecord);

	/**
	 * The <code>updateMerchantRelatedJobRecord</code> method is used to update the MerchantRelatedJobRecord to database.
	 *
	 * @param merchantRelatedJobRecord
	 */
	public void updateMerchantRelatedJobRecord(MerchantRelatedJobRecord merchantRelatedJobRecord);

	/**
	 * This method <code>findById</code> is used for get the MerchantRelatedJobRecord object from database by merchantJobRecordId param.
	 *
	 * @param merchantJobRecordId
	 * @return
	 */
	public MerchantRelatedJobRecord findById(UUID merchantJobRecordId);

	/**
	 * This method <code>getListByRequestTypeAndStatus</code> is used for get the list by requestType and status params.
	 *
	 * @param requestType
	 * @param status
	 * @return
	 */
	public Set<MerchantRelatedJobRecord> getListByRequestTypeAndStatus(String requestType, String status);
}
