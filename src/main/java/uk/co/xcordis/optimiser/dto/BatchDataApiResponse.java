package uk.co.xcordis.optimiser.dto;

import java.io.Serializable;
import java.util.Set;

/**
 * The <code>BatchDataApiResponse</code> class use to hold batch data api response details in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
public class BatchDataApiResponse implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String userId;
	private String operationType;
	private String referenceId;
	private String requestDate;
	private String status;
	private Set<String> errorMessage;

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
	 * @return the userId
	 */
	public String getUserId() {

		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(final String userId) {

		this.userId = userId;
	}

	/**
	 * @return the operationType
	 */
	public String getOperationType() {

		return operationType;
	}

	/**
	 * @param operationType
	 *            the operationType to set
	 */
	public void setOperationType(final String operationType) {

		this.operationType = operationType;
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
	 * @return the errorMessage
	 */
	public Set<String> getErrorMessage() {

		return errorMessage;
	}

	/**
	 * @param errorMessage
	 *            the errorMessage to set
	 */
	public void setErrorMessage(final Set<String> errorMessage) {

		this.errorMessage = errorMessage;
	}

}
