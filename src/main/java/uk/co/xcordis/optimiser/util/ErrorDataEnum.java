/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.util;

/**
 * The <code>ErrorDataEnum</code> class contains all Constants related to server level error for <b>FTL</b> application.
 *
 * @author Rob Atkin
 */
public enum ErrorDataEnum {

	// Common error message properties
	OPTIMISER_USER_NOTEXIST("optimiser.user.notexist"), COMMON_ERROR_MESSAGE("common.Error.Message"),
	SIGNUP_CONNECTION_TO_OPENID_ERROR("openid.connection.error"), COMMON_EXISTS_ERROR_MESSAGE("common.field.exists.message"),
	COMMON_MISSING_FIELD("common.missing.field"), START_WITH_ALPHA("start.with.alpha.msg"), VALID_MESSAGE_ERROR_KEY("error.Message.For.valid"),

	COMMON_BADREQUEST_ERROR_MESSAGE("common.badRequest.error.message"), AUTHENTICATION_PARAM_MISSING("authentication.param.missing"),
	AUTHENTICATION_ERROR("authentication.error"), AMOUNT_MISSING_ERROR("amount.missing.error"), CARDTYPE_MISSING_ERROR("cardType.missing.error"),
	CARDNUMBER_MISSING_ERROR("cardNumber.missing.error"), CURRENCYCODE_MISSING_ERROR("currencyCode.missing.error"),
	COUNTRYCODE_MISSING_ERROR("countryCode.missing.error"), INVALID_AMOUNT_ERROR("invalid.amount.error"), INVALID_CARDTYPE_ERROR("invalid.cardType.error"),
	INVALID_CARDNUMBER_ERROR("invalid.cardNumber.error"), INVALID_CURRENCYCODE_ERROR("invalid.currencyCode.error"),
	INVALID_COUNTRYCODE_ERROR("invalid.countryCode.error"), REFERENCEID_MISSING_ERROR("referenceId.missing.error"),
	MERCHANT_CONFIGURATION_ERROR("merchant.configuration.error"), CARDISSUECOUNTRY_MISSING_ERROR("cardIssueCountry.missing.error"),
	INVALID_CARDISSUECOUNTRY_ERROR("invalid.cardIssueCountry.error"), INVALID_CARDTYPEBYCARDNUMBER_ERROR("invalid.cardTypeByCardNumber.error"),
	COMMON_BLANK("common.blank.field"), RULENAME_ERROR("view.ruleslist.ruleName"), RULECLASS_ERROR("view.ruleslist.ruleClass"),
	RULEDESCRIPTION_ERROR("view.ruleslist.ruleDescription"), SELECTORKEY_ERROR("label.view.selectorKey"),
	DEFAULT_SEQUENCE_ERROR("view.ruleslist.ruleDefaultSequence"), ERROR_ALPHAANDSPACE("error.alphaAndSpace"), COMMON_SELECT("common.select.field"),
	COMMON_SELECT_OR_ENTER("common.select.or.blank.field"), ERROR_RULENAME_EXISTS("error.rulename.exists"), ADD_SUCCESS_MESSAGE("add.success.message"),
	EDIT_SUCCESS_MESSAGE("edit.success.message"), DELETE_SUCCESS_MESSAGE("delete.success.message"), ERROR_RULECLASS_INVALID("error.ruleclass.invalid"),
	ERROR_ONLY_INTEGER("error.only.integer"), COMMON_RULE_NOT_FOUND_ERROR_MESSAGE("common.rule.notfound.error.message"), MID_MISSING_ERROR("mid.missing.error"),
	ERROR_ALPHANUMERICDASHUNDERSCORE("error.alphaNumericDashUnderscore"), HOSTEDPAYMENTPAGE_MISSING_ERROR("hostedPaymentPage.missing.error"),
	INVALID_HOSTEDPAYMENTPAGE_ERROR("invalid.hostedPaymentPage.error"), ERROR_MAX_INTEGER("error.max.integer"),
	ERROR_ALPHANUMERICSEMICOLONDASH("error.alphaNumericSemicolonDash"),

