package uk.co.xcordis.optimiser.dto;

import java.util.Map;

/**
 * The <code>GatewayBatchDataDto</code> class use to hold gateway batch data details in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
public class GatewayBatchDataDto {

	private String gatewayName;
	private Map<String, Object> gatewayParameter;

	/**
	 * @return the gatewayName
	 */
	public String getGatewayName() {

		return gatewayName;
	}

	/**
	 * @param gatewayName
	 *            the gatewayName to set
	 */
	public void setGatewayName(final String gatewayName) {

		this.gatewayName = gatewayName;
	}

	/**
	 * @return the gatewayParameter
	 */
	public Map<String, Object> getGatewayParameter() {

		return gatewayParameter;
	}

	/**
	 * @param gatewayParameter
	 *            the gatewayParameter to set
	 */
	public void setGatewayParameter(final Map<String, Object> gatewayParameter) {

		this.gatewayParameter = gatewayParameter;
	}

}
