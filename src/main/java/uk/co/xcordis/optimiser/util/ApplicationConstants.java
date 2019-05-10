/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.util;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 * The <code>ApplicationConstants</code> interface contains all the constants present in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
public interface ApplicationConstants {

	// Cassandra configuration label - Start
	String CASSANDRACONNECTOR_LABEL = "cassandraConnector";
	String APPLICATIONINITIALIZER_LABEL = "applicationInitializer";
	String SERVICEREGISTRY_LABEL = "serviceRegistry";
	String MESSAGESOURCE_LABEL = "messageSource";
	String COMMON_CONFIG_PARAMS = "commonConfigParam";

	String DATABASE_PROPERTY_FILE_CLASSPATH = "classpath:database.properties";
	String PROJECT_PROPERTY_FILE_CLASSPATH = "classpath:project.properties";
	String CASSANDRA_CONTACTPOINTS_VALUE_LABEL = "${cassandra.contactpoints}";
	String CASSANDRA_PORT_VALUE_LABEL = "${cassandra.port}";
	String CASSANDRA_KEYSPACE_VALUE_LABEL = "${cassandra.keyspace}";
	String CASSANDRA_USER_VALUE_LABEL = "${cassandra.user}";
	String CASSANDRA_PASSWORD_VALUE_LABEL = "${cassandra.password}";
	String ADMIN_EMAIL_VALUE_LABEL = "${admin.email}";
	// Cassandra configuration label - End

	String DD_MM_YYYY = "dd/MM/yyyy";
	String DD_MM_YYYY_HH_MM_SS_AM_PM = "dd/MM/yyyy HH:mm:ss a";
	String DD_MM_YYYY_HH_MM_SS = "dd/MM/yyyy HH:mm:ss";
	String UTF8 = "UTF-8";
	String CONTENT_TYPE_LABEL = "Content-Type";
	String CONTENT_TYPE_JSON = "application/json";

	String ACCESSTOKEN_LABEL = "accessToken";
	String REDIRECTURL_LABLE = "redirectURL";
	String ENCRYPTION_PADDING_DATA = "openidapplicatio";
	String ENCRYPT_DECRYPT_ALGO_NAME = "AES";
	String REDIRECT_LABLE = "redirect:";

	String CODE_LABLE = "code";
	String STATUS_LABLE = "status";
	String STATUS_UPDATE = "statusupdate";
	String STATUS_ERROR = "error";
	String STATUS_SUCCESS = "success";
	String ERRORCODE_LABLE = "ErrorCode";
	String ERRORMESSAGE_LABLE = "ErrorMessage";
	String ERRORMSG_LABLE = "errorMessage";
	String COMMON_ERRORMESSAGE = "Internal server error. Please contact to Administrator.";
	String BATCH_DATA_NOT_VALID_ERROR_MESSAGE = "Data is invalid which was sent by you.";
	String REFERENCE_ID_LABEL = "referenceId";
	String NOTIFICATION_URL_LABEL = "notificationUrl";

	String MERCHANT_MODULE_LABEL = "Merchant ";

	String CLIENTID_LABLE = "client_id";
	String CLIENTSECRET_LABLE = "client_secret";
	String APPLICATIONNAME_LABLE = "applicationName";
	String SECRET_LABLE = "secret";
	String URL_LABEL = "url";
	String MERCHANTID_LABLE = "merchantId";
	String USERID_LABLE = "userId";
	String MERCHANT_NAME = "merchantName";
	String ROLE_LABEL = "role";
	String SECRET_KEY_LABEL = "secretKey";
	String EMAIL_LABEL = "email";
	String OPENID_LABLE = "openId";
	String ADMIN_TOKEN = "adminToken";
	String IS_ADMIN = "isAdmin";
	String ACCESSTOKENLIST_LABEL = "accessTokenList";

