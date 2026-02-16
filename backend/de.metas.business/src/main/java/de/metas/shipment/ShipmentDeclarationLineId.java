package de.metas.shipment;

import javax.annotation.Nullable;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */

@Value
public class ShipmentDeclarationLineId implements RepoIdAware
{
	int repoId;

	@NonNull
	ShipmentDeclarationId shipmentDeclarationId;

	public static ShipmentDeclarationLineId ofRepoId(@NonNull final ShipmentDeclarationId shipmentDeclarationId, final int shipmentDeclarationLineId)
	{
		return new ShipmentDeclarationLineId(shipmentDeclarationId, shipmentDeclarationLineId);
	}

	public static ShipmentDeclarationLineId ofRepoId(final int shipmentDeclarationId, final int shipmentDeclarationLineId)
	{
		return new ShipmentDeclarationLineId(ShipmentDeclarationId.ofRepoId(shipmentDeclarationId), shipmentDeclarationLineId);
	}

	public static ShipmentDeclarationLineId ofRepoIdOrNull(
			@Nullable final ShipmentDeclarationId shipmentDeclarationId,
			final int shipmentDeclarationLineId)
	{
		return shipmentDeclarationId != null && shipmentDeclarationLineId > 0 ? ofRepoId(shipmentDeclarationId, shipmentDeclarationLineId) : null;
	}

	private ShipmentDeclarationLineId(@NonNull final ShipmentDeclarationId shipmentDeclarationId, final int shipmentDeclarationLineId)
	{
		this.repoId = Check.assumeGreaterThanZero(shipmentDeclarationLineId, "shipmentDeclarationLineId");
		this.shipmentDeclarationId = shipmentDeclarationId;
	}

}


