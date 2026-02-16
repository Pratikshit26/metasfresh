/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

package de.metas.shipping.model;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

@Value
public class ShipperTransportationId implements RepoIdAware
{
	int repoId;

	@NonNull
	@JsonCreator
	public static ShipperTransportationId ofRepoId(final int repoId)
	{
		return new ShipperTransportationId(repoId);
	}

	@Nullable
	public static ShipperTransportationId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	private ShipperTransportationId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "M_ShipperTransportation_ID");
	}


	public static int toRepoId(@Nullable final ShipperTransportationId shipperTransportationId)
	{
		return shipperTransportationId != null ? shipperTransportationId.getRepoId() : -1;
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
