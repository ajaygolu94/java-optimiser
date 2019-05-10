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

import uk.co.xcordis.optimiser.rules.RulesUtil;
import uk.co.xcordis.optimiser.service.AccessTokenService;
import uk.co.xcordis.optimiser.service.AppConfigService;
import uk.co.xcordis.optimiser.service.ApplicationUserService;
import uk.co.xcordis.optimiser.service.ErrorLogService;
import uk.co.xcordis.optimiser.service.JobService;
import uk.co.xcordis.optimiser.service.MenuService;
import uk.co.xcordis.optimiser.service.MerchantGatewaysService;
import uk.co.xcordis.optimiser.service.MerchantRelatedJobRecordService;
import uk.co.xcordis.optimiser.service.MerchantRelatedRequestDataService;
import uk.co.xcordis.optimiser.service.MerchantRulesService;
import uk.co.xcordis.optimiser.service.MerchantService;
import uk.co.xcordis.optimiser.service.PaymentGatewaysService;
import uk.co.xcordis.optimiser.service.RequestDataService;
import uk.co.xcordis.optimiser.service.RuleSelectorKeysService;
import uk.co.xcordis.optimiser.service.RulesService;
import uk.co.xcordis.optimiser.service.UserRoleBaseUrlService;

/**
 * The <code>ServiceRegistry</code> class responsible for @Autowired all the Service interface in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Repository
@DependsOn(ApplicationConstants.APPLICATIONINITIALIZER_LABEL)
public class ServiceRegistry {

	@Autowired
	private AppConfigService appConfigService;

	@Autowired
	private ApplicationUserService userService;

	@Autowired
	private MenuService menuService;

	@Autowired
	private MerchantService merchantService;

	@Autowired
	private MerchantGatewaysService merchantGatewaysService;

	@Autowired
	private PaymentGatewaysService paymentGatewaysService;

	@Autowired
	private RulesService rulesService;

	@Autowired
	private MerchantRulesService merchantRulesService;

	@Autowired
	private RequestDataService requestDataService;

	@Autowired
	private RulesUtil rulesUtil;

	@Autowired
	private ErrorLogService errorLogService;

	@Autowired
	private RuleSelectorKeysService ruleSelectorKeysService;

	@Autowired
	private AccessTokenService accessTokenService;

	@Autowired
	private UserRoleBaseUrlService userRoleBaseUrlService;

	@Autowired
	private MerchantRelatedRequestDataService merchantRelatedRequestDataService;

	@Autowired
	private MerchantRelatedJobRecordService merchantRelatedJobRecordService;

	@Autowired
	private JobService jobService;

	/**
	 * @return the appConfigService
	 */
	public AppConfigService getAppConfigService() {

		return appConfigService;
	}

	/**
	 * @return the userService
	 */
	public ApplicationUserService getUserService() {

		return userService;
	}

	/**
	 * @return the menuService
	 */
	public MenuService getMenuService() {

		return menuService;
	}

	/**
	 * @return the merchantService
	 */
	public MerchantService getMerchantService() {

		return merchantService;
	}

	/**
	 * @return the merchantGatewaysService
	 */
	public MerchantGatewaysService getMerchantGatewaysService() {

		return merchantGatewaysService;
	}

	/**
	 * @return the paymentGatewaysService
	 */
	public PaymentGatewaysService getPaymentGatewaysService() {

		return paymentGatewaysService;
	}

	/**
	 * @return the rulesService
	 */
	public RulesService getRulesService() {

		return rulesService;
	}

	/**
	 * @return the merchantRulesService
	 */
	public MerchantRulesService getMerchantRulesService() {

		return merchantRulesService;
	}

	/**
	 * @return the requestDataService
	 */
	public RequestDataService getRequestDataService() {

		return requestDataService;
	}

	/**
	 * @return the rulesUtil
	 */
	public RulesUtil getRulesUtil() {

		return rulesUtil;
	}

	/**
	 * @return the errorLogService
	 */
	public ErrorLogService getErrorLogService() {

		return errorLogService;
	}

	/**
	 * @return the ruleSelectorKeysService
	 */
	public RuleSelectorKeysService getRuleSelectorKeysService() {

		return ruleSelectorKeysService;
	}

	/**
	 * @return the accessTokenService
	 */
	public AccessTokenService getAccessTokenService() {

		return accessTokenService;
	}

	/**
	 * @return the userRoleBaseUrlService
	 */
	public UserRoleBaseUrlService getUserRoleBaseUrlService() {

		return userRoleBaseUrlService;
	}

	/**
	 * @return the merchantRelatedRequestDataService
	 */
	public MerchantRelatedRequestDataService getMerchantRelatedRequestDataService() {

		return merchantRelatedRequestDataService;
	}

	/**
	 * @return the merchantRelatedJobRecordService
	 */
	public MerchantRelatedJobRecordService getMerchantRelatedJobRecordService() {

		return merchantRelatedJobRecordService;
	}

	/**
	 * @return the jobService
	 */
	public JobService getJobService() {

		return jobService;
	}

}