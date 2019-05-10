package uk.co.xcordis.optimiser.rules;

import java.util.ArrayList;
import java.util.Arrays;
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
 * The <code>MerchantGatewaySequenceRule</code> class is one rule for select merchant gateway base on given sequence in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Rule
public class MerchantGatewaySequenceRule implements ApplicationConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(MerchantGatewaySequenceRule.class);

	private int priority;
	private UUID merchantRuleId;
	private ServiceRegistry serviceRegistry;

	public MerchantGatewaySequenceRule() {

	}

	public MerchantGatewaySequenceRule(final Integer priority, final UUID merchantRuleId) {

		this.priority = priority.intValue();
		this.merchantRuleId = merchantRuleId;
	}

	/**
	 * This <code>selectMerchantGatewayBySequence</code> method is use to select merchant gateway base on sequence.
	 *
	 * @param facts
	 * @return
	 */
	@Condition
	public boolean selectMerchantGatewayBySequence(final Facts facts) {

		LOGGER.info(" ==> Method ==> selectMerchantGatewayBySequence ==> Enter");

		Boolean result = Boolean.FALSE;
		final StringBuilder commonErrorMessage = new StringBuilder(" ==> Method ==> selectMerchantGatewayBySequence ==> merchantRuleId ==> ")
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

				serviceRegistry.getRulesUtil().setRulesResultFromMerchantGatewayList(getMerchantGatewayBySequence(gatewayRuleEngineDTO.getMerchantGateways()),
						gatewayRuleEngineDTO, (RequestData) rulesCommonOperationResponse.get(FACT_REQUESTDATA_LABEL), facts, Boolean.FALSE,
						merchantRule.getMerchantRuleId());
				result = gatewayRuleEngineDTO.getRuleResult();

			} else {

				if (rulesCommonOperationResponse.get(RULESCOMMONOPERATIONS_RESULT_LABEL) != null) {
					result = (Boolean) rulesCommonOperationResponse.get(RULESCOMMONOPERATIONS_RESULT_LABEL);
				}
			}
		} catch (final Exception e) {
			serviceRegistry.getRulesUtil().logErrorForRules(LOGGER, merchantId, e, commonErrorMessage.toString());
		}

		LOGGER.info(" ==> Method ==> selectMerchantGatewayBySequence ==> Exit");
		return result;
	}

	/**
	 * This <code>getMerchantGatewayBySequence</code> method is use to get merchant gateway base on sequence.
	 *
	 * @param merchantGateways
	 * @return
	 */
	private List<MerchantGateways> getMerchantGatewayBySequence(final List<MerchantGateways> merchantGateways) {

		LOGGER.info(" ==> Method ==> getMerchantGatewayBySequence ==> Called");

		final MerchantGateways merchantGateway = merchantGateways.stream()
				.collect(Collectors.minBy(Comparator.comparing(MerchantGateways::getPaymentGatewaySequence))).get();

		if (merchantGateway != null && !ApplicationUtils.isEmpty(merchantGateway.getSourceMerchantGatewayId())) {

			return new ArrayList<>(Arrays.asList(merchantGateway));
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
