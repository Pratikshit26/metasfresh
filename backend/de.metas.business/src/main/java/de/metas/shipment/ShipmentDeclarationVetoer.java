package de.metas.shipment;

import de.metas.inout.InOutAndLineId;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */
@FunctionalInterface
public interface ShipmentDeclarationVetoer
{

	public enum OnShipmentDeclarationConfig
	{
		I_VETO,

		I_DONT_CARE
	}

	public OnShipmentDeclarationConfig foundShipmentLineForConfig(InOutAndLineId shipmentLineId, ShipmentDeclarationConfig shipmentDeclarationConfig);
}
