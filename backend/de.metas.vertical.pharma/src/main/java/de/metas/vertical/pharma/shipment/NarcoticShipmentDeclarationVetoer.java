package de.metas.vertical.pharma.shipment;

import org.springframework.stereotype.Component;

import de.metas.inout.InOutAndLineId;
import de.metas.shipment.ShipmentDeclarationConfig;
import de.metas.shipment.ShipmentDeclarationVetoer;
import de.metas.vertical.pharma.PharmaProductRepository;
import de.metas.vertical.pharma.shipment.repo.PharmaShipmentConfigRepository;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma
     
 * #L%
 */

@Component
public class NarcoticShipmentDeclarationVetoer implements ShipmentDeclarationVetoer
{
	private final PharmaShipmentConfigRepository pharmaShipmentDeclarationConfigRepo;
	private final PharmaProductRepository pharmaProductRepo;

	private NarcoticShipmentDeclarationVetoer(
			@NonNull final PharmaShipmentConfigRepository pharmaShipmentDeclarationConfigRepo,
			@NonNull final PharmaProductRepository pharmaProductRepo)
	{
		this.pharmaShipmentDeclarationConfigRepo = pharmaShipmentDeclarationConfigRepo;
		this.pharmaProductRepo = pharmaProductRepo;
	}

	@Override
	public OnShipmentDeclarationConfig foundShipmentLineForConfig(
			final InOutAndLineId shipmentLineId,
			final ShipmentDeclarationConfig shipmentDeclarationConfig)
	{
		if (!pharmaShipmentDeclarationConfigRepo.isOnlyNarcoticProducts(shipmentDeclarationConfig))
		{
			return OnShipmentDeclarationConfig.I_DONT_CARE;
		}

		if (pharmaProductRepo.isLineForNarcoticProduct(shipmentLineId))
		{
			return OnShipmentDeclarationConfig.I_VETO;
		}

		return OnShipmentDeclarationConfig.I_DONT_CARE;
	}
}
