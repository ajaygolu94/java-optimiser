/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datastax.driver.mapping.Result;

import uk.co.xcordis.optimiser.model.ApplicationUser;
import uk.co.xcordis.optimiser.service.ApplicationUserService;
import uk.co.xcordis.optimiser.util.ApplicationUtils;
import uk.co.xcordis.optimiser.util.DaoFactory;

/**
 * The <code>ApplicationUserServiceImpl</code> class responsible for implement the UserService method in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Service
public class ApplicationUserServiceImpl implements ApplicationUserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationUserServiceImpl.class);

	@Autowired
	private DaoFactory daoFactory;

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.UserService#getUserById(java.util.UUID)
	 */
	@Override
	public ApplicationUser getUserById(final UUID userId) {

		LOGGER.info(" ==> Method ==> getUserById ==> called");
		return (ApplicationUser) daoFactory.getUserDao().findById(userId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.UserService#getUserByOpenId(java.lang.String)
	 */
	@Override
	public ApplicationUser getUserByOpenId(final String openId) {

		LOGGER.info(" ==> Method ==> getUserByOpenId ==> called");
		return daoFactory.getUserDao().getUserByOpenId(openId);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.ApplicationUserService#saveUserByOpenId(uk.co.xcordis.optimiser.model.ApplicationUser)
	 */
	@Override
	public void saveUserByOpenId(final ApplicationUser applicationUser) {

		LOGGER.info(" ==> Method ==> saveUserByOpenId ==> Enter");

		final ApplicationUser user = new ApplicationUser();
		user.setUserId(UUID.randomUUID());
		user.setEmail(applicationUser.getEmail());
		user.setName(applicationUser.getName());
		user.setOpenId(applicationUser.getOpenId());
		user.setFirstTimeLogin(Boolean.TRUE);
		user.setRole(applicationUser.getRole());
		user.setCreatedDate(new Date().toString());
		user.setAuditTimeStamp(ApplicationUtils.getCurrentTimeStamp());
		user.setActive(Boolean.TRUE);
		user.setSecretKey(ApplicationUtils.generateSecretKey());

		daoFactory.getUserDao().add(user);

		LOGGER.info(" ==> Method ==> saveUserByOpenId ==> Exit");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.UserService#getUsersByRole(java.lang.String)
	 */
	@Override
	public List<ApplicationUser> getUsersByRole(final String role) {

		LOGGER.info(" ==> Method ==> getUsersByRole ==> called");

		final Result<ApplicationUser> userByRole = daoFactory.getUserDao().getUserByRole(role);

		return userByRole.all();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.ApplicationUserService#updateUserData(uk.co.xcordis.optimiser.model.ApplicationUser)
	 */
	@Override
	public void updateUserData(final ApplicationUser applicationUser) {

		LOGGER.info(" ==> Method ==> updateUserData ==> Enter");

		final ApplicationUser user = getUserById(applicationUser.getUserId());

		if (user != null) {
			user.setFirstTimeLogin(Boolean.FALSE);
			user.setManagerId(applicationUser.getManagerId());
			user.setContactNumber(applicationUser.getContactNumber());
			user.setAuditTimeStamp(ApplicationUtils.getCurrentTimeStamp());

			daoFactory.getUserDao().update(user);
		}

		LOGGER.info(" ==> Method ==> updateUserData ==> Exit");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.ApplicationUserService#getAllUsers()
	 */
	@Override
	public List<ApplicationUser> getAllUsers() {

		LOGGER.info(" ==> Method ==> getAllUsers ==> called");

		final Result<ApplicationUser> applicationUserList = daoFactory.getUserDao().getAllUsers();

		if (applicationUserList != null) {

			return applicationUserList.all();
		}

		return Collections.emptyList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.co.xcordis.optimiser.service.ApplicationUserService#getUserByManagerId(java.util.UUID)
	 */
	@Override
	public ApplicationUser getUserByManagerId(final UUID managerId) {

		LOGGER.info(" ==> Method ==> getUserByManagerId ==> Called");
		return daoFactory.getUserDao().getUserByManagerId(managerId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.ApplicationUserService#getNotAssignedMerchantManagerUserList(java.lang.String)
	 */
	@Override
	public List<ApplicationUser> getNotAssignedMerchantManagerUserList(final String merchantManagerRole) {

		LOGGER.info(" ==> Method ==> getNotAssignedMerchantManagerUserList ==> Enter");

		final List<ApplicationUser> applicationUserByRoleList = getUsersByRole(merchantManagerRole);

		final List<ApplicationUser> applicationUserList = new ArrayList<>();

		for (final ApplicationUser user : applicationUserByRoleList) {

			if (user != null && user.getUserId() != null && daoFactory.getUserDao().getUserByManagerId(user.getUserId()) == null) {
				applicationUserList.add(user);
			}
		}

		LOGGER.info(" ==> Method ==> getNotAssignedMerchantManagerUserList ==> Exit");
		return applicationUserList;
	}

}
