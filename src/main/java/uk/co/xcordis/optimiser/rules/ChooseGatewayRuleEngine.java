package uk.co.xcordis.optimiser.rules;

import static org.jeasy.rules.core.RulesEngineBuilder.aNewRulesEngine;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import uk.co.xcordis.optimiser.controller.BaseController;
import uk.co.xcordis.optimiser.dto.ChooseGatewayRequest;
import uk.co.xcordis.optimiser.dto.ChooseGatewayResponse;
import uk.co.xcordis.optimiser.dto.ChooseGatewayRuleEngineDTO;
import uk.co.xcordis.optimiser.model.Merchant;
import uk.co.xcordis.optimiser.model.MerchantGateways;
import uk.co.xcordis.optimiser.model.MerchantRules;
import uk.co.xcordis.optimiser.model.RequestData;
import uk.co.xcordis.optimiser.util.ApplicationConstants;
import uk.co.xcordis.optimiser.util.ApplicationUtils;
import uk.co.xcordis.optimiser.util.ErrorDataEnum;
import uk.co.xcordis.optimiser.util.RequestResponseCodeEnum;
import uk.co.xcordis.optimiser.util.RequestResponseStatusEnum;

/**
 * The <code>ChooseGatewayRuleEngine</code> class is responsible setup rule engine for choose gateway base on merchant rules in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Repository
public class ChooseGatewayRuleEngine extends BaseController implements ApplicationConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(ChooseGatewayRuleEngine.class);

	/**
	 * This <code>setupRuleEngine</code> method is use to setup rule engine for choose gateway.
	 *
	 * @param gatewayRequest
	 */
	public ChooseGatewayResponse setupRuleEngine(final ChooseGatewayRequest gatewayRequest) {

		LOGGER.info(" ==> Method ==> setupRuleEngine ==> Enter");

		final StringBuilder commonErrorMessage = new StringBuilder(" ==> Method ==> setupRuleEngine").append(LOGERROR_EXCEPTION_LABEL);
		final ChooseGatewayResponse gatewayResponse = new ChooseGatewayResponse();
		Boolean isCommonError = Boolean.FALSE;

		try {

			final Merchant merchant = getServiceRegistry().getMerchantService().getMerchantBySourceMerchantId(gatewayRequest.getMerchantId());

			if (merchant != null && merchant.getMerchantId() != null) {

				final List<MerchantRules> merchantRules = getServiceRegistry().getMerchantRulesService().getMerchantRulesByMerchantId(merchant.getMerchantId());
				final List<MerchantGateways> merchantGateways = getServiceRegistry().getMerchantGatewaysService()
						.getMerchantGatewaysByMerchantId(merchant.getMerchantId());

				if (ApplicationUtils.isValid(merchantRules) && ApplicationUtils.isValid(merchantGateways)) {

					final ChooseGatewayRuleEngineDTO gatewayRuleEngineDTO = new ChooseGatewayRuleEngineDTO();
					BeanUtils.copyProperties(gatewayRequest, gatewayRuleEngineDTO);
					gatewayRuleEngineDTO.setMerchantGateways(merchantGateways);
					gatewayRuleEngineDTO.setMerchantRules(merchantRules);
					populateMerchantRulesAndSetupRuleEngine(gatewayRuleEngineDTO, merchantRules, gatewayResponse);

				} else {
					gatewayResponse.setCode(RequestResponseCodeEnum.CONFIGURATIONERROR.code());
					gatewayResponse.setMessages(
							new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.MERCHANT_CONFIGURATION_ERROR.message(), null, null))));
				}

			} else {
				isCommonError = Boolean.TRUE;
			}

		} catch (final Exception e) {
			logError(LOGGER, gatewayRequest.getMerchantId(), e, commonErrorMessage.toString());
			isCommonError = Boolean.TRUE;
		}

		if (isCommonError) {
			setCommonErrorResponse(gatewayResponse);
		}

		LOGGER.info(" ==> Method ==> setupRuleEngine ==> Exit");
		return gatewayResponse;
	}

	/**
	 * This <code>populateMerchantRulesAndSetupRuleEngine</code> method is use to populate merchant rules then setup rule engine for choose gateway.
	 *
	 * @param gatewayRuleEngineDTO
	 * @param merchantRules
	 * @param gatewayResponse
	 */
	private void populateMerchantRulesAndSetupRuleEngine(final ChooseGatewayRuleEngineDTO gatewayRuleEngineDTO, final List<MerchantRules> merchantRules,
			final ChooseGatewayResponse gatewayResponse) {

		LOGGER.info(" ==> Method ==> populateMerchantRulesAndSetupRuleEngine ==> Enter");

		final RulesEngine chooseGatewayRulesEngine = aNewRulesEngine().withSkipOnFirstAppliedRule(true).build();
		final Rules rules = new Rules();

		merchantRules.forEach(merchantRule -> {

			if (merchantRule != null && !ApplicationUtils.isEmpty(merchantRule.getRuleclass())
					&& !ApplicationUtils.isEmpty(merchantRule.getMerchantRulesSequence()) && !ApplicationUtils.isEmpty(merchantRule.getRulename())) {

				try {

					rules.register(Class.forName(merchantRule.getRuleclass()).getConstructor(Integer.class, UUID.class)
							.newInstance(merchantRule.getMerchantRulesSequence(), merchantRule.getMerchantRuleId()));

				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
						| SecurityException | ClassNotFoundException e) {
					logError(LOGGER, gatewayRuleEngineDTO.getMerchantId(), e, "  ==> Method ==> populateMerchantRulesAndSetupRuleEngine ==> Exception ==> ");
				}
			}
		});

		final RequestData requestData = getServiceRegistry().getRequestDataService().findById(gatewayRuleEngineDTO.getOptimiserRequestDataId());
		if (!rules.isEmpty() && requestData != null) {

			requestData.setResponseData(ApplicationUtils.generateJSONFromObject(gatewayRuleEngineDTO));
			getServiceRegistry().getRequestDataService().updateRequestData(requestData);

			final Facts chooseGatewayFacts = new Facts();
			chooseGatewayFacts.put(FACTS_REQUESTDATAID_LABEL, requestData.getRequestDataId());
			chooseGatewayFacts.put(SERVICEREGISTRY_LABEL, getServiceRegistry());
			chooseGatewayFacts.put(FACTS_MESSAGESOURCE_LABEL, getMessageSource());

			chooseGatewayRulesEngine.fire(rules, chooseGatewayFacts);
			populateResponseForRulesEngin(gatewayRuleEngineDTO, gatewayResponse);
		} else {
			setCommonErrorResponse(gatewayResponse);
		}

		LOGGER.info(" ==> Method ==> populateMerchantRulesAndSetupRuleEngine ==> Exit");
	}

	/**
	 * This <code>populateResponseForRulesEngin</code> method is use to populate and set response for choose gateway rules.
	 *
	 * @param gatewayRuleEngineDTO
	 * @param gatewayResponse
	 */
	private void populateResponseForRulesEngin(final ChooseGatewayRuleEngineDTO gatewayRuleEngineDTO, final ChooseGatewayResponse gatewayResponse) {

		LOGGER.info(" ==> Method ==> populateResponseForRulesEngin ==> Enter");

		final RequestData requestData = getServiceRegistry().getRequestDataService().findById(gatewayRuleEngineDTO.getOptimiserRequestDataId());
		Boolean isCommonError = Boolean.FALSE;

		if (requestData != null && !ApplicationUtils.isEmpty(requestData.getResponseData())) {

			ChooseGatewayResponse gatewayResponseOfRules = null;

			try {
				gatewayResponseOfRules = ApplicationUtils.generateObjectFromJSON(requestData.getResponseData(), ChooseGatewayResponse.class, Boolean.FALSE);
			} catch (final Exception e) {
				logError(LOGGER, gatewayRuleEngineDTO.getMerchantId(), e, "  ==> Method ==> populateResponseForRulesEngin ==> Exception ==> ");
				gatewayResponseOfRules = null;
			}

			if (gatewayResponseOfRules != null && !ApplicationUtils.isEmpty(gatewayResponseOfRules.getCode())) {
				BeanUtils.copyProperties(gatewayResponseOfRules, gatewayResponse);
			} else {

				gatewayResponseOfRules = setResponseFromChooseGatewayRuleEngineDTO(requestData.getResponseData(), gatewayRuleEngineDTO.getMerchantId());

				if (gatewayResponseOfRules != null) {
					BeanUtils.copyProperties(gatewayResponseOfRules, gatewayResponse);
				} else {
					isCommonError = Boolean.TRUE;
				}
			}

		} else {
			isCommonError = Boolean.TRUE;
		}

		if (isCommonError) {
			setCommonErrorResponse(gatewayResponse);
		}

		LOGGER.info(" ==> Method ==> populateResponseForRulesEngin ==> Exit");
	}

	/**
	 * This <code>setResponseFromChooseGatewayRuleEngineDTO</code> method is use to set choose gateway response base on the ChooseGatewayRuleEngineDTO object
	 * data.
	 *
	 * @param responseData
	 * @param merchantId
	 * @return
	 */
	private ChooseGatewayResponse setResponseFromChooseGatewayRuleEngineDTO(final String responseData, final String merchantId) {

		LOGGER.info(" ==> Method ==> setResponseFromChooseGatewayRuleEngineDTO ==> Called");

		ChooseGatewayRuleEngineDTO chooseGatewayRuleEngineDTO = null;
		try {
			chooseGatewayRuleEngineDTO = ApplicationUtils.generateObjectFromJSON(responseData, ChooseGatewayRuleEngineDTO.class, Boolean.FALSE);
		} catch (final Exception e) {
			logError(LOGGER, merchantId, e, "  ==> Method ==> setResponseFromChooseGatewayRuleEngineDTO ==> Exception ==> ");
			chooseGatewayRuleEngineDTO = null;
		}

		if (chooseGatewayRuleEngineDTO != null && !RequestResponseStatusEnum.FAILED.status().equalsIgnoreCase(chooseGatewayRuleEngineDTO.getStatus())) {

			MerchantGateways merchantGateway = null;

			if (ApplicationUtils.isValid(chooseGatewayRuleEngineDTO.getMerchantGateways())) {
				merchantGateway = chooseGatewayRuleEngineDTO.getMerchantGateways().get(0);
			} else if (chooseGatewayRuleEngineDTO.getSelectedMerchantGateway() != null) {
				merchantGateway = chooseGatewayRuleEngineDTO.getSelectedMerchantGateway();
			}

			if (merchantGateway != null) {

				final ChooseGatewayResponse gatewayResponse = new ChooseGatewayResponse();
				gatewayResponse.setMerchantGatewayId(merchantGateway.getSourceMerchantGatewayId());
				gatewayResponse.setGatewayName(merchantGateway.getGatewayName());
				gatewayResponse.setCode(RequestResponseCodeEnum.SUCCESS.code());
				return gatewayResponse;
			}
		}
		return null;
	}

	/**
	 * This <code>setCommonErrorResponse</code> method is use to set common error response
	 *
	 * @param gatewayResponse
	 */
	private void setCommonErrorResponse(final ChooseGatewayResponse gatewayResponse) {

		LOGGER.info(" ==> Method ==> setCommonErrorResponse ==> Called");

		gatewayResponse.setCode(RequestResponseCodeEnum.INTERNALSERVERERROR.code());
		gatewayResponse.setMessages(new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))));
	}
}
