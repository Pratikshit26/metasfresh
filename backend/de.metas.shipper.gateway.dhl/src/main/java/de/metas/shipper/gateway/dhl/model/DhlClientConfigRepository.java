/*
 * #%L
 * de.metas.shipper.gateway.dhl
     
 * #L%
 */

package de.metas.shipper.gateway.dhl.model;

import de.metas.cache.CCache;
import de.metas.shipping.ShipperId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Shipper;
import org.springframework.stereotype.Repository;

@Repository
public class DhlClientConfigRepository
{
	private final CCache<Integer, DhlClientConfig> cache = CCache.newCache(I_DHL_Shipper_Config.Table_Name, 1, CCache.EXPIREMINUTES_Never);

	@NonNull
	public DhlClientConfig getByShipperId(@NonNull final ShipperId shipperId)
	{
		final int repoId = shipperId.getRepoId();
		return cache.getOrLoad(repoId, () -> retrieveConfig(repoId));
	}

	private static DhlClientConfig retrieveConfig(final int shipperId)
	{
		final I_DHL_Shipper_Config configPO = Services.get(IQueryBL.class)
				.createQueryBuilder(I_DHL_Shipper_Config.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DHL_Shipper_Config.COLUMNNAME_M_Shipper_ID, shipperId)
				.orderBy(I_DHL_Shipper_Config.COLUMNNAME_DHL_Shipper_Config_ID)
				.create()
				.first();
		if (configPO == null)
		{
			throw new AdempiereException("No DHL shipper configuration found for shipperId=" + shipperId);
		}
		return DhlClientConfig.builder()
				.applicationID(configPO.getapplicationID())
				.applicationToken(configPO.getApplicationToken())
				.baseUrl(configPO.getdhl_api_url())
				.accountNumber(configPO.getAccountNumber())
				.username(configPO.getUserName())
				.signature(configPO.getSignature())
				.lengthUomId(UomId.ofRepoId(configPO.getDhl_LenghtUOM_ID()))
				.trackingUrlBase(retrieveTrackingUrl(configPO.getM_Shipper_ID()))
				.build();
	}

	private static String retrieveTrackingUrl(final int m_shipper_id)
	{
		final I_M_Shipper shipperPo = InterfaceWrapperHelper.load(m_shipper_id, I_M_Shipper.class);
		return shipperPo.getTrackingURL();
	}
}
