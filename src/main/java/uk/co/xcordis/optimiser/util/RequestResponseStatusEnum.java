package uk.co.xcordis.optimiser.util;

/**
 * The <code>RequestResponseStatusEnum</code> class contains request response status that need in the <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
public enum RequestResponseStatusEnum {

	SUCCESS("SUCCESS"), PROCESSING("PROCESSING"), FAILED("FAILED"), ACCEPT("ACCEPT"), PENDING("PENDING"), RECEIVED("RECEIVED"), STEP1_START("STEP1_START"),
	STEP1_COMPLETED("STEP1_COMPLETED");

	private String status;

	RequestResponseStatusEnum(final String status) {

		this.status = status;
	}

	public String status() {

		return status;
	}
}
