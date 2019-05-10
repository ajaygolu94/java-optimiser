package uk.co.xcordis.optimiser.dto;

import java.io.Serializable;

/**
 * The <code>RegistrationResponse</code> class use to response for the add merchant details request in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
public class RegistrationResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String status;
	private String errorCode;
	private String errorMessage;

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

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {

		return errorCode;
	}

	/**
	 * @param errorCode
	 *            the errorCode to set
	 */
	public void setErrorCode(final String errorCode) {

		this.errorCode = errorCode;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {

		return errorMessage;
	}

	/**
	 * @param errorMessage
	 *            the errorMessage to set
	 */
	public void setErrorMessage(final String errorMessage) {

		this.errorMessage = errorMessage;
	}
}
