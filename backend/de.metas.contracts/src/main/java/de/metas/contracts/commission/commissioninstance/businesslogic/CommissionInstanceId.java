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
public class CommissionInstanceId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static CommissionInstanceId ofRepoId(final int repoId)
	{
		return new CommissionInstanceId(repoId);
	}

	public static CommissionInstanceId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		if (repoId == null || repoId <= 0)
		{
			return null;
		}
		return new CommissionInstanceId(repoId);
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

}
