package uk.co.xcordis.optimiser.dto;

import java.io.Serializable;

/**
 * The <code>BaseBatchDataApiRequest</code> class use to hold base request details for batch data api in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
public class BaseBatchDataApiRequest implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String operationType;
	private String referenceId;
	private String notificationUrl;

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
	 * @return the notificationUrl
	 */
	public String getNotificationUrl() {

		return notificationUrl;
	}

	/**
	 * @param notificationUrl
	 *            the notificationUrl to set
	 */
	public void setNotificationUrl(final String notificationUrl) {

		this.notificationUrl = notificationUrl;
	}

}
