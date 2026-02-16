package de.metas.error;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@Value
public class AdIssueId implements RepoIdAware
{
	@JsonCreator
	public static AdIssueId ofRepoId(final int repoId)
	{
		return new AdIssueId(repoId);
	}

	public static AdIssueId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	public static int toRepoId(@Nullable final AdIssueId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	int repoId;

	private AdIssueId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "AD_Issue_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static boolean equals(@Nullable final AdIssueId id1, @Nullable final AdIssueId id2) {return Objects.equals(id1, id2);}
}