	USER_CONTACT_NUMBER_ERROR("form.registration.user.contact"), USER_SELECT_ERROR_MESSAGE("merchant.form.user.error.message"),
	USER_USER_NAME_ERROR("form.registration.user.username"), MERCHANT_NAME_ERROR_MESSAGE("merchant.form.name.error.message"),
	MERCHANT_COMMON_NOT_FOUND_ERROR_MESSAGE("merchant.common.Error.Message"), SOURCE_MERCHANT_ID_ERROR_MESSAGE("merchant.form.sourcemerchantid.error.message"),
	SOURCE_MERCHANT_ID_ERROR("form.merchant.sourceMerchantId"), MERCHANT_NAME_ERROR("form.merchant.merchantName"),

	PAYMENT_GATEWAY_COMMON_ERROR_MESSAGE("paymentgateway.common.Error.Message"), PAYMENT_GATEWAY_NAME_ERROR_MESSAGE("paymentgateway.form.name.error.message"),
	PAYMENT_GATEWAY_DESCRIPTION_ERROR_MESSAGE("paymentgateway.form.description.error.message"),
	PAYMENT_GATEWAY_GATEWAY_PARAMETERS_ERROR_MESSAGE("paymentgateway.form.gatewayparameters.error.message"),
	PAYMENT_GATEWAY_NAME_ERROR("payment.gateway.paymentGatewayName"),
	PAYMENT_GATEWAY_PARAMETER_VALUE_ERROR_MESSAGE("form.paymentgateway.gatewayparametervalue.error.message"),
	PAYMENT_GATEWAY_PARAMETER_KEY_ERROR_MESSAGE("form.paymentgateway.gatewayparameterkey.error.message"),
	PAYMENT_GATEWAY_PARAMETER_KEY("form.paymentgateway.gatewayparameterkey"), PAYMENT_GATEWAY_PARAMETER_VALUE("form.paymentgateway.gatewayparametervalue"),

	/* RuleSelectorKey validation constants: starts */
	SELECTOR_KEY_ERROR("view.ruleSelectorKeys.selectorKey"), SELECTOR_KEY_CLASS_ERROR("view.ruleSelectorKeys.selectorKeyClass"),
	OPTIMISER_PARAM_ERROR("view.ruleSelectorKeys.optimiserParam"), ERROR_SELECTOR_KEY_CLASS_INVALID("error.selectorKeyClass.invalid"),
	ERROR_ALPHANUMERIC_UNDERSCORE_ONLY("error.alphaNumericAndUnderScore"),
	COMMON_RULE_SELECTOR_KEY_NOT_FOUND_ERROR_MESSAGE("common.ruleSelectorKey.notfound.error.message"), SELECTOR_KEY_EXISTS_ERROR("error.selectorKey.exists"),
	/* RuleSelectorKey validation constants: Ends */

	COMMON_MERCHANTGATEWAY_NOT_FOUND_ERROR_MESSAGE("common.merchantgateway.notfound.error.message"), MERCHANT_ID_ERROR("form.merchant.merchantId"),
	MERCHANT_ID_INVALID_ERROR_MESSAGE("form.merchant.merchantId.error.message"), PAYMENT_GATEWAY_ERROR("view.merchantgatewaylist.paymentGatewayId"),
	GATEWAY_NAME_ERROR("view.merchantgatewaylist.gatewayName"), PAYMENT_GATEWAY_PREFERENCE_ERROR("view.merchantgatewaylist.paymentGatewayPreference"),
	PAYMENT_GATEWAY_SEQUENCE_ERROR("view.merchantgatewaylist.paymentGatewaySequence"),
	SOURCE_MERCHANTGATEWAY_ID_ERROR("view.merchantgatewaylist.sourceMerchantGatewayId"), SELECTOR_ERROR("view.merchantgatewaylist.selector"),
	MERCHANT_GATEWAY_PARAMETER_ERROR("view.merchantgatewaylist.merchantGatewayParameter"), COMMON_FORMAT_INVALID("common.format.invalid"),
	COMMON_FIELD_NOT_VALID_ERROR_MESSAGE("common.field.notvalid.message"),
	MERCHANGATEWAY_SELECTOR_VALUE_ERROR_MESSAGE("merchangateway.selector.value.error.message"),

