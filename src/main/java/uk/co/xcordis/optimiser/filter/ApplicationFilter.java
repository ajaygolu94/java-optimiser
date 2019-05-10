package uk.co.xcordis.optimiser.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import uk.co.xcordis.optimiser.dto.UIOperationResponse;
import uk.co.xcordis.optimiser.util.ApplicationTableConstants;
import uk.co.xcordis.optimiser.util.ApplicationURIConstants;
import uk.co.xcordis.optimiser.util.OptimiserUtility;

/**
 * The <code>ApplicationFilter</code> class responsible to verify every request coming in <b>Optimiser</b> application for session or authentication.
 *
 * @author Rob Atkin
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApplicationFilter implements Filter, ApplicationURIConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationFilter.class);

	@Autowired
	private OptimiserUtility optimiserUtility;

	private final ArrayList<String> avoidUrlList = new ArrayList<>();
	private final ArrayList<String> restApiUrlList = new ArrayList<>();

	private static String[] urlsToSkip = { "/notification", CHOOSE_GATEWAY_API_URL, GENERATE_ACCESSTOKEN_API_URL, BATCH_DATA_GATEWAY, BATCH_DATA_MERCHANT,
			BATCH_DATA_MERCHANTGATEWAY, "/resources", "/login", "/loginwithopenid", "/signupwithopenid", "/logincode", "/logincoderesponse",
			"/loginaccesstoken", "/loginrequestresponse", "/openiderrorresponse", "/userregistration", "/adduser", "/logout", "loginController",
			"/addmerchantdetailbyopenid", "/forgotpassword", "/css", "/img", "/font-awesome", "/fonts", "/js" };

	private static String[] restApiUrls = {
			/* Merchant Module URL */
			MERCHANT_LIST_URL, MERCHANT_VIEW_URL, MERCHANT_ADD_URL, MERCHANT_EDIT_URL, MERCHANT_DELETE_URL,
			/* Merchant Gateway Module URL */
			MERCHANT_GATEWAY_LIST_URL, ADD_MERCHANT_GATEWAY_URL, EDIT_MERCHANT_GATEWAY_URL, DELETE_MERCHANT_GATEWAY_URL, DETAILS_MERCHANT_GATEWAY_URL,
			/* Payment Gateway Module URL */
			PAYMENT_GATEWAY_LIST_URL, PAYMENT_GATEWAY_VIEW_URL, PAYMENT_GATEWAY_ADD_URL, PAYMENT_GATEWAY_EDIT_URL, PAYMENT_GATEWAY_DELETE_URL,
			/* Rule Module URL */
			LIST_RULES_URL, ADD_RULE_URL, VIEW_RULE_URL, EDIT_RULE_URL, DELETE_RULE_URL, LIST_RULES_SELECTORKEYS_URL,
			/* RuleSelectorKey Module URL */
			LIST_RULESELECTORKEYS_URL, ADD_RULESELECTORKEY_URL, VIEW_RULESELECTORKEY_URL, EDIT_RULESELECTORKEY_URL,
			/* RequestData Module URL */
			LIST_REQUESTDATA_URL, VIEW_REQUESTDATA_URL,
			/* MerchantRules Module URL */
			VIEW_MERCHANTRULES_URL, LIST_MERCHANTRULES_URL, EDIT_MERCHANTRULES_URL, DELETE_MERCHANTRULES_URL, ADD_MERCHANTRULES_URL,
			/* Application Configuration module */
			LIST_APP_CONFIG_URL, DELETE_APP_CONFIG_URL, ADD_APP_CONFIG_URL, EDIT_APP_CONFIG_URL,
			/* Job Module */
			LIST_JOB_URL, DELETE_JOB_URL, ADD_JOB_URL, EDIT_JOB_URL, VIEW_JOB_URL, STATUS_JOB_URL,
			/* ErrorLog Module */
			LIST_ERROR_LOG_URL, VIEW_ERROR_LOG_URL };

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {

		LOGGER.info(" ==> Method : destroy ==> Called");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain chain)
			throws IOException, ServletException {

		final HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		final HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
		final HttpSession session = httpServletRequest.getSession();

		try {

			final String requestURI = httpServletRequest.getRequestURI();
			boolean allowedRequest = false;

			if ((httpServletRequest.getContextPath() + "/").equals(requestURI)) {
				allowedRequest = true;
			} else {
				avoidUrlLabel: for (final String avoidUrl : avoidUrlList) {

					if (requestURI.contains(avoidUrl)) {
						allowedRequest = true;
						break avoidUrlLabel;
					}
				}
			}

			if (!allowedRequest) {

				if (restApiRequestUrl(requestURI)) {

					final UIOperationResponse uiOperationResponse = new UIOperationResponse();

					if (optimiserUtility.authenticateRequest(httpServletRequest, uiOperationResponse)) {
						chain.doFilter(httpServletRequest, httpServletResponse);
					} else {
						final byte[] responseToSend = restResponseBytes(uiOperationResponse);
						httpServletResponse.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
						httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
						httpServletResponse.getOutputStream().write(responseToSend);
						return;
					}

				} else {

					if (session.getAttribute(ApplicationTableConstants.TABLENAME_APPLICATIONUSER) != null) {
						chain.doFilter(httpServletRequest, httpServletResponse);
					} else {
						httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + ApplicationURIConstants.LOGIN_LOGINPAGE_URL);
						return;
					}
				}

			} else {
				chain.doFilter(httpServletRequest, httpServletResponse);
			}

		} catch (final Exception e) {

			LOGGER.error(" ==> Method : doFilter ==> Exception ==> " + e);
			httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + ApplicationURIConstants.LOGIN_LOGINPAGE_URL);
			return;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(final FilterConfig filterConfig) throws ServletException {

		LOGGER.info(" ==> Method : init ==> Called");
		avoidUrlList.addAll(Arrays.asList(urlsToSkip));
		restApiUrlList.addAll(Arrays.asList(restApiUrls));
	}

	/**
	 * This method <code>restApiRequestUrl</code> is used for check the whether request url is exists or not.
	 *
	 * @param requestURI
	 * @return
	 */
	private Boolean restApiRequestUrl(final String requestURI) {

		Boolean allowedRequest = Boolean.FALSE;

		restApiUrlLabel: for (String restApiUrl : restApiUrlList) {

			if (restApiUrl.contains("{")) {
				restApiUrl = restApiUrl.substring(0, restApiUrl.indexOf("{"));
			}

			if (requestURI.contains(restApiUrl)) {
				allowedRequest = true;
				break restApiUrlLabel;
			}
		}
		return allowedRequest;
	}

	/**
	 * This method <code>restResponseBytes</code> is used for return the bytes from object.
	 *
	 * @param uiOperationResponse
	 * @return
	 * @throws IOException
	 */
	private byte[] restResponseBytes(final UIOperationResponse uiOperationResponse) throws IOException {

		final String serialized = new ObjectMapper().writeValueAsString(uiOperationResponse);
		return serialized.getBytes();
	}
}
