package uk.co.xcordis.optimiser.dto;

/**
 * The <code>MerchantGatewayBatchDataDto</code> class use to hold merchant gateway batch data details in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
public class MerchantGatewayBatchDataDto {

	private String merchantId;
	private String merchantGatewayId;
	private String gatewayName;

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

}
