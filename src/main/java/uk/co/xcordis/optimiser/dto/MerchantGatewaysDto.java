package uk.co.xcordis.optimiser.dto;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import uk.co.xcordis.optimiser.model.MerchantGateways;

/**
 * The <code>MerchantGatewaysDto</code> Dto is used to contain the all the properties of merchantGateways with extra fields in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 *
 */
public class MerchantGatewaysDto extends MerchantGateways {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private List<String> merchantGatewayParameterList;

	/**
	 * @return the merchantGatewayParameterList
	 */
	public List<String> getMerchantGatewayParameterList() {

		return merchantGatewayParameterList;
	}

	/**
	 * @param merchantGatewayParameterList
	 *            the merchantGatewayParameterList to set
	 */
	public void setMerchantGatewayParameterList(final List<String> merchantGatewayParameterList) {

		this.merchantGatewayParameterList = merchantGatewayParameterList;
	}

	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
