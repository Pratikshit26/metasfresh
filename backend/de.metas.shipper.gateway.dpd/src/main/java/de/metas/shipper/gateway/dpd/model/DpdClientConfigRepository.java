/*
 * #%L
 * de.metas.shipper.gateway.dpd
     
 * #L%
 */

package de.metas.shipper.gateway.dpd.model;

import de.metas.cache.CCache;
import de.metas.shipping.ShipperId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Shipper;
import org.springframework.stereotype.Repository;

@Repository
public class DpdClientConfigRepository
{
	private final CCache<Integer, DpdClientConfig> cache = CCache.newCache(I_DPD_Shipper_Config.Table_Name, 10, CCache.EXPIREMINUTES_Never);

	@NonNull
	public DpdClientConfig getByShipperId(@NonNull final ShipperId shipperId)
	{
		final int repoId = shipperId.getRepoId();
		return cache.getOrLoad(repoId, () -> retrieveConfig(repoId));
	}

	@NonNull
	private static DpdClientConfig retrieveConfig(final int shipperId)
	{
		final I_DPD_Shipper_Config configPO = Services.get(IQueryBL.class)
				.createQueryBuilder(I_DPD_Shipper_Config.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DPD_Shipper_Config.COLUMNNAME_M_Shipper_ID, shipperId)
				.create()
				.firstOnly(I_DPD_Shipper_Config.class); // we have a UC on M_Shipper_ID
		if (configPO == null)
		{
			throw new AdempiereException("No DPD shipper configuration found for shipperId=" + shipperId);
		}

		return DpdClientConfig.builder()
				.loginApiUrl(configPO.getLoginApiUrl())
				.shipmentServiceApiUrl(configPO.getShipmentServiceApiUrl())
				.delisID(configPO.getDelisID())
				.delisPassword(configPO.getDelisPassword())
				.trackingUrlBase(retrieveTrackingUrl(configPO.getM_Shipper_ID()))
				.paperFormat(DpdPaperFormat.ofCode(configPO.getPaperFormat()))
				.shipperProduct(DpdShipperProduct.ofCode(configPO.getShipperProduct()))
				.build();
	}

	private static String retrieveTrackingUrl(final int shipperId)
	{
		final I_M_Shipper shipperPo = InterfaceWrapperHelper.load(shipperId, I_M_Shipper.class);
		return shipperPo.getTrackingURL();
	}
}
