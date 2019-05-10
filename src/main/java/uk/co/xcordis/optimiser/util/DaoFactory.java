/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import uk.co.xcordis.optimiser.dao.AccessTokenDao;
import uk.co.xcordis.optimiser.dao.AppConfigDao;
import uk.co.xcordis.optimiser.dao.ApplicationUserDao;
import uk.co.xcordis.optimiser.dao.ErrorLogDao;
import uk.co.xcordis.optimiser.dao.JobDao;
import uk.co.xcordis.optimiser.dao.MenuDao;
import uk.co.xcordis.optimiser.dao.MerchantDao;
import uk.co.xcordis.optimiser.dao.MerchantGatewaysDao;
import uk.co.xcordis.optimiser.dao.MerchantRelatedJobRecordDao;
import uk.co.xcordis.optimiser.dao.MerchantRelatedRequestDataDao;
import uk.co.xcordis.optimiser.dao.MerchantRulesDao;
import uk.co.xcordis.optimiser.dao.PaymentGatewaysDao;
import uk.co.xcordis.optimiser.dao.RequestDataDao;
import uk.co.xcordis.optimiser.dao.RuleSelectorKeysDao;
import uk.co.xcordis.optimiser.dao.RulesDao;
import uk.co.xcordis.optimiser.dao.UserRoleBaseUrlDao;

/**
 * The <code>DaoFactory</code> class responsible for @Autowired all the Dao interface in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Repository
@DependsOn(ApplicationConstants.APPLICATIONINITIALIZER_LABEL)
public class DaoFactory {

	@Autowired
	private AppConfigDao appConfigDao;

	@Autowired
	private ApplicationUserDao userDao;

	@Autowired
	private MenuDao menuDao;

	@Autowired
	private MerchantDao merchantDao;

	@Autowired
	private MerchantGatewaysDao merchantGatewaysDao;

	@Autowired
	private PaymentGatewaysDao paymentGatewaysDao;

	@Autowired
	private RulesDao rulesDao;

	@Autowired
	private MerchantRulesDao merchantRulesDao;

	@Autowired
	private RequestDataDao requestDataDao;

	@Autowired
	private ErrorLogDao errorLogDao;

	@Autowired
	private RuleSelectorKeysDao ruleSelectorKeysDao;

	@Autowired
	private AccessTokenDao accessTokenDao;

	@Autowired
	private UserRoleBaseUrlDao userRoleBaseUrlDao;

	@Autowired
	private MerchantRelatedRequestDataDao merchantRelatedRequestDataDao;

	@Autowired
	private MerchantRelatedJobRecordDao merchantRelatedJobRecordDao;

	@Autowired
	private JobDao jobDao;

	/**
	 * @return the appConfigDao
	 */
	public AppConfigDao getAppConfigDao() {

		return appConfigDao;
	}

	/**
	 * @return the userDao
	 */
	public ApplicationUserDao getUserDao() {

		return userDao;
	}

	/**
	 * @return the menuDao
	 */
	public MenuDao getMenuDao() {

		return menuDao;
	}

	/**
	 * @return the merchantDao
	 */
	public MerchantDao getMerchantDao() {

		return merchantDao;
	}

	/**
	 * @return the merchantGatewaysDao
	 */
	public MerchantGatewaysDao getMerchantGatewaysDao() {

		return merchantGatewaysDao;
	}

	/**
	 * @return the paymentGatewaysDao
	 */
	public PaymentGatewaysDao getPaymentGatewaysDao() {

		return paymentGatewaysDao;
	}

	/**
	 * @return the rulesDao
	 */
	public RulesDao getRulesDao() {

		return rulesDao;
	}

	/**
	 * @return the merchantRulesDao
	 */
	public MerchantRulesDao getMerchantRulesDao() {

		return merchantRulesDao;
	}

	/**
	 * @return the requestDataDao
	 */
	public RequestDataDao getRequestDataDao() {

		return requestDataDao;
	}

	/**
	 * @return the errorLogDao
	 */
	public ErrorLogDao getErrorLogDao() {

		return errorLogDao;
	}

	/**
	 * @return the ruleSelectorKeysDao
	 */
	public RuleSelectorKeysDao getRuleSelectorKeysDao() {

		return ruleSelectorKeysDao;
	}

	/**
	 * @return the accessTokenDao
	 */
	public AccessTokenDao getAccessTokenDao() {

		return accessTokenDao;
	}

	/**
	 * @return the userRoleBaseUrlDao
	 */
	public UserRoleBaseUrlDao getUserRoleBaseUrlDao() {

		return userRoleBaseUrlDao;
	}

	/**
	 * @return the merchantRelatedRequestDataDao
	 */
	public MerchantRelatedRequestDataDao getMerchantRelatedRequestDataDao() {

		return merchantRelatedRequestDataDao;
	}

	/**
	 * @return the merchantRelatedJobRecordDao
	 */
	public MerchantRelatedJobRecordDao getMerchantRelatedJobRecordDao() {

		return merchantRelatedJobRecordDao;
	}

	/**
	 * @return the jobDao
	 */
	public JobDao getJobDao() {

		return jobDao;
	}

}
