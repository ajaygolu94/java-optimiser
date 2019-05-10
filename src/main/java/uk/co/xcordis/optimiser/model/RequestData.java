/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.model;

import java.io.Serializable;
import java.util.UUID;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import uk.co.xcordis.optimiser.util.ApplicationTableConstants;

/**
 * The <code>RequestData</code> model is used to store the Request Data details into database in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 *
 */
@Table(name = ApplicationTableConstants.TABLENAME_REQUESTDATA)
public class RequestData implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@PartitionKey(0)
	private UUID requestDataId;

	private String sourceMerchantId;

	private String ipaddress;

	private String requestData;

	private String requestDate;

	private String responseData;

	private String responseDate;

	private String responseStatus;

	private String status;

	private String auditTimeStamp;

	private String requestType;

	/**
	 * @return the requestDataId
	 */
	public UUID getRequestDataId() {

		return requestDataId;
	}

	/**
	 * @param requestDataId
	 *            the requestDataId to set
	 */
	public void setRequestDataId(final UUID requestDataId) {

		this.requestDataId = requestDataId;
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
	 * @return the ipaddress
	 */
	public String getIpaddress() {

		return ipaddress;
	}

	/**
	 * @param ipaddress
	 *            the ipaddress to set
	 */
	public void setIpaddress(final String ipaddress) {

		this.ipaddress = ipaddress;
	}

	/**
	 * @return the requestdata
	 */
	public String getRequestData() {

		return requestData;
	}

	/**
	 * @param requestData
	 *            the requestdata to set
	 */
	public void setRequestData(final String requestData) {

		this.requestData = requestData;
	}

	/**
	 * @return the requestdate
	 */
	public String getRequestDate() {

		return requestDate;
	}

	/**
	 * @param requestDate
	 *            the requestdate to set
	 */
	public void setRequestDate(final String requestDate) {

		this.requestDate = requestDate;
	}

	/**
	 * @return the responsedata
	 */
	public String getResponseData() {

		return responseData;
	}

	/**
	 * @param responseData
	 *            the responsedata to set
	 */
	public void setResponseData(final String responseData) {

		this.responseData = responseData;
	}

	/**
	 * @return the responsedate
	 */
	public String getResponseDate() {

		return responseDate;
	}

	/**
	 * @param responseDate
	 *            the responsedate to set
	 */
	public void setResponseDate(final String responseDate) {

		this.responseDate = responseDate;
	}

	/**
	 * @return the responsestatus
	 */
	public String getResponseStatus() {

		return responseStatus;
	}

	/**
	 * @param responsestatus
	 *            the responsestatus to set
	 */
	public void setResponseStatus(final String responseStatus) {

		this.responseStatus = responseStatus;
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

}
