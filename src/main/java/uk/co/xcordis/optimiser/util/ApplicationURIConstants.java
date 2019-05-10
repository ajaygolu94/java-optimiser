/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.util;

/**
 * The <code>ApplicationURIConstants</code> interface contains all the Request Mapping URIs in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
public interface ApplicationURIConstants {

	String LOGIN_VIEW = "login";

	String REDIRECT_LABEL = "redirect:";
	String HOME = "/home";

	/* Generic URL EndPoints : Starts */
	String LIST_URL = "/list";
	String ADD_URL = "/add";
	String EDIT_URL = "/edit";
	String VIEW_URL = "/view";
	String DETAILS_URL = "/details";
	String DELETE_URL = "/delete";
	String NEW_URL = "/new";
	String UI_LABEL = "/ui";
	/* Generic URL EndPoints : Ends */

	String MERCHANT = "/merchant";
	String PAYMENT_GATEWAY = "/paymentgateway";

	String HOME_VIEW = "home";
	String MERCHANT_VIEW = "merchant";
	String MERCHANT_VIEW_VIEW = "merchantView";
	String PAYMENT_GATEWAY_VIEW_VIEW = "paymentGatewayView";
	String MERCHANT_ADD_VIEW = "addMerchant";
	String PAYMENT_GATEWAY_ADD_VIEW = "addPaymentGateway";
	String MERCHANT_EDIT_VIEW = "merchantEdit";
	String PAYMENT_GATEWAY_EDIT_VIEW = "paymentGatewayEdit";

	String PAYMENT_GATEWAY_VIEW = "paymentGateway";

	String MERCHANT_LABEL = "merchant";
	String PAYMENT_GATEWAY_LABEL = "paymentgateway";

	String ADD_PAYMENT_GATEWAY_URL = "paymentgatewayadd";
	String UPDATE_PAYMENT_GATEWAY_URL = "paymentgatewayupdate";

	String REDIRECT_MERCHANT_ADD = "merchantAdd";
	String REDIRECT_PAYMENT_GATEWAY_ADD = "paymentGatewayAdd";
	String REDIRECT_MERCHANT_VIEW = "/merchantView/{merchantId}";
	String REDIRECT_PAYMENT_GATEWAY_VIEW = "/viewPaymentGatewayPage/{paymentGatewayId}";
	String REDIRECT_MERCHANT_EDIT = "/editMerchant/{merchantId}";
	String REDIRECT_PAYMENT_GATEWAY_EDIT = "/editPaymentGateway/{paymentGatewayId}";

	String MERCHANT_LIST_URL = MERCHANT_LABEL + LIST_URL;
	String MERCHANT_VIEW_URL = MERCHANT_LABEL + VIEW_URL + "/{merchantId}";
	String MERCHANT_ADD_URL = MERCHANT_LABEL + ADD_URL;
	String MERCHANT_EDIT_URL = MERCHANT_LABEL + EDIT_URL;
	String MERCHANT_DELETE_URL = MERCHANT_LABEL + DELETE_URL + "/{merchantId}";

	String PAYMENT_GATEWAY_LIST_URL = PAYMENT_GATEWAY_LABEL + LIST_URL;
	String PAYMENT_GATEWAY_VIEW_URL = PAYMENT_GATEWAY_LABEL + VIEW_URL + "/{paymentGatewayId}";
	String PAYMENT_GATEWAY_ADD_URL = PAYMENT_GATEWAY_LABEL + ADD_URL;
	String PAYMENT_GATEWAY_EDIT_URL = PAYMENT_GATEWAY_LABEL + EDIT_URL;
	String PAYMENT_GATEWAY_DELETE_URL = PAYMENT_GATEWAY_LABEL + DELETE_URL + "/{paymentGatewayId}";

	String LOGIN_LOGINWITHOPENID_URL = "/loginwithopenid";
	String LOGIN_GETLOGINCODE_URL = "/logincode";
	String LOGIN_LOGINCODERESPONSE_URL = "/logincoderesponse";
	String LOGIN_LOGINACCESSTOKEN_URL = "/loginaccesstoken";
	String LOGIN_LOGINREQUESTRESPONSE_URL = "/loginrequestresponse";
	String LOGIN_OPENIDERRORRESPONSE_URL = "/openiderrorresponse";
	String LOGOUT_URL = "/logout";
	String LOGIN_LOGINPAGE_URL = "/login";
	String LOGOUTREDIRECT_VIEW = "logoutRedirect";

	String SIGNUP_OPTIMISER = "/signupoptimiser";
	String UPDATE_USER_DETAILS = "/updateuserdetails";
	String SIGNUP_OPTIMISER_PAGE = "signupOptimiser";
	String SIGNUP_WITH_OPENID_URL = "/signupwithopenid";
	String SIGNUP_REDIRECT_VIEW = "signupRedirect";
	String OPENID_SIGNUP_REQUEST_URL = "/xcordissignuprequest";

	String LOGIN_URL = "login";
	String ACCESSTOKENREDIRECT_VIEW = "accessTokenRedirect";
	String MERCHANT_ADD_MERCHANTDETAILS_BY_OPENID = "/addmerchantdetailbyopenid";
	String ADD_MERCHANT_DETAILS_URL = "addmerchantdetailbyopenid";

	String ERROR_PAGE_URL = "/error";
	String ERROR_PAGE_VIEW = "errorPage";

	/* Optimiser API URL Start */
	String CHOOSE_GATEWAY_API_URL = "choosegateway";
	String GENERATE_ACCESSTOKEN_API_URL = "generateaccesstoken";
	String BATCH_DATA_GATEWAY = "gatewaybatchdata";
	String BATCH_DATA_MERCHANT = "merchantbatchdata";
	String BATCH_DATA_MERCHANTGATEWAY = "merchantgatewaybatchdata";
	/* Optimiser API URL End */

	/* Rule Configuration Start */
	// generic
	String RULE = "rule";
	String RULE_SELECTORKEYS = "/selectorkeys";
	String RULE_ID = "/{ruleId}";
	String RULE_NAME = "/{ruleName}";

	// RuleRestController
	String LIST_RULES_URL = RULE + LIST_URL;
	String ADD_RULE_URL = RULE + ADD_URL;
	String VIEW_RULE_URL = RULE + VIEW_URL + RULE_ID;
	String EDIT_RULE_URL = RULE + EDIT_URL;
	String DELETE_RULE_URL = RULE + DELETE_URL + RULE_ID;
	String LIST_RULES_SELECTORKEYS_URL = RULE + RULE_SELECTORKEYS + RULE_NAME;

	// RuleController
	String ADD_RULE_VIEW_URL = RULE + UI_LABEL + ADD_URL;
	String EDIT_RULE_VIEW_URL = RULE + UI_LABEL + EDIT_URL + RULE_ID;
	String DETAILS_RULE_VIEW_URL = RULE + DETAILS_URL + RULE_ID;

	// Views
	String LIST_RULES_VIEW = "listRules";
	String ADD_RULE_VIEW = "addRule";
	String VIEW_RULE_VIEW = "viewRules";
	String EDIT_RULE_VIEW = "editRule";
	/* Rule Configuration End */

	/* RuleSelectorKey URI Constants: Starts */
	// generic
	String RULESELECTORKEY_URL = "ruleselectorkey";
	String SELECTOR_KEY = "/{selectorKey}";

	// RuleSelectorKeyRestController
	String LIST_RULESELECTORKEYS_URL = RULESELECTORKEY_URL + LIST_URL;
	String ADD_RULESELECTORKEY_URL = RULESELECTORKEY_URL + ADD_URL;
	String VIEW_RULESELECTORKEY_URL = RULESELECTORKEY_URL + VIEW_URL + SELECTOR_KEY;
	String EDIT_RULESELECTORKEY_URL = RULESELECTORKEY_URL + EDIT_URL;

	// RuleSelectorKeyController
	String DETAILS_RULESELECTORKEY_VIEW_URL = RULESELECTORKEY_URL + DETAILS_URL + SELECTOR_KEY;
	String EDIT_RULESELECTORKEY_VIEW_URL = RULESELECTORKEY_URL + UI_LABEL + EDIT_URL + SELECTOR_KEY;
	String ADD_RULESELECTORKEY_VIEW_URL = RULESELECTORKEY_URL + UI_LABEL + ADD_URL;

	// Views
	String LIST_RULESELECTORKEYS_VIEW = "listRuleSelectorKey";
	String EDIT_RULESELECTORKEY_VIEW = "editRuleSelectorKeys";
	String ADD_RULESELECTORKEY_VIEW = "addRuleSelectorKey";
	String VIEW_RULESELECTORKEY_VIEW = "viewRuleSelectorKey";
	/* RuleSelectorKey URI Constants: Ends */

	/* MerchantGateway Configuration - Start */
	// generic
	String MERCHANT_GATEWAY_ID_LABEL = "merchantGatewayId";
	String MERCHANT_GATEWAY_ID_PATH_VARIABLE = "/{merchantGatewayId}";
	String MERCHANT_GATEWAY_MODULE = "/merchantgateway";
	String MERCHANT_ID_PATH_VARIABLE = "/{merchantId}";

	// MerchantGatewayController
	String LOAD_MERCHANT_GATEWAY_URL = MERCHANT_GATEWAY_MODULE + MERCHANT_ID_PATH_VARIABLE;
	String LOAD_ADD_MERCHANT_GATEWAY_URL = MERCHANT_GATEWAY_MODULE + NEW_URL + MERCHANT_ID_PATH_VARIABLE;
	String ADD_MERCHANT_GATEWAY_UI_URL = MERCHANT_GATEWAY_MODULE + UI_LABEL + ADD_URL;
	String EDIT_MERCHANT_GATEWAY_VIEW_URL = MERCHANT_GATEWAY_MODULE + UI_LABEL + EDIT_URL + MERCHANT_GATEWAY_ID_PATH_VARIABLE;
	String EDIT_MERCHANT_GATEWAY_UI_URL = MERCHANT_GATEWAY_MODULE + UI_LABEL + EDIT_URL;
	String VIEW_MERCHANT_GATEWAY_URL = MERCHANT_GATEWAY_MODULE + VIEW_URL + MERCHANT_GATEWAY_ID_PATH_VARIABLE;
	String SEQUENCE_MERCHANT_GATEWAY_VIEW_URL = MERCHANT_GATEWAY_MODULE + UI_LABEL + ApplicationURIConstants.SEQUENCE_URL
			+ ApplicationURIConstants.MERCHANT_ID_URL;

	// MerchantGatewayRestController
	String MERCHANT_GATEWAY_LIST_URL = MERCHANT_GATEWAY_MODULE + LIST_URL;
	String ADD_MERCHANT_GATEWAY_URL = MERCHANT_GATEWAY_MODULE + ADD_URL;
	String EDIT_MERCHANT_GATEWAY_URL = MERCHANT_GATEWAY_MODULE + EDIT_URL;
	String DELETE_MERCHANT_GATEWAY_URL = MERCHANT_GATEWAY_MODULE + DELETE_URL + MERCHANT_GATEWAY_ID_PATH_VARIABLE;
	String DETAILS_MERCHANT_GATEWAY_URL = MERCHANT_GATEWAY_MODULE + DETAILS_URL + MERCHANT_GATEWAY_ID_PATH_VARIABLE;
	String SEQUENCE_MERCHANTGATEWAYS_URL = MERCHANT_GATEWAY_MODULE + ApplicationURIConstants.SEQUENCE_URL;

	// Views
	String MERCHANT_GATEWAY_LIST_VIEW = "merchantGatewaysList";
	String ADD_MERCHANT_GATEWAY_VIEW = "addMerchantGateway";
	String EDIT_MERCHANT_GATEWAY_VIEW = "editMerchantGateway";
	String VIEW_MERCHANT_GATEWAY_VIEW = "viewMerchantGateway";
	String SEQUENCE_MERCHANT_GATEWAY_VIEW = "changeSequenceMerchantGateways";
	/* MerchantGateway Configuration - End */

	/* RequestData Constants - Start */
	// generic
	String REQUEST_DATA_ID_PATH_VARIABLE = "/{requestDataId}";
	String REQUEST_DATA_LABEL = "requestData";

	// RequestDataController
	String REQUEST_DATA = "/requestdata";
	String DETAILS_REQUESTDATA_VIEW_URL = REQUEST_DATA + DETAILS_URL + REQUEST_DATA_ID_PATH_VARIABLE;

	// RequestDataRestController
	String LIST_REQUESTDATA_URL = REQUEST_DATA + LIST_URL;
	String VIEW_REQUESTDATA_URL = REQUEST_DATA + VIEW_URL + REQUEST_DATA_ID_PATH_VARIABLE;

	// Views
	String LIST_REQUESTDATA_VIEW = "requestDataList";
	String VIEW_REQUESTDATA_VIEW = "requestDataView";
	/* RequestData Constants - End */

	/* Merchant Rules Configuration Start */
	// generic
	String MERCHANTRULES_URL = "merchantrules";
	String MERCHANTRULE_ID_URL = "/{merchantRuleId}";
	String MERCHANT_ID_URL = "/{merchantId}";
	String SEQUENCE_URL = "/sequence";

	// MerchantRulesRestController
	String LIST_MERCHANTRULES_URL = MERCHANTRULES_URL + LIST_URL;
	String ADD_MERCHANTRULES_URL = MERCHANTRULES_URL + ADD_URL;
	String VIEW_MERCHANTRULES_URL = MERCHANTRULES_URL + VIEW_URL + MERCHANTRULE_ID_URL;
	String EDIT_MERCHANTRULES_URL = MERCHANTRULES_URL + EDIT_URL;
	String ADD_MERCHANTRULES_UI_URL = MERCHANTRULES_URL + UI_LABEL + ADD_URL;
	String EDIT_MERCHANTRULES_UI_URL = MERCHANTRULES_URL + UI_LABEL + EDIT_URL;
	String SEQUENCE_MERCHANTRULES_URL = MERCHANTRULES_URL + SEQUENCE_URL;
	String DELETE_MERCHANTRULES_URL = MERCHANTRULES_URL + DELETE_URL + MERCHANTRULE_ID_URL;

	// MerchantRulesController
	String LIST_MERCHANTRULES_VIEW_URL = MERCHANTRULES_URL + MERCHANT_ID_URL;
	String DETAILS_MERCHANTRULES_VIEW_URL = MERCHANTRULES_URL + DETAILS_URL + MERCHANTRULE_ID_URL;
	String EDIT_MERCHANTRULES_VIEW_URL = MERCHANTRULES_URL + UI_LABEL + EDIT_URL + MERCHANTRULE_ID_URL;
	String ADD_MERCHANTRULES_VIEW_URL = MERCHANTRULES_URL + NEW_URL + MERCHANT_ID_URL;
	String SEQUENCE_MERCHANTRULES_VIEW_URL = MERCHANTRULES_URL + UI_LABEL + SEQUENCE_URL + MERCHANT_ID_URL;

	// Views
	String LIST_MERCHANTRULES_VIEW = "listMerchantRules";
	String ADD_MERCHANTRULES_VIEW = "addMerchantRules";
	String VIEW_MERCHANTRULES_VIEW = "viewMerchantRule";
	String EDIT_MERCHANTRULES_VIEW = "editMerchantRules";
	String SEQUENCE_MERCHANTRULES_VIEW = "changeSequenceMerchantRules";
	/* Merchant Rules Configuration End */

	/* Application Configuration URLs : Start */

	// generic
	String APP_CONFIG_URL = "appconfig";
	String APP_CONFIG_ID_URL = "/{appConfigId}";

	// ApplicationConfigurationRestController
	String LIST_APP_CONFIG_URL = APP_CONFIG_URL + LIST_URL;
	String DELETE_APP_CONFIG_URL = APP_CONFIG_URL + DELETE_URL + APP_CONFIG_ID_URL;
	String ADD_APP_CONFIG_URL = APP_CONFIG_URL + ADD_URL;
	String EDIT_APP_CONFIG_URL = APP_CONFIG_URL + EDIT_URL;

	// ApplicationConfigurationController
	String ADD_APP_CONFIG_VIEW_URL = APP_CONFIG_URL + NEW_URL;
	String EDIT_APP_CONFIG_VIEW_URL = APP_CONFIG_URL + UI_LABEL + EDIT_URL + APP_CONFIG_ID_URL;

	// Views
	String LIST_APP_CONFIG_VIEW = "listAppConfig";
	String ADD_APP_CONFIG_VIEW = "addAppConfig";
	String EDIT_APP_CONFIG_VIEW = "editAppConfig";

	/* Application Configuration URLs : End */

	/* User Profile URLs : Starts */

	// ApplicationUserController
	String DETAILS_USERPROFILE_VIEW = "userProfileDetails";

	// views
	String DETAILS_USERPROFILE_VIEW_URL = "/userprofile";

	/* User Profile URLs : Ends */

	/* Job URLs : Start */

	// generic
	String JOB_URL = "job";
	String JOB_CONFIG_URL = "jobconfig";
	String JOB_ID_URL = "/{jobId}";
	String JOB_STATUS_URL = "/status";
	String JOB_EXECUTE_URL = "/execute";
	String JOB_STATUS_PATH_VARIABLE_LABEL = "/{status}";

	// JobRestController
	String LIST_JOB_URL = JOB_URL + LIST_URL;
	String DELETE_JOB_URL = JOB_URL + DELETE_URL + JOB_ID_URL;
	String VIEW_JOB_URL = JOB_URL + VIEW_URL + JOB_ID_URL;
	String ADD_JOB_URL = JOB_URL + ADD_URL;
	String EDIT_JOB_URL = JOB_URL + EDIT_URL;
	String STATUS_JOB_URL = JOB_URL + JOB_ID_URL + JOB_STATUS_URL + JOB_STATUS_PATH_VARIABLE_LABEL;
	String EXECUTE_JOB_URL = JOB_URL + JOB_ID_URL + JOB_EXECUTE_URL;

	// JobController
	String ADD_JOB_VIEW_URL = JOB_CONFIG_URL + NEW_URL;
	String EDIT_JOB_VIEW_URL = JOB_CONFIG_URL + EDIT_URL + JOB_ID_URL;
	String DETAILS_JOB_VIEW_URL = JOB_CONFIG_URL + DETAILS_URL + JOB_ID_URL;

	// Views
	String LIST_JOB_VIEW = "listJobs";
	String ADD_JOB_VIEW = "addJob";
	String EDIT_JOB_VIEW = "editJob";
	String VIEW_JOB_VIEW = "viewJob";

	/* Job URLs : End */

	/* Error Log Configuration - Start */

	// generic
	String ERRORLOG_URL = "errorlog";
	String ERRORLOG_ID_URL = "/{errorLogId}";

	// ErrorLogController
	String LIST_ERROR_LOG_VIEW_URL = "/errorlog";
	String DETAILS_ERRORLOG_VIEW_URL = ERRORLOG_URL + DETAILS_URL + ERRORLOG_ID_URL;

	// ErrorLogRestController
	String LIST_ERROR_LOG_URL = "errorlog/list";
	String VIEW_ERROR_LOG_URL = "errorlog/view/{errorLogId}";

	// Views
	String LIST_ERROR_LOG_VIEW = "listErrorLog";
	String VIEW_ERRORLOG_VIEW = "viewErrorLog";

	/* Error Log Configuration - End */
}