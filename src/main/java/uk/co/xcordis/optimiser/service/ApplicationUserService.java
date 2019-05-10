/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.service;

import java.util.List;
import java.util.UUID;

import uk.co.xcordis.optimiser.model.ApplicationUser;

/**
 * The <code>ApplicationUserService</code> interface responsible for provide the User Register related method in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
public interface ApplicationUserService {

	/**
	 * This <code>getUserById</code> method is used to get the User details by userId.
	 *
	 * @param userId
	 * @return
	 */
	public ApplicationUser getUserById(UUID userId);

	/**
	 * This <code>getUserByOpenId</code> method is used to get the User details by openId.
	 *
	 * @param openId
	 * @return
	 */
	public ApplicationUser getUserByOpenId(String openId);

	/**
	 * This <code>saveUserByOpenId</code> method is used to store the user details into the database.
	 *
	 * @param applicationUser
	 */
	public void saveUserByOpenId(ApplicationUser applicationUser);

	/**
	 * This <code>getUsersByRole</code> method is used to get all users by role.
	 *
	 * @param role
	 * @return
	 */
	public List<ApplicationUser> getUsersByRole(String role);

	/**
	 * This <code>updateUserData</code> method is used to save the user details with extra fields.
	 *
	 * @param applicationUser
	 */
	public void updateUserData(ApplicationUser applicationUser);

	/**
	 * This <code>getAllUsers</code> method is used to get all the active users of the application.
	 *
	 * @return
	 */
	public List<ApplicationUser> getAllUsers();

	/**
	 * This method <code>getUserByManagerId</code> is used for get the application user based on managerId params.
	 *
	 * @param managerId
	 * @return
	 */
	public ApplicationUser getUserByManagerId(UUID managerId);

	/**
	 * This <code>getNotAssignedMerchantManagerUserList</code> method is used to get user list which has Merchant Manager does not assign to any user.
	 *
	 * @param role
	 * @return
	 */
	public List<ApplicationUser> getNotAssignedMerchantManagerUserList(String merchantManagerRole);
}
