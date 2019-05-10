package uk.co.xcordis.optimiser.dto;

import java.io.Serializable;
import java.util.UUID;

/**
 * The <code>ChooseGatewayRequest</code> class use to hold choose gateway request details in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
public class ChooseGatewayRequest implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String merchantId;
	private String accessToken;
	private String cardType;
	private String currencyCode;
	private String amount;
	private String countryCode;
	private String cardNumber;
	private String referenceId;
	private String cardIssueCountry;
	private String mid;
	private String hostedPaymentPage;

	// Hold requestDataId of requestdata table.
	private UUID optimiserRequestDataId;

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
	 * @return the accessToken
	 */
	public String getAccessToken() {

		return accessToken;
	}

	/**
	 * @param accessToken
	 *            the accessToken to set
	 */
	public void setAccessToken(final String accessToken) {

		this.accessToken = accessToken;
	}

	/**
	 * @return the cardType
	 */
	public String getCardType() {

		return cardType;
	}

	/**
	 * @param cardType
	 *            the cardType to set
	 */
	public void setCardType(final String cardType) {

		this.cardType = cardType;
	}

	/**
	 * @return the currencyCode
	 */
	public String getCurrencyCode() {

		return currencyCode;
	}

	/**
	 * @param currencyCode
	 *            the currencyCode to set
	 */
	public void setCurrencyCode(final String currencyCode) {

		this.currencyCode = currencyCode;
	}

	/**
	 * @return the amount
	 */
	public String getAmount() {

		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(final String amount) {

		this.amount = amount;
	}

	/**
	 * @return the countryCode
	 */
	public String getCountryCode() {

		return countryCode;
	}

	/**
	 * @param countryCode
	 *            the countryCode to set
	 */
	public void setCountryCode(final String countryCode) {

		this.countryCode = countryCode;
	}

	/**
	 * @return the cardNumber
	 */
	public String getCardNumber() {

		return cardNumber;
	}

	/**
	 * @param cardNumber
	 *            the cardNumber to set
	 */
	public void setCardNumber(final String cardNumber) {

		this.cardNumber = cardNumber;
	}

	/**
	 * @return the optimiserRequestDataId
	 */
	public UUID getOptimiserRequestDataId() {

		return optimiserRequestDataId;
	}

	/**
	 * @param optimiserRequestDataId
	 *            the optimiserRequestDataId to set
	 */
	public void setOptimiserRequestDataId(final UUID optimiserRequestDataId) {

		this.optimiserRequestDataId = optimiserRequestDataId;
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
	 * @return the cardIssueCountry
	 */
	public String getCardIssueCountry() {

		return cardIssueCountry;
	}

	/**
	 * @param cardIssueCountry
	 *            the cardIssueCountry to set
	 */
	public void setCardIssueCountry(final String cardIssueCountry) {

		this.cardIssueCountry = cardIssueCountry;
	}

	/**
	 * @return the mid
	 */
	public String getMid() {

		return mid;
	}

	/**
	 * @param mid
	 *            the mid to set
	 */
	public void setMid(final String mid) {

		this.mid = mid;
	}

	/**
	 * @return the hostedPaymentPage
	 */
	public String getHostedPaymentPage() {

		return hostedPaymentPage;
	}

	/**
	 * @param hostedPaymentPage
	 *            the hostedPaymentPage to set
	 */
	public void setHostedPaymentPage(final String hostedPaymentPage) {

		this.hostedPaymentPage = hostedPaymentPage;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		return "ChooseGatewayRequest [merchantId=" + merchantId + ", accessToken=" + accessToken + ", cardType=" + cardType + ", currencyCode=" + currencyCode
				+ ", amount=" + amount + ", countryCode=" + countryCode + ", cardNumber=" + cardNumber + ", referenceId=" + referenceId + ", cardIssueCountry="
				+ cardIssueCountry + ", mid=" + mid + ", optimiserRequestDataId=" + optimiserRequestDataId + "]";
	}
}
