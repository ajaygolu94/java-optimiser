/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.util;

/**
 * The <code>ApplicationRegexConstant</code> interface contains all the constants of REGEX in entire <b>FTL</b> application.
 *
 * @author Rob Atkin
 */
public interface ApplicationRegexConstant {

	// REGEX_FOR_DECIMALNUMBER and REGEX_FOR_INTEGERNUMBER will accept both negative and positive number.
	String REGEX_FOR_DECIMALNUMBER = "^(-|[0-9]){1}([0-9])*\\.*([0-9])*$";
	String REGEX_FOR_INTEGERNUMBER = "^(-|[0-9]){1}([0-9])*\\.*([0-9])*$";
	String REGEX_FOR_ONLYNUMBER = "^([0-9])*$";
	String REGEX_FOR_ONLYCHAR = "^([a-z]|[A-Z])*$";

	String REGEX_FOR_ONLYSPACEANDCHAR = "^[a-zA-Z ]*$";
	String REGEX_FOR_ALPHANUMERICDASHUNDERSCORE = "^[a-zA-Z0-9]{1}[a-zA-Z0-9-_]*$";
	String REGEX_FOR_ALPHANUMERIC = "^[a-zA-Z0-9]*$";

	// REGEX_FOR_FULLNAME will accept dot, space and letter only
	String REGEX_FOR_FULLNAME = "^(?!.*\\.\\.)(?!.*\\s\\s)[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$";
	String REGEX_FOR_ONLYSPACE_ALPHA_COMMA = "^(?!.*\\s\\s)(?!.*,,)[A-Za-z]{1,}[A-Za-z\\s,]*$";
	// String REGEX_FOR_EMAIL = "^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]"
	// + "{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";

	String REGEX_FOR_EMAIL = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
			+ "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
			+ "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
			+ "([a-zA-Z0-9]+[\\w-]+\\.)+[a-zA-Z]{1}[a-zA-Z0-9-]{1,23})$";

	// REGEX_FOR_PHONENUMBER will accept + as start and 15 maximum number and 10 minimum number
	String REGEX_FOR_PHONENUMBER = "^(\\+){0,1}([0-9 \\-()]){10,15}$";

	/*
	 * REGEX_FOR_WEBSITE_URL will accept these type of URL only www.test.com, http://www.test.com and https://www.test.com
	 */
	String REGEX_FOR_WEBSITE_URL = "^(?!.*\\.\\.)(https?:\\/\\/)?www\\.[\\w.\\-]+(\\.[a-zA-Z]{2,3})+(\\/[\\w.?%#&=\\/\\-]*)?$";
	String REGEX_FOR_ONLYSPACE_ALPHA_NUMERIC_DOT = "^[A-Za-z0-9.\\-_+\\/ ]*$";
	String REGEX_FOR_ADDRESS = "^[^`~!@$%^*+=\\{}<>?]{0,64}$";
	String REGEX_FOR_IP_ADDRESS = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$";
	String REGEX_FOR_WEBSITE_DOMAIN = "^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])$";

	// REGEX FOR CONTROLLER METHOD URL
	String METHOD_URL = "^(/)[a-zA-Z][A-Za-z0-9/?/&/=]+$";
	String REGEX_FOR_COLONCOMMA_PARAMETER = "^([^:]+):([^,]+)+$";

	String REGEX_FOR_ONLY_CAPITAL_CHAR = "^[A-Z]*$";
	String REGEX_FOR_DATE = "^(\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2})$";
	String REGEX_FOR_CUSTOM_FIELDS = "^[ A-Za-z0-9_@.\\/#$&%+-]{0,200}$";
	String REGEX_FOR_AMOUNT = "^((\\d{1,4})(((\\.)(\\d{0,2})){0,1}))$";
	String REGEX_FOR_CARDNUMBER = "^[1-9][0-9]{10,}$";

	String REGEX_FOR_BROWSER_USER_AGENT = "^[a-zA-Z0-9 .#&+-=?_:;]*$";

	String REGEX_FOR_EXPIRY_MONTH = "^(0[1-9]|[0-9]|1[0-2])$";
	String REGEX_FOR_DOUBLENUMBER = "^\\s*(?=.*[1-9])\\d{1,8}(?:\\.\\d{1,2})?\\s*$";

