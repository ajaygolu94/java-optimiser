package uk.co.xcordis.optimiser.model;

import java.io.Serializable;
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import uk.co.xcordis.optimiser.util.ApplicationTableConstants;

/**
 * The <code>MerchantRelatedRequestData</code> model is used to store the merchant related request data details in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Table(name = ApplicationTableConstants.TABLENAME_MERCHANTRELATEDREQUESTDATA)
public class MerchantRelatedRequestData implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@PartitionKey(0)
	private UUID merchantRequestDataId;

	private String userId;

	private String ipAddress;

	private String operationType;

	private String requestType;

	private String requestClass;

	private String requestData;

	private String responseClass;

	private String responseData;

	private String requestDate;

	private String responseDate;

	private String status;

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
	 * @return the ipAddress
	 */
	public String getIpAddress() {

		return ipAddress;
	}

	/**
	 * @param ipAddress
	 *            the ipAddress to set
	 */
	public void setIpAddress(final String ipAddress) {

		this.ipAddress = ipAddress;
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
	 * @return the requestClass
	 */
	public String getRequestClass() {

		return requestClass;
	}

	/**
	 * @param requestClass
	 *            the requestClass to set
	 */
	public void setRequestClass(final String requestClass) {

		this.requestClass = requestClass;
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
	 * @return the responseClass
	 */
	public String getResponseClass() {

		return responseClass;
	}

	/**
	 * @param responseClass
	 *            the responseClass to set
	 */
	public void setResponseClass(final String responseClass) {

		this.responseClass = responseClass;
	}

	/**
	 * @return the responseData
	 */
	public String getResponseData() {

		return responseData;
	}

	/**
	 * @param responseData
	 *            the responseData to set
	 */
	public void setResponseData(final String responseData) {

		this.responseData = responseData;
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
	 * @return the responseDate
	 */
	public String getResponseDate() {

		return responseDate;
	}

	/**
	 * @param responseDate
	 *            the responseDate to set
	 */
	public void setResponseDate(final String responseDate) {

		this.responseDate = responseDate;
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

	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
