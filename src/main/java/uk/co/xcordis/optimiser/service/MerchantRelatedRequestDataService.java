package uk.co.xcordis.optimiser.service;

import java.util.List;
import java.util.UUID;

import uk.co.xcordis.optimiser.model.MerchantRelatedRequestData;

/**
 * The <code>MerchantRelatedRequestDataService</code> interface responsible for provide the Merchant Request Data related method in <b>Optimiser</b>
 * application.
 *
 * @author Rob Atkin
 */
public interface MerchantRelatedRequestDataService {

	/**
	 * This <code>addMerchantRelatedRequestData</code> method is used for store the MerchantRelatedRequestData details into the dataBase.
	 *
	 * @param merchantRelatedRequestData
	 */
	public void addMerchantRelatedRequestData(MerchantRelatedRequestData merchantRelatedRequestData);

	/**
	 * This <code>findById</code> method is used to get the MerchantRealtedRequestData object from database.
	 *
	 * @param merchantRequestDataId
	 * @return
	 */
	public MerchantRelatedRequestData findById(UUID merchantRequestDataId);

	/**
	 * This <code>updateMerchantRelatedRequestData</code> method is used to update the MerchantRelatedRequestData to database.
	 *
	 * @param merchantRelatedRequestData
	 */
	public void updateMerchantRelatedRequestData(MerchantRelatedRequestData merchantRelatedRequestData);

	/**
	 * This method <code>getListByStatus</code> is used for get the merchant request data list based on status param.
	 *
	 * @param status
	 * @return
	 */
	public List<MerchantRelatedRequestData> getListByStatus(String status);
}
