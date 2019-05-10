package uk.co.xcordis.optimiser.dao;

import com.datastax.driver.mapping.annotations.Accessor;

import uk.co.xcordis.optimiser.model.UserRoleBaseUrl;

/**
 * The <code>UserRoleBaseUrlDao</code> interface responsible for provide the user role base urls related method in <b>optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Accessor
public interface UserRoleBaseUrlDao extends BaseDao<UserRoleBaseUrl> {

}
