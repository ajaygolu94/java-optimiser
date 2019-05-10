package uk.co.xcordis.optimiser.dao;

import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Query;

import uk.co.xcordis.optimiser.model.AccessToken;

/**
 * The <code>AccessTokenDao</code> interface responsible for provide the Access Token related method in <b>optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Accessor
public interface AccessTokenDao extends BaseDao<AccessToken> {

	/**
	 * This <code>getAccessTokenByOpenIdAndToken</code> method is used to get the accesstoken details based on openid and token params.
	 *
	 * @param openId
	 * @param accessToken
	 * @return
	 */
	@Query("select * from accesstoken where openid = ? and accesstoken = ?")
	public AccessToken getAccessTokenByOpenIdAndToken(String openId, String accessToken);
}
