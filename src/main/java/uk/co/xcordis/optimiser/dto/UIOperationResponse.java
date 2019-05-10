/**
 *
 */
package uk.co.xcordis.optimiser.dto;

import java.io.Serializable;

/**
 * The <code>UIOperationResponse</code> class use to hold data of request and also extends by base response class in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
public class UIOperationResponse extends BaseResponse implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Object responsedata;
	private String code;

	/**
	 * @return the responsedata
	 */
	public Object getResponsedata() {

		return responsedata;
	}

	/**
	 * @param responsedata
	 *            the responsedata to set
	 */
	public void setResponsedata(Object responsedata) {

		this.responsedata = responsedata;
	}

	/**
	 * @return the code
	 */
	public String getCode() {

		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {

		this.code = code;
	}

}