	/* Request Data constant - Start */
	COMMON_REQUESTDATA_NOT_FOUND_ERROR_MESSAGE("common.requestdata.notfound.error.message"),
	/* Request Data constant - End */

	/* Request Header constant - Start */
	REQUEST_HEADER_USERID_MISSING_ERROR_MESSAGE("request.header.userid.missing.error"),
	REQUEST_HEADER_SECRETKEY_MISSING_ERROR_MESSAGE("request.header.secretkey.missing.error"),
	REQUEST_HEADER_EMAIL_MISSING_ERROR_MESSAGE("request.header.email.missing.error"),
	REQUEST_HEADER_ACCESSTOKEN_MISSING_ERROR_MESSAGE("request.header.accesstoken.missing.error"),
	REQUEST_HEADER_ACCESSTOKEN_INACTIVE_ERROR_MESSAGE("request.header.accesstoken.inactive.error"),
	REQUEST_HEADER_ACCESSTOKEN_EXPIRED_ERROR_MESSAGE("request.header.accesstoken.expired.error"),
	REQUEST_HEADER_USER_NOT_FOUND_ERROR_MESSAGE("request.header.user.not.found.error"),
	REQUEST_URL_USER_NOT_ACCESS_RIGHTS_ERROR_MESSAGE("request.url.user.not.accessrights.error"),
	/* Request Header constant - End */

	/* MerchantRules validation constants: starts */
	MERCHANT_RULES_OPRATION_ERROR("view.merchantRules.operation"), MERCHANT_RULES_SEQUENCE_ERROR("view.merchantRules.merchantRulesSequence"),
	MERCHANT_RULES_ENTRY_CRITERIA_VALUE_ERROR("view.merchantRules.entryCriteria.value"), MERCHANT_RULES_PARAM_ERROR("view.merchantRules.merchantRuleParam"),
	MERCHANT_RULES_NOT_FOUND_ERROR_MESSAGE("common.merchantRules.notfound.error.message"), MERCHANT_RULES_RULE_ID_ERROR_MESSAGE("view.merchantRules.ruleId"),
	MERCHANT_RULES_ENTRY_CRITERIA_MISMATCH_ERROR("common.merchantRules.entryCriteria.mismatch.error"),
	MERCHANT_RULES_PARAMETER_VALUE_ERROR_MESSAGE("common.merchantRules.merchantRuleParamValue.error.message"),
	MERCHANT_RULES_PARAMETER_KEY_ERROR_MESSAGE("common.merchantRules.merchantRuleParamKey.error.message"),
	/* MerchantRules validation constants: ends */

	/* Batch Data API Constant - Start */
	BATCH_DATA_API_OPERATIONTYPE_LABEL("batch.data.api.operationType.label"), BATCH_DATA_API_REFERENCEID_LABEL("batch.data.api.referenceId.label"),
	BATCH_DATA_API_NOTIFICATIONURL_LABEL("batch.data.api.notificationUrl.label"),
	BATCH_DATA_API_OPERATIONTYPE_INVALID_ERROR_MESSAGE("batch.data.api.operationType.invalid.error"),
	BATCH_DATA_API_BATCHDATA_LABEL("batch.data.api.batchdata.label"),
	/* Batch Data API Constant - End */

	/* Application Configuration validation constants : Starts */
	COMMON_APP_CONFIG_NOT_FOUND_ERROR_MESSAGE("common.appconfig.notfound.error.message"), APP_CONFIG_CODE_ERROR("view.appconfig.code"),
	APP_CONFIG_VALUE_ERROR("view.appconfig.value"),
	/* Application Configuration validation constants : Ends */

