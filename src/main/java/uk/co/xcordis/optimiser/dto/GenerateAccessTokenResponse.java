package uk.co.xcordis.optimiser.dto;

import java.io.Serializable;

/**
 * The <code>GenerateAccessTokenResponse</code> class use to hold generate access token response details in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
public class GenerateAccessTokenResponse extends BaseResponse implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String userId;
	private String accessToken;
	private String expiryDate;

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
	 * @return the accessToken
	 */
	public String getAccessToken() {

		return accessToken;
	}

	/**
	 * @param accessToken
	 *            the accessToken to set
	 */
	public void setAccessToken(final String accessToken) {

		this.accessToken = accessToken;
	}

	/**
	 * @return the expiryDate
	 */
	public String getExpiryDate() {

		return expiryDate;
	}

	/**
	 * @param expiryDate
	 *            the expiryDate to set
	 */
	public void setExpiryDate(final String expiryDate) {

		this.expiryDate = expiryDate;
	}

}
