/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.dao.impl;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.datastax.driver.mapping.Result;

import uk.co.xcordis.optimiser.dao.ApplicationUserDao;
import uk.co.xcordis.optimiser.model.ApplicationUser;
import uk.co.xcordis.optimiser.util.ApplicationTableConstants;

/**
 * The <code>ApplicationUserDaoImpl</code> class responsible for implement the UserDao method in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Repository
public class ApplicationUserDaoImpl extends BaseDaoImpl<ApplicationUser> implements ApplicationUserDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationUserDaoImpl.class);

	private ApplicationUserDao userDao;

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.dao.BaseDaoImpl#setMappper()
	 */
	@Override
	protected void setMappper() {

		mapper = getManager().mapper(ApplicationUser.class);
		tableName = ApplicationTableConstants.TABLENAME_APPLICATIONUSER;
		userDao = getManager().createAccessor(ApplicationUserDao.class);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.dao.UserDao#getUserByOpenId(java.lang.String)
	 */
	@Override
	public ApplicationUser getUserByOpenId(final String openId) {

		LOGGER.info(" ==> Method ==> getUserByOpenId ==> called");

		return userDao.getUserByOpenId(openId);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.dao.UserDao#getUserByRole(java.lang.String)
	 */
	@Override
	public Result<ApplicationUser> getUserByRole(final String role) {

		LOGGER.info(" ==> Method ==> getUserByRole ==> called");

		return userDao.getUserByRole(role);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.dao.ApplicationUserDao#getAllUsers()
	 */
	@Override
	public Result<ApplicationUser> getAllUsers() {

		LOGGER.info(" ==> Method ==> getAllUsers ==> called");

		return userDao.getAllUsers();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.dao.ApplicationUserDao#getUserByManagerId(java.lang.String)
	 */
	@Override
	public ApplicationUser getUserByManagerId(final UUID managerId) {

		LOGGER.info(" ==> Method ==> getUserByManagerId ==> called");
		return userDao.getUserByManagerId(managerId);
	}
}
