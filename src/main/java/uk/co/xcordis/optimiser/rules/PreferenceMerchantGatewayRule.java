package uk.co.xcordis.optimiser.rules;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Priority;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.xcordis.optimiser.dto.ChooseGatewayRuleEngineDTO;
import uk.co.xcordis.optimiser.model.MerchantGateways;
import uk.co.xcordis.optimiser.model.MerchantRules;
import uk.co.xcordis.optimiser.model.RequestData;
import uk.co.xcordis.optimiser.util.ApplicationConstants;
import uk.co.xcordis.optimiser.util.ApplicationUtils;
import uk.co.xcordis.optimiser.util.ServiceRegistry;

/**
 * The <code>PreferenceMerchantGatewayRule</code> class is one rule for select merchant gateway base on given preference in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Rule
public class PreferenceMerchantGatewayRule implements ApplicationConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(PreferenceMerchantGatewayRule.class);

	private int priority;
	private UUID merchantRuleId;
	private ServiceRegistry serviceRegistry;

	public PreferenceMerchantGatewayRule() {

	}

	public PreferenceMerchantGatewayRule(final Integer priority, final UUID merchantRuleId) {

		this.priority = priority.intValue();
		this.merchantRuleId = merchantRuleId;
	}

	/**
	 * This <code>selectMerchantGatewayByPreference</code> method is use to select merchant gateway base on given preference.
	 *
	 * @param facts
	 * @return
	 */
	@Condition
	public boolean selectMerchantGatewayByPreference(final Facts facts) {

		LOGGER.info(" ==> Method ==> selectMerchantGatewayByPreference ==> Enter");

		Boolean result = Boolean.FALSE;
		final StringBuilder commonErrorMessage = new StringBuilder(" ==> Method ==> selectMerchantGatewayByPreference ==> merchantRuleId ==> ")
				.append(merchantRuleId).append(LOGERROR_EXCEPTION_LABEL);
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
				final ChooseGatewayRuleEngineDTO gatewayRuleEngineDTO = (ChooseGatewayRuleEngineDTO) rulesCommonOperationResponse
						.get(FACT_GATEWAYRULEENGINEDTO_LABEL);
				final MerchantRules merchantRule = (MerchantRules) rulesCommonOperationResponse.get(RULESCOMMONOPERATIONS_MERCHANTRULES_LABEL);

				serviceRegistry.getRulesUtil().setRulesResultFromMerchantGatewayList(
						getMerchantGatewayByPreferencePriority(gatewayRuleEngineDTO.getMerchantGateways()), gatewayRuleEngineDTO,
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

		LOGGER.info(" ==> Method ==> selectMerchantGatewayByPreference ==> Exit");
		return result;
	}

	/**
	 * This <code>getMerchantGatewayByPreferencePriority</code> method is use to get merchant gateways by given preference.
	 *
	 * @param merchantGateways
	 * @return
	 */
	private List<MerchantGateways> getMerchantGatewayByPreferencePriority(final List<MerchantGateways> merchantGateways) {

		LOGGER.info(" ==> Method ==> getMerchantGatewayByPreferencePriority ==> Called");

		final Integer minPreferredValue = merchantGateways.stream()
				.collect(Collectors.minBy(Comparator.comparing(MerchantGateways::getPaymentGatewayPreference)))
				.map(MerchantGateways::getPaymentGatewayPreference).orElse(null);

		if (!ApplicationUtils.isEmpty(minPreferredValue)) {

			return merchantGateways.stream()
					.filter(merchantGateway -> merchantGateway != null && minPreferredValue == merchantGateway.getPaymentGatewayPreference())
					.collect(Collectors.toList());
		}

		return Collections.emptyList();
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
