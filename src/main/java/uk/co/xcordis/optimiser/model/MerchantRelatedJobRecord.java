package uk.co.xcordis.optimiser.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import uk.co.xcordis.optimiser.util.ApplicationTableConstants;

/**
 * The <code>MerchantRelatedJobRecord</code> model is used to store the merchant related job record details in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Table(name = ApplicationTableConstants.TABLENAME_MERCHANTRELATEDJOBRECORD)
public class MerchantRelatedJobRecord implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@PartitionKey(0)
	private UUID merchantJobRecordId;

	private String userId;

	private String jobId;

	private UUID merchantRequestDataId;

	private String createdDate;

	private String auditTimeStamp;

	private String sourceMerchantId;

	private String requestData;

	private String operationType;

	private String requestType;

	private String status;

	private Set<String> errorMessage;

	/**
	 * @return the merchantJobRecordId
	 */
	public UUID getMerchantJobRecordId() {

		return merchantJobRecordId;
	}

	/**
	 * @param merchantJobRecordId
	 *            the merchantJobRecordId to set
	 */
	public void setMerchantJobRecordId(final UUID merchantJobRecordId) {

		this.merchantJobRecordId = merchantJobRecordId;
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
	 * @return the jobId
	 */
	public String getJobId() {

		return jobId;
	}

	/**
	 * @param jobId
	 *            the jobId to set
	 */
	public void setJobId(final String jobId) {

		this.jobId = jobId;
	}

	/**
	 * @return the merchantRequestDataId
	 */
	public UUID getMerchantRequestDataId() {

		return merchantRequestDataId;
	}

	/**
	 * @param merchantRequestDataId
	 *            the merchantRequestDataId to set
	 */
	public void setMerchantRequestDataId(final UUID merchantRequestDataId) {

		this.merchantRequestDataId = merchantRequestDataId;
	}

	/**
	 * @return the createdDate
	 */
	public String getCreatedDate() {

		return createdDate;
	}

	/**
	 * @param createdDate
	 *            the createdDate to set
	 */
	public void setCreatedDate(final String createdDate) {

		this.createdDate = createdDate;
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
	 * @return the sourceMerchantId
	 */
	public String getSourceMerchantId() {

		return sourceMerchantId;
	}

	/**
	 * @param sourceMerchantId
	 *            the sourceMerchantId to set
	 */
	public void setSourceMerchantId(final String sourceMerchantId) {

		this.sourceMerchantId = sourceMerchantId;
	}

	/**
	 * @return the requestData
	 */
	public String getRequestData() {

		return requestData;
	}

	/**
	 * @param requestData
	 *            the requestData to set
	 */
	public void setRequestData(final String requestData) {

		this.requestData = requestData;
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
	 * @return the requestType
	 */
	public String getRequestType() {

		return requestType;
	}

	/**
	 * @param requestType
	 *            the requestType to set
	 */
	public void setRequestType(final String requestType) {

		this.requestType = requestType;
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

	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {

		// TODO Auto-generated method stub
		final MerchantRelatedJobRecord merchantRelatedJobRecord = (MerchantRelatedJobRecord) obj;
		return getMerchantJobRecordId().equals(merchantRelatedJobRecord.getMerchantJobRecordId());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {

		return Objects.hash(getMerchantJobRecordId());
	}
}
