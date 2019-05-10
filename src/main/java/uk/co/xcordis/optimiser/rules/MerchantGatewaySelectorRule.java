package uk.co.xcordis.optimiser.rules;

import java.util.Map;
import java.util.UUID;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Priority;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.xcordis.optimiser.dto.ChooseGatewayRuleEngineDTO;
import uk.co.xcordis.optimiser.model.MerchantRules;
import uk.co.xcordis.optimiser.model.RequestData;
import uk.co.xcordis.optimiser.util.ApplicationConstants;
import uk.co.xcordis.optimiser.util.ServiceRegistry;

/**
 * The <code>MerchantGatewaySelectorRule</code> class is one rule for choose merchant gateway base on merchant rule entry level criteria and merchant gateway
 * selector in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Rule
public class MerchantGatewaySelectorRule implements ApplicationConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(MerchantGatewaySelectorRule.class);

	private int priority;
	private UUID merchantRuleId;
	private ServiceRegistry serviceRegistry;

	public MerchantGatewaySelectorRule() {

	}

	public MerchantGatewaySelectorRule(final Integer priority, final UUID merchantRuleId) {

		this.priority = priority.intValue();
		this.merchantRuleId = merchantRuleId;
	}

	/**
	 * This <code>selectMerchantGatewayBySelector</code> method is use to select merchant gateways base on selector or criteria.
	 *
	 * @param facts
	 * @return
	 */
	@Condition
	public boolean selectMerchantGatewayBySelector(final Facts facts) {

		LOGGER.info(" ==> Method ==> selectMerchantGatewayBySelector ==> Enter ");

		final StringBuilder commonErrorMessage = new StringBuilder(" ==> Method ==> selectMerchantGatewayBySelector ==> merchantRuleId ==> ")
				.append(merchantRuleId).append(LOGERROR_EXCEPTION_LABEL);
		Boolean result = Boolean.FALSE;
		String merchantId = "";

		try {

			if (facts.get(ApplicationConstants.SERVICEREGISTRY_LABEL) != null) {
				serviceRegistry = (ServiceRegistry) facts.get(ApplicationConstants.SERVICEREGISTRY_LABEL);
			} else {
				LOGGER.error(MARKER, commonErrorMessage.append(STOP_RULEENIGINE_SERVICEREGISTRY).toString());
				return Boolean.TRUE;
			}

			final Map<String, Object> rulesCommonOperationResponse = serviceRegistry.getRulesUtil().rulesCommonOperations(facts, merchantRuleId);

			if (serviceRegistry.getRulesUtil().checkRulesCommonOperationResponse(rulesCommonOperationResponse)) {

				merchantId = (String) rulesCommonOperationResponse.get(RULESCOMMONOPERATIONS_MERCHANTID_LABEL);
				final MerchantRules merchantRule = (MerchantRules) rulesCommonOperationResponse.get(RULESCOMMONOPERATIONS_MERCHANTRULES_LABEL);
				final ChooseGatewayRuleEngineDTO gatewayRuleEngineDTO = (ChooseGatewayRuleEngineDTO) rulesCommonOperationResponse
						.get(FACT_GATEWAYRULEENGINEDTO_LABEL);

				serviceRegistry.getRulesUtil().setRulesResultFromMerchantGatewayList(
						serviceRegistry.getRulesUtil().selectMerchantGatewayBySelector(gatewayRuleEngineDTO, merchantRule), gatewayRuleEngineDTO,
						(RequestData) rulesCommonOperationResponse.get(FACT_REQUESTDATA_LABEL), facts, Boolean.FALSE, merchantRule.getMerchantRuleId());
				result = gatewayRuleEngineDTO.getRuleResult();

			} else {

				if (rulesCommonOperationResponse.get(RULESCOMMONOPERATIONS_RESULT_LABEL) != null) {
					result = (Boolean) rulesCommonOperationResponse.get(RULESCOMMONOPERATIONS_RESULT_LABEL);
				}
			}
		} catch (final Exception e) {
			serviceRegistry.getRulesUtil().logErrorForRules(LOGGER, merchantId, e, commonErrorMessage.toString());
		}

		LOGGER.info(" ==> Method ==> selectMerchantGatewayBySelector ==> Exit");
		return result;
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