	String REGEX_FOR_NAVITAIRE_FULLNAME = "^\\p{L}*[0-9\\p{L}\\s]*[\\p{L}0-9]$";
	String REGEX_FOR_NAVITAIRE_EMAIL = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}$";

	// REGEX_FOR_CARDHOLDERPHONE will accept - and space including number and maximum length is 10
	String REGEX_FOR_CARDHOLDERPHONE = "^[\\d -]{0,10}$";

	String REGEX_FOR_ONLYSPACE_CHAR_SPECIALCHARCTERS = "^[a-zA-Z .,\\-]*$";
	String REGEX_FOR_ONLYSPACE_ALPHANUMERIC_SOME_SPECIALCHARCTERS = "^[A-Za-z0-9.\\-_+\\/ ]*$";
	String REGEX_FOR_ALPHA_SPACE_UNDERSCORE = "^[a-zA-Z _]*$";
	String REGEX_FOR_ALPHA_SPACE_DOT = "^[a-zA-Z .]*$";
	String REGEX_FOR_ALL_SPECIALCHARACTERS_ALPHANUMERIC_SPACE = "^[A-Za-z0-9_@.\\/#$&%+\\-!^*()=;:',?\\©|\\[{}\\]\\`~\\>< ]*$";
	String REGEX_FOR_NAVIATIRE_ONLYNUMBER = "^(\\d)*$";
	String REGEX_FOR_ALPHANUMERIC_SPACE_UNDERSCORE = "^[a-zA-Z0-9\\-_ ]*$";
	String REGEX_FOR_NUMBER_SOME_SPECIALCHARACTERS = "^[0-9\\-,]*$";
	String REGEX_FOR_ALPHANUMERIC_LINE_BREAK = "^[a-zA-Z0-9,.\\r\\n]*$";
	String REGEX_FOR_URL = "(https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|www\\.[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9]\\.[^\\s]{2,}|www\\.[a-zA-Z0-9]\\.[^\\s]{2,})";
	String REGEX_FOR_NAVITAIRE_CITY = "^[a-zA-Z .,\\-+]*$";
	String REGEX_FOR_ONLY_NUMBER_HYPHEN = "^[\\d -]*$";

	String REGEX_FOR_SEMICOLON_SEPARATED_VALUES = "^[\\u0400-\\u04FFa-zA-Z ]+(;[\\u0400-\\u04FFa-zA-Z ]+)*$";
	String REGEX_FOR_CARDTYPE = "^(?!.*\\s\\s)(?!.*\\_\\_)[a-zA-Z0-9][a-zA-Z0-9_\\s]{1,15}$";
	String REGEX_FOR_CARDNUMBER_CURRANCEY_CODE = "^[A-Z]{3}$";
	String REGEX_FOR_COUNTRY_CODE = "^[A-Z]{2,3}$";
	String REGEX_FOR_ALPHANUMERIC_UNDERSCORE = "^[a-zA-Z][a-zA-Z0-9_]*$";
	String REGEX_FOR_ALPHANUMERIC_UNDERSCORE_AND_DOT = "^(?!.*\\.\\.)[A-Za-z]{1,}[A-Za-z|0-9\\_\\.]*";
	String REGEX_FOR_ALPHA_DOT_UNDERSCORE = "^[a-zA-Z._]*$";
	String REGEX_FOR_CLASS_NAME = "(([a-zA-Z]+[_][a-zA-Z]+\\.)|([a-zA-Z][a-zA-Z]+\\.))*[a-zA-Z]*";
	String REGEX_FOR_HOSTED_PAYMENT_PAGE = "^(00|01)$";
	String REGEX_FOR_THREE_DIGIT_LENGTH = "^([1-9][0-9]{0,2})$";
	String REGEX_FOR_ALPHANUMERIC_DASH_UNDERSCORE_START_WITH_ALPHA = "^[a-zA-Z]{1}[a-zA-Z0-9-_]*$";
	String REGEX_FOR_AMOUNT_SEMICOLON = "^([1-9][0-9]*[0-9;.-])*$";
	String REGEX_FOR_ALPHA_NUMERIC_DASH_SEMICOLON = "^([a-zA-Z0-9]*[a-zA-Z0-9;-])*$";
}
