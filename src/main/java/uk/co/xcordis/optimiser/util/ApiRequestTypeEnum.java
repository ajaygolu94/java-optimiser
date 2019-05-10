package uk.co.xcordis.optimiser.util;

/**
 * The <code>ApiRequestTypeEnum</code> class contains Api request type enum that need in the <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
public enum ApiRequestTypeEnum {

	CHOOSE_GATEWAY_API("CHOOSE_GATEWAY_API"), GENERATE_ACCESSTOKEN_API("GENERATE_ACCESSTOKEN_API"), GATEWAY_BATCH_DATA("GATEWAY_BATCH_DATA"),
	MERCHANT_BATCH_DATA("MERCHANT_BATCH_DATA"), MERCHANT_GATEWAY_BATCH_DATA("MERCHANT_GATEWAY_BATCH_DATA");

	private String requestType;

	ApiRequestTypeEnum(final String requestType) {

		this.requestType = requestType;
	}

	public String requestType() {

		return requestType;
	}
}
