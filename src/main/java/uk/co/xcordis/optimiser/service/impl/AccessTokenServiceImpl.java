package uk.co.xcordis.optimiser.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.xcordis.optimiser.model.AccessToken;
import uk.co.xcordis.optimiser.model.ApplicationUser;
import uk.co.xcordis.optimiser.service.AccessTokenService;
import uk.co.xcordis.optimiser.util.ApplicationConstants;
import uk.co.xcordis.optimiser.util.ApplicationUtils;
import uk.co.xcordis.optimiser.util.DaoFactory;
import uk.co.xcordis.optimiser.util.ServiceRegistry;

/**
 * The <code>AccessTokenServiceImpl</code> class responsible for implement the AccessTokenService method in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Service
public class AccessTokenServiceImpl implements AccessTokenService {

	@Autowired
	private DaoFactory daoFactory;

	@Autowired
	private ServiceRegistry serviceRegistry;

	private static final Logger LOGGER = LoggerFactory.getLogger(AccessTokenServiceImpl.class);

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.AccessTokenService#addAccessToken(uk.co.xcordis.optimiser.model.AccessToken)
	 */
	@Override
	public void addAccessToken(final AccessToken accessToken) {

		LOGGER.info(" ==> Method ==> addAccessToken ==> called");

		accessToken.setAccesstoken(ApplicationUtils.generateSecretKey());
		accessToken.setActive(Boolean.TRUE);
		accessToken.setCreateddate(ApplicationUtils.getCurrentTimeStamp());
		accessToken.setExpirydate(ApplicationUtils.getExpiryDateForAccessToken(ApplicationUtils.getCurrentTimeStamp()));
		daoFactory.getAccessTokenDao().add(accessToken);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.AccessTokenService#findById(java.lang.Object[])
	 */
	@Override
	public AccessToken findById(final Object... objects) {

		LOGGER.info(" ==> Method ==> findById ==> called");
		return (AccessToken) daoFactory.getAccessTokenDao().findById(objects);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.AccessTokenService#getAccessTokenByOpenIdAndToken(java.lang.String, java.lang.String)
	 */
	@Override
	public AccessToken getAccessTokenByOpenIdAndToken(final String openId, final String accessToken) {

		LOGGER.info(" ==> Method ==> getAccessTokenByOpenIdAndToken ==> called");
		return daoFactory.getAccessTokenDao().getAccessTokenByOpenIdAndToken(openId, accessToken);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.AccessTokenService#updateAccessToken(uk.co.xcordis.optimiser.model.AccessToken)
	 */
	@Override
	public void updateAccessToken(final AccessToken accessToken) {

		LOGGER.info(" ==> Method ==> updateAccessToken ==> called");
		daoFactory.getAccessTokenDao().update(accessToken);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.AccessTokenService#isAccessTokenExpired(java.lang.String)
	 */
	@Override
	public Boolean isAccessTokenExpired(final String expiryDate) {

		LOGGER.info(" ==> Method ==> isAccessTokenExpired ==> called");

		try {

			final Date expiryDateObj = new SimpleDateFormat(ApplicationConstants.DD_MM_YYYY_HH_MM_SS_AM_PM).parse(expiryDate);

			if (expiryDateObj.before(new Date())) {

				return Boolean.TRUE;
			}

		} catch (final Exception e) {
			LOGGER.error(" ==> Method ==> isAccessTokenExpired ==> Exception : " + e);
			return Boolean.FALSE;
		}

		return Boolean.FALSE;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.AccessTokenService#generateAccessToken(java.lang.String)
	 */
	@Override
	public Boolean generateAccessToken(final String openId) {

		LOGGER.info(" ==> Method ==> generateAccessToken ==> called");

		try {

			final AccessToken accessToken = findById(openId);

			// Remove accessToken, If already exist to DB
			if (accessToken != null) {
				daoFactory.getAccessTokenDao().delete(accessToken);
			}

			final AccessToken generateNewAccessToken = new AccessToken();
			generateNewAccessToken.setOpenid(openId);
			addAccessToken(generateNewAccessToken);

			return Boolean.TRUE;

		} catch (final Exception e) {
			return Boolean.FALSE;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.AccessTokenService#authenticateGenerateAccessTokenRequest(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Boolean authenticateGenerateAccessTokenRequest(final String openId, final String email, final String secretKey) {

		LOGGER.info(" ==> Method ==> authenticateGenerateAccessTokenRequest ==> Called");

		final ApplicationUser applicationUser = serviceRegistry.getUserService().getUserByOpenId(openId);

		if (applicationUser != null && !ApplicationUtils.isEmpty(applicationUser.getEmail()) && !ApplicationUtils.isEmpty(applicationUser.getSecretKey())
				&& applicationUser.getEmail().equals(email) && applicationUser.getSecretKey().equals(secretKey)) {
			return Boolean.TRUE;
		}

		return Boolean.FALSE;
	}
}
