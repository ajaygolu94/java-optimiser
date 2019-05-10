package uk.co.xcordis.optimiser.service;

import uk.co.xcordis.optimiser.model.UserRoleBaseUrl;

/**
 * The <code>UserRoleBaseUrlService</code> interface responsible for provide the user role base url related method in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
public interface UserRoleBaseUrlService {

	/**
	 * This method <code>getUserRoleBaseUrlByRole</code> is used for get the User Role Base Url details based on role params.
	 *
	 * @param role
	 * @return
	 */
	public UserRoleBaseUrl getUserRoleBaseUrlByRole(String role);
}
