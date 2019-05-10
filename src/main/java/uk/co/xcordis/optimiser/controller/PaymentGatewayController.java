package uk.co.xcordis.optimiser.controller;

import java.util.Arrays;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import uk.co.xcordis.optimiser.dto.UIOperationResponse;
import uk.co.xcordis.optimiser.model.PaymentGateways;
import uk.co.xcordis.optimiser.util.ApplicationConstants;
import uk.co.xcordis.optimiser.util.ApplicationURIConstants;
import uk.co.xcordis.optimiser.util.ApplicationUtils;
import uk.co.xcordis.optimiser.util.ErrorDataEnum;

/**
 * Handles requests for the application payment gateway page.
 */
@Controller
public class PaymentGatewayController extends BaseController implements ApplicationURIConstants, ApplicationConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentGatewayController.class);

	/**
	 * The method <code>loadPaymentGatewayPage</code> is responsible for load payment gateway page screen in Optimiser application.
	 *
	 * @param request
	 * @param model
	 * @return
	 */
	@GetMapping(value = PAYMENT_GATEWAY)
	public ModelAndView loadPaymentGatewayPage(@RequestParam(value = ApplicationConstants.CODE_LABLE, required = false) String code,
			final HttpServletRequest request, final Model model) {

		LOGGER.info(" ==> Method : loadPaymentGatewayPage ==> called");

		ModelAndView modelAndView = new ModelAndView(PAYMENT_GATEWAY_VIEW);

		try {

			if (!ApplicationUtils.isEmpty(code)) {
				modelAndView.addObject(STATUS_SUCCESS, getMessageByCode(code, ApplicationConstants.PAYMENT_GATEWAY_MODULE_LABEL));
			}

		} catch (Exception e) {
			logError(LOGGER, null, e, " ==> Method ==> loadPaymentGatewayPage ==> Exception ==> ");
			commonErrorResponse(new UIOperationResponse(),
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return modelAndView;
	}

	/**
	 * The method <code>loadPaymentGatewayAddPage</code> is responsible for payment gateway add page screen in Optimiser application.
	 *
	 * @param request
	 * @param model
	 * @return
	 */
	@GetMapping(value = PAYMENT_GATEWAY_ADD_VIEW)
	public ModelAndView loadPaymentGatewayAddPage(final HttpServletRequest request, final Model model) {

		LOGGER.info(" ==> Method : loadPaymentGatewayAddPage ==> called");

		ModelAndView modelAndView = new ModelAndView();

		try {
			modelAndView.setViewName(REDIRECT_PAYMENT_GATEWAY_ADD);
			modelAndView.addObject(ApplicationConstants.PAYMENT_GATEWAY_OBJECT_LABEL, new PaymentGateways());

		} catch (Exception e) {

			logError(LOGGER, null, e, " ==> Method ==> loadPaymentGatewayAddPage ==> Exception ==> ");
			commonErrorResponse(new UIOperationResponse(),
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return modelAndView;
	}

	/**
	 * The method <code>loadPaymentGatewayEditPage</code> is responsible for redirect to payment gateway edit page screen in Optimiser application.
	 *
	 * @param paymentGatewayId
	 * @param model
	 * @return
	 */
	@GetMapping(value = REDIRECT_PAYMENT_GATEWAY_EDIT)
	public ModelAndView loadPaymentGatewayEditPage(@PathVariable(ApplicationConstants.PAYMENT_GATEWAY_ID) String paymentGatewayId, ModelMap model) {

		LOGGER.info(" ==> Method : loadPaymentGatewayEditPage ==> called ");

		ModelAndView modelAndView = new ModelAndView(PAYMENT_GATEWAY_EDIT_VIEW);

		try {

			if (!ApplicationUtils.isEmpty(paymentGatewayId)) {
				modelAndView.addObject(ApplicationConstants.PAYMENT_GATEWAY_ID, paymentGatewayId);

				PaymentGateways paymentGateways = getServiceRegistry().getPaymentGatewaysService().getPaymentGatewayById(paymentGatewayId);

				modelAndView.addObject(ApplicationConstants.PAYMENT_GATEWAY_OBJECT_LABEL, paymentGateways);

			}
		} catch (Exception e) {
			logError(LOGGER, null, e, " ==> Method ==> loadPaymentGatewayEditPage ==> Exception ==> ");
			commonErrorResponse(new UIOperationResponse(),
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);

		}
		return modelAndView;
	}

	/**
	 * The method <code>viewPaymentGatewayPage</code> is responsible for payment gateway view page screen in Optimiser application.
	 *
	 * @param paymentGatewayId
	 * @param model
	 * @return
	 */
	@GetMapping(value = REDIRECT_PAYMENT_GATEWAY_VIEW)
	public ModelAndView viewPaymentGatewayPage(@PathVariable(ApplicationConstants.PAYMENT_GATEWAY_ID) String paymentGatewayId, ModelMap model) {

		LOGGER.info(" ==> Method : viewPaymentGatewayPage ==> called ");

		ModelAndView modelAndView = new ModelAndView(PAYMENT_GATEWAY_VIEW_VIEW);

		try {

			if (!ApplicationUtils.isEmpty(paymentGatewayId)) {
				modelAndView.addObject(ApplicationConstants.PAYMENT_GATEWAY_ID, paymentGatewayId);
			} else {
				modelAndView.addObject(ApplicationConstants.STATUS_ERROR,
						getMessageSource().getMessage(ErrorDataEnum.PAYMENT_GATEWAY_COMMON_ERROR_MESSAGE.message(), null, null));
				modelAndView.setViewName(PAYMENT_GATEWAY);
				return modelAndView;
			}
		} catch (Exception e) {
			logError(LOGGER, paymentGatewayId, e, " ==> Method ==> viewPaymentGatewayPage ==> Exception ==> ");
			commonErrorResponse(new UIOperationResponse(),
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return modelAndView;
	}

}