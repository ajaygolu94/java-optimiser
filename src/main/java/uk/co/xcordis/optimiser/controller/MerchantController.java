package uk.co.xcordis.optimiser.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import uk.co.xcordis.optimiser.model.ApplicationUser;
import uk.co.xcordis.optimiser.model.Merchant;
import uk.co.xcordis.optimiser.util.ApplicationConstants;
import uk.co.xcordis.optimiser.util.ApplicationTableConstants;
import uk.co.xcordis.optimiser.util.ApplicationURIConstants;
import uk.co.xcordis.optimiser.util.ApplicationUtils;
import uk.co.xcordis.optimiser.util.ErrorDataEnum;

/**
 * Handles requests for the application merchant page.
 */
@Controller
public class MerchantController extends BaseController implements ApplicationURIConstants, ApplicationConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(MerchantController.class);

	/**
	 * The method <code>merchantPage</code> is responsible for merchant page screen in Optimiser application.
	 *
	 * @param code
	 * @param request
	 * @param model
	 * @return
	 */
	@GetMapping(value = MERCHANT)
	public ModelAndView loadMerchantPage(@RequestParam(value = ApplicationConstants.CODE_LABLE, required = false) final String code,
			final HttpServletRequest request, final Model model) {

		LOGGER.info(" ==> Method : loadMerchantPage ==> called");

		final ModelAndView modelAndView = new ModelAndView(MERCHANT_VIEW);

		if (!ApplicationUtils.isEmpty(code)) {
			modelAndView.addObject(STATUS_SUCCESS, getMessageByCode(code, ApplicationConstants.MERCHANT_MODULE_LABEL));
		}
		return modelAndView;
	}

	/**
	 * The method <code>loadMerchantAddPage</code> is responsible for load the merchant page screen in Optimiser application.
	 *
	 * @param request
	 * @param model
	 * @return
	 */
	@GetMapping(value = MERCHANT_ADD_VIEW)
	public ModelAndView loadMerchantAddPage(final HttpServletRequest request) {

		LOGGER.info(" ==> Method : loadMerchantAddPage ==> called");

		final ModelAndView modelAndView = new ModelAndView(REDIRECT_MERCHANT_ADD);

		try {

			final ApplicationUser user = (ApplicationUser) request.getSession().getAttribute(ApplicationTableConstants.TABLENAME_APPLICATIONUSER);

			if (user != null && !ApplicationUtils.isEmpty(user.getRole()) && ROLE_MERCHANT_MANAGER.equalsIgnoreCase(user.getRole())) {

				modelAndView.addObject(APPLICATION_USER_OBJECT_LABEL, getServiceRegistry().getUserService().getUserByManagerId(user.getUserId()));
			}

		} catch (final Exception e) {

			logError(LOGGER, null, e, " ==> Method ==> loadMerchantAddPage ==> Exception ==> ");
			modelAndView.addObject(ApplicationConstants.STATUS_ERROR, ApplicationConstants.COMMON_ERRORMESSAGE);
		}
		return modelAndView;
	}

	/**
	 * The method <code>loadMerchantViewPage</code> is responsible for load merchant view page screen in Optimiser application.
	 *
	 * @param merchantId
	 * @param model
	 * @return
	 */
	@GetMapping(value = REDIRECT_MERCHANT_VIEW)
	public ModelAndView loadMerchantViewPage(@PathVariable(value = ApplicationConstants.MERCHANTID_LABLE, required = false) final String merchantId,
			final ModelMap model) {

		LOGGER.info(" ==> Method : loadMerchantViewPage ==> called ");

		final ModelAndView modelAndView = new ModelAndView(MERCHANT_VIEW_VIEW);

		try {

			if (!ApplicationUtils.isEmpty(merchantId)) {
				modelAndView.addObject(ApplicationConstants.MERCHANTID_LABLE, merchantId);
			} else {

				modelAndView.addObject(ApplicationConstants.STATUS_ERROR,
						getMessageSource().getMessage(ErrorDataEnum.MERCHANT_COMMON_NOT_FOUND_ERROR_MESSAGE.message(), null, null));
				modelAndView.setViewName(MERCHANT);
				return modelAndView;
			}
		} catch (final Exception e) {

			logError(LOGGER, merchantId, e, " ==> Method ==> loadMerchantViewPage ==> Exception ==> ");
			model.addAttribute(ApplicationConstants.STATUS_ERROR, ApplicationConstants.COMMON_ERRORMESSAGE);
			return new ModelAndView(MERCHANT, model);
		}
		return modelAndView;
	}

	/**
	 * The method <code>merchantEditPage</code> is responsible for redirect to merchant edit page screen in Optimiser application.
	 *
	 * @param merchantId
	 * @param model
	 * @return
	 */
	@GetMapping(value = REDIRECT_MERCHANT_EDIT)
	public ModelAndView loadMerchantEditPage(@PathVariable(value = ApplicationConstants.MERCHANTID_LABLE, required = false) final String merchantId,
			final ModelMap model) {

		LOGGER.info(" ==> Method : loadMerchantEditPage ==> called ");

		final ModelAndView modelAndView = new ModelAndView(MERCHANT_EDIT_VIEW);

		try {

			if (!ApplicationUtils.isEmpty(merchantId)) {

				final Merchant merchant = getServiceRegistry().getMerchantService().getMerchantByMerchantId(UUID.fromString(merchantId));

				if (merchant != null) {
					modelAndView.addObject(ApplicationConstants.MERCHANT_OBJECT_LABEL, merchant);
					modelAndView.addObject(ApplicationConstants.USER_OBJECT_LABEL, getServiceRegistry().getUserService().getUserById(merchant.getUserId()));
				}

			} else {

				modelAndView.addObject(ApplicationConstants.STATUS_ERROR,
						getMessageSource().getMessage(ErrorDataEnum.MERCHANT_COMMON_NOT_FOUND_ERROR_MESSAGE.message(), null, null));
				modelAndView.setViewName(MERCHANT);
				return modelAndView;
			}
		} catch (final Exception e) {

			logError(LOGGER, merchantId, e, " ==> Method ==> loadMerchantEditPage ==> Exception ==> ");
			model.addAttribute(ApplicationConstants.STATUS_ERROR, ApplicationConstants.COMMON_ERRORMESSAGE);
			return new ModelAndView(MERCHANT, model);
		}
		return modelAndView;
	}

}
