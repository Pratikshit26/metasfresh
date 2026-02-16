/*
 * #%L
 * de.metas.serviceprovider.base
     
 * #L%
 */

package de.metas.serviceprovider.issue;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class IssueId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static IssueId ofRepoId(final int repoId)
	{
		return new IssueId(repoId);
	}

	@Nullable
	public static IssueId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? new IssueId(repoId) : null;
	}

	private IssueId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "S_Issue_ID");
	}

	@JsonValue
	public int toJson()
	{
		return getRepoId();
	}

	public boolean equalsNullSafe(@Nullable final IssueId issueId)
	{
		return issueId != null && issueId.getRepoId() == this.repoId;
	}
}
