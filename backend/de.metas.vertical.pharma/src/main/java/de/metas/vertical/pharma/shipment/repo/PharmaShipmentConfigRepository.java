package de.metas.vertical.pharma.shipment.repo;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import org.springframework.stereotype.Repository;

import de.metas.shipment.ShipmentDeclarationConfig;
import de.metas.shipment.ShipmentDeclarationConfigId;
import de.metas.vertical.pharma.shipment.model.I_M_Shipment_Declaration_Config;

/*
 * #%L
 * metasfresh-pharma
     
 * #L%
 */
@Repository
public class PharmaShipmentConfigRepository
{

	public boolean isOnlyNarcoticProducts(final ShipmentDeclarationConfig config)
	{
		final ShipmentDeclarationConfigId configId = config.getId();
		final I_M_Shipment_Declaration_Config configRecord = load(configId, I_M_Shipment_Declaration_Config.class);

		return configRecord.isOnlyNarcoticProducts();
	}
}
