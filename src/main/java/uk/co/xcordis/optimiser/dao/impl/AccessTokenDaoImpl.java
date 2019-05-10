package uk.co.xcordis.optimiser.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import uk.co.xcordis.optimiser.dao.AccessTokenDao;
import uk.co.xcordis.optimiser.model.AccessToken;
import uk.co.xcordis.optimiser.util.ApplicationTableConstants;

/**
 * The <code>AccessTokenDaoImpl</code> interface responsible for provide the Access Token related method in <b>optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Repository
public class AccessTokenDaoImpl extends BaseDaoImpl<AccessToken> implements AccessTokenDao {

	private AccessTokenDao accessTokenDao;

	private static final Logger LOGGER = LoggerFactory.getLogger(AccessTokenDaoImpl.class);

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.dao.impl.BaseDaoImpl#setMappper()
	 */
	@Override
	protected void setMappper() {

		mapper = getManager().mapper(AccessToken.class);
		tableName = ApplicationTableConstants.TABLENAME_ACCESSTOKEN;
		accessTokenDao = getManager().createAccessor(AccessTokenDao.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.co.xcordis.optimiser.dao.AccessTokenDao#getAccessTokenByOpenIdAndToken(java.lang.String, java.lang.String)
	 */
	@Override
	public AccessToken getAccessTokenByOpenIdAndToken(final String openId, final String accessToken) {

		LOGGER.info(" ==> Method ==> getAccessTokenByOpenIdAndToken ==> Called");
		return accessTokenDao.getAccessTokenByOpenIdAndToken(openId, accessToken);
	}

}
