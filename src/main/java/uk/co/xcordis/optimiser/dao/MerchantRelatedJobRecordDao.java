/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.dao;

import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Query;

import uk.co.xcordis.optimiser.model.MerchantRelatedJobRecord;

/**
 * The <code>MerchantRelatedJobRecordDao</code> interface responsible for provide the MerchantRelatedJobRecord related method in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Accessor
public interface MerchantRelatedJobRecordDao extends BaseDao<MerchantRelatedJobRecord> {

	/**
	 * This <code>getListByRequestType</code> method is used for get merchant related job record list based on requestType params.
	 *
	 * @param requestType
	 * @return
	 */
	@Query("select * from merchantrelatedjobrecord where requesttype = ?")
	public Result<MerchantRelatedJobRecord> getListByRequestType(String requestType);

	/**
	 * This <code>getListByStatus</code> method is used for get merchant related job record list based on status params.
	 *
	 * @param status
	 * @return
	 */
	@Query("select * from merchantrelatedjobrecord where status = ?")
	public Result<MerchantRelatedJobRecord> getListByStatus(String status);
}
