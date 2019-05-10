package uk.co.xcordis.optimiser.service;

import uk.co.xcordis.optimiser.model.AccessToken;

/**
 * The <code>AccessTokenService</code> interface responsible for provide the Access Token related method in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
public interface AccessTokenService {

	/**
	 * This <code>addAccessToken</code> method is used for save the AccessToken details into the dataBase.
	 *
	 * @param accessToken
	 */
	public void addAccessToken(AccessToken accessToken);

	/**
	 * This <code>findById</code> method is used to get the AccessToken object from database.
	 *
	 * @param token
	 * @return
	 */
	public AccessToken findById(Object... objects);

	/**
	 * This <code>getAccessTokenByOpenIdAndToken</code> method is used to get the accesstoken details based on openid and token params.
	 *
	 * @param openId
	 * @param accessToken
	 * @return
	 */
	public AccessToken getAccessTokenByOpenIdAndToken(String openId, String accessToken);

	/**
	 * This <code>updateAccessToken</code> method is used to update the AccessToken to database.
	 *
	 * @param accessToken
	 */
	public void updateAccessToken(AccessToken accessToken);

	/**
	 * This <code>isAccessTokenExpired</code> method is used to check the given access token is Expired or not.
	 *
	 * @param expiryDate
	 * @return
	 */
	public Boolean isAccessTokenExpired(String expiryDate);

	/**
	 * This method <code>generateAccessToken</code> is used for generate the access token based on openid and add it to database.
	 *
	 * @param openId
	 * @return
	 */
	public Boolean generateAccessToken(String openId);

	/**
	 * This method <code>authenticateGenerateAccessTokenRequest</code> is used for authenticate the generate access token api request.
	 *
	 * @param openId
	 * @param email
	 * @param secretKey
	 * @return
	 */
	public Boolean authenticateGenerateAccessTokenRequest(String openId, String email, String secretKey);
}