	String COMMON_CONFIG_OPENID_URL_CODE = "openIdURL";
	String COMMON_CONFIG_OPENID_CODE_URL = "openIdCodeURL";
	String COMMON_CONFIG_OPENID_CODE_URL_VALUE = "/authorizecoderequest";
	String COMMON_CONFIG_OPENID_ACCESSTOKEN_URL = "openIdAccessTokenURL";
	String COMMON_CONFIG_OPENID_ACCESSTOKEN_URL_VALUE = "/authorizeaccesstoken";
	String COMMON_CONFIG_OPENID_LOGINREQUEST_URL = "openIdLoginRequestURL";
	String COMMON_CONFIG_OPENID_LOGINREQUEST_URL_VALUE = "/loginrequest";
	String COMMON_CONFIG_OPENID_ADDMERCHANTURL_CODE = "openIdAddMerchantURL";
	String COMMON_CONFIG_OPENID_ADDMERCHANTURL_CODE_VALUE = "/addmerchant";
	String COMMON_CONFIG_OPENID_GETACCESSTOKENLIST_CODE = "openIdGetAccessTokenListURL";
	String COMMON_CONFIG_OPENID_GETACCESSTOKENLIST_CODE_VALUE = "/accesstokenlistbyappname?applicationName=";
	String APPLICATION_URL = "appURL";

	String ADD_MERCHANT_URL_LABEL = "addMerchantURL";

	String TREE_MENULIST = "treeMenuList";
	String OPTIMISER_MENULIST = "optimiserMenuList";
	String CONFIGURATION_SUBMENU_LIST = "configSubMenuList";
	String CONFIGURATION_TOKEN_LABLE = "configurationToken";
	String MENU_LIST = "menuList";

	String USER_OBJECT_LABEL = "user";
	String APPLICATION_USER_OBJECT_LABEL = "applicationUser";
	String MERCHANT_OBJECT_LABEL = "merchant";

	String OBJ_MERCHANT_LIST = "merchantList";
	String APPLICATION_USER_LIST_LABEL = "applicationUserList";
	String OBJ_MERCHANT_MANAGER_LIST = "merchantManagerList";

	String ROLE_MERCHANT = "Merchant";
	String ROLE_MERCHANT_MANAGER = "Merchant Manager";

	String ROLE_ADMIN = "Admin";

	// Error Page Start
	String MESSAGE_LABEL = "message";
	// Error Page End

	String SERVLET_CONTEXT_XML_FILE_LABEL = "classpath:servlet-context.xml";
	String REQUEST_GETHEADER_LABEL = "X-FORWARDED-FOR";
	String FACTS_REQUESTDATAID_LABEL = "requestDataId";
	String[] CHOOSEGATEAY_IGNOREPROPERTIES = new String[] { "id", "merchantId", "referenceId" };

	// Application Configuration related constants - Start
	String APPLICATION_LOGO_RELATIVE_PATH = "APPLICATION_LOGO_RELATIVE_PATH";
	String APPLICATION_FOOTER_TEXT = "APPLICATION_FOOTER_TEXT";
	String APPLICATION_HEADER_TEXT = "APPLICATION_HEADER_TEXT";
	String APPLICATION_CSS = "APPLICATION_CSS";
	String FAVICON_IMAGE = "FAVICON_IMAGE";
	String BACKGROUND_IMAGE = "BACKGROUND_IMAGE";
	String OPTIMISER_LOGO_IMAGE = "OPTIMISER_LOGO_IMAGE";
	String LOGIN_PAGE_LOGO = "LOGIN_PAGE_LOGO";
	String LOGIN_PAGE_HEADER = "LOGIN_PAGE_HEADER";
	String REGISTRATION_PAGE_HEADER = "REGISTRATION_PAGE_HEADER";
	String DASHBOARD_BACKGROUND_IMAGE = "DASHBOARD_BACKGROUND_IMAGE";
	String ADMIN_EMAIL = "ADMIN_EMAIL";

