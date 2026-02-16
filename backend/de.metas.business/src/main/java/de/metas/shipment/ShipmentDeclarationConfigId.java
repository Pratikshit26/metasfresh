package de.metas.shipment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */
@Value
public class ShipmentDeclarationConfigId implements RepoIdAware
{
	@JsonCreator
	public static ShipmentDeclarationConfigId ofRepoId(final int repoId)
	{
		return new ShipmentDeclarationConfigId(repoId);
	}

	public static ShipmentDeclarationConfigId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	int repoId;

	private ShipmentDeclarationConfigId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "M_Shipment_Declaration_Config_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

}
