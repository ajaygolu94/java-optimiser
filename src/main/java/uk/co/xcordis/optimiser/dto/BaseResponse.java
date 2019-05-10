package uk.co.xcordis.optimiser.dto;

import java.io.Serializable;
import java.util.Set;

/**
 * The <code>BaseResponse</code> class use to hold basic details of request and also extends by all other response classes in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
public class BaseResponse implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String status;
	private Set<String> messages;
	private String requestDate;

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
	 * @return the messages
	 */
	public Set<String> getMessages() {

		return messages;
	}

	/**
	 * @param messages
	 *            the messages to set
	 */
	public void setMessages(final Set<String> messages) {

		this.messages = messages;
	}

	/**
	 * @return the requestDate
	 */
	public String getRequestDate() {

		return requestDate;
	}

	/**
	 * @param requestDate
	 *            the requestDate to set
	 */
	public void setRequestDate(final String requestDate) {

		this.requestDate = requestDate;
	}
}
