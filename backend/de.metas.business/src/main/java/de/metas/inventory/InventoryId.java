package de.metas.inventory;

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
public class InventoryId implements RepoIdAware
{
	@JsonCreator
	public static InventoryId ofRepoId(final int repoId)
	{
		return new InventoryId(repoId);
	}

	public static InventoryId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	public static int toRepoId(@Nullable final InventoryId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	int repoId;

	private InventoryId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "M_Inventory_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
