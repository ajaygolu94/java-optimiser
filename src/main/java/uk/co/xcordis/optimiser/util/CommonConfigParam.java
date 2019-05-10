/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.util;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import uk.co.xcordis.optimiser.model.ApplicationConfiguration;
import uk.co.xcordis.optimiser.model.UserRoleBaseUrl;

/**
 * The <code>CommonConfigParam</code> class responsible for setting the OpenId related parameters in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Repository
@DependsOn(ApplicationConstants.SERVICEREGISTRY_LABEL)
public class CommonConfigParam {

	private static final Logger logger = LoggerFactory.getLogger(CommonConfigParam.class);

	private String applicationName;
	private String clientId;
	private String clientSecrate;
	private String openIdCodeURL;
	private String openIdAccessTokenURL;
	private String openIdLoginRequestURL;
	private String appURL;
	private String openidURL;
	private String openIdAddMerchantURL;
	private String openIdGetAccessTokenListURL;
	private String adminEmail;
	private List<String> adminRoleUrls;
	private List<String> merchantRoleUrls;
	private List<String> merchantManagerRoleUrls;

	@Autowired
	private ServiceRegistry serviceRegistry;

	/**
	 * @return the applicationName
	 */
	public String getApplicationName() {

		return applicationName;
	}

	/**
	 * @param applicationName
	 *            the applicationName to set
	 */
	public void setApplicationName(final String applicationName) {

		this.applicationName = applicationName;
	}

	/**
	 * @return the clientId
	 */
	public String getClientId() {

		return clientId;
	}

	/**
	 * @param clientId
	 *            the clientId to set
	 */
	public void setClientId(final String clientId) {

		this.clientId = clientId;
	}

	/**
	 * @return the clientSecrate
	 */
	public String getClientSecrate() {

		return clientSecrate;
	}

	/**
	 * @param clientSecrate
	 *            the clientSecrate to set
	 */
	public void setClientSecrate(final String clientSecrate) {

		this.clientSecrate = clientSecrate;
	}

	/**
	 * @return the openIdCodeURL
	 */
	public String getOpenIdCodeURL() {

		return openIdCodeURL;
	}

	/**
	 * @param openIdCodeURL
	 *            the openIdCodeURL to set
	 */
	public void setOpenIdCodeURL(final String openIdCodeURL) {

		this.openIdCodeURL = openIdCodeURL;
	}

	/**
	 * @return the openIdAccessTokenURL
	 */
	public String getOpenIdAccessTokenURL() {

		return openIdAccessTokenURL;
	}

	/**
	 * @param openIdAccessTokenURL
	 *            the openIdAccessTokenURL to set
	 */
	public void setOpenIdAccessTokenURL(final String openIdAccessTokenURL) {

		this.openIdAccessTokenURL = openIdAccessTokenURL;
	}

	/**
	 * @return the openIdLoginRequestURL
	 */
	public String getOpenIdLoginRequestURL() {

		return openIdLoginRequestURL;
	}

	/**
	 * @param openIdLoginRequestURL
	 *            the openIdLoginRequestURL to set
	 */
	public void setOpenIdLoginRequestURL(final String openIdLoginRequestURL) {

		this.openIdLoginRequestURL = openIdLoginRequestURL;
	}

	/**
	 * @return the appURL
	 */
	public String getAppURL() {

		return appURL;
	}

	/**
	 * @param appURL
	 *            the appURL to set
	 */
	public void setAppURL(final String appURL) {

		this.appURL = appURL;
	}

	/**
	 * @return the openidURL
	 */
	public String getOpenidURL() {

		return openidURL;
	}

	/**
	 * @param openidURL
	 *            the openidURL to set
	 */
	public void setOpenidURL(final String openidURL) {

		this.openidURL = openidURL;
	}

	/**
	 * @return the openIdAddMerchantURL
	 */
	public String getOpenIdAddMerchantURL() {

		return openIdAddMerchantURL;
	}

	/**
	 * @param openIdAddMerchantURL
	 *            the openIdAddMerchantURL to set
	 */
	public void setOpenIdAddMerchantURL(final String openIdAddMerchantURL) {

		this.openIdAddMerchantURL = openIdAddMerchantURL;
	}

	/**
	 * @return the openIdGetAccessTokenListURL
	 */
	public String getOpenIdGetAccessTokenListURL() {

		return openIdGetAccessTokenListURL;
	}

	/**
	 * @param openIdGetAccessTokenListURL
	 *            the openIdGetAccessTokenListURL to set
	 */
	public void setOpenIdGetAccessTokenListURL(final String openIdGetAccessTokenListURL) {

		this.openIdGetAccessTokenListURL = openIdGetAccessTokenListURL;
	}

	/**
	 * @return the adminEmail
	 */
	public String getAdminEmail() {

		return adminEmail;
	}

	/**
	 * @param adminEmail
	 *            the adminEmail to set
	 */
	public void setAdminEmail(final String adminEmail) {

		this.adminEmail = adminEmail;
	}

	/**
	 * @return the merchantRoleUrls
	 */
	public List<String> getMerchantRoleUrls() {

		return merchantRoleUrls;
	}

	/**
	 * @param merchantRoleUrls
	 *            the merchantRoleUrls to set
	 */
	public void setMerchantRoleUrls(final List<String> merchantRoleUrls) {

		this.merchantRoleUrls = merchantRoleUrls;
	}

	/**
	 * @return the merchantManagerRoleUrls
	 */
	public List<String> getMerchantManagerRoleUrls() {

		return merchantManagerRoleUrls;
	}

	/**
	 * @param merchantManagerRoleUrls
	 *            the merchantManagerRoleUrls to set
	 */
	public void setMerchantManagerRoleUrls(final List<String> merchantManagerRoleUrls) {

		this.merchantManagerRoleUrls = merchantManagerRoleUrls;
	}

	/**
	 * @return the adminRoleUrls
	 */
	public List<String> getAdminRoleUrls() {

		return adminRoleUrls;
	}

	/**
	 * @param adminRoleUrls
	 *            the adminRoleUrls to set
	 */
	public void setAdminRoleUrls(final List<String> adminRoleUrls) {

		this.adminRoleUrls = adminRoleUrls;
	}

	@PostConstruct
	public void setApplicationInitializer() {

		logger.info(" ==> Method: set Value ApplicationInitializer Level ==> Enter");

		try {

			configOpenIdParam();

		} catch (final Exception e) {
			logger.error(" ==> Method: set Value ApplicationInitializer Level ==> Exception : " + e);
		}

		logger.info(" ==> Method: setApplicationInitializer ==> Exit");
	}

	/**
	 * This <code>configOpenIdParam</code> method is used to set all the openid and optimiser related parameters into the OpenIdConfig bean in application
	 * startup.
	 *
	 */
	public void configOpenIdParam() {

		logger.info(" Method ==> configOpenIdParam ==> Enter");

		try {

			final ApplicationConfiguration appConfigClientId = serviceRegistry.getAppConfigService().getAppConfigByCode(ApplicationConstants.CLIENTID_LABLE);

			if (appConfigClientId != null && !ApplicationUtils.isEmpty(appConfigClientId.getValue())) {
				setClientId(appConfigClientId.getValue());
			}

			final ApplicationConfiguration appConfigClientSecret = serviceRegistry.getAppConfigService()
					.getAppConfigByCode(ApplicationConstants.CLIENTSECRET_LABLE);

			if (appConfigClientSecret != null && !ApplicationUtils.isEmpty(appConfigClientSecret.getValue())) {
				setClientSecrate(appConfigClientSecret.getValue());
			}

			final ApplicationConfiguration appConfigClientAppName = serviceRegistry.getAppConfigService()
					.getAppConfigByCode(ApplicationConstants.APPLICATIONNAME_LABLE);

			if (appConfigClientAppName != null && !ApplicationUtils.isEmpty(appConfigClientAppName.getValue())) {
				setApplicationName(appConfigClientAppName.getValue());
			}

			final ApplicationConfiguration appUrl = serviceRegistry.getAppConfigService().getAppConfigByCode(ApplicationConstants.APPLICATION_URL);

			if (appUrl != null && !ApplicationUtils.isEmpty(appUrl.getValue())) {
				setAppURL(appUrl.getValue());
			}

			final ApplicationConfiguration appConfigOpenIdURL = serviceRegistry.getAppConfigService()
					.getAppConfigByCode(ApplicationConstants.COMMON_CONFIG_OPENID_URL_CODE);

			if (appConfigOpenIdURL != null && !ApplicationUtils.isEmpty(appConfigOpenIdURL.getValue())) {
				setOpenidURL(appConfigOpenIdURL.getValue());
				setOpenIdCodeURL(appConfigOpenIdURL.getValue() + ApplicationConstants.COMMON_CONFIG_OPENID_CODE_URL_VALUE);
				setOpenIdAccessTokenURL(appConfigOpenIdURL.getValue() + ApplicationConstants.COMMON_CONFIG_OPENID_ACCESSTOKEN_URL_VALUE);
				setOpenIdLoginRequestURL(appConfigOpenIdURL.getValue() + ApplicationConstants.COMMON_CONFIG_OPENID_LOGINREQUEST_URL_VALUE);
				setOpenIdAddMerchantURL(appConfigOpenIdURL.getValue() + ApplicationConstants.COMMON_CONFIG_OPENID_ADDMERCHANTURL_CODE_VALUE);
				setOpenIdGetAccessTokenListURL(appConfigOpenIdURL.getValue() + ApplicationConstants.COMMON_CONFIG_OPENID_GETACCESSTOKENLIST_CODE_VALUE);
			}

			// Set the role base urls
			final UserRoleBaseUrl adminRoleUrl = serviceRegistry.getUserRoleBaseUrlService().getUserRoleBaseUrlByRole(ApplicationConstants.ROLE_ADMIN);

			if (adminRoleUrl != null && ApplicationUtils.isValid(adminRoleUrl.getRoleUrl())) {
				setAdminRoleUrls(adminRoleUrl.getRoleUrl());
			}

			final UserRoleBaseUrl merchantRoleUrl = serviceRegistry.getUserRoleBaseUrlService().getUserRoleBaseUrlByRole(ApplicationConstants.ROLE_MERCHANT);

			if (merchantRoleUrl != null && ApplicationUtils.isValid(merchantRoleUrl.getRoleUrl())) {
				setMerchantRoleUrls(merchantRoleUrl.getRoleUrl());
			}

			final UserRoleBaseUrl merchantManagerRoleUrl = serviceRegistry.getUserRoleBaseUrlService()
					.getUserRoleBaseUrlByRole(ApplicationConstants.ROLE_MERCHANT_MANAGER);

			if (merchantManagerRoleUrl != null && ApplicationUtils.isValid(merchantManagerRoleUrl.getRoleUrl())) {
				setMerchantManagerRoleUrls(merchantManagerRoleUrl.getRoleUrl());
			}

		} catch (final Exception e) {
			logger.error("Method ==> configOpenIdParam ==> Exception ==> " + e);
		}

		logger.info("Method ==> configOpenIdParam ==> Exit");
	}
}
