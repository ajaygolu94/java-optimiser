package uk.co.xcordis.optimiser.dto;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * The <code>GatewayBatchDataApiRequest</code> class use to hold gateway batch data api request details in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
public class GatewayBatchDataApiRequest extends BaseBatchDataApiRequest implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private List<GatewayBatchDataDto> data;

	/**
	 * @return the data
	 */
	public List<GatewayBatchDataDto> getData() {

		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final List<GatewayBatchDataDto> data) {

		this.data = data;
	}

	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
