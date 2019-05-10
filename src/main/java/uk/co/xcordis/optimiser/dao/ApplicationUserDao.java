/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.dao;

import java.util.UUID;

import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Query;

import uk.co.xcordis.optimiser.model.ApplicationUser;

/**
 * The <code>ApplicationUserDao</code> interface responsible for provide the User related method in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Accessor
public interface ApplicationUserDao extends BaseDao<ApplicationUser> {

	/**
	 * This <code>getUserByOpenId</code> method is used to get the User details by openId.
	 *
	 * @param openId
	 * @return
	 */
	@Query("select * from applicationuser where openid = ?")
	public ApplicationUser getUserByOpenId(String openId);

	/**
	 * This <code>getUserByRole</code> method is used to get the Users by role.
	 *
	 * @param role
	 * @return
	 */
	@Query("select * from applicationuser where role = ?")
	public Result<ApplicationUser> getUserByRole(String role);

	/**
	 * This <code>getAllUsers</code> method is used to get all active user of the application.
	 *
	 * @return
	 */
	@Query("select * from applicationuser where active = true")
	public Result<ApplicationUser> getAllUsers();

	/**
	 * This method <code>getUserByManagerId</code> is used for get the application user details based on managerId params.
	 *
	 * @param managerId
	 * @return
	 */
	@Query("select * from applicationuser where managerid = ?")
	public ApplicationUser getUserByManagerId(UUID managerId);

}
