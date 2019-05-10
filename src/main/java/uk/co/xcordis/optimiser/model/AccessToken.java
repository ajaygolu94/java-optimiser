package uk.co.xcordis.optimiser.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import uk.co.xcordis.optimiser.util.ApplicationTableConstants;

/**
 * The <code>AccessToken</code> model is used to store the AccessToken details in <b>optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Table(name = ApplicationTableConstants.TABLENAME_ACCESSTOKEN)
public class AccessToken {

	@PartitionKey(0)
	private String openid;

	private String accesstoken;

	private Boolean active;

	private String createddate;

	private String expirydate;

	/**
	 * @return the openid
	 */
	public String getOpenid() {

		return openid;
	}

	/**
	 * @param openid
	 *            the openid to set
	 */
	public void setOpenid(final String openid) {

		this.openid = openid;
	}

	/**
	 * @return the accesstoken
	 */
	public String getAccesstoken() {

		return accesstoken;
	}

	/**
	 * @param accesstoken
	 *            the accesstoken to set
	 */
	public void setAccesstoken(final String accesstoken) {

		this.accesstoken = accesstoken;
	}

	/**
	 * @return the active
	 */
	public Boolean getActive() {

		return active;
	}

	/**
	 * @param active
	 *            the active to set
	 */
	public void setActive(final Boolean active) {

		this.active = active;
	}

	/**
	 * @return the createddate
	 */
	public String getCreateddate() {

		return createddate;
	}

	/**
	 * @param createddate
	 *            the createddate to set
	 */
	public void setCreateddate(final String createddate) {

		this.createddate = createddate;
	}

	/**
	 * @return the expirydate
	 */
	public String getExpirydate() {

		return expirydate;
	}

	/**
	 * @param expirydate
	 *            the expirydate to set
	 */
	public void setExpirydate(final String expirydate) {

		this.expirydate = expirydate;
	}

	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
