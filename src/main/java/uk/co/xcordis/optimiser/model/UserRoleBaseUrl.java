package uk.co.xcordis.optimiser.model;

import java.util.List;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import uk.co.xcordis.optimiser.util.ApplicationTableConstants;

/**
 * The <code>UserRoleBaseUrl</code> model is used to store the user role base url details in <b>optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Table(name = ApplicationTableConstants.TABLENAME_USERROLEBASEURL)
public class UserRoleBaseUrl {

	@PartitionKey(0)
	private String role;

	private List<String> roleUrl;

	/**
	 * @return the role
	 */
	public String getRole() {

		return role;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRole(final String role) {

		this.role = role;
	}

	/**
	 * @return the roleUrl
	 */
	public List<String> getRoleUrl() {

		return roleUrl;
	}

	/**
	 * @param roleUrl
	 *            the roleUrl to set
	 */
	public void setRoleUrl(final List<String> roleUrl) {

		this.roleUrl = roleUrl;
	}

}
