package uk.co.xcordis.optimiser.rules;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.jeasy.rules.api.Facts;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import uk.co.xcordis.optimiser.controller.BaseController;
import uk.co.xcordis.optimiser.dto.ChooseGatewayResponse;
import uk.co.xcordis.optimiser.dto.ChooseGatewayRuleEngineDTO;
import uk.co.xcordis.optimiser.model.MerchantGateways;
import uk.co.xcordis.optimiser.model.MerchantRules;
import uk.co.xcordis.optimiser.model.RequestData;
import uk.co.xcordis.optimiser.model.RuleSelectorKeys;
import uk.co.xcordis.optimiser.model.Rules;
import uk.co.xcordis.optimiser.util.ApplicationConstants;
import uk.co.xcordis.optimiser.util.ApplicationUtils;
import uk.co.xcordis.optimiser.util.ErrorDataEnum;
import uk.co.xcordis.optimiser.util.RequestResponseCodeEnum;
import uk.co.xcordis.optimiser.util.RequestResponseStatusEnum;

/**
 * The <code>RulesUtil</code> class contains all the utility methods required for Rules in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Repository
public class RulesUtil extends BaseController implements ApplicationConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(RulesUtil.class);

	/**
	 * This <code>getRequestDataFromFacts</code> method is use to get RequestData object from facts.
	 *
	 * @param facts
	 * @return
	 */
	public RequestData getRequestDataFromFacts(final Facts facts) {

		LOGGER.info(" ==> Method ==> getRequestDataFromFacts ==> Called");

		if (facts.get(FACTS_REQUESTDATAID_LABEL) != null) {
			return getServiceRegistry().getRequestDataService().findById((UUID) facts.get(FACTS_REQUESTDATAID_LABEL));
		}

		return null;
	}

	/**
	 * This <code>getChooseGatewayRuleEngineDTOFromFacts</code> method is use to get ChooseGatewayRuleEngineDTO object from facts.
	 *
	 * @param requestData
	 * @return
	 */
	public ChooseGatewayRuleEngineDTO getChooseGatewayRuleEngineDTOFromFacts(final RequestData requestData) {

		LOGGER.info(" ==> Method ==> getChooseGatewayRuleEngineDTOFromFacts ==> Called");

		if (requestData != null && !ApplicationUtils.isEmpty(requestData.getResponseData())) {

			return ApplicationUtils.generateObjectFromJSON(requestData.getResponseData(), ChooseGatewayRuleEngineDTO.class, Boolean.FALSE);
		}
		return null;
	}

	/**
	 * This <code>getMerchantRulesByRuleName</code> method is use to get merchantRules object from rule name.
	 *
	 * @param merchantRules
	 * @param merchantRuleId
	 * @return
	 */
	public MerchantRules getMerchantRulesByRuleName(final List<MerchantRules> merchantRules, final UUID merchantRuleId) {

		LOGGER.info(" ==> Method ==> getMerchantRulesByRuleName ==> Called");
		return merchantRules.stream().filter(merchantRule -> merchantRule != null && merchantRuleId.equals(merchantRule.getMerchantRuleId())).findFirst().get();
	}

	/**
	 * This <code>validateMerchantRuleParam</code> method is use to validate merchant rule param base for keys
	 *
	 * @param merchantRuleParam
	 * @param merchantRuleParamKeys
	 * @return
	 */
	public Boolean validateMerchantRuleParam(final Map<String, String> merchantRuleParam, final List<String> merchantRuleParamKeys) {

		LOGGER.info(" ==> Method ==> validateMerchantRuleParam ==> Enter");

		Boolean result = Boolean.FALSE;

		if (merchantRuleParam != null && !merchantRuleParam.isEmpty()) {

			for (final String key : merchantRuleParamKeys) {

				if (ApplicationUtils.isEmpty(merchantRuleParam.get(key))) {
					result = Boolean.FALSE;
					break;
				} else {
					result = Boolean.TRUE;
				}
			}
		}

		LOGGER.info(" ==> Method ==> validateMerchantRuleParam ==> Exit");
		return result;
	}

	/**
	 * This <code>getResponseStringFromHttpResponse</code> method is used for get response string from httpResponse object.
	 *
	 * @param httpResponse
	 * @return
	 */
	public String getResponseStringFromHttpResponse(final HttpResponse httpResponse) {

		LOGGER.info(" ==> Method : getResponseStringFromHttpResponse ==> Called");

		if (httpResponse != null) {

			final HttpEntity entity = httpResponse.getEntity();
			try {
				return EntityUtils.toString(entity, UTF8);
			} catch (ParseException | IOException e) {
				logError(LOGGER, "", e, " ==> Method : getResponseStringFromHttpResponse ==> Exception ==> ");
			}
		}
		return "";
	}

	/**
	 * This <code>getJsonObjectFromJsonString</code> method is used for get JSONObject from Json string.
	 *
	 * @param key
	 * @param jsonString
	 * @return
	 */
	public synchronized JSONObject getJsonObjectFromJsonString(final String jsonString) {

		LOGGER.info(" ==> Method : getJsonObjectFromJsonString ==> Called");
		return new JSONObject(jsonString);
	}

	/**
	 * This <code>getKeyValueFromJsonObject</code> method is used for get key value from Json object.
	 *
	 * @param jsonObject
	 * @param key
	 * @return
	 */
	public synchronized Object getKeyValueFromJsonObject(final JSONObject jsonObject, final String findKey) {

		LOGGER.info(" ==> Method : getKeyValueFromJsonObject ==> Called");

		Object finalValue = null;

		try {

			if (jsonObject == null) {
				return null;
			}

			@SuppressWarnings("unchecked")
			final Iterator<String> jsonObjectIterator = jsonObject.keys();
			final Map<String, Object> jsonValue = new HashMap<>();

			while (jsonObjectIterator.hasNext()) {

				final String key = jsonObjectIterator.next();
				jsonValue.put(key, jsonObject.get(key));
			}

			for (final Map.Entry<String, Object> entry : jsonValue.entrySet()) {

				final String key = entry.getKey();
				if (key.equalsIgnoreCase(findKey)) {
					return jsonObject.get(findKey);
				}

				// read value
				final Object value = jsonObject.get(key);

				if (value instanceof JSONObject) {
					finalValue = getKeyValueFromJsonObject((JSONObject) value, findKey);
				}
			}

		} catch (final Exception e) {
			logError(LOGGER, "", e, " ==> Method : getKeyValueFromJsonObject ==> Exception ==> ");
		}

		return finalValue;
	}

	/**
	 * This <code>setRulesResultFromMerchantGatewayList</code> method is used to get rules result base on merchant gateway.
	 *
	 * @param selectedMerchantGateways
	 * @param gatewayRuleEngineDTO
	 * @param requestData
	 * @param facts
	 * @param stopRuleEngine
	 */
	public void setRulesResultFromMerchantGatewayList(final List<MerchantGateways> selectedMerchantGateways,
			final ChooseGatewayRuleEngineDTO gatewayRuleEngineDTO, final RequestData requestData, final Facts facts, final Boolean stopRuleEngine,
			final UUID merchantRuleId) {

		LOGGER.info(" ==> Method : setRulesResultFromMerchantGatewayList ==> Enter");

		final StringBuilder commonErrorMessage = new StringBuilder(" ==> Method ==> setRulesResultFromMerchantGatewayList ==> merchantRuleId ==> ")
				.append(merchantRuleId).append(LOGERROR_EXCEPTION_LABEL);
		gatewayRuleEngineDTO.setStatus("");

		if (ApplicationUtils.isValid(selectedMerchantGateways)) {

			if (selectedMerchantGateways.size() == 1) {

				gatewayRuleEngineDTO.setRuleResult(Boolean.TRUE);
				gatewayRuleEngineDTO.setStatus(RequestResponseStatusEnum.SUCCESS.status());
				gatewayRuleEngineDTO.setSelectedMerchantGateway(selectedMerchantGateways.get(0));

			} else {
				gatewayRuleEngineDTO.setRuleResult(Boolean.FALSE);
				gatewayRuleEngineDTO.setMerchantGateways(selectedMerchantGateways);
			}

		} else {

			if (stopRuleEngine) {

				logErrorForRules(LOGGER, gatewayRuleEngineDTO.getMerchantId(), null,
						commonErrorMessage.append(STOP_RULEENIGINE_SELECTEDMERCHANTGATEWAYS).toString());
				gatewayRuleEngineDTO.setRuleResult(Boolean.TRUE);
				gatewayRuleEngineDTO.setMessage(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null));
				gatewayRuleEngineDTO.setStatus(RequestResponseStatusEnum.FAILED.status());

			} else {
				gatewayRuleEngineDTO.setRuleResult(Boolean.FALSE);
				logErrorForRules(LOGGER, gatewayRuleEngineDTO.getMerchantId(), null, commonErrorMessage.append(SKIP_RULE_SELECTEDMERCHANTGATEWAYS).toString());
			}
		}

		if (requestData != null) {

			requestData.setResponseData(ApplicationUtils.generateJSONFromObject(gatewayRuleEngineDTO));
			getServiceRegistry().getRequestDataService().updateRequestData(requestData);
		}

		if (gatewayRuleEngineDTO.getRuleResult()) {

			facts.put(FACT_GATEWAYRULEENGINEDTO_LABEL, gatewayRuleEngineDTO);
			facts.put(FACT_REQUESTDATA_LABEL, requestData);
		}

		LOGGER.info(" ==> Method : setRulesResultFromMerchantGatewayList ==> Exit");
	}

	/**
	 * This <code>setRuleActionResponse</code> method is used to set rule action or set response when any single rule success.
	 *
	 * @param facts
	 */
	public void setRuleActionResponse(final Facts facts) {

		LOGGER.info(" ==> Method ==> setRuleActionResponse ==> Exit");

		ChooseGatewayRuleEngineDTO gatewayRuleEngineDTO;
		RequestData requestData;
		final ChooseGatewayResponse response = new ChooseGatewayResponse();

		if (facts.get(FACT_GATEWAYRULEENGINEDTO_LABEL) != null && facts.get(FACT_REQUESTDATA_LABEL) != null) {
			gatewayRuleEngineDTO = (ChooseGatewayRuleEngineDTO) facts.get(FACT_GATEWAYRULEENGINEDTO_LABEL);
			requestData = (RequestData) facts.get(FACT_REQUESTDATA_LABEL);
		} else {
			requestData = getRequestDataFromFacts(facts);
			gatewayRuleEngineDTO = getChooseGatewayRuleEngineDTOFromFacts(requestData);
		}

		if (gatewayRuleEngineDTO != null) {

			if (RequestResponseStatusEnum.SUCCESS.status().equalsIgnoreCase(gatewayRuleEngineDTO.getStatus())
					&& gatewayRuleEngineDTO.getSelectedMerchantGateway() != null) {

				response.setCode(RequestResponseCodeEnum.SUCCESS.code());
				response.setGatewayName(gatewayRuleEngineDTO.getSelectedMerchantGateway().getGatewayName());
				response.setMerchantGatewayId(gatewayRuleEngineDTO.getSelectedMerchantGateway().getSourceMerchantGatewayId());

			} else {

				if (ApplicationUtils.isEmpty(gatewayRuleEngineDTO.getMessage())) {
					response.setMessages(new HashSet<>(Arrays.asList(gatewayRuleEngineDTO.getMessage())));
				} else {
					response.setMessages(new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))));
				}
				response.setCode(RequestResponseCodeEnum.INTERNALSERVERERROR.code());
			}
		} else {
			response.setCode(RequestResponseCodeEnum.INTERNALSERVERERROR.code());
			response.setMessages(new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))));
		}

		requestData.setResponseData(ApplicationUtils.generateJSONFromObject(response));
		getServiceRegistry().getRequestDataService().updateRequestData(requestData);

		LOGGER.info(" ==> Method ==> setRuleActionResponse ==> Exit");
	}

	/**
	 * This <code>logErrorForRules</code> method is use to log error into database for rules.
	 *
	 * @param LOGGER
	 * @param identifier
	 * @param exception
	 * @param message
	 */
	public void logErrorForRules(final Logger LOGGER, final String identifier, final Exception exception, final String message) {

		logError(LOGGER, identifier, exception, message);
	}

	/**
	 * This <code>rulesCommonOperations</code> method is use to perform common operations those are needed by every rule in Rules Engine.
	 *
	 * @param facts
	 * @param merchantRuleId
	 * @return
	 */
	public Map<String, Object> rulesCommonOperations(final Facts facts, final UUID merchantRuleId) {

		LOGGER.info(" ==> Method ==> rulesCommonOperations ==> Enter");

		Boolean result = Boolean.FALSE;
		String status = RequestResponseStatusEnum.FAILED.status();
		final Map<String, Object> rulesCommonOperationResponse = new HashMap<>();
		final StringBuilder commonErrorMessage = new StringBuilder(" ==> Method ==> rulesCommonOperations ==> merchantRuleId ==> ").append(merchantRuleId)
				.append(LOGERROR_EXCEPTION_LABEL);
		final RequestData requestData = getRequestDataFromFacts(facts);

		if (requestData == null) {

			logErrorForRules(LOGGER, null, null, commonErrorMessage.append(STOP_RULEENIGINE_RUQESTDATA).toString());
			result = Boolean.TRUE;

		} else {

			rulesCommonOperationResponse.put(FACT_REQUESTDATA_LABEL, requestData);
			final ChooseGatewayRuleEngineDTO gatewayRuleEngineDTO = getChooseGatewayRuleEngineDTOFromFacts(requestData);

			if (gatewayRuleEngineDTO != null && ApplicationUtils.isValid(gatewayRuleEngineDTO.getMerchantGateways())
					&& ApplicationUtils.isValid(gatewayRuleEngineDTO.getMerchantRules())) {

				final String merchantId = gatewayRuleEngineDTO.getMerchantId();
				final MerchantRules merchantRules = getMerchantRulesByRuleName(gatewayRuleEngineDTO.getMerchantRules(), merchantRuleId);
				rulesCommonOperationResponse.put(FACT_GATEWAYRULEENGINEDTO_LABEL, gatewayRuleEngineDTO);
				rulesCommonOperationResponse.put(RULESCOMMONOPERATIONS_MERCHANTID_LABEL, merchantId);

				if (merchantRules != null) {
					rulesCommonOperationResponse.put(RULESCOMMONOPERATIONS_MERCHANTRULES_LABEL, merchantRules);
					status = RequestResponseStatusEnum.SUCCESS.status();
				} else {
					logErrorForRules(LOGGER, merchantId, null, commonErrorMessage.append(SKIP_RULES__MISSING).toString());
				}
			} else {
				result = Boolean.TRUE;
				logErrorForRules(LOGGER, null, null, commonErrorMessage.append(STOP_RULEENIGINE_MERCHANTRULES_MERCHANTGATEWAY).toString());
			}
		}

		rulesCommonOperationResponse.put(RULESCOMMONOPERATIONS_RESULT_LABEL, result);
		rulesCommonOperationResponse.put(STATUS_LABLE, status);

		LOGGER.info(" ==> Method ==> rulesCommonOperations ==> Exit");
		return rulesCommonOperationResponse;
	}

	/**
	 * This <code>checkRulesCommonOperationResponse</code> method is use to check rules common operations response.
	 *
	 * @param rulesCommonOperationResponse
	 * @return
	 */
	public Boolean checkRulesCommonOperationResponse(final Map<String, Object> rulesCommonOperationResponse) {

		LOGGER.info(" ==> Method ==> checkRulesCommonOperationResponse ==> Called");

		if (rulesCommonOperationResponse.get(RULESCOMMONOPERATIONS_RESULT_LABEL) != null
				&& !(Boolean) rulesCommonOperationResponse.get(RULESCOMMONOPERATIONS_RESULT_LABEL) && rulesCommonOperationResponse.get(STATUS_LABLE) != null
				&& RequestResponseStatusEnum.SUCCESS.status().equalsIgnoreCase(String.valueOf(rulesCommonOperationResponse.get(STATUS_LABLE)))) {
			return Boolean.TRUE;
		}

		return Boolean.FALSE;
	}

	/**
	 * This <code>selectMerchantGatewayBySelector</code> method is use to select merchant gateways base on merchant rules selector.
	 *
	 * @param gatewayRuleEngineDTO
	 * @param merchantRule
	 */
	public List<MerchantGateways> selectMerchantGatewayBySelector(final ChooseGatewayRuleEngineDTO gatewayRuleEngineDTO, final MerchantRules merchantRule) {

		LOGGER.info(" ==> Method ==> selectMerchantGatewayBySelector ==> Enter");

		final StringBuilder commonErrorMessage = new StringBuilder(" ==> Method ==> selectMerchantGatewayBySelector ==> merchantRuleId ==> ")
				.append(merchantRule.getMerchantRuleId()).append(LOGERROR_EXCEPTION_LABEL);

		try {

			final Rules rule = getServiceRegistry().getRulesService().findById(merchantRule.getRuleId());

			if (rule != null && ApplicationUtils.isValid(rule.getSelectorKey()) && !ApplicationUtils.isEmpty(merchantRule.getOperation())) {

				Boolean workOnRuleSelector = Boolean.FALSE;

				if (ApplicationUtils.isValid(merchantRule.getEntryCriteria())) {

					if (filterRequestByMerchantRulesEntryCiteria(merchantRule, gatewayRuleEngineDTO, rule.getSelectorKey())) {
						workOnRuleSelector = Boolean.TRUE;
					} else {
						logError(LOGGER, gatewayRuleEngineDTO.getMerchantId(), null, commonErrorMessage.append(SKIP_RULE_BY_ENTRY_CRITERIA).toString());
					}

				} else {
					workOnRuleSelector = Boolean.TRUE;
				}

				if (workOnRuleSelector) {
					return getMerchantGatewayBaseOnSelector(rule, gatewayRuleEngineDTO, merchantRule.getOperation());
				}

			} else {
				logError(LOGGER, gatewayRuleEngineDTO.getMerchantId(), null, commonErrorMessage.append(SKIP_RULE_RULEOBJECT_NULL).toString());
			}
		} catch (final Exception e) {
			logError(LOGGER, gatewayRuleEngineDTO.getMerchantId(), e, commonErrorMessage.toString());
		}

		LOGGER.info(" ==> Method ==> selectMerchantGatewayBySelector ==> Exit");
		return Collections.emptyList();
	}

	/**
	 * This <code>filterRequestByMerchantRulesEntryCiteria</code> method is use to filter request base on the merchant rule's entry level criteria.
	 *
	 * @param merchantRuleSelector
	 * @param gatewayRuleEngineDTO
	 * @param selectorKey
	 * @return
	 */
	public Boolean filterRequestByMerchantRulesEntryCiteria(final MerchantRules merchantRule, final ChooseGatewayRuleEngineDTO gatewayRuleEngineDTO,
			final List<String> selectorKey) {

		LOGGER.info(" ==> Method ==> filterRequestByMerchantRulesEntryCiteria ==> Enter");

		if (ApplicationUtils.isValid(merchantRule.getEntryCriteria())) {

			final List<
					String> merchantRuleSelectorKey = merchantRule.getEntryCriteria().entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList());

			if (ApplicationUtils.isValid(merchantRuleSelectorKey)) {

				Integer countOfMatchSelector = 0;

				for (final String key : selectorKey) {

					if (merchantRuleSelectorKey.contains(key) && checkRequestValueBySelectorKeyAndValue(key, gatewayRuleEngineDTO,
							merchantRule.getEntryCriteria(), merchantRule.getOperation())) {
						countOfMatchSelector++;
					} else {
						countOfMatchSelector--;
					}
				}

				if (countOfMatchSelector == selectorKey.size()) {
					return Boolean.TRUE;
				}
			}
		}

		LOGGER.info(" ==> Method ==> filterRequestByMerchantRulesEntryCiteria ==> Exit");
		return Boolean.FALSE;
	}

	/**
	 * This <code>checkRequestValueBySelectorKeyAndValue</code> method is use to check request value by the selector key and value.
	 *
	 * @param key
	 * @param requestObject
	 * @param selectorValues
	 * @param operation
	 * @return
	 */
	private Boolean checkRequestValueBySelectorKeyAndValue(final String key, final Object requestObject, final Map<String, String> selectorValues,
			final String operation) {

		LOGGER.info(" ==> Method ==> checkRequestValueBySelectorKeyAndValue ==> Called");

		final RuleSelectorKeys ruleSelectorKeys = getServiceRegistry().getRuleSelectorKeysService().findById(key);

		if (ruleSelectorKeys != null && !ApplicationUtils.isEmpty(ruleSelectorKeys.getOptimiserParam()) && !ApplicationUtils.isEmpty(selectorValues.get(key))) {

			try {

				final Object requestObjectValue = new PropertyDescriptor(ruleSelectorKeys.getOptimiserParam(), requestObject.getClass()).getReadMethod()
						.invoke(requestObject);

				if (requestObjectValue != null && !ApplicationUtils.isEmpty(String.valueOf(requestObjectValue))) {
					return checkRequestValueAgainstSelectorValueByRuleOperation(selectorValues.get(key), String.valueOf(requestObjectValue), operation);
				}

			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | IntrospectionException e) {
				logError(LOGGER, null, e, " ==> Method ==> checkRequestValueBySelectorKeyAndValue ==> Exception ==> ");
			}
		}

		return Boolean.FALSE;
	}

	/**
	 * This <code>checkRequestValueAgainstSelectorValueByRuleOperation</code> method is use to check request value by merchant rule operation against selector
	 * value.
	 *
	 * @param selectorValue
	 * @param requestValue
	 * @param operation
	 * @return
	 */
	private Boolean checkRequestValueAgainstSelectorValueByRuleOperation(final String selectorValue, final String requestValue, final String operation) {

		LOGGER.info(" ==> Method ==> checkRequestValueAgainstSelectorValueByRuleOperation ==> Enter");

		Boolean result = Boolean.FALSE;
		final RuleOperationEnum ruleOperation = RuleOperationEnum.valueOf(operation);

		if (selectorValue.contains(";")) {

			for (final String value : selectorValue.split(";")) {

				if (checkRequestValueByRulesValidator(ruleOperation.getMethodName(), value, requestValue)) {

					result = Boolean.TRUE;
					break;
				}
			}

		} else {
			return checkRequestValueByRulesValidator(ruleOperation.getMethodName(), selectorValue, requestValue);
		}

		LOGGER.info(" ==> Method ==> checkRequestValueAgainstSelectorValueByRuleOperation ==> Exit");
		return result;
	}

	/**
	 * This <code>checkRequestValueByRulesValidator</code> method is use to check request value by operation validator against selector value.
	 *
	 * @param methodName
	 * @param selectorValue
	 * @param objectValue
	 * @return
	 */
	private Boolean checkRequestValueByRulesValidator(final String methodName, final String selectorValue, final String objectValue) {

		LOGGER.info(" ==> Method ==> checkRequestValueByRulesValidator ==> Called");

		try {

			final Object objectResult = RulesOperationValidator.class.getMethod(methodName, String.class, String.class).invoke(new RulesOperationValidator(),
					selectorValue, objectValue);

			if (objectResult != null && (Boolean) objectResult) {
				return Boolean.TRUE;
			}
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			LOGGER.error(ApplicationConstants.MARKER, " ==> Method ==> checkRequestValueByRulesValidator ==> Exception ==> ");
		}

		return Boolean.FALSE;
	}

	/**
	 * This <code>getMerchantGatewayBaseOnSelector</code> method is use to get merchant gateway base on selector.
	 *
	 * @param rule
	 * @param gatewayRuleEngineDTO
	 * @param operation
	 * @return
	 */
	private List<MerchantGateways> getMerchantGatewayBaseOnSelector(final Rules rule, final ChooseGatewayRuleEngineDTO gatewayRuleEngineDTO,
			final String operation) {

		LOGGER.info(" ==> Method ==> getMerchantGatewayBaseOnSelector ==> Enter");

		final List<MerchantGateways> selectedMerchantGateways = new ArrayList<>();

		gatewayRuleEngineDTO.getMerchantGateways().forEach(merchantGateway -> {

			if (merchantGateway != null && ApplicationUtils.isValid(rule.getSelectorKey())) {

				Boolean selected = Boolean.FALSE;
				if (ApplicationUtils.isValid(merchantGateway.getSelector())) {

					final List<String> merchantGatewaySelectorKey = merchantGateway.getSelector().entrySet().stream().map(Map.Entry::getKey)
							.collect(Collectors.toList());

					if (ApplicationUtils.isValid(merchantGatewaySelectorKey)) {

						Integer count = 0;
						for (final String key : rule.getSelectorKey()) {

							if (!ApplicationUtils.isEmpty(key) && selectMerchantGatewayBySelectorKeyAndValue(key, merchantGatewaySelectorKey,
									merchantGateway.getSelector(), gatewayRuleEngineDTO, operation)) {
								count++;
							} else {
								count--;
							}
						}

						if (count == rule.getSelectorKey().size()) {
							selected = Boolean.TRUE;
						}
					} else {
						selected = Boolean.TRUE;
					}
				} else {
					selected = Boolean.TRUE;
				}

				if (selected) {
					selectedMerchantGateways.add(merchantGateway);
				}
			}
		});

		LOGGER.info(" ==> Method ==> getMerchantGatewayBaseOnSelector ==> Enter");
		return selectedMerchantGateways;
	}

	/**
	 * This <code>selectMerchantGatewayBySelectorKeyAndValue</code> method is use to select merchant gateway base on selector key and value.
	 *
	 * @param key
	 * @param merchantGatewaySelectorKey
	 * @param merchantGatewaySelectorValue
	 * @param gatewayRuleEngineDTO
	 * @param operation
	 * @return
	 */
	private Boolean selectMerchantGatewayBySelectorKeyAndValue(final String key, final List<String> merchantGatewaySelectorKey,
			final Map<String, String> merchantGatewaySelectorValue, final ChooseGatewayRuleEngineDTO gatewayRuleEngineDTO, final String operation) {

		LOGGER.info(" ==> Method ==> selectMerchantGatewayBySelectorKeyAndValue ==> Called");

		if (merchantGatewaySelectorKey.contains(key)) {
			return checkRequestValueBySelectorKeyAndValue(key, gatewayRuleEngineDTO, merchantGatewaySelectorValue, operation);
		} else {
			return Boolean.TRUE;
		}
	}
}
