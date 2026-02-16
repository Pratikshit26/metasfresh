package de.metas.inventory;

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
public class InventoryLineId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static InventoryLineId ofRepoId(final int repoId)
	{
		return new InventoryLineId(repoId);
	}

	public static InventoryLineId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	private InventoryLineId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "M_InventoryLine_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

}