	String OPTIMISER_APP_LOGO_RELATIVE = "Optimiser.png";
	String OPTIMISER_FOOTER = "&copy; XCordis Optimiser 2017";
	String OPTIMISER_HEADER = "Optimiser";
	String OPTIMISER_APPLICATION_CSS = "/css/optimiser.css";
	String OPTIMISER_FAVICON = "/img/favicon_optimiser.ico";
	String OPTIMISER_HOME_JPG = "/img/home.jpg";
	String OPTIMISER_IMG_PNG = "/img/optimiser_img.png";
	String OPTIMISER_PNG = "/img/optimiser.png";
	String OPTIMISER_LOGIN = "OPTIMISER LOGIN";
	String OPTIMISER_SIGNUP = "OPTIMISER SIGN UP";
	String OPTIMISER_BACKGROUND_IMAGE = "/img/optimiser_background_new_image.jpg";
	String OPTIMISER_ADMIN_EMAIL_VALUE = "ankit.jani@techforceinfotech.in";

	String DTO_USERDTO = "userDto";

	String RESPONSE_CODE_100 = "100";

	String COREVOLUMEURL_LABEL = "coreVolumeURL";
	String COREACCESSTOKEN_LABEL = "coreAccessToken";
	String MERCHANTGATEWAY_VOLUME_LABEL = "volume";
	String FACT_GATEWAYRULEENGINEDTO_LABEL = "gatewayRuleEngineDTO ";
	String FACT_REQUESTDATA_LABEL = "requestData";
	String FACTS_MESSAGESOURCE_LABEL = "messageSource";
	String CRITICAL_LABEL = "Critical";
	String CHECKGATEWAYVOLUME_METHOD_LABEL = "checkGatewayVolume";
	Marker MARKER = MarkerFactory.getMarker("error");
	String LOGGER_THREAD_CONTEXT_IDENTIFIER_LABEL = "identifier";

	String PAYMENT_GATEWAY_MODULE_LABEL = "Payment Gateway ";
	String PAYMENT_GATEWAY_ID = "paymentGatewayId";
	String PAYMENT_GATEWAY_OBJECT_LABEL = "paymentgateways";

	String RULESCOMMONOPERATIONS_METHOD_LABEL = "rulesCommonOperations ";
	String RULESCOMMONOPERATIONS_MERCHANTRULES_LABEL = "merchantRule";
	String RULESCOMMONOPERATIONS_MERCHANTID_LABEL = "merchantId";
	String RULESCOMMONOPERATIONS_RESULT_LABEL = "result";
	String CHECKCARDTYPE_METHOD_LABEL = "checkCardType ";
	String SKIP_RULE_SELECTOR_MISSING = " Required selector or entry criteria not found that's why this rule skip by the application.";
	String SKIP_RULE_BY_ENTRY_CRITERIA = " The request is not able to pass the entry level criteria of merchant rules.";
	String SKIP_RULE_RULEOBJECT_NULL = " Application found a null object of Rule that's why this rule skips by the application.";
	String LOGERROR_EXCEPTION_LABEL = " ==> Exception ==> ";
	String STOP_RULEENIGINE_SERVICEREGISTRY = "Application found a null object of serviceRegistry that's why rule engine is stopped.";
	String STOP_RULEENIGINE_RUQESTDATA = " Application found a null object of RequestData that's why rule engine is stopped.";
	String STOP_RULEENIGINE_MERCHANTRULES_MERCHANTGATEWAY = " Application found null list object of merchant rules and Merchant Gateway that's why rule engine is stopped.";
	String SKIP_RULES_INTERNALSERVER_ERROR = " Application skip this rule because of internal server error";
	String SKIP_RULES_REQUIRED_PARAM_MISSING = " Required parameters are not found that's why this rule skip by the application.";
	String SKIP_RULES__MISSING = " Merchant Rule was not found that's why this rule skips by the application.";
	String STOP_RULEENIGINE_SELECTEDMERCHANTGATEWAYS = " Application found a null object of selectedMerchantGateways or list that's why rule engine is stopped.";
	String SKIP_RULE_SELECTEDMERCHANTGATEWAYS = " Application found a null object of selectedMerchantGateways or list that's why this rule skip.";

