package uk.co.xcordis.optimiser.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.datastax.driver.mapping.Result;

import uk.co.xcordis.optimiser.dao.MerchantRelatedRequestDataDao;
import uk.co.xcordis.optimiser.model.MerchantRelatedRequestData;
import uk.co.xcordis.optimiser.util.ApplicationTableConstants;

/**
 * The <code>MerchantRelatedRequestDataDaoImpl</code> interface responsible for provide the MerchantRelatedRequestDataDao related method in <b>optimiser</b>
 * application.
 *
 * @author Rob Atkin
 */
@Repository
public class MerchantRelatedRequestDataDaoImpl extends BaseDaoImpl<MerchantRelatedRequestData> implements MerchantRelatedRequestDataDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(MerchantRelatedRequestDataDaoImpl.class);

	private MerchantRelatedRequestDataDao merchantRelatedRequestDataDao;

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.dao.impl.BaseDaoImpl#setMappper()
	 */
	@Override
	protected void setMappper() {

		mapper = getManager().mapper(MerchantRelatedRequestData.class);
		tableName = ApplicationTableConstants.TABLENAME_MERCHANTRELATEDREQUESTDATA;
		merchantRelatedRequestDataDao = getManager().createAccessor(MerchantRelatedRequestDataDao.class);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.optimiser.dao.MerchantRelatedRequestDataDao#getListByStatus(java.lang.String)
	 */
	@Override
	public Result<MerchantRelatedRequestData> getListByStatus(final String status) {

		LOGGER.info(" ==> Method ==> getListByStatus ==> Called");
		return merchantRelatedRequestDataDao.getListByStatus(status);
	}

}
