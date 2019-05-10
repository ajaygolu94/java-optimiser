package uk.co.xcordis.optimiser.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import uk.co.xcordis.optimiser.dao.UserRoleBaseUrlDao;
import uk.co.xcordis.optimiser.model.UserRoleBaseUrl;
import uk.co.xcordis.optimiser.util.ApplicationTableConstants;

/**
 * The <code>UserRoleBaseUrlDaoImpl</code> interface responsible for implement the UserRoleBaseUrlDao method in <b>optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Repository
public class UserRoleBaseUrlDaoImpl extends BaseDaoImpl<UserRoleBaseUrl> implements UserRoleBaseUrlDao {

	private UserRoleBaseUrlDao userRoleBaseUrlDao;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleBaseUrlDaoImpl.class);

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.dao.impl.BaseDaoImpl#setMappper()
	 */
	@Override
	protected void setMappper() {

		mapper = getManager().mapper(UserRoleBaseUrl.class);
		tableName = ApplicationTableConstants.TABLENAME_USERROLEBASEURL;
		userRoleBaseUrlDao = getManager().createAccessor(UserRoleBaseUrlDao.class);
	}

}
