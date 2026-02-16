package de.metas.shipment;

import javax.annotation.Nullable;

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
public class ShipmentDeclarationId implements RepoIdAware
{
	@JsonCreator
	public static ShipmentDeclarationId ofRepoId(final int repoId)
	{
		return new ShipmentDeclarationId(repoId);
	}

	public static ShipmentDeclarationId ofRepoIdOrNull(@Nullable final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	int repoId;

	private ShipmentDeclarationId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "M_Shipment_Declaration_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static int toRepoId(final ShipmentDeclarationId id)
	{
		return id != null ? id.getRepoId() : -1;
	}
}