	/* Rules Configuration Start */
	String RULES_LABEL = "rules";
	String RULEID_LABEL = "ruleId";
	String RULE_STATUS_ACTIVE = "Active";
	String RULE_STATUS_INACTIVE = "InActive";
	String PATHVARIABLE_RULESTATUS_LABEL = "ruleStatus";
	String SELECTOR_KEY_LABEL = "selectorKeys";
	String RULE_MODULE_LABEL = "Rule ";
	String SELECTEDKEYVALUE_LABEL = "selectedKeyValue";
	String HIDDEN_VALUE_LABEL = "Hidden Value";
	String OTHER_LABEL = "Other";
	String REQUESTPARAM_CODE_LABEL = "code";
	String RULE_NAME = "ruleName";
	String AMOUNT_LABEL = "amount";

	String FIELD_AUDITTIMESTAMP_LABEL = "auditTimeStamp";
	String USERID_LABEL = "userId";
	String CODE_LABEL = "code";
	String LIST_OF_RULES_CLASS = "rulesClassList";
	String REGEX_FOR_ALPHANUMERIC_UNDERSCORE_AND_DOT = "^(?!.*\\.\\.)[A-Za-z]{1,}[A-Za-z|0-9\\_\\.]*";
	/* Rules Configuration End */

	/* Rule Selector Keys constants: Starts */
	String SELECTOR_KEY = "selectorKey";
	String RULE_SELECTOR_KEY = "ruleSelectorKey";
	String RULE_SELECTOR_KEY_LABEL = "Rule Selector Key ";
	/* Rule Selector Keys constants: Ends */

	/* Merchant Gateway - Start */
	String MERCHANT_GATEWAY_MODULE_LABEL = "Merchant Gateway ";
	String PAYMENT_GATEWAY_LIST_LABEL = "paymentGatewayList";
	String RULE_SELECTOR_KEYS_LIST_LABLE = "ruleSelectorKeysList";
	String MERCHANT_GATEWAY_LABEL = "merchantGateway";
	String MERCHANT_GATEWAYS_LIST_OBJECT_LABEL = "merchantGatewaysList";
	/* Merchant Gateway - End */

	/* Merchant Rules Constants: Starts */
	String MERCHANT_RULES_LABEL = "Merchant Rule ";
	String MERCHANT_RULES_OBJECT_LABEL = "merchantRule";
	String MERCHANT_RULES_MERCHANT_LIST_OBJECT_LABEL = "merchantList";
	String MERCHANT_RULES_RULES_LIST_OBJECT_LABEL = "rulesList";
	String MERCHANT_RULES_OPERATION_LIST_OBJECT_LABEL = "operationList";
	String MERCHANTRULE_ID = "merchantRuleId";
	String MERCHANT_RULES_LIST_OBJECT_LABEL = "merchantRulesList";
	/* Merchant Rules Constants: Ends */

	/* Generic configurations : Starts */
	String BASE_PACKAGE = "uk.co.xcordis.optimiser";
	/* Generic configurations : Ends */

	/* Batch Data API Constants - Start */
	String GATEWAY_PARAMETER_LABEL = "gatewayParameter";
	/* Batch Data API Constants - End */

	/* Application Configuration Constants: Starts */
	String APP_CONFIG_LABEL = "Application Configuration ";
	String APP_CONFIG_ID_LABEL = "appConfigId";
	String APP_CONFIG = "appconfig";
	/* Application Configuration Constants: Ends */

	String HPP_TRUE_VALUE = "00";

	/* Job Constants: Starts */
	String JOB_LABEL = "Job ";
	String JOB_ID_LABEL = "jobId";
	String JOB_STATUS_LABEL = "status";
	String JOB = "job";
	String JOB_SCHEDULER_PACKAGE = "uk.co.xcordis.optimiser";
	String LIST_OF_SCHEDULER_CLASS = "schedulerClassList";

	String KEY = "Key";
	String GROUP = "Group";
	/* Job Constants: Ends */

	/* Error Log Constants: Starts */
	String ERROR_LOG_MODULE_LABEL = "Error Log";
	String ERROR_LOG_ID_KEY = "errorLogId";
	/* Error Log Constants: Ends */
}