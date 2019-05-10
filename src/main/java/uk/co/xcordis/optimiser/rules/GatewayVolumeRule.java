package uk.co.xcordis.optimiser.rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Priority;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.xcordis.optimiser.dto.ChooseGatewayRuleEngineDTO;
import uk.co.xcordis.optimiser.model.MerchantGateways;
import uk.co.xcordis.optimiser.model.MerchantRules;
import uk.co.xcordis.optimiser.model.RequestData;
import uk.co.xcordis.optimiser.util.ApplicationConstants;
import uk.co.xcordis.optimiser.util.ApplicationUtils;
import uk.co.xcordis.optimiser.util.MerchantRuleParamKeyEnum;
import uk.co.xcordis.optimiser.util.RequestResponseStatusEnum;
import uk.co.xcordis.optimiser.util.ServiceRegistry;

/**
 * The <code>GatewayVolumeRule</code> class is one rule for check the gateway remaining volume of particular merchant in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Rule
public class GatewayVolumeRule implements ApplicationConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(GatewayVolumeRule.class);

	private int priority;
	private UUID merchantRuleId;
	private ServiceRegistry serviceRegistry;

	public GatewayVolumeRule() {

	}

	public GatewayVolumeRule(final Integer priority, final UUID merchantRuleId) {

		this.priority = priority.intValue();
		this.merchantRuleId = merchantRuleId;
	}

	/**
	 * This <code>checkGatewayVolume</code> method is use to check the gateway remaining volume of particular merchant.
	 *
	 * @param facts
	 * @return
	 */
	@Condition
	public boolean checkGatewayVolume(final Facts facts) {

		LOGGER.info(" ==> Method ==> checkGatewayVolume ==> Enter");

		Boolean result = Boolean.FALSE;
		final StringBuilder commonErrorMessage = new StringBuilder(" ==> Method ==> checkGatewayVolume ==> merchantRuleId ==> ").append(merchantRuleId)
				.append(LOGERROR_EXCEPTION_LABEL);
		String merchantId = "";

		try {

			if (facts.get(SERVICEREGISTRY_LABEL) != null) {
				serviceRegistry = (ServiceRegistry) facts.get(SERVICEREGISTRY_LABEL);
			} else {
				LOGGER.error(MARKER, commonErrorMessage.append(STOP_RULEENIGINE_SERVICEREGISTRY).toString());
				return Boolean.TRUE;
			}

			final Map<String, Object> rulesCommonOperationResponse = serviceRegistry.getRulesUtil().rulesCommonOperations(facts, merchantRuleId);

			if (serviceRegistry.getRulesUtil().checkRulesCommonOperationResponse(rulesCommonOperationResponse)) {

				merchantId = (String) rulesCommonOperationResponse.get(RULESCOMMONOPERATIONS_MERCHANTID_LABEL);
				final MerchantRules merchantRule = (MerchantRules) rulesCommonOperationResponse.get(RULESCOMMONOPERATIONS_MERCHANTRULES_LABEL);

				if (ApplicationUtils.isValid(merchantRule.getMerchantRuleParam())) {

					final ChooseGatewayRuleEngineDTO gatewayRuleEngineDTO = (ChooseGatewayRuleEngineDTO) rulesCommonOperationResponse
							.get(FACT_GATEWAYRULEENGINEDTO_LABEL);

					serviceRegistry.getRulesUtil().setRulesResultFromMerchantGatewayList(
							getAndCheckGatewayVolumeMerchantWise(merchantRule, gatewayRuleEngineDTO.getMerchantGateways(),
									Double.valueOf(gatewayRuleEngineDTO.getAmount())),
							gatewayRuleEngineDTO, (RequestData) rulesCommonOperationResponse.get(FACT_REQUESTDATA_LABEL), facts, Boolean.TRUE,
							merchantRule.getMerchantRuleId());
					result = gatewayRuleEngineDTO.getRuleResult();

				} else {
					serviceRegistry.getRulesUtil().logErrorForRules(LOGGER, merchantId, null,
							commonErrorMessage.append(SKIP_RULES_REQUIRED_PARAM_MISSING).toString());
				}
			} else {

				if (rulesCommonOperationResponse.get(RULESCOMMONOPERATIONS_RESULT_LABEL) != null) {
					result = (Boolean) rulesCommonOperationResponse.get(RULESCOMMONOPERATIONS_RESULT_LABEL);
				}
			}
		} catch (final Exception e) {
			serviceRegistry.getRulesUtil().logErrorForRules(LOGGER, merchantId, e, commonErrorMessage.toString());
		}

		LOGGER.info(" ==> Method ==> checkGatewayVolume ==> Exit");
		return result;
	}

	/**
	 * This <code>getAndCheckGatewayVolumeMerchantWise</code> method is use to get the volume and check with transaction amount merchant gateway wise.
	 *
	 * @param merchantRule
	 * @param merchantGateways
	 * @param transctionAmount
	 * @return
	 */
	private List<MerchantGateways> getAndCheckGatewayVolumeMerchantWise(final MerchantRules merchantRule, final List<MerchantGateways> merchantGateways,
			final Double transctionAmount) {

		LOGGER.info(" ==> Method ==> getAndCheckGatewayVolumeMerchantWise ==> Enter");

		final List<MerchantGateways> selectedMerchantGateways = new ArrayList<>();

		merchantGateways.forEach(merchantGateway -> {

			if (merchantGateway != null && !ApplicationUtils.isEmpty(merchantGateway.getSourceMerchantGatewayId()) && serviceRegistry.getRulesUtil()
					.validateMerchantRuleParam(merchantRule.getMerchantRuleParam(), MerchantRuleParamKeyEnum.GATEWAYVOLUMERULE.keys())) {

				final StringBuilder url = new StringBuilder(merchantRule.getMerchantRuleParam().get(COREVOLUMEURL_LABEL)).append("/")
						.append(merchantGateway.getSourceMerchantGatewayId());

				final Map<String, String> headers = new HashMap<>();
				headers.put(ACCESSTOKEN_LABEL, merchantRule.getMerchantRuleParam().get(COREACCESSTOKEN_LABEL));

				final Double volume = getVolumeFromReponseString(
						serviceRegistry.getRulesUtil().getResponseStringFromHttpResponse(ApplicationUtils.getRequest(url.toString(), headers)));

				if (!ApplicationUtils.isEmpty(volume) && volume >= transctionAmount) {
					selectedMerchantGateways.add(merchantGateway);
				}
			}
		});

		LOGGER.info(" ==> Method ==> getAndCheckGatewayVolumeMerchantWise ==> Enter");
		return selectedMerchantGateways;
	}

	/**
	 * This <code>getVolumeFromReponseString</code> method is use to get merchant gateway volume from response string.
	 *
	 * @param responseString
	 * @return
	 */
	private Double getVolumeFromReponseString(final String responseString) {

		LOGGER.info(" ==> Method ==> getVolumeFromReponseString ==> Enter");

		Double volume = null;

		if (!ApplicationUtils.isEmpty(responseString)) {

			final JSONObject jsonObject = serviceRegistry.getRulesUtil().getJsonObjectFromJsonString(responseString);
			final Object status = serviceRegistry.getRulesUtil().getKeyValueFromJsonObject(jsonObject, STATUS_LABLE);

			if (status != null && RequestResponseStatusEnum.SUCCESS.status().equalsIgnoreCase(String.valueOf(status))) {

				final Object volumeStringValue = serviceRegistry.getRulesUtil().getKeyValueFromJsonObject(jsonObject, MERCHANTGATEWAY_VOLUME_LABEL);

				if (volumeStringValue != null && !ApplicationUtils.isEmpty(String.valueOf(volumeStringValue))
						&& ApplicationUtils.isDoubleNumber(String.valueOf(volumeStringValue))) {

					volume = Double.valueOf(String.valueOf(volumeStringValue));
				}
			}
		}

		LOGGER.info(" ==> Method ==> getVolumeFromReponseString ==> Exit");
		return volume;
	}

	/**
	 * This <code>setRuleAction</code> method is use to set the repose for rule if rule success.
	 *
	 * @param facts
	 */
	@Action
	public void setRuleAction(final Facts facts) {

		LOGGER.info(" ==> Method ==> setRuleAction ==> Called");

		serviceRegistry.getRulesUtil().setRuleActionResponse(facts);
	}

	/**
	 * @return the priority
	 */
	@Priority
	public int getPriority() {

		return priority;
	}

	/**
	 * @return the merchantRuleId
	 */
	public UUID getMerchantRuleId() {

		return merchantRuleId;
	}
}