	/* Job Scheduler Constant - Start */
	GATEWAYNAME_FIELD_LABEL("gatewayname.field.label"), JOB_SCHEDULER_BATCH_DATA_INVALID_ERROR_MESSAGE("job.scheduler.batch.data.invalid.error.message"),
	JOB_SCHEDULER_MERCHANT_BATCH_DATA_UPDATE_OPERATION_FAILED_MESSAGE("job.scheduler.merchant.batch.data.update.operation.failed.message"),
	JOB_SCHEDULER_BATCH_DATA_MISSING_FIELD_ERROR("job.scheduler.batch.data.missing.field.error"),
	JOB_SCHEDULER_BATCH_DATA_ERROR_ALPHAANDSPACE("job.scheduler.batch.data.error.alphaAndSpace"),
	JOB_SCHEDULER_GATEWAY_BATCH_DATA_UPDATE_OPERATION_FAILED_MESSAGE("job.scheduler.gateway.batch.data.update.operation.failed.message"),
	MERCHANTID_FIELD_LABEL("merchantId.field.label"), MERCHANTGATEWAYID_FIELD_LABEL("merchantGatewayId.field.label"),
	JOB_SCHEDULER_MERCHANTGATEWAY_BATCH_DATA_GATEWAYNAME_NOTFOUND_MESSAGE("job.scheduler.merchantgateway.batch.data.gatewayname.notfound.message"),
	JOB_SCHEDULER_MERCHANTGATEWAY_BATCH_DATA_MERCHANTID_NOTFOUND_MESSAGE("job.scheduler.merchantgateway.batch.data.merchantid.notfound.message"),
	JOB_SCHEDULER_MERCHANTGATEWAY_BATCH_DATA_UPDATE_OPERATION_FAILED_MESSAGE("job.scheduler.merchantgateway.batch.data.update.operation.failed.message"),
	JOB_SCHEDULER_BATCH_DATA_UPDATE_OPERATION_FAILED_MESSAGE("job.scheduler.batch.data.update.operation.failed.message"),
	JOB_EXECUTE_SUCCESS_MESSAGE("job.execute.success.message"),
	/* Job Scheduler Constant - End */

	/* Job validation constants : Starts */
	COMMON_JOB_NOT_FOUND_ERROR_MESSAGE("common.job.notfound.error.message"), JOB_NAME_ERROR("view.job.jobName"), JOB_CLASS_NAME_ERROR("view.job.jobClassName"),
	JOB_DURATON_ERROR("view.job.jobDuration"), JOB_LAST_RUN_TIME_ERROR("view.job.lastRunTime"), JOB_STATUS_EDIT_ERROR("job.status.edit.error"),
	/* Job validation constants : Ends */

	/* HTTP error message properties : Starts */
	HTTP_ERROR_400("http.error.400"), HTTP_ERROR_403("http.error.403"), HTTP_ERROR_404("http.error.404"), HTTP_ERROR_405("http.error.405"),
	HTTP_ERROR_401("http.error.401"), HTTP_ERROR_503("http.error.503"),
	/* HTTP error message properties : Ends */

	/* Error Log Constant : Starts */
	COMMON_ERROR_LOG_NOT_FOUND_ERROR_MESSAGE("common.errorLog.notfound.error.message");
	/* Error Log Constant : Ends */

	private String message;

	ErrorDataEnum(final String message) {

		this.message = message;
	}

	public String message() {

		return message;
	}

	public enum ErrorDataEnumUser {

		REGISTRATION_USER_OPEN_ID("111", "Please provide the user open Id."), REGISTRATION_EMAIL_INVALID("112", "Please provide the Email."),
		REGISTRATION_USER_NAME_INVALID("113", "Please provide the MerchantName."),
		REGISTRATION_USER_EXIST("114", "Merchant is already available for this application."),
		REGISTRATION_USERDATA_INVALID("115", "Merchant data is not valid."), REGISTRATION_ROLE_INVALID("116", "Please provide the User Role"),;

		private String code;
		private String message;

		ErrorDataEnumUser(final String code, final String message) {

			this.code = code;
			this.message = message;
		}

		public String code() {

			return code;
		}

		public String message() {

			return message;
		}
	}

}