package de.metas.contracts.commission.commissioninstance.businesslogic;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import de.metas.util.lang.RepoIdAware;
import lombok.Value;

/*
 * #%L
 * de.metas.contracts
     
 * #L%
 */

@Value
public class CommissionSettingsLineId implements RepoIdAware
{
	int repoId;

	public static int toRepoId(@Nullable final CommissionSettingsLineId commissionSettingsLineId)
	{
		if (commissionSettingsLineId == null)
		{
			return -1;
		}
		return commissionSettingsLineId.getRepoId();
	}

	@JsonCreator
	public static CommissionSettingsLineId ofRepoId(final int repoId)
	{
		return new CommissionSettingsLineId(repoId);
	}

	public static CommissionSettingsLineId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		if (repoId == null || repoId <= 0)
		{
			return null;
		}
		return new CommissionSettingsLineId(repoId);
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
