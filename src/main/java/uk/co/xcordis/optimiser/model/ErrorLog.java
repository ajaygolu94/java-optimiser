package uk.co.xcordis.optimiser.model;

import java.util.UUID;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import uk.co.xcordis.optimiser.util.ApplicationTableConstants;

/**
 * The <code>ErrorLog</code> class is responsible for storing the error log in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Table(name = ApplicationTableConstants.TABLENAME_ERROR_LOG)
public class ErrorLog {

	@PartitionKey
	private UUID errorLogId;

	private String message;

	private String className;

	private String auditTimeStamp;

	private String identifier;

	private String type;

	/**
	 * @return the errorLogId
	 */
	public UUID getErrorLogId() {

		return errorLogId;
	}

	/**
	 * @param errorLogId
	 *            the errorLogId to set
	 */
	public void setErrorLogId(final UUID errorLogId) {

		this.errorLogId = errorLogId;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {

		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(final String message) {

		this.message = message;
	}

	/**
	 * @return the className
	 */
	public String getClassName() {

		return className;
	}

	/**
	 * @param className
	 *            the className to set
	 */
	public void setClassName(final String className) {

		this.className = className;
	}

	/**
	 * @return the auditTimeStamp
	 */
	public String getAuditTimeStamp() {

		return auditTimeStamp;
	}

	/**
	 * @param auditTimeStamp
	 *            the auditTimeStamp to set
	 */
	public void setAuditTimeStamp(final String auditTimeStamp) {

		this.auditTimeStamp = auditTimeStamp;
	}

	/**
	 * @return the identifier
	 */
	public String getIdentifier() {

		return identifier;
	}

	/**
	 * @param identifier
	 *            the identifier to set
	 */
	public void setIdentifier(final String identifier) {

		this.identifier = identifier;
	}

	/**
	 * @return the type
	 */
	public String getType() {

		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(final String type) {

		this.type = type;
	}
}
