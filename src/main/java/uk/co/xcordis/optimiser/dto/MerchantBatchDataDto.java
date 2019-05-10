package uk.co.xcordis.optimiser.dto;

/**
 * The <code>MerchantBatchDataDto</code> class use to hold merchant batch data details in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
public class MerchantBatchDataDto {

	private String merchantId;
	private String merchantName;

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
	 * @return the merchantName
	 */
	public String getMerchantName() {

		return merchantName;
	}

	/**
	 * @param merchantName
	 *            the merchantName to set
	 */
	public void setMerchantName(final String merchantName) {

		this.merchantName = merchantName;
	}

}
