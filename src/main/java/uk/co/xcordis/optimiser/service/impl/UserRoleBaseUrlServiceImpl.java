package uk.co.xcordis.optimiser.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.xcordis.optimiser.model.UserRoleBaseUrl;
import uk.co.xcordis.optimiser.service.UserRoleBaseUrlService;
import uk.co.xcordis.optimiser.util.DaoFactory;

/**
 * The <code>UserRoleBaseUrlServiceImpl</code> class responsible for implement the UserRoleBaseUrlService method in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Service
public class UserRoleBaseUrlServiceImpl implements UserRoleBaseUrlService {

	@Autowired
	private DaoFactory daoFactory;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleBaseUrlServiceImpl.class);

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.service.UserRoleBaseUrlService#getUserRoleBaseUrlByRole(java.lang.String)
	 */
	@Override
	public UserRoleBaseUrl getUserRoleBaseUrlByRole(final String role) {

		LOGGER.info(" ==> Method : getUserRoleBaseUrlByRole ==> Called");
		return (UserRoleBaseUrl) daoFactory.getUserRoleBaseUrlDao().findById(role);
	}

}
