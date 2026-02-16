/*
 * #%L
 * de.metas.serviceprovider.base
     
 * #L%
 */

package de.metas.serviceprovider.milestone;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class MilestoneId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static MilestoneId ofRepoId(final int repoId)
	{
		return new MilestoneId(repoId);
	}

	@Nullable
	public static MilestoneId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? new MilestoneId(repoId) : null;
	}

	private MilestoneId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "S_Milestone_ID");
	}

	@JsonValue
	public int toJson()
	{
		return getRepoId();
	}
}
