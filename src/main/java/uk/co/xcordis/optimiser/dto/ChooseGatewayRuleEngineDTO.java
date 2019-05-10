package uk.co.xcordis.optimiser.dto;

import java.util.List;

import uk.co.xcordis.optimiser.model.MerchantGateways;
import uk.co.xcordis.optimiser.model.MerchantRules;

/**
 * The <code>ChooseGatewayRuleEngineDTO</code> class use to response for hold details that required when setup or fire rule engine for choose gateway in
 * <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
public class ChooseGatewayRuleEngineDTO extends ChooseGatewayRequest {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private List<MerchantGateways> merchantGateways;

	private List<MerchantRules> merchantRules;

	private MerchantGateways selectedMerchantGateway;

	private Boolean ruleResult;

	private String message;

	private String status;

	public ChooseGatewayRuleEngineDTO() {

		ruleResult = Boolean.FALSE;
	}

	/**
	 * @return the merchantGateways
	 */
	public List<MerchantGateways> getMerchantGateways() {

		return merchantGateways;
	}

	/**
	 * @param merchantGateways
	 *            the merchantGateways to set
	 */
	public void setMerchantGateways(final List<MerchantGateways> merchantGateways) {

		this.merchantGateways = merchantGateways;
	}

	/**
	 * @return the merchantRules
	 */
	public List<MerchantRules> getMerchantRules() {

		return merchantRules;
	}

	/**
	 * @param merchantRules
	 *            the merchantRules to set
	 */
	public void setMerchantRules(final List<MerchantRules> merchantRules) {

		this.merchantRules = merchantRules;
	}

	/**
	 * @return the selectedMerchantGateway
	 */
	public MerchantGateways getSelectedMerchantGateway() {

		return selectedMerchantGateway;
	}

	/**
	 * @param selectedMerchantGateway
	 *            the selectedMerchantGateway to set
	 */
	public void setSelectedMerchantGateway(final MerchantGateways selectedMerchantGateway) {

		this.selectedMerchantGateway = selectedMerchantGateway;
	}

	/**
	 * @return the ruleResult
	 */
	public Boolean getRuleResult() {

		return ruleResult;
	}

	/**
	 * @param ruleResult
	 *            the ruleResult to set
	 */
	public void setRuleResult(final Boolean ruleResult) {

		this.ruleResult = ruleResult;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {

		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(final String message) {

		this.message = message;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {

		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(final String status) {

		this.status = status;
	}

}
