/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.security.Key;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.core.util.CronExpression;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.json.JSONObject;
import org.omg.CORBA.portable.UnknownException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

/**
 * The <code>ApplicationUtils</code> class contains all the utility methods required across <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
/**
 * @author Parth
 *
 */
public final class ApplicationUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationUtils.class);

	private ApplicationUtils() {

	}

	/**
	 * The method <code>isValidDate</code> checks whether given field has valid date or not.
	 *
	 * @param dateToValidate
	 * @param dateFromat
	 * @return
	 */
	public static boolean isValidDate(final String dateToValidate, final String dateFromat) {

		if (dateToValidate == null) {
			return false;
		}

		final SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
		sdf.setLenient(false);

		try {

			// if not valid, it will throw ParseException
			final Date date = sdf.parse(dateToValidate);
			LOGGER.info("==> date : " + date);

		} catch (final ParseException e) {
			return false;
		}

		return true;
	}

	private final static SimpleDateFormat simpleDateFormatForIsValidDate = new SimpleDateFormat(ApplicationConstants.DD_MM_YYYY);

	/**
	 * The method <code>isValidDate</code> is check whether given field is valid date or not.
	 *
	 * @param dateToValidate
	 * @return
	 */
	public static boolean isValidDate(final String dateToValidate) {

		if (dateToValidate == null) {
			return false;
		}

		simpleDateFormatForIsValidDate.setLenient(false);

		try {
			// if not valid, it will throw ParseException
			simpleDateFormatForIsValidDate.parse(dateToValidate);
		} catch (final ParseException e) {
			return false;
		}
		return true;
	}

	/**
	 * The method <code>isFutureDate</code> is check whether given date is future date or not.
	 *
	 * @param date
	 * @return
	 *
	 */
	public static boolean isFutureDate(final String date) {

		// today date
		final Date current = new Date();

		// create date object
		Date next;

		try {
			next = new SimpleDateFormat(ApplicationConstants.DD_MM_YYYY).parse(date);
			if (next.after(current)) {
				return true;
			}
		} catch (final ParseException e) {
			return false;
		}
		// compare both dates
		return false;
	}

	/**
	 * The method <code>isPastDate</code> is check whether given date is past date or not.
	 *
	 * @param date
	 * @return
	 */
	public static boolean isPastDate(final String date) {

		// today date
		final Date currentDate = new Date();
		// create date object
		Date beforeDate;

		try {
			beforeDate = new SimpleDateFormat(ApplicationConstants.DD_MM_YYYY).parse(date);
			if (beforeDate.before(currentDate)) {
				return true;
			}
		} catch (final ParseException e) {
			return false;
		}
		// compare both dates
		return false;

	}

	/**
	 * The method <code>isNumeric</code> checks whether given value is numeric or not.
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(final String str) {

		try {
			final Double valueOf = Double.valueOf(str);
			if (valueOf != null) {
				return true;
			}
		} catch (final NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	/**
	 * This method <code>isNotEmpty</code> is used for check the String whether is not empty or not null.
	 *
	 * @param s
	 * @return
	 */
	public static boolean isNotEmpty(final String s) {

		LOGGER.info(" ==> Method : isNotEmpty ==> Called");
		return s != null && !s.isEmpty();
	}

	/**
	 * This method <code>isValid</code> is used for check the Collection whether is not empty or not null.
	 *
	 * @param collection
	 * @return
	 */
	public static boolean isValid(final Collection<?> collection) {

		LOGGER.info(" ==> Method : isValid ==> Called");
		return collection != null && !collection.isEmpty();
	}

	/**
	 * This method <code>isValid</code> is used for check the Collection whether is not empty or not null.
	 *
	 * @param collection
	 * @return
	 */
	public static boolean isValid(final Map<?, ?> map) {

		LOGGER.info(" ==> Method : isValid ==> Called");
		return map != null && !map.isEmpty();
	}

	/**
	 * This method <code>toDate</code> is used for convert String Date to Date Object format
	 *
	 * @param strDate
	 * @param format
	 * @return
	 */
	public static Date toDate(final String strDate, final String format) {

		final SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Date date = null;

		try {

			date = dateFormat.parse(strDate);

		} catch (final Exception e) {
			LOGGER.error(" ==> Method : toDate ==> " + e);
		}

		return date;
	}

	/**
	 * This method <code>dateToString</code> is used for convert the Date object to String Date
	 *
	 * @param date
	 * @param format
	 * @return
	 */
	public static String dateToString(final Date date, final String format) {

		LOGGER.info(" ==> Method : dateToString ==> Enter");

		String stringDate = null;

		try {

			final SimpleDateFormat sdf = new SimpleDateFormat(format);
			stringDate = sdf.format(date);

		} catch (final Exception e) {
			LOGGER.error(" ==> Method : dateToString ==> " + e);
		}

		LOGGER.info(" ==> Method : dateToString ==> Exit");
		return stringDate;
	}

	private final static SimpleDateFormat currentTimeStampFormat = new SimpleDateFormat(ApplicationConstants.DD_MM_YYYY_HH_MM_SS_AM_PM);

	/**
	 * This method <code>currentDate</code> is used for get current date in string format
	 *
	 * @return
	 */
	public static String getCurrentTimeStamp() {

		LOGGER.info(" ==> Method : getCurrentTimeStamp ==> called");
		return currentTimeStampFormat.format(new Date());
	}

	private final static SimpleDateFormat currentTimeStampWithoutAMPMFormat = new SimpleDateFormat(ApplicationConstants.DD_MM_YYYY_HH_MM_SS);

	/**
	 * This method <code>getCurrentTimeStamp</code> is used for get current time stamp in string.
	 *
	 * @return
	 */
	public static String getCurrentTimeStampWithoutAMPM() {

		LOGGER.info(" ==> Method : getCurrentTimeStampWithoutAMPM ==> called");
		return currentTimeStampWithoutAMPMFormat.format(new Date());
	}

	/**
	 * This method <code>currentDate</code> is used for get current time stamp base on give format in string.
	 *
	 * @param dateFormat
	 * @return
	 */
	public static String getCurrentTimeStamp(final String dateFormat) {

		LOGGER.info(" ==> Method : getCurrentTimeStamp ==> called");

		final SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		return format.format(new Date());
	}

	/**
	 * This method <code>generateJSONFromObject</code> is responsible to generate JSON from a given object.
	 *
	 * @param object
	 * @return
	 */
	public static String generateJSONFromObject(final Object object) {

		LOGGER.info(" ==> Method : generateJSONFromObject ==> Enter");
		final ObjectMapper objectMapper = new ObjectMapper();

		String jsonString = null;

		try {
			objectMapper.setSerializationInclusion(Include.NON_NULL);

			/**
			 * Store JSON in to jsonString
			 */
			jsonString = objectMapper.writeValueAsString(object);

		} catch (final JsonGenerationException e) {
			LOGGER.error(" ==> Method : generateJSONFromObject ==> JsonGenerationException : " + e);
		} catch (final JsonMappingException e) {
			LOGGER.error(" ==> Method : generateJSONFromObject ==> JsonMappingException : " + e);
		} catch (final IOException e) {
			LOGGER.error(" ==> Method : generateJSONFromObject ==> IOException : " + e);
		}

		LOGGER.info(" ==> Method : generateJSONFromObject ==> Exit");
		return jsonString;
	}

	/**
	 * This method <code>generateObjectFromJSON</code> is responsible to generate object from a given JSON.
	 *
	 * @param jsonString
	 * @param clazz
	 * @param isList
	 * @return
	 */
	public static <T> T generateObjectFromJSON(final String jsonString, final Class<T> clazz, final boolean isList) {

		LOGGER.info(" ==> Method : generateObjectFromJSON ==> Enter");

		T object = null;

		if (!ApplicationUtils.isEmpty(jsonString)) {

			try {

				final ObjectMapper objectMapper = new ObjectMapper();

				if (isList) {

					object = objectMapper.readValue(jsonString, TypeFactory.defaultInstance().constructCollectionType(List.class, clazz));
				} else {
					object = clazz.newInstance();
					object = objectMapper.readValue(jsonString, clazz);
				}

			} catch (final JsonParseException e) {
				LOGGER.error(" ==> Method : generateObjectFromJSON ==> JsonParseException : " + e);
			} catch (final JsonMappingException e) {
				LOGGER.error(" ==> Method : generateObjectFromJSON ==> JsonMappingException : " + e);
			} catch (final IOException e) {
				LOGGER.error(" ==> Method : generateObjectFromJSON ==> IOException : " + e);
			} catch (final Exception e) {
				LOGGER.error(" ==> Method : generateObjectFromJSON ==> Exception : " + e);
			}
		}

		LOGGER.info(" ==> Method : generateObjectFromJSON ==> Exit");
		return object;
	}

	/**
	 * This <code>isEmpty</code> method is responsible to check whether the String value passed is empty or not.
	 *
	 * @param param
	 * @return
	 */
	public static boolean isEmpty(final String param) {

		final boolean error = false;

		if (param == null || param.trim().length() <= 0) {
			return true;
		}
		return error;
	}

	/**
	 * This <code>isEmpty</code> method is responsible to check whether the Boolean value passed is empty or not.
	 *
	 * @param param
	 * @return
	 */
	public static boolean isEmpty(final Boolean param) {

		final boolean error = false;

		if (param == null) {
			return true;
		}
		return error;
	}

	/**
	 * This <code>isEmpty</code> method is responsible to check whether the Long value passed is empty or not.
	 *
	 * @param param
	 * @return
	 */
	public static boolean isEmpty(final Long param) {

		final boolean error = false;

		if (param == null || param <= 0L) {
			return true;
		}
		return error;
	}

	/**
	 * This <code>isEmpty</code> method is responsible to check whether the Integer value passed is empty or not.
	 *
	 * @param param
	 * @return
	 */
	public static boolean isEmpty(final Integer param) {

		final boolean error = false;

		if (param == null || param <= 0) {
			return true;
		}
		return error;
	}

	/**
	 * This <code>isEmpty</code> method is responsible to check whether the Double value passed is empty or not.
	 *
	 * @param param
	 * @return
	 */
	public static boolean isEmpty(final Double param) {

		final boolean error = false;

		if (param == null || param <= 0) {
			return true;
		}
		return error;
	}

	/**
	 * This <code>isEmpty</code> method is responsible to check whether the String Array passed is empty or not.
	 *
	 * @param param
	 * @return
	 */
	public static boolean isEmpty(final String[] param) {

		final boolean error = false;

		if (param == null || param.length == 0) {
			return true;
		}
		return error;
	}

	/**
	 * This <code>isDecimalNumber</code> method is responsible to check whether given value valid decimal or double number
	 *
	 * @param value
	 * @return
	 */
	public static Boolean isDecimalNumber(final String value) {

		return validateValueForRegex(ApplicationRegexConstant.REGEX_FOR_DECIMALNUMBER, value);
	}

	/**
	 * This <code>isIntegerNumber</code> method is responsible to check whether given value valid integer number.
	 *
	 * @param value
	 * @return
	 */
	public static Boolean isIntegerNumber(final String value) {

		return validateValueForRegex(ApplicationRegexConstant.REGEX_FOR_INTEGERNUMBER, value);
	}

	/**
	 * This <code>isOnlyNumber</code> method is responsible to check whether given value valid digit.
	 *
	 * @param value
	 * @return
	 */
	public static Boolean isOnlyNumber(final String value) {

		return validateValueForRegex(ApplicationRegexConstant.REGEX_FOR_ONLYNUMBER, value);
	}

	/**
	 * This <code>isOnlyAlphabet</code> method is responsible to check whether given value valid alphabet.
	 *
	 * @param value
	 * @return
	 */
	public static Boolean isOnlyAlphabet(final String value) {

		return validateValueForRegex(ApplicationRegexConstant.REGEX_FOR_ONLYCHAR, value);
	}

	/**
	 * This <code>isFullName</code> method is responsible to check whether given value valid full name.
	 *
	 * @param value
	 * @return
	 */
	public static Boolean isFullName(final String value) {

		return validateValueForRegex(ApplicationRegexConstant.REGEX_FOR_FULLNAME, value);
	}

	/**
	 * This <code>isEmail</code> method is responsible to check whether given value valid email.
	 *
	 * @param value
	 * @return
	 */
	public static Boolean isEmail(final String value) {

		return validateValueForRegex(ApplicationRegexConstant.REGEX_FOR_EMAIL, value);
	}

	/**
	 * This <code>isPhoneNumber</code> method is responsible to check whether given value valid phone number.
	 *
	 * @param value
	 * @return
	 */
	public static Boolean isPhoneNumber(final String value) {

		return validateValueForRegex(ApplicationRegexConstant.REGEX_FOR_PHONENUMBER, value);
	}

	/**
	 * This <code>isAlphaNumeric</code> method is responsible to check whether given value valid alphabet numeric.
	 *
	 * @param value
	 * @return
	 */
	public static Boolean isAlphaNumeric(final String value) {

		return validateValueForRegex(ApplicationRegexConstant.REGEX_FOR_ALPHANUMERIC, value);
	}

	/**
	 * This <code>isAlphaNumericSpaceAndDot</code> method is responsible to check whether given value contains only alphabet, space, numeric and dot.
	 *
	 * @param value
	 * @return
	 */
	public static Boolean isAlphaNumericSpaceAndDot(final String value) {

		return validateValueForRegex(ApplicationRegexConstant.REGEX_FOR_ONLYSPACE_ALPHA_NUMERIC_DOT, value);
	}

	/**
	 * This <code>isAlphaCommaAndSpace</code> method is responsible to check whether given value contains only alphabet, space and comma.
	 *
	 * @param value
	 * @return
	 */
	public static Boolean isAlphaCommaAndSpace(final String value) {

		return validateValueForRegex(ApplicationRegexConstant.REGEX_FOR_ONLYSPACE_ALPHA_COMMA, value);
	}

	/**
	 * This <code>isOnlyAlphaAndSpace</code> method is responsible to check whether given value valid alphabet and white space.
	 *
	 * @param value
	 * @return
	 */
	public static Boolean isOnlyAlphaAndSpace(final String value) {

		return validateValueForRegex(ApplicationRegexConstant.REGEX_FOR_ONLYSPACEANDCHAR, value.trim());
	}

	/**
	 * This <code>isOnlyAlphaNumericDashUnderscore</code> method is responsible to check whether given value valid alphabet, number, dash and underscore.
	 *
	 * @param value
	 * @return
	 */
	public static Boolean isOnlyAlphaNumericDashUnderscore(final String value) {

		return validateValueForRegex(ApplicationRegexConstant.REGEX_FOR_ALPHANUMERICDASHUNDERSCORE, value.trim());
	}

	/**
	 * This <code>isOnlyAlphaNumericDashUnderscoreStartWithAlpha</code> method is responsible to check whether given value valid alphabet, number, dash,
	 * underscore and start with alpha.
	 *
	 * @param value
	 * @return
	 */
	public static Boolean isOnlyAlphaNumericDashUnderscoreStartWithAlpha(final String value) {

		return validateValueForRegex(ApplicationRegexConstant.REGEX_FOR_ALPHANUMERIC_DASH_UNDERSCORE_START_WITH_ALPHA, value.trim());
	}

	/**
	 * This <code>isValidAmount</code> method is responsible to check whether the given value contains a valid value for Amount.
	 *
	 * @param value
	 * @return
	 */
	public static Boolean isValidAmount(final String value) {

		return validateValueForRegex(ApplicationRegexConstant.REGEX_FOR_AMOUNT, value);
	}

	/**
	 * This <code>isValidAmountWithSemicolon</code> method is responsible to check whether the given value contains a valid value for Amount with semicolon
	 * separated.
	 *
	 * @param value
	 * @return
	 */
	public static Boolean isValidAmountWithSemicolon(final String value) {

		return validateValueForRegex(ApplicationRegexConstant.REGEX_FOR_AMOUNT_SEMICOLON, value);
	}

	/**
	 * This <code>isValidValueWithSemicolonAndDash</code> method is responsible to check whether the given value contains a valid value with dash and semicolon
	 * separated.
	 *
	 * @param value
	 * @return
	 */
	public static Boolean isValidValueWithSemicolonAndDash(final String value) {

		return validateValueForRegex(ApplicationRegexConstant.REGEX_FOR_ALPHA_NUMERIC_DASH_SEMICOLON, value);
	}

	/**
	 * This <code>isValidCurrency</code> method is responsible to check whether the given value contains a valid value for Currency.
	 *
	 * @param value
	 * @return
	 */
	public static Boolean isCapitalAlpha(final String value) {

		return validateValueForRegex(ApplicationRegexConstant.REGEX_FOR_ONLY_CAPITAL_CHAR, value);
	}

	/**
	 * This <code>isValidCardNumber</code> method is responsible to check whether the given value contains a valid value for Card Number.
	 *
	 * @param value
	 * @return
	 */
	public static Boolean isValidCardNumber(final String value) {

		return validateValueForRegex(ApplicationRegexConstant.REGEX_FOR_CARDNUMBER, value);
	}

	/**
	 * This <code>isValidBrowserUserAgent</code> method is responsible to check whether the given value contains a valid value for browserUserAgent.
	 *
	 * @param value
	 * @return
	 */
	public static Boolean isValidBrowserUserAgent(final String value) {

		return validateValueForRegex(ApplicationRegexConstant.REGEX_FOR_BROWSER_USER_AGENT, value);
	}

	/**
	 * This <code>isOnlyDate</code> method is responsible to check whether the given value contains a valid value for date.
	 *
	 * @param value
	 * @return
	 */
	public static Boolean isOnlyDate(final String value) {

		return validateValueForRegex(ApplicationRegexConstant.REGEX_FOR_DATE, value);
	}

	/**
	 * This <code>isValidCustomField</code> method is responsible to check whether the given value contains a valid value for custom fields.
	 *
	 * @param value
	 * @return
	 */
	public static Boolean isValidCustomField(final String value) {

		return validateValueForRegex(ApplicationRegexConstant.REGEX_FOR_CUSTOM_FIELDS, value);
	}

	/**
	 * This <code>isValidPassengerName</code> method is responsible to check whether the given value contains a valid value for passenger name.
	 *
	 * @param value
	 * @return
	 */
	public static Boolean isValidPassengerName(final String value) {

		return validateValueForRegex(ApplicationRegexConstant.REGEX_FOR_NAVITAIRE_FULLNAME, value);
	}

	/**
	 * This <code>isValidExpiryMonth</code> method is responsible to check whether the given value contains a valid value for expiry month.
	 *
	 * @param value
	 * @return
	 */
	public static Boolean isValidExpiryMonth(final String value) {

		return validateValueForRegex(ApplicationRegexConstant.REGEX_FOR_EXPIRY_MONTH, value);
	}

	/**
	 * This <code>isDoubleNumber</code> method is responsible to check whether the given value contains a valid value for exchange rate.
	 *
	 * @param value
	 * @return
	 */
	public static Boolean isDoubleNumber(final String value) {

		return validateValueForRegex(ApplicationRegexConstant.REGEX_FOR_DOUBLENUMBER, value);
	}

	/**
	 * This <code>isValidIpAddress</code> method is responsible to check whether the given value contains a valid value for IP Address.
	 *
	 * @param value
	 * @return
	 */
	public static Boolean isValidIpAddress(final String value) {

		return validateValueForRegex(ApplicationRegexConstant.REGEX_FOR_IP_ADDRESS, value.trim());
	}

	/**
	 * This <code>isValidAddress</code> method is responsible to check whether the given value contains a valid value for address.
	 *
	 * @param value
	 * @return
	 */
	public static Boolean isValidAddress(final String value) {

		return validateValueForRegex(ApplicationRegexConstant.REGEX_FOR_ADDRESS, value);
	}

	/**
	 * This <code>isValidEmail</code> method is responsible to check whether the given value contains a valid value for email.
	 *
	 * @param value
	 * @return
	 */
	public static Boolean isValidEmailNavitaire(final String value) {

		return validateValueForRegex(ApplicationRegexConstant.REGEX_FOR_NAVITAIRE_EMAIL, value);
	}

	/**
	 * This <code>isValidPhoneNumber</code> method is responsible to check whether the given value contains a valid value for Card-Holder Phone.
	 *
	 * @param value
	 * @return
	 */
	public static Boolean isValidPhoneNumber(final String value) {

		return validateValueForRegex(ApplicationRegexConstant.REGEX_FOR_CARDHOLDERPHONE, value.trim());
	}

	/**
	 * This method <code>isSemicolonSeparaterCharacter</code> is used for validate the semicolon separated character or not.
	 *
	 * @param value
	 * @return
	 */
	public static Boolean isSemicolonSeparaterCharacter(final String value) {

		return validateValueForRegex(ApplicationRegexConstant.REGEX_FOR_SEMICOLON_SEPARATED_VALUES, value.trim());
	}

	/**
	 * This <code>validateValueForRegex</code> method is responsible to validate the given value with the given regex.
	 *
	 * @param regex
	 * @param value
	 * @return
	 */
	public static Boolean validateValueForRegex(final String regex, final String value) {

		return Pattern.compile(regex).matcher(value).matches();
	}

	/**
	 * This <code>postRequest</code> is used for post request.
	 *
	 * @param url
	 * @param headers
	 * @param parameters
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static HttpResponse postRequest(final String url, final Map<String, String> headers, final Map<String, String> parameters)
			throws ClientProtocolException, IOException {

		LOGGER.info(" ==> Method : postRequest ==> Enter");

		final HttpPost httpPost = new HttpPost(url);

		if (headers != null && !headers.isEmpty()) {
			for (final Map.Entry<String, String> entry : headers.entrySet()) {
				httpPost.addHeader(entry.getKey(), entry.getValue());
			}
		}

		// Setting the URL Parameters
		final List<NameValuePair> formParams = new ArrayList<>();

		if (parameters != null && !parameters.isEmpty()) {
			for (final Map.Entry<String, String> entry : parameters.entrySet()) {
				formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}

		final UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, ApplicationConstants.UTF8);
		httpPost.setEntity(entity);

		final HttpClient httpclient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
		final HttpResponse httpResponse = httpclient.execute(httpPost);

		LOGGER.info(" ==> Method : postRequest ==> Exit");
		return httpResponse;
	}

	/**
	 * The <code>getRequest</code> method is used for call the get request and get the response.
	 *
	 * @param url
	 * @param headers
	 * @param parameters
	 * @return
	 */
	public static HttpResponse getRequest(final String url, final Map<String, String> headers) {

		LOGGER.info(" ==> Method : getRequest ==> Enter");

		HttpResponse httpResponse = null;

		try {

			final HttpGet httpGet = new HttpGet(url);

			if (headers != null && !headers.isEmpty()) {

				for (final Map.Entry<String, String> entry : headers.entrySet()) {
					httpGet.addHeader(entry.getKey(), entry.getValue());
				}
			}

			httpResponse = HttpClientBuilder.create().build().execute(httpGet);

		} catch (final IOException e) {
			LOGGER.error(" ==> Method : getRequest ==> IOException ==> " + e);
		}

		LOGGER.info(" ==> Method : getRequest ==> Exit");
		return httpResponse;
	}

	/**
	 * This method <code>postJSONRequest</code> is used for sending the JSON Request via HTTP POST Call.
	 *
	 * @param gatewayURL
	 * @param requestHeader
	 * @param jsonDataAsString
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static HttpResponse postJSONRequest(final String gatewayURL, final Map<String, String> requestHeader, final String jsonDataAsString)
			throws ClientProtocolException, IOException {

		LOGGER.info(" ==> Method : postJSONRequest ==> Enter");

		final HttpPost httpPost = new HttpPost(gatewayURL);

		if (requestHeader != null && !requestHeader.isEmpty()) {
			for (final Map.Entry<String, String> entry : requestHeader.entrySet()) {
				httpPost.addHeader(entry.getKey(), entry.getValue());
			}
		}

		// Posting JSON Data as String to the Payments URL
		httpPost.setEntity(new StringEntity(jsonDataAsString));

		final HttpClient httpclient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
		final HttpResponse httpResponse = httpclient.execute(httpPost);

		LOGGER.info(" ==> Method : postJSONRequest ==> Exit");
		return httpResponse;
	}

	/**
	 * This <code>isOnlyAlphaSpaceAndSpecialCharacter</code> method is responsible to check whether given value valid alphabet and white space and some special
	 * characters.
	 *
	 * @param value
	 * @return
	 */
	public static Boolean isOnlyAlphaSpaceAndSpecialCharacter(final String value) {

		return validateValueForRegex(ApplicationRegexConstant.REGEX_FOR_ONLYSPACE_CHAR_SPECIALCHARCTERS, value.trim());
	}

	/**
	 * This <code>isOnlyAlphaNumericSpaceAndSomeSpecialCharacters</code> method is responsible to check whether given value valid alphanumeric and white space
	 * and some special characters.
	 *
	 * @param value
	 * @return
	 */
	public static Boolean isOnlyAlphaNumericSpaceAndSomeSpecialCharacters(final String value) {

		return validateValueForRegex(ApplicationRegexConstant.REGEX_FOR_ONLYSPACE_ALPHANUMERIC_SOME_SPECIALCHARCTERS, value.trim());
	}

	/**
	 * This <code>isOnlyAlphaSpaceAndUnderScore</code> method is responsible to check whether given value valid alphabet and white space and under score.
	 *
	 * @param value
	 * @return
	 */
	public static Boolean isOnlyAlphaSpaceAndUnderScore(final String value) {

		return validateValueForRegex(ApplicationRegexConstant.REGEX_FOR_ALPHA_SPACE_UNDERSCORE, value.trim());
	}

	/**
	 * This <code>isOnlyAllSpecialCharactersSpaceAndAlphaNumeric</code> method is responsible to check whether given value valid alphanumeric and all special
	 * characters.
	 *
	 * @param value
	 * @return
	 */
	public static Boolean isOnlyAllSpecialCharactersSpaceAndAlphaNumeric(final String value) {

		return validateValueForRegex(ApplicationRegexConstant.REGEX_FOR_ALL_SPECIALCHARACTERS_ALPHANUMERIC_SPACE, value.trim());
	}

	/**
	 * This <code>isOnlyNaviatireNumber</code> method is responsible to check whether given value valid number or not.
	 *
	 * @param value
	 * @return
	 */
	public static Boolean isOnlyNaviatireNumber(final String value) {

		return validateValueForRegex(ApplicationRegexConstant.REGEX_FOR_NAVIATIRE_ONLYNUMBER, value.trim());
	}

	/**
	 * This <code>isOnlyAlphaNumericSpaceAndUnderScore</code> method is responsible to check whether given value valid alphanumeric and white space and
	 * underscore.
	 *
	 * @param value
	 * @return
	 */
	public static Boolean isOnlyAlphaNumericSpaceAndUnderScore(final String value) {

		return validateValueForRegex(ApplicationRegexConstant.REGEX_FOR_ALPHANUMERIC_SPACE_UNDERSCORE, value.trim());
	}

	/**
	 * This <code>isOnlyNumberAndSomeSpecialCharacters</code> method is responsible to check whether given value valid number and some special characters.
	 *
	 * @param value
	 * @return
	 */
	public static Boolean isOnlyNumberAndSomeSpecialCharacters(final String value) {

		return validateValueForRegex(ApplicationRegexConstant.REGEX_FOR_NUMBER_SOME_SPECIALCHARACTERS, value.trim());
	}

	/**
	 * This <code>isOnlyAlphaNumericAndLineBreak</code> method is responsible to check whether given value valid alphanumeric and line break(new Line).
	 *
	 * @param value
	 * @return
	 */
	public static Boolean isOnlyAlphaNumericAndLineBreak(final String value) {

		return validateValueForRegex(ApplicationRegexConstant.REGEX_FOR_ALPHANUMERIC_LINE_BREAK, value.trim());
	}

	/**
	 * This <code>isValidUrl</code> method is responsible to check whether given value valid url.
	 *
	 * @param value
	 * @return
	 */
	public static Boolean isValidUrl(final String value) {

		return validateValueForRegex(ApplicationRegexConstant.REGEX_FOR_URL, value.trim());
	}

	/**
	 * This <code>isOnlyAlphaSpaceAndDot</code> method is responsible to check whether given value valid alphabet and white space and Dot.
	 *
	 * @param value
	 * @return
	 */
	public static Boolean isOnlyAlphaSpaceAndDot(final String value) {

		return validateValueForRegex(ApplicationRegexConstant.REGEX_FOR_ALPHA_SPACE_DOT, value.trim());
	}

	/**
	 * This <code>isValidCity</code> method is responsible to check whether given value valid alphabet and white space and some special characters.
	 *
	 * @param value
	 * @return
	 */
	public static Boolean isValidCity(final String value) {

		return validateValueForRegex(ApplicationRegexConstant.REGEX_FOR_NAVITAIRE_CITY, value.trim());
	}

	/**
	 * This <code>isOnlyNumberAndHyphen</code> method is responsible to check whether given value is valid or not.
	 *
	 * @param value
	 * @return
	 */
	public static Boolean isOnlyNumberAndHyphen(final String value) {

		return validateValueForRegex(ApplicationRegexConstant.REGEX_FOR_ONLY_NUMBER_HYPHEN, value.trim());
	}

	/**
	 * This method <code>isValidLong</code> is responsible to check whether provided value is valid Long value or not.
	 *
	 * @param value
	 * @return
	 */
	public static boolean isValidLong(final String value) {

		LOGGER.info(" ==> Method : isValidLong ==> called");

		try {
			Long.valueOf(value);
			return true;
		} catch (final Exception e) {
			return false;
		}

	}

	/**
	 * This method <code>isValidDouble</code> is responsible to check whether provided value is valid Double value or not.
	 *
	 * @param value
	 * @return
	 */
	public static boolean isValidDouble(final String value) {

		try {

			final Double param = Double.valueOf(value);
			if (!isEmpty(param)) {
				return true;
			}

		} catch (final Exception e) {
			return false;
		}
		return false;
	}

	/**
	 * This method <code>isValidCardType</code> is responsible to validate card type
	 *
	 * @param value
	 * @return
	 */
	public static boolean isValidCardType(final String value) {

		return validateValueForRegex(ApplicationRegexConstant.REGEX_FOR_CARDTYPE, value.trim());
	}

	/**
	 * This method <code>isValidCurrencyOrCountryCode</code> is responsible to validate currency and country code.
	 *
	 * @param value
	 * @return
	 */
	public static boolean isValidCurrencyOrCountryCode(final String value) {

		return validateValueForRegex(ApplicationRegexConstant.REGEX_FOR_CARDNUMBER_CURRANCEY_CODE, value.trim());
	}

	/**
	 * This method <code>isValidHostedPaymentPage</code> is responsible to validate Hosted Payment Page value.
	 *
	 * @param value
	 * @return
	 */
	public static boolean isValidHostedPaymentPage(final String value) {

		return validateValueForRegex(ApplicationRegexConstant.REGEX_FOR_HOSTED_PAYMENT_PAGE, value.trim());
	}

	/**
	 * This method <code>isValidCountryCode</code> is responsible to validate currency and country code.
	 *
	 * @param value
	 * @return
	 */
	public static boolean isValidCountryCode(final String value) {

		return validateValueForRegex(ApplicationRegexConstant.REGEX_FOR_COUNTRY_CODE, value.trim());
	}

	/**
	 * This <code>encrypt</code> method is responsible to encrypt the given string using the given public key and generated private key.
	 *
	 * @param key
	 * @param code
	 * @return
	 */
	public static String encrypt(final String key, final String code) {

		return doCrypto(Cipher.ENCRYPT_MODE, key, code);
	}

	/**
	 * This <code>decrypt</code> method is responsible to decrypt the given string using the given public key and generated private key.
	 *
	 * @param key
	 * @param code
	 * @return
	 */
	public static String decrypt(final String key, final String code) {

		return doCrypto(Cipher.DECRYPT_MODE, key, code);
	}

	/**
	 * This <code>doCrypto</code> method is contains the common code for encrypt and decrypt.
	 *
	 * @param cipherMode
	 * @param key
	 * @param code
	 * @return
	 */
	private static String doCrypto(final int cipherMode, final String key, final String code) {

		LOGGER.info(" ==> Method : doCrypto ==> Enter");

		final Key secretKey = new SecretKeySpec(key.getBytes(), ApplicationConstants.ENCRYPT_DECRYPT_ALGO_NAME);
		Cipher cipher;
		byte[] outputBytes = null;
		String result = null;

		try {

			cipher = Cipher.getInstance(ApplicationConstants.ENCRYPT_DECRYPT_ALGO_NAME);
			cipher.init(cipherMode, secretKey);
			if (Cipher.DECRYPT_MODE == cipherMode) {
				outputBytes = cipher.doFinal(java.util.Base64.getMimeDecoder().decode(code.getBytes()));
			} else {
				outputBytes = cipher.doFinal(code.getBytes());
			}

		} catch (final Exception e) {
			LOGGER.error(" ==> Method : doCrypto ==> Exception ==> " + e);
		}

		if (outputBytes != null && outputBytes.length > 0) {
			if (Cipher.DECRYPT_MODE == cipherMode) {
				result = new String(outputBytes);
			} else {
				result = java.util.Base64.getMimeEncoder().encodeToString(outputBytes);
			}
		}

		LOGGER.info(" ==> Method : doCrypto ==> Exit");
		return result;
	}

	/**
	 * This method <Code>sortListByTimeStamp</Code> is used for sort the given list base on Time Stamp.
	 *
	 * @param paramList
	 * @param fieldName
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List sortListByTimeStamp(final List paramList, final String fieldName, final String dateFormate) {

		LOGGER.info(" ==> Method : before sortListByTimeStamp ==> Enter");

		try {

			paramList.sort((object1, object2) -> {

				final String fieldObject1Value = getFieldValueFromObject(object1, fieldName);
				final String fieldObject2Value = getFieldValueFromObject(object2, fieldName);

				if (!isEmpty(fieldObject1Value) && !isEmpty(fieldObject2Value)) {
					return toDate(fieldObject2Value, dateFormate).compareTo(toDate(fieldObject1Value, dateFormate));
				}
				return 0;
			});

		} catch (final Exception e) {
			LOGGER.error(" ==> Method : sortListByTimeStamp ==> Exception ==> " + e);
		}

		LOGGER.info(" ==> Method : sortListByTimeStamp ==> Exit");
		return paramList;
	}

	/**
	 * This method <Code>getFieldValueFromObject</Code> is used for get field value from object base on field name.
	 *
	 * @param object
	 * @param fieldName
	 * @return
	 */
	public static String getFieldValueFromObject(final Object object, final String fieldName) {

		LOGGER.info(" ==> Method : getFieldValueFromObject ==> Called");

		try {

			if (object != null) {

				final Field fieldObject = object.getClass().getDeclaredField(fieldName);
				fieldObject.setAccessible(Boolean.TRUE);

				if (fieldObject.get(object) != null) {
					return String.valueOf(fieldObject.get(object));
				} else {
					return "";
				}
			} else {
				return "";
			}
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			LOGGER.error(" ==> Method : getFieldValueFromObject ==> Exception ==> " + e);
			return "";
		}
	}

	/**
	 * This <code>isAlphaNumericUnderscoreAndDot</code> method is responsible to check whether given value contains only alphabet, numeric, underscore and dot.
	 *
	 * @param value
	 * @return
	 */
	public static boolean isAlphaUnderscoreAndDot(final String value) {

		return validateValueForRegex(ApplicationRegexConstant.REGEX_FOR_ALPHA_DOT_UNDERSCORE, value);
	}

	/**
	 * This <code>isAlphaNumericAndUnderscore</code> method is responsible to check whether given value contains only alphabet, numeric, underscore and dot.
	 *
	 * @param value
	 * @return
	 */
	public static boolean isAlphaNumericAndUnderscore(final String value) {

		return validateValueForRegex(ApplicationRegexConstant.REGEX_FOR_ALPHANUMERIC_UNDERSCORE, value);
	}

	/**
	 * This <code>isClassName</code> method is responsible to check whether given value contains a Class Name in <code>Optimiser</code> application.
	 *
	 * @param value
	 * @return
	 */
	public static boolean isClassName(final String value) {

		return validateValueForRegex(ApplicationRegexConstant.REGEX_FOR_CLASS_NAME, value);
	}

	/**
	 * This <code>isOnlyNumberWithThreeDigit</code> method is responsible to check whether given value valid three digit.
	 *
	 * @param value
	 * @return
	 */
	public static Boolean isOnlyNumberWithThreeDigit(final String value) {

		return validateValueForRegex(ApplicationRegexConstant.REGEX_FOR_THREE_DIGIT_LENGTH, value);
	}

	/**
	 * This method <code>getExpiryDateForAccessToken</code> is used for generate the expiry date with adding next 3 months.
	 *
	 * @return date
	 */
	public static String getExpiryDateForAccessToken(final String date) {

		LOGGER.info(" ==> Method : getExpiryDateForAccessToken ==> called");

		Calendar calendar = null;

		try {

			final Date paramDate = currentTimeStampFormat.parse(date);
			calendar = Calendar.getInstance();
			calendar.setTime(paramDate);
			calendar.add(Calendar.MONTH, 3);

		} catch (final ParseException e) {
			LOGGER.error(" ==> Method : getExpiryDateForAccessToken ==> Exception ==> " + e);
		}

		return currentTimeStampFormat.format(calendar.getTime());
	}

	/**
	 * This method <code>generateSecretKey</code> is used for generate the secret key for the user based on userId and userEmail.
	 *
	 * @param userId
	 * @param userEmail
	 * @return
	 */
	public static String generateSecretKey() {

		LOGGER.info(" ==> Method : generateSecretKey ==> called");

		String secretKey = "";
		final OAuthIssuerImpl oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());

		try {

			final StringBuilder key = new StringBuilder();
			key.append(ApplicationConstants.ENCRYPTION_PADDING_DATA);
			secretKey = ApplicationUtils.encrypt(key.substring(0, 16), oauthIssuerImpl.refreshToken());

		} catch (final OAuthSystemException e) {
			LOGGER.error(" ==> Method : generateSecretKey ==> Exception ==> " + e);
		}

		return secretKey;
	}

	/**
	 * This method <code>sendResponseToClient</code> is used for send the response to the client as POST call.
	 *
	 * @param response
	 * @param notificationUrl
	 */
	public static void sendResponseToClient(final Object response, final String notificationUrl) {

		LOGGER.info(" ==> Method : sendResponseToClient ==> Enter");

		if (response != null && !ApplicationUtils.isEmpty(notificationUrl)) {

			final Map<String, String> headers = new HashMap<>();
			headers.put(ApplicationConstants.CONTENT_TYPE_LABEL, ApplicationConstants.CONTENT_TYPE_JSON);

			try {
				ApplicationUtils.postJSONRequest(notificationUrl, headers, ApplicationUtils.generateJSONFromObject(response));
			} catch (final IOException e) {
				LOGGER.error(" ==> Method : sendResponseToClient ==> Exception ==> " + e);
			}
		}
		LOGGER.info(" ==> Method : sendResponseToClient ==> Exit");
	}

	/**
	 * This method <code>getCommonElements</code> is used for get the common element from list of collection.
	 *
	 * @param collections
	 * @return
	 */
	public static <T> Set<T> getCommonElements(final Collection<? extends Collection<T>> collections) {

		LOGGER.info(" ==> Method : getCommonElements ==> Enter");

		final Set<T> common = new LinkedHashSet<>();
		if (!collections.isEmpty()) {
			final Iterator<? extends Collection<T>> iterator = collections.iterator();
			common.addAll(iterator.next());
			while (iterator.hasNext()) {
				common.retainAll(iterator.next());
			}
		}

		LOGGER.info(" ==> Method : getCommonElements ==> Exit");
		return common;
	}

	/**
	 * This <code>getJsonObjectFromJsonString</code> method is used for get JSONObject from Json string.
	 *
	 * @param key
	 * @param jsonString
	 * @return
	 */
	public synchronized static JSONObject getJsonObjectFromJsonString(final String jsonString) {

		LOGGER.info(" ==> Method : getJsonObjectFromJsonString ==> Called");
		return new JSONObject(jsonString);
	}

	/**
	 * This <code>getKeyValueFromJsonObject</code> method is used for get key value from Json object.
	 *
	 * @param jsonObject
	 * @param key
	 * @return
	 */
	public synchronized static String getKeyValueFromJsonObject(final JSONObject jsonObject, final String findKey) {

		LOGGER.info(" ==> Method : getKeyValueFromJsonObject ==> Called");

		String finalValue = "";

		try {

			if (jsonObject == null) {
				return "";
			}

			@SuppressWarnings("unchecked")
			final Iterator<String> jsonObjectIterator = jsonObject.keys();
			final Map<String, Object> map = new HashMap<>();

			while (jsonObjectIterator.hasNext()) {

				final String key = jsonObjectIterator.next();
				map.put(key, jsonObject.get(key));
			}

			for (final Map.Entry<String, Object> entry : map.entrySet()) {

				final String key = entry.getKey();
				if (key.equalsIgnoreCase(findKey)) {
					return jsonObject.getString(findKey);
				}

				// read value
				final Object value = jsonObject.get(key);

				if (value instanceof JSONObject) {
					finalValue = getKeyValueFromJsonObject((JSONObject) value, findKey);
				}
			}

		} catch (final Exception e) {
			LOGGER.error(" ==> Method : getKeyValueFromJsonObject ==> Exception : " + e);
			throw new UnknownException(e);
		}

		return finalValue;
	}

	/**
	 * The method <code>isValidCronJobExpression</code> is used verify the cron expression.
	 *
	 * @param value
	 * @return
	 */
	public static Boolean isValidCronJobExpression(String value) {

		LOGGER.info(" ==> Method : isValidCronJobExpression ==> called");
		return CronExpression.isValidExpression(value.trim());
	}

}