package de.metas.process;

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
public class AdProcessId implements RepoIdAware
{
	@JsonCreator
	public static AdProcessId ofRepoId(final int repoId)
	{
		return new AdProcessId(repoId);
	}

	public static AdProcessId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new AdProcessId(repoId) : null;
	}

	public static int toRepoId(@Nullable final AdProcessId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	int repoId;

	private AdProcessId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "AD_Process_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static boolean equals(@Nullable final AdProcessId id1, @Nullable final AdProcessId id2) { return Objects.equals(id1, id2); }
}
