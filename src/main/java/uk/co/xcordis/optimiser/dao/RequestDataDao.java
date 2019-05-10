/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.dao;

import com.datastax.driver.mapping.annotations.Accessor;

import uk.co.xcordis.optimiser.model.RequestData;

/**
 * The <code>RequestDataDao</code> interface responsible for provide the request data related method in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Accessor
public interface RequestDataDao extends BaseDao<RequestData> {
}
