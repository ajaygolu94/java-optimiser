package uk.co.xcordis.optimiser.util;

/**
 * The <code>RequestResponseCodeEnum</code> class contains all request response code that need in the <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
public enum RequestResponseCodeEnum {

	SUCCESS("00"), INTERNALSERVERERROR("01"), VALIDATIONERROR("02"), CONFIGURATIONERROR("03"), AUTHENTICATIONERROR("04"), FAILED("05");

	private String code;

	RequestResponseCodeEnum(final String code) {

		this.code = code;
	}

	public String code() {

		return code;
	}
}
