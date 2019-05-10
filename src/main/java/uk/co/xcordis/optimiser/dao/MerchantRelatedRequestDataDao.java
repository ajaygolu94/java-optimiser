package uk.co.xcordis.optimiser.dao;

import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Query;

import uk.co.xcordis.optimiser.model.MerchantRelatedRequestData;

/**
 * The <code>MerchantRelatedRequestDataDao</code> interface responsible for provide the Access Token related method in <b>optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Accessor
public interface MerchantRelatedRequestDataDao extends BaseDao<MerchantRelatedRequestData> {

	/**
	 * This <code>getListByStatus</code> method is used for get merchant related request data list based on status params.
	 *
	 * @param status
	 * @return
	 */
	@Query("select * from merchantrelatedrequestdata where status = ?")
	public Result<MerchantRelatedRequestData> getListByStatus(String status);
}
