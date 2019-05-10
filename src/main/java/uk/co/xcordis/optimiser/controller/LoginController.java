
/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import uk.co.xcordis.optimiser.dto.MenuDto;
import uk.co.xcordis.optimiser.model.ApplicationUser;
import uk.co.xcordis.optimiser.model.Menu;
import uk.co.xcordis.optimiser.util.ApplicationConstants;
import uk.co.xcordis.optimiser.util.ApplicationTableConstants;
import uk.co.xcordis.optimiser.util.ApplicationURIConstants;
import uk.co.xcordis.optimiser.util.ApplicationUtils;
import uk.co.xcordis.optimiser.util.CommonConfigParam;
import uk.co.xcordis.optimiser.util.ErrorDataEnum;

/**
 * The <code>LoginController</code> class responsible for handle all the login related thing like authentication in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Controller
@DependsOn(ApplicationConstants.COMMON_CONFIG_PARAMS)
public class LoginController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private CommonConfigParam commonConfigParam;

	/**
	 * This <code>loginWithOpenId</code> method is use to check access token is store into the cookies or not
	 *
	 * @param servletRequest
	 * @param model
	 * @return
	 */
	@GetMapping(value = ApplicationURIConstants.LOGIN_LOGINWITHOPENID_URL)
	public String loginWithOpenId(final HttpServletRequest servletRequest, final Model model) {

		LOGGER.info(" ==> Method ==> loginWithOpenId ==> Enter");

		String viewName = ApplicationConstants.REDIRECT_LABLE + ApplicationURIConstants.LOGIN_GETLOGINCODE_URL;

		try {

			final Cookie[] cookies = servletRequest.getCookies();
			String accessToken = null;

			if (cookies != null && cookies.length > 0) {
				for (final Cookie cookie : cookies) {

					if (ApplicationConstants.ACCESSTOKEN_LABEL.equalsIgnoreCase(cookie.getName())) {

						final String secret = cookie.getValue();
						if (!ApplicationUtils.isEmpty(secret)) {

							final String secretArray[] = secret.split("-");
							if (secretArray != null && secretArray.length == 3) {
								accessToken = secretArray[2];
								break;
							}
						}
					}
				}
			}

			if (!ApplicationUtils.isEmpty(accessToken)) {

				model.addAttribute(ApplicationConstants.STATUS_LABLE, ApplicationConstants.STATUS_SUCCESS);
				model.addAttribute(ApplicationConstants.ACCESSTOKEN_LABEL, accessToken);
				viewName = ApplicationConstants.REDIRECT_LABLE + ApplicationURIConstants.LOGIN_LOGINACCESSTOKEN_URL;
			}
		} catch (final Exception e) {
			LOGGER.error(" ==> Method ==> loginWithOpenId ==> Exception ==> " + e.getMessage(), e);
			model.addAttribute(ApplicationConstants.ERRORMESSAGE_LABLE, ApplicationConstants.COMMON_ERRORMESSAGE);
			viewName = ApplicationConstants.REDIRECT_LABLE + ApplicationURIConstants.LOGIN_OPENIDERRORRESPONSE_URL;
		}

		LOGGER.info(" ==> Method ==> loginWithOpenId ==> Exit");
		return viewName;
	}

	/**
	 * This <code>getLoginCode</code> method is use to get the authentication code from the OpenId application.
	 *
	 * @param servletRequest
	 * @param servletResponse
	 */
	@RequestMapping(value = ApplicationURIConstants.LOGIN_GETLOGINCODE_URL, method = { RequestMethod.GET, RequestMethod.POST })
	public void getLoginCode(final HttpServletRequest servletRequest, final HttpServletResponse servletResponse) {

		LOGGER.info(" ==> Method ==> getLoginCode ==> Enter");

		if (checkRequiredParamForOpenId(commonConfigParam)) {

			try {
				final OAuthClientRequest request = OAuthClientRequest.authorizationLocation(commonConfigParam.getOpenIdCodeURL())
						.setClientId(commonConfigParam.getClientId())
						.setRedirectURI(commonConfigParam.getAppURL() + ApplicationURIConstants.LOGIN_LOGINCODERESPONSE_URL)
						.setResponseType(ApplicationConstants.CODE_LABLE)
						.setParameter(ApplicationConstants.APPLICATIONNAME_LABLE, commonConfigParam.getApplicationName())
						.setParameter(ApplicationConstants.CLIENTSECRET_LABLE, commonConfigParam.getClientSecrate()).buildQueryMessage();

				servletResponse.sendRedirect(request.getLocationUri());

			} catch (final OAuthSystemException e) {
				LOGGER.error(" ==> Method ==> getLoginCode ==> Exception ==> " + e.getMessage(), e);
			} catch (final IOException e) {
				LOGGER.error(" ==> Method ==> getLoginCode ==> Exception ==> " + e.getMessage(), e);
			}
		}
		LOGGER.info(" ==> Method ==> getLoginCode ==> Exit");
	}

	/**
	 * This <code>loginCodeResponse</code> method is use to handle the authentication response and get the access token from the OpenId application.
	 *
	 * @param servletRequest
	 * @param servletResponse
	 * @param modelMap
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = ApplicationURIConstants.LOGIN_LOGINCODERESPONSE_URL, method = { RequestMethod.GET, RequestMethod.POST })
	public String loginCodeResponse(final HttpServletRequest servletRequest, final HttpServletResponse servletResponse, final Model modelMap)
			throws IOException {

		LOGGER.info(" ==> Method ==> loginCodeResponse ==> Enter");
		String accessToken = "";
		String viewName = ApplicationConstants.REDIRECT_LABLE + ApplicationURIConstants.LOGIN_LOGINACCESSTOKEN_URL;

		try {

			if (ApplicationConstants.STATUS_SUCCESS.equalsIgnoreCase(servletRequest.getParameter(ApplicationConstants.STATUS_LABLE))
					&& !ApplicationUtils.isEmpty(servletRequest.getParameter(ApplicationConstants.CODE_LABLE))
					&& checkRequiredParamForOpenId(commonConfigParam)) {

				final OAuthClientRequest request = OAuthClientRequest.tokenLocation(commonConfigParam.getOpenIdAccessTokenURL())
						.setGrantType(GrantType.AUTHORIZATION_CODE).setClientId(commonConfigParam.getClientId())
						.setClientSecret(commonConfigParam.getClientSecrate())
						.setRedirectURI(commonConfigParam.getAppURL() + ApplicationURIConstants.LOGIN_LOGINACCESSTOKEN_URL)
						.setCode(servletRequest.getParameter(ApplicationConstants.CODE_LABLE))
						.setParameter(ApplicationConstants.APPLICATIONNAME_LABLE, commonConfigParam.getApplicationName()).buildQueryMessage();

				final OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
				final OAuthAccessTokenResponse oauthResponse = oAuthClient.accessToken(request);
				accessToken = oauthResponse.getAccessToken();

				if (!ApplicationUtils.isEmpty(oauthResponse.getParam(ApplicationConstants.SECRET_LABLE))) {

					final Cookie cookie = new Cookie(ApplicationConstants.ACCESSTOKEN_LABEL, oauthResponse.getParam(ApplicationConstants.SECRET_LABLE));
					cookie.setMaxAge(60 * 60 * 12 * 30); // 30 day

					cookie.setSecure(true);
					servletResponse.addCookie(cookie);
				}

				if (ApplicationConstants.STATUS_SUCCESS.equalsIgnoreCase(servletRequest.getParameter(ApplicationConstants.STATUS_LABLE))
						&& !ApplicationUtils.isEmpty(accessToken)) {

					modelMap.addAttribute(ApplicationConstants.STATUS_LABLE, ApplicationConstants.STATUS_SUCCESS);
					modelMap.addAttribute(ApplicationConstants.ACCESSTOKEN_LABEL, accessToken);
				}
			} else {
				modelMap.addAttribute(ApplicationConstants.ERRORMESSAGE_LABLE, ApplicationConstants.COMMON_ERRORMESSAGE);
				viewName = ApplicationConstants.REDIRECT_LABLE + ApplicationURIConstants.LOGIN_OPENIDERRORRESPONSE_URL;
			}
		} catch (final OAuthProblemException e) {
			LOGGER.error(" ==> Method ==> loginCodeResponse ==> OAuthProblemException ==> " + e.getMessage(), e);
			modelMap.addAttribute(ApplicationConstants.ERRORMESSAGE_LABLE, ApplicationConstants.COMMON_ERRORMESSAGE);
			viewName = ApplicationConstants.REDIRECT_LABLE + ApplicationURIConstants.LOGIN_OPENIDERRORRESPONSE_URL;
		} catch (final OAuthSystemException e) {
			LOGGER.error(" ==> Method ==> loginCodeResponse ==> OAuthSystemException ==> " + e.getMessage(), e);
			modelMap.addAttribute(ApplicationConstants.ERRORMESSAGE_LABLE, ApplicationConstants.COMMON_ERRORMESSAGE);
			viewName = ApplicationConstants.REDIRECT_LABLE + ApplicationURIConstants.LOGIN_OPENIDERRORRESPONSE_URL;
		} catch (final Exception e) {
			LOGGER.error(" ==> Method ==> loginCodeResponse ==> Exception ==> " + e.getMessage(), e);
			modelMap.addAttribute(ApplicationConstants.ERRORMESSAGE_LABLE, ApplicationConstants.COMMON_ERRORMESSAGE);
			viewName = ApplicationConstants.REDIRECT_LABLE + ApplicationURIConstants.LOGIN_OPENIDERRORRESPONSE_URL;
		}

		LOGGER.info(" ==> Method ==> loginCodeResponse ==> Exit");
		return viewName;
	}

	/**
	 * This <code>loginAccessToken</code> method is use to make login request OpenId application with required parameters.
	 *
	 * @param servletRequest
	 * @param model
	 * @return
	 */
	@RequestMapping(value = ApplicationURIConstants.LOGIN_LOGINACCESSTOKEN_URL, method = { RequestMethod.POST, RequestMethod.GET })
	public String loginAccessToken(final HttpServletRequest servletRequest, final Model model) {

		LOGGER.info(" ==> Method ==> loginAccessToken ==> Enter");
		String viewName = ApplicationURIConstants.ACCESSTOKENREDIRECT_VIEW;

		try {

			if (ApplicationConstants.STATUS_SUCCESS.equalsIgnoreCase(servletRequest.getParameter(ApplicationConstants.STATUS_LABLE))
					&& !ApplicationUtils.isEmpty(servletRequest.getParameter(ApplicationConstants.ACCESSTOKEN_LABEL))
					&& commonConfigParam.getOpenIdLoginRequestURL() != null && !ApplicationUtils.isEmpty(commonConfigParam.getOpenIdLoginRequestURL())) {

				model.addAttribute(ApplicationConstants.ACCESSTOKEN_LABEL, servletRequest.getParameter(ApplicationConstants.ACCESSTOKEN_LABEL));
				model.addAttribute(ApplicationConstants.REDIRECTURL_LABLE,
						commonConfigParam.getAppURL() + ApplicationURIConstants.LOGIN_LOGINREQUESTRESPONSE_URL);
				model.addAttribute(ApplicationConstants.APPLICATIONNAME_LABLE, commonConfigParam.getApplicationName());
				model.addAttribute(ApplicationConstants.URL_LABEL, commonConfigParam.getOpenIdLoginRequestURL());
			} else {
				model.addAttribute(ApplicationConstants.ERRORMESSAGE_LABLE, ApplicationConstants.COMMON_ERRORMESSAGE);
				viewName = ApplicationConstants.REDIRECT_LABLE + ApplicationURIConstants.LOGIN_OPENIDERRORRESPONSE_URL;
			}

		} catch (final Exception e) {
			LOGGER.error(" ==> Method ==> loginAccessToken ==> Exception ==> " + e.getMessage(), e);
			viewName = ApplicationConstants.REDIRECT_LABLE + ApplicationURIConstants.LOGIN_OPENIDERRORRESPONSE_URL;
			return viewName;
		}

		LOGGER.info(" ==> Method ==> loginAccessToken ==> Exit");
		return viewName;
	}

	/**
	 * This <code>loginRequestResponse</code> method is use to handle login request's response from OpenId.
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping(value = ApplicationURIConstants.LOGIN_LOGINREQUESTRESPONSE_URL)
	public String loginRequestResponse(final HttpServletRequest request, final Model model) {

		LOGGER.info(" ==> Method ==> loginRequestResponse ==> Enter");

		String viewName = ApplicationConstants.REDIRECT_LABLE + ApplicationURIConstants.HOME;
		Boolean isCommonError = Boolean.FALSE;

		try {

			if (ApplicationConstants.STATUS_SUCCESS.equalsIgnoreCase(request.getParameter(ApplicationConstants.STATUS_LABLE))
					&& !ApplicationUtils.isEmpty(request.getParameter(ApplicationConstants.MERCHANTID_LABLE))) {

				final ApplicationUser applicationUser = getServiceRegistry().getUserService()
						.getUserByOpenId(String.valueOf(request.getParameter(ApplicationConstants.MERCHANTID_LABLE)));

				if (applicationUser != null && !ApplicationUtils.isEmpty(request.getParameter(ApplicationConstants.ACCESSTOKENLIST_LABEL))
						&& !ApplicationUtils.isEmpty(applicationUser.getOpenId())) {

					request.getSession().setAttribute(ApplicationTableConstants.TABLENAME_APPLICATIONUSER, applicationUser);
					request.getSession().setAttribute(ApplicationConstants.ACCESSTOKENLIST_LABEL,
							request.getParameter(ApplicationConstants.ACCESSTOKENLIST_LABEL));

					request.getSession().setAttribute(ApplicationConstants.ROLE_LABEL, applicationUser.getRole());

					request.getSession().setAttribute(ApplicationConstants.ACCESSTOKEN_LABEL,
							getServiceRegistry().getAccessTokenService().findById(applicationUser.getOpenId()));

					setMenuAccordingToUser(request);

					if (applicationUser.getFirstTimeLogin()) {

						if (!ApplicationUtils.isEmpty(applicationUser.getRole())
								&& (ApplicationConstants.ROLE_MERCHANT.equalsIgnoreCase(applicationUser.getRole())
										|| ApplicationConstants.ROLE_MERCHANT_MANAGER.equalsIgnoreCase(applicationUser.getRole()))) {

							request.getSession().setAttribute(ApplicationConstants.USER_OBJECT_LABEL, applicationUser);

							viewName = ApplicationConstants.REDIRECT_LABLE + ApplicationURIConstants.SIGNUP_OPTIMISER;
						}
					}

				} else {
					model.addAttribute(ApplicationConstants.ERRORMESSAGE_LABLE,
							getMessageSource().getMessage(ErrorDataEnum.OPTIMISER_USER_NOTEXIST.message(), null, null));
					viewName = ApplicationConstants.REDIRECT_LABLE + ApplicationURIConstants.LOGIN_OPENIDERRORRESPONSE_URL;
				}

			} else {
				isCommonError = Boolean.TRUE;
			}

			if (isCommonError) {
				model.addAttribute(ApplicationConstants.ERRORMESSAGE_LABLE, ApplicationConstants.COMMON_ERRORMESSAGE);
				viewName = ApplicationConstants.REDIRECT_LABLE + ApplicationURIConstants.LOGIN_OPENIDERRORRESPONSE_URL;
			}
		}

		catch (final Exception e) {
			LOGGER.error(" ==> Method ==> loginRequestResponse ==> Exception ==> " + e.getMessage(), e);
			model.addAttribute(ApplicationConstants.ERRORMESSAGE_LABLE, ApplicationConstants.COMMON_ERRORMESSAGE);
			viewName = ApplicationConstants.REDIRECT_LABLE + ApplicationURIConstants.LOGIN_OPENIDERRORRESPONSE_URL;
		}
		LOGGER.info(" ==> Method ==> loginRequestResponse ==> Exit");
		return viewName;
	}

	/**
	 * This <code>loginRequestResponse</code> method is use to handle all the error that will be send by the openId application into the login process.
	 *
	 * @param request
	 * @param model
	 * @return
	 */
	@GetMapping(value = ApplicationURIConstants.LOGIN_OPENIDERRORRESPONSE_URL)
	public ModelAndView openIdErrorResponse(final HttpServletRequest request) {

		LOGGER.info(" ==> Method ==> openIdErrorResponse ==> Called");

		return new ModelAndView(ApplicationURIConstants.LOGIN_VIEW)
				.addObject(ApplicationConstants.STATUS_ERROR, request.getParameter(ApplicationConstants.ERRORMESSAGE_LABLE)).addObject(new ApplicationUser());
	}

	/**
	 * This <code>logout</code> method is use to logout the login merchant and invalidate all the session.
	 *
	 * @param request
	 * @return
	 */
	@GetMapping(value = ApplicationURIConstants.LOGOUT_URL)
	public ModelAndView logout(final HttpServletRequest request) {

		LOGGER.info(" ==> Method ==> logout ==> called");

		request.getSession().invalidate();

		// TODO: Below code used for logout from openId application, if keep only above one line {invalidate() session} then logout from only Optimiser.
		final ModelAndView modelAndView = new ModelAndView(ApplicationURIConstants.LOGOUTREDIRECT_VIEW);

		if (!ApplicationUtils.isEmpty(commonConfigParam.getOpenidURL()) && !ApplicationUtils.isEmpty(commonConfigParam.getAppURL())) {
			modelAndView.addObject(ApplicationConstants.REDIRECTURL_LABLE, commonConfigParam.getAppURL() + ApplicationURIConstants.LOGIN_URL);
			modelAndView.addObject(ApplicationConstants.URL_LABEL, commonConfigParam.getOpenidURL() + ApplicationURIConstants.LOGOUT_URL);
		}

		LOGGER.info(" ==> Method ==> logout ==> Exit");
		return modelAndView;
	}

	/**
	 * This <code>setMenuAccordingToUser</code> method is use to set the menu according to user.
	 *
	 * @param request
	 */
	private void setMenuAccordingToUser(final HttpServletRequest request) {

		LOGGER.info(" ==> Method ==> setMenuAccordingToUser ==> Enter");

		final String accessTokenList = request.getParameter(ApplicationConstants.ACCESSTOKENLIST_LABEL);

		List<Menu> optimiserMenuList;

		final List<Menu> parentMenuList = new ArrayList<>();

		final List<Menu> childMenuList = new ArrayList<>();

		if (!ApplicationUtils.isEmpty(accessTokenList)) {

			final List<MenuDto> menuDtoList = new ArrayList<>();
			optimiserMenuList = getServiceRegistry().getMenuService().getMenuListByAccessToken(accessTokenList);

			if (ApplicationUtils.isValid(optimiserMenuList)) {

				optimiserMenuList.forEach(menu -> {
					if (menu.getIsParent()) {
						parentMenuList.add(menu);
					} else {
						childMenuList.add(menu);
					}
				});

				parentMenuList.forEach(parentMenu -> {

					final MenuDto menuDto = new MenuDto();
					BeanUtils.copyProperties(parentMenu, menuDto);
					menuDto.setMenuList(new ArrayList<Menu>());

					childMenuList.forEach(menu -> {

						if (menu.getParentMenuId() != null && parentMenu.getMenuId().equals(menu.getParentMenuId())) {
							menuDto.getMenuList().add(menu);
						}
					});
					menuDtoList.add(menuDto);
				});
			}

			request.getSession().setAttribute(ApplicationConstants.MENU_LIST, menuDtoList);
		}

		LOGGER.info(" ==> Method ==> setMenuAccordingToUser ==> Exit");
	}

	/**
	 * This <code>checkRequiredParamForOpenId</code> method is use to check all the required parameters to request for the OpenId login request.
	 *
	 * @param context
	 * @return
	 */
	public boolean checkRequiredParamForOpenId(final CommonConfigParam openIdConfig) {

		LOGGER.info(" ==> Method ==> checkRequiredParamForOpenId ==> Enter");
		Boolean result = Boolean.FALSE;

		if (openIdConfig.getOpenIdCodeURL() != null && openIdConfig.getClientId() != null && openIdConfig.getClientSecrate() != null
				&& openIdConfig.getApplicationName() != null && openIdConfig.getOpenIdAccessTokenURL() != null
				&& openIdConfig.getOpenIdLoginRequestURL() != null && openIdConfig.getAppURL() != null
				&& openIdConfig.getOpenIdGetAccessTokenListURL() != null) {

			if (!ApplicationUtils.isEmpty(openIdConfig.getOpenIdCodeURL()) && !ApplicationUtils.isEmpty(openIdConfig.getClientId())
					&& !ApplicationUtils.isEmpty(openIdConfig.getClientSecrate()) && !ApplicationUtils.isEmpty(openIdConfig.getApplicationName())
					&& !ApplicationUtils.isEmpty(openIdConfig.getOpenIdAccessTokenURL()) && !ApplicationUtils.isEmpty(openIdConfig.getOpenIdLoginRequestURL())
					&& !ApplicationUtils.isEmpty(openIdConfig.getAppURL()) && !ApplicationUtils.isEmpty(openIdConfig.getOpenIdGetAccessTokenListURL())) {

				result = Boolean.TRUE;
			}
		}

		LOGGER.info(" ==> Method ==> checkRequiredParamForOpenId ==> Exit");
		return result;
	}

	/**
	 * This <code>loginPage</code> method is use to redirect to the login page.
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = ApplicationURIConstants.LOGIN_LOGINPAGE_URL, method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView loginPage(final HttpServletRequest request) {

		LOGGER.info(" ==> Method ==> loginPage ==> Enter");

		ModelAndView redirectView = null;

		if (request.getSession().getAttribute(ApplicationTableConstants.TABLENAME_APPLICATIONUSER) != null) {

			redirectView = new ModelAndView(ApplicationURIConstants.REDIRECT_LABEL + ApplicationURIConstants.HOME);

		} else {

			redirectView = new ModelAndView(ApplicationURIConstants.LOGIN_VIEW);

			if (!ApplicationUtils.isEmpty(request.getParameter(ApplicationConstants.STATUS_LABLE))) {
				redirectView.addObject(ApplicationConstants.STATUS_SUCCESS, request.getParameter(ApplicationConstants.STATUS_LABLE));
			}
		}

		LOGGER.info(" ==> Method ==> loginPage ==> Exit");
		return redirectView.addObject(new ApplicationUser());
	}

}
