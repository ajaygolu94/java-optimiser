package uk.co.xcordis.optimiser.controller;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import uk.co.xcordis.optimiser.model.MerchantGateways;
import uk.co.xcordis.optimiser.util.ApplicationConstants;
import uk.co.xcordis.optimiser.util.ApplicationURIConstants;
import uk.co.xcordis.optimiser.util.ApplicationUtils;
import uk.co.xcordis.optimiser.util.ErrorDataEnum;

/**
 * The <code>MerchantGatewaysController</code> class responsible for handling all the Merchant Gateway related configuration in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Controller
public class MerchantGatewaysController extends BaseController implements ApplicationURIConstants, ApplicationConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(MerchantGatewaysController.class);

	/**
	 * This <code>loadMerchantGatewaysPage</code> method is used to load Merchant Gateways list page.
	 *
	 * @param merchantId
	 * @param code
	 * @return
	 */
	@GetMapping(LOAD_MERCHANT_GATEWAY_URL)
	public ModelAndView loadMerchantGatewaysPage(@PathVariable(value = MERCHANTID_LABLE, required = false) final UUID merchantId,
			@RequestParam(value = REQUESTPARAM_CODE_LABEL, required = false) final String code) {

		LOGGER.info(" ==> Method : loadMerchantGatewaysPage ==> Enter");

		final ModelAndView modelAndView = new ModelAndView(MERCHANT_GATEWAY_LIST_VIEW);

		try {

			if (merchantId != null) {

				modelAndView.addObject(ApplicationConstants.MERCHANTID_LABLE, merchantId);

				if (!ApplicationUtils.isEmpty(code)) {
					modelAndView.addObject(ApplicationConstants.STATUS_SUCCESS, getMessageByCode(code, MERCHANT_GATEWAY_MODULE_LABEL));
				}

			} else {
				modelAndView.addObject(ApplicationConstants.STATUS_ERROR,
						getMessageSource().getMessage(ErrorDataEnum.COMMON_BADREQUEST_ERROR_MESSAGE.message(), null, null));
				return modelAndView;
			}

		} catch (final Exception e) {

			logError(LOGGER, null, e, " ==> Method ==> loadMerchantGatewaysPage ==> Exception ==> ");
			modelAndView.addObject(ApplicationConstants.STATUS_ERROR, ApplicationConstants.COMMON_ERRORMESSAGE);
		}

		LOGGER.info(" ==> Method : loadMerchantGatewaysPage ==> Exit");
		return modelAndView;
	}

	/**
	 * This <code>loadAddMerchantGatewayPage</code> method is used to load the add Merchant Gateway details Page.
	 *
	 * @param merchantId
	 * @return
	 */
	@GetMapping(LOAD_ADD_MERCHANT_GATEWAY_URL)
	public ModelAndView loadAddMerchantGatewayPage(@PathVariable(value = MERCHANTID_LABLE, required = false) final UUID merchantId) {

		LOGGER.info(" ==> Method ==> loadAddMerchantGatewayPage ==> Enter");

		final ModelAndView modelAndView = new ModelAndView(ADD_MERCHANT_GATEWAY_VIEW);

		try {

			if (merchantId != null) {
				modelAndView.addObject(ApplicationConstants.MERCHANTID_LABLE, merchantId);
				modelAndView.addObject(PAYMENT_GATEWAY_LIST_LABEL, getServiceRegistry().getPaymentGatewaysService().getAllPaymentGateways());
				modelAndView.addObject(RULE_SELECTOR_KEYS_LIST_LABLE, getServiceRegistry().getRuleSelectorKeysService().getRuleSelectorKeysList());
			} else {
				modelAndView.addObject(ApplicationConstants.STATUS_ERROR,
						getMessageSource().getMessage(ErrorDataEnum.COMMON_BADREQUEST_ERROR_MESSAGE.message(), null, null));
				return modelAndView;
			}

		} catch (final Exception e) {

			logError(LOGGER, null, e, " ==> Method ==> loadAddMerchantGatewayPage ==> Exception ==> ");
			modelAndView.addObject(ApplicationConstants.STATUS_ERROR, ApplicationConstants.COMMON_ERRORMESSAGE);
		}

		LOGGER.info(" ==> Method ==> loadAddMerchantGatewayPage ==> Exit");
		return modelAndView;
	}

	/**
	 * This <code>loadEditMerchantGatewayPage</code> method is used to load the edit Merchant Gateway details Page.
	 *
	 * @param merchantGatewayId
	 * @return
	 */
	@GetMapping(EDIT_MERCHANT_GATEWAY_VIEW_URL)
	public ModelAndView loadEditMerchantGatewayPage(@PathVariable(value = MERCHANT_GATEWAY_ID_LABEL, required = false) final UUID merchantGatewayId) {

		LOGGER.info(" ==> Method ==> loadEditMerchantGatewayPage ==> Enter");

		final ModelAndView modelAndView = new ModelAndView(EDIT_MERCHANT_GATEWAY_VIEW);

		try {

			if (merchantGatewayId != null) {

				modelAndView.addObject(MERCHANT_GATEWAY_LABEL, getServiceRegistry().getMerchantGatewaysService().findById(merchantGatewayId));
				modelAndView.addObject(PAYMENT_GATEWAY_LIST_LABEL, getServiceRegistry().getPaymentGatewaysService().getAllPaymentGateways());
				modelAndView.addObject(RULE_SELECTOR_KEYS_LIST_LABLE, getServiceRegistry().getRuleSelectorKeysService().getRuleSelectorKeysList());

			} else {
				modelAndView.addObject(ApplicationConstants.STATUS_ERROR,
						getMessageSource().getMessage(ErrorDataEnum.COMMON_BADREQUEST_ERROR_MESSAGE.message(), null, null));
				return modelAndView;
			}

		} catch (final Exception e) {

			logError(LOGGER, null, e, " ==> Method ==> loadEditMerchantGatewayPage ==> Exception ==> ");
			modelAndView.addObject(ApplicationConstants.STATUS_ERROR, ApplicationConstants.COMMON_ERRORMESSAGE);
		}

		LOGGER.info(" ==> Method ==> loadEditMerchantGatewayPage ==> Exit");
		return modelAndView;
	}

	/**
	 * This <code>loadViewMerchantGatewayPage</code> method is used to load the view Merchant Gateway Details Page.
	 *
	 * @param merchantGatewayId
	 * @return
	 */
	@GetMapping(VIEW_MERCHANT_GATEWAY_URL)
	public ModelAndView loadViewMerchantGatewayPage(@PathVariable(value = MERCHANT_GATEWAY_ID_LABEL, required = false) final UUID merchantGatewayId) {

		LOGGER.info(" ==> Method : loadViewMerchantGatewayPage ==> Enter");

		final ModelAndView modelAndView = new ModelAndView(ApplicationURIConstants.VIEW_MERCHANT_GATEWAY_VIEW);

		try {

			if (merchantGatewayId != null) {
				modelAndView.addObject(MERCHANT_GATEWAY_ID_LABEL, merchantGatewayId);
				modelAndView.addObject(MERCHANT_GATEWAY_LABEL, getServiceRegistry().getMerchantGatewaysService().findById(merchantGatewayId));
			} else {
				modelAndView.addObject(ApplicationConstants.STATUS_ERROR,
						getMessageSource().getMessage(ErrorDataEnum.COMMON_BADREQUEST_ERROR_MESSAGE.message(), null, null));
				return modelAndView;
			}

		} catch (final Exception e) {

			logError(LOGGER, null, e, " ==> Method ==> loadViewMerchantGatewayPage ==> Exception ==> ");
			modelAndView.addObject(ApplicationConstants.STATUS_ERROR, getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null));
		}

		LOGGER.info(" ==> Method : loadViewMerchantGatewayPage ==> Exit");
		return modelAndView;
	}


	/**
	 * This <code>loadViewSequencePage</code> method is used to load the view for changing the sequence of Merchant Gateways.
	 *
	 * @param merchantId
	 * @return
	 */
	@GetMapping(SEQUENCE_MERCHANT_GATEWAY_VIEW_URL)
	public ModelAndView loadViewSequencePage(@PathVariable(value = MERCHANTID_LABLE, required = false) final String merchantId) {

		LOGGER.info(" ==> Method : loadViewSequencePage ==> Enter");

		final ModelAndView modelAndView = new ModelAndView(SEQUENCE_MERCHANT_GATEWAY_VIEW);

		try {

			final List<MerchantGateways> merchantGatewaysList = getServiceRegistry().getMerchantGatewaysService().getMerchantGatewaysByMerchantId(
					UUID.fromString(merchantId));

			if (ApplicationUtils.isValid(merchantGatewaysList)) {

				// sorting the merchant gateway by sequence.
				merchantGatewaysList.sort(Comparator.comparing(MerchantGateways::getPaymentGatewaySequence));

				modelAndView.addObject(MERCHANT_GATEWAYS_LIST_OBJECT_LABEL, merchantGatewaysList);

			} else {
				modelAndView.addObject(ApplicationConstants.STATUS_ERROR,
						getMessageSource().getMessage(ErrorDataEnum.COMMON_MERCHANTGATEWAY_NOT_FOUND_ERROR_MESSAGE.message(), null, null));
			}

		} catch (final Exception e) {

			logError(LOGGER, null, e, " ==> Method ==> loadViewRequestDataPage ==> Exception ==> ");
			modelAndView.addObject(ApplicationConstants.STATUS_ERROR, getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null));
		}

		LOGGER.info(" ==> Method : loadViewSequencePage ==> Exit");
		return modelAndView;
	}
}