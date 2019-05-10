/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import uk.co.xcordis.optimiser.dto.RegistrationResponse;
import uk.co.xcordis.optimiser.model.AccessToken;
import uk.co.xcordis.optimiser.model.ApplicationUser;
import uk.co.xcordis.optimiser.util.ApplicationConstants;
import uk.co.xcordis.optimiser.util.ApplicationTableConstants;
import uk.co.xcordis.optimiser.util.ApplicationURIConstants;
import uk.co.xcordis.optimiser.util.ApplicationUtils;
import uk.co.xcordis.optimiser.util.CommonConfigParam;
import uk.co.xcordis.optimiser.util.ErrorDataEnum;

/**
 * The <code>ApplicationUserController</code> class responsible for handle all the User related like add User details in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Controller
@PropertySource(ApplicationConstants.PROJECT_PROPERTY_FILE_CLASSPATH)
public class ApplicationUserController extends BaseController implements ApplicationURIConstants, ApplicationConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationUserController.class);

	@Autowired
	private CommonConfigParam commonConfigParam;

	/**
	 * This method <code>signupWithOpenId</code> is used for redirect to OpenID SignUp screen for User registration process.
	 *
	 * @return
	 */
	@RequestMapping(value = ApplicationURIConstants.SIGNUP_WITH_OPENID_URL, method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView signupWithOpenId(final RedirectAttributes redirectAttributes) {

		LOGGER.info(" ==> Method ==> signupWithOpenId ==> Enter");

		final ModelAndView modelAndView = new ModelAndView(ApplicationURIConstants.SIGNUP_REDIRECT_VIEW);

		try {

			if (!ApplicationUtils.isEmpty(commonConfigParam.getOpenidURL()) && !ApplicationUtils.isEmpty(commonConfigParam.getApplicationName())
					&& !ApplicationUtils.isEmpty(commonConfigParam.getAppURL())) {

				modelAndView.addObject(URL_LABEL, commonConfigParam.getOpenidURL() + ApplicationURIConstants.OPENID_SIGNUP_REQUEST_URL);
				modelAndView.addObject(REDIRECTURL_LABLE, commonConfigParam.getAppURL() + ApplicationURIConstants.LOGIN_URL);
				modelAndView.addObject(ADD_MERCHANT_URL_LABEL, commonConfigParam.getAppURL() + ApplicationURIConstants.ADD_MERCHANT_DETAILS_URL);
				modelAndView.addObject(APPLICATIONNAME_LABLE, commonConfigParam.getApplicationName());

			} else {

				redirectAttributes.addFlashAttribute(STATUS_ERROR,
						getMessageSource().getMessage(ErrorDataEnum.SIGNUP_CONNECTION_TO_OPENID_ERROR.message(), null, null));
				modelAndView.setViewName(REDIRECT_LABLE + LOGIN_LOGINPAGE_URL);
			}

		} catch (final Exception e) {
			LOGGER.error(" ==> Method ==> signupWithOpenId ==> Exception : " + e);
			redirectAttributes.addFlashAttribute(STATUS_ERROR,
					getMessageSource().getMessage(ErrorDataEnum.SIGNUP_CONNECTION_TO_OPENID_ERROR.message(), null, null));
			modelAndView.setViewName(REDIRECT_LABLE + LOGIN_LOGINPAGE_URL);
		}

		LOGGER.info(" ==> Method ==> signupWithOpenId ==> Exit");
		return modelAndView;
	}

	/**
	 * This method <code>addMerchantDetailsByOpenId</code> is used for store the Merchant registration details into Optimiser database.
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = ApplicationURIConstants.MERCHANT_ADD_MERCHANTDETAILS_BY_OPENID, method = RequestMethod.POST)
	@ResponseBody
	public RegistrationResponse addMerchantDetailsByOpenId(final HttpServletRequest request) {

		LOGGER.info(" ==> Method ==> addMerchantDetailsByOpenId ==> Enter");

		final RegistrationResponse response = new RegistrationResponse();

		try {

			final ErrorDataEnum.ErrorDataEnumUser errorDataEnum = validateUserData(request);

			if (errorDataEnum != null) {

				response.setStatus(ApplicationConstants.STATUS_ERROR);
				response.setErrorCode(errorDataEnum.code());
				response.setErrorMessage(errorDataEnum.message());

			} else {

				final Map<String, String> data = populateRequiredDataFromRequest(request.getParameter(ApplicationTableConstants.TABLENAME_MERCHANT));

				final ApplicationUser applicationUser = new ApplicationUser();
				applicationUser.setOpenId(data.get(MERCHANTID_LABLE));
				applicationUser.setEmail(data.get(EMAIL_LABEL));
				applicationUser.setName(data.get(MERCHANT_NAME));
				applicationUser.setRole(data.get(ROLE_LABEL));

				getServiceRegistry().getUserService().saveUserByOpenId(applicationUser);

				// Generate access token for register new User and add it to "AccessToken" table
				final AccessToken accessToken = new AccessToken();
				accessToken.setOpenid(applicationUser.getOpenId());
				getServiceRegistry().getAccessTokenService().addAccessToken(accessToken);

				response.setStatus(ApplicationConstants.STATUS_SUCCESS);
				response.setErrorCode(null);
				response.setErrorMessage(null);
			}

		} catch (final Exception e) {
			LOGGER.error(" ==> Method ==> addMerchantDetailsByOpenId ==> Exception ==> " + e);
			response.setStatus(ApplicationConstants.STATUS_ERROR);
			response.setErrorCode(ApplicationConstants.RESPONSE_CODE_100);
			response.setErrorMessage(ApplicationConstants.COMMON_ERRORMESSAGE);
		}

		LOGGER.info(" ==> Method ==> addMerchantDetailsByOpenId ==> Exit");
		return response;
	}

	/**
	 * This <code>userSignUp</code> method is use to redirect to the signup page of optimiser.
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = ApplicationURIConstants.SIGNUP_OPTIMISER, method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView userSignUp(@SessionAttribute final ApplicationUser user, final RedirectAttributes redirectAttributes) {

		LOGGER.info(" ==> Method ==> userSignUp ==> Enter");

		final ModelAndView modelAndView = new ModelAndView(ApplicationURIConstants.SIGNUP_OPTIMISER_PAGE);

		try {

			if (user != null) {

				modelAndView.addObject(ApplicationConstants.USER_OBJECT_LABEL, user);

				if (ApplicationConstants.ROLE_MERCHANT.equalsIgnoreCase(user.getRole())) {

					modelAndView.addObject(OBJ_MERCHANT_MANAGER_LIST,
							getServiceRegistry().getUserService().getNotAssignedMerchantManagerUserList(ROLE_MERCHANT_MANAGER));

				}
			}
		} catch (final Exception e) {

			LOGGER.error(" ==> Method ==> userSignUp ==> Exception : " + e);
			redirectAttributes.addFlashAttribute(STATUS_ERROR, getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null));
			modelAndView.setViewName(REDIRECT_LABLE + LOGIN_LOGINPAGE_URL);

		}
		LOGGER.info(" ==> Method ==> userSignUp ==> Exit");

		return modelAndView;
	}

	/**
	 * This <code>updateUserDetails</code> method is use to update the registration data of the user.
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = ApplicationURIConstants.UPDATE_USER_DETAILS, method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView updateUserDetails(@ModelAttribute final ApplicationUser applicationUser) {

		LOGGER.info(" ==> Method ==> updateUserDetails ==> Enter");

		ModelAndView modelAndView = new ModelAndView();

		try {

			List<String> errors = validateApplicationUser(applicationUser);
			if (!ApplicationUtils.isValid(errors)) {

				modelAndView.setViewName(HOME_VIEW);
				getServiceRegistry().getUserService().updateUserData(applicationUser);

			} else {
				modelAndView.addObject(ApplicationConstants.ERRORMSG_LABLE, errors);
				modelAndView.addObject(ApplicationConstants.USER_OBJECT_LABEL, applicationUser);
				modelAndView.setViewName(ApplicationURIConstants.SIGNUP_OPTIMISER_PAGE);
			}

		} catch (final Exception e) {
			LOGGER.error(" ==> Method ==> updateUserDetails ==> Exception ==> " + e);
		}

		LOGGER.info(" ==> Method ==> updateUserDetails ==> Exit");
		return modelAndView;
	}

	/**
	 * This <code>validateUserData</code> method is use to verify the each and every parameter required for add the user details.
	 *
	 * @param request
	 * @return
	 */
	public ErrorDataEnum.ErrorDataEnumUser validateUserData(final HttpServletRequest request) {

		LOGGER.info(" ==> Method ==> validateUserData ==> Called");

		if (ApplicationUtils.isEmpty(request.getParameter(ApplicationTableConstants.TABLENAME_MERCHANT))) {
			return ErrorDataEnum.ErrorDataEnumUser.REGISTRATION_USERDATA_INVALID;
		} else {

			final String merchantData = request.getParameter(ApplicationTableConstants.TABLENAME_MERCHANT);

			final Map<String, String> data = populateRequiredDataFromRequest(merchantData);

			if (data == null) {
				return ErrorDataEnum.ErrorDataEnumUser.REGISTRATION_USERDATA_INVALID;
			} else {

				if (ApplicationUtils.isEmpty(data.get(ApplicationConstants.MERCHANTID_LABLE))) {
					return ErrorDataEnum.ErrorDataEnumUser.REGISTRATION_USER_OPEN_ID;
				} else if (ApplicationUtils.isEmpty(data.get(ApplicationConstants.EMAIL_LABEL))) {
					return ErrorDataEnum.ErrorDataEnumUser.REGISTRATION_EMAIL_INVALID;
				} else if (ApplicationUtils.isEmpty(data.get(ApplicationConstants.MERCHANT_NAME))) {
					return ErrorDataEnum.ErrorDataEnumUser.REGISTRATION_USER_NAME_INVALID;
				} else if (ApplicationUtils.isEmpty(data.get(ApplicationConstants.ROLE_LABEL))) {
					return ErrorDataEnum.ErrorDataEnumUser.REGISTRATION_ROLE_INVALID;
				} else if (getServiceRegistry().getUserService().getUserByOpenId(data.get(ApplicationConstants.MERCHANTID_LABLE)) != null) {
					return ErrorDataEnum.ErrorDataEnumUser.REGISTRATION_USER_EXIST;
				} else {
					return null;
				}
			}
		}
	}

	/**
	 * This method <code>populateRequiredDataFromRequest</code> is used for populate the each field and set to Map.
	 *
	 * @param merchantData
	 * @return
	 */
	private Map<String, String> populateRequiredDataFromRequest(final String merchantData) {

		LOGGER.info(" ==> Method ==> populateRequiredDataFromRequest ==> Enter");

		Map<String, String> data = null;
		final String tempData = ApplicationUtils.decrypt(ApplicationConstants.ENCRYPTION_PADDING_DATA, merchantData);

		if (!ApplicationUtils.isEmpty(tempData)) {

			final String merchantDataArray[] = tempData.split("--");

			if (merchantDataArray.length == 4) {

				data = new HashMap<>();
				data.put(ApplicationConstants.MERCHANTID_LABLE, merchantDataArray[0]);
				data.put(ApplicationConstants.EMAIL_LABEL, merchantDataArray[1]);
				data.put(ApplicationConstants.MERCHANT_NAME, merchantDataArray[2]);
				data.put(ApplicationConstants.ROLE_LABEL, merchantDataArray[3]);
			}
		}

		LOGGER.info(" ==> Method ==> populateRequiredDataFromRequest ==> Exit");
		return data;
	}

	/**
	 * This <code>loadUserProfile</code> method is used to load the view of User Profile Details Page.
	 *
	 * @param request
	 * @return
	 */
	@GetMapping(DETAILS_USERPROFILE_VIEW_URL)
	public ModelAndView loadUserProfile(final HttpServletRequest request) {

		LOGGER.info(" ==> Method : loadUserProfile ==> Enter");

		final ModelAndView modelAndView = new ModelAndView(DETAILS_USERPROFILE_VIEW);

		try {

			if (request.getSession().getAttribute(ApplicationTableConstants.TABLENAME_APPLICATIONUSER) != null) {

				final ApplicationUser user = (ApplicationUser) request.getSession().getAttribute(ApplicationTableConstants.TABLENAME_APPLICATIONUSER);

				if (user != null && user.getUserId() != null) {

					modelAndView.addObject(APPLICATION_USER_OBJECT_LABEL, getServiceRegistry().getUserService().getUserById(user.getUserId()));
				}
			}

		} catch (final Exception e) {
			LOGGER.error(" ==> Method ==> updateUserDetails ==> Exception ==> " + e);
		}

		LOGGER.info(" ==> Method : loadUserProfile ==> Exit");
		return modelAndView;
	}

	/**
	 * This <code>validateApplicationUser</code> method is used to validate the application user details.
	 *
	 * @param applicationUser
	 * @return
	 */
	private List<String> validateApplicationUser(final ApplicationUser applicationUser) {

		final List<String> errors = new ArrayList<>();

		LOGGER.info(" ==> Method ==> validateApplicationUser ==> Enter");

		if (ApplicationUtils.isEmpty(applicationUser.getName())) {
			errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_BLANK.message(), null, null)
					+ getMessageSource().getMessage(ErrorDataEnum.USER_USER_NAME_ERROR.message(), null, null));
		} else {
			if (!ApplicationUtils.isOnlyAlphaAndSpace(applicationUser.getName())) {
				errors.add(getMessageSource().getMessage(ErrorDataEnum.VALID_MESSAGE_ERROR_KEY.message(), null, null)
						+ getMessageSource().getMessage(ErrorDataEnum.USER_USER_NAME_ERROR.message(), null, null));
			}
		}

		if (ApplicationUtils.isEmpty(applicationUser.getContactNumber())) {
			errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_BLANK.message(), null, null)
					+ getMessageSource().getMessage(ErrorDataEnum.USER_CONTACT_NUMBER_ERROR.message(), null, null));
		} else {

			if (!ApplicationUtils.isPhoneNumber(applicationUser.getContactNumber())) {
				errors.add(getMessageSource().getMessage(ErrorDataEnum.VALID_MESSAGE_ERROR_KEY.message(), null, null)
						+ getMessageSource().getMessage(ErrorDataEnum.USER_CONTACT_NUMBER_ERROR.message(), null, null));
			}
		}

		LOGGER.info(" ==> Method ==> validateApplicationUser ==> Exit");

		return errors;
	}

}
