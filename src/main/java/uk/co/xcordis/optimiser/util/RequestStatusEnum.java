package uk.co.xcordis.optimiser.util;

/**
 * The <code>RequestStatusEnum</code> class contains request status that need in the <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
public enum RequestStatusEnum {

	PENDING("PENDING"), PROCESSING("PROCESSING"), SENT("SENT"), SENDFAILED("SENDFAILED");

	private String status;

	RequestStatusEnum(final String status) {

		this.status = status;
	}

	public String status() {

		return status;
	}
}
