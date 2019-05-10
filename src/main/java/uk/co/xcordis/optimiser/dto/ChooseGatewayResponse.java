package uk.co.xcordis.optimiser.dto;

import java.io.Serializable;

/**
 * The <code>ChooseGatewayResponse</code> class use to hold choose gateway response details in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
public class ChooseGatewayResponse extends BaseResponse implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String merchantId;
	private String merchantGatewayId;
	private String gatewayName;
	private String referenceId;
	private String code;

	/**
	 * @return the id
	 */
	public String getId() {

		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(final String id) {

		this.id = id;
	}

	/**
	 * @return the merchantId
	 */
	public String getMerchantId() {

		return merchantId;
	}

	/**
	 * @param merchantId
	 *            the merchantId to set
	 */
	public void setMerchantId(final String merchantId) {

		this.merchantId = merchantId;
	}

	/**
	 * @return the merchantGatewayId
	 */
	public String getMerchantGatewayId() {

		return merchantGatewayId;
	}

	/**
	 * @param merchantGatewayId
	 *            the merchantGatewayId to set
	 */
	public void setMerchantGatewayId(final String merchantGatewayId) {

		this.merchantGatewayId = merchantGatewayId;
	}

	/**
	 * @return the gatewayName
	 */
	public String getGatewayName() {

		return gatewayName;
	}

	/**
	 * @param gatewayName
	 *            the gatewayName to set
	 */
	public void setGatewayName(final String gatewayName) {

		this.gatewayName = gatewayName;
	}

	/**
	 * @return the referenceId
	 */
	public String getReferenceId() {

		return referenceId;
	}

	/**
	 * @param referenceId
	 *            the referenceId to set
	 */
	public void setReferenceId(final String referenceId) {

		this.referenceId = referenceId;
	}

	/**
	 * @return the code
	 */
	public String getCode() {

		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(final String code) {

		this.code = code;
	}
}
