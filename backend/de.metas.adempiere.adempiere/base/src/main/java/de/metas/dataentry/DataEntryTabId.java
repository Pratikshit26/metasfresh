package de.metas.dataentry;

import static de.metas.util.Check.assumeGreaterThanZero;

import java.util.Objects;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import de.metas.util.lang.RepoIdAware;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
     
 * #L%
 */

@Value
public class DataEntryTabId implements RepoIdAware
{
	public static DataEntryTabId ofRepoId(final int repoId)
	{
		return new DataEntryTabId(repoId);
	}

	public static DataEntryTabId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	int repoId;

	@JsonCreator
	public DataEntryTabId(final int repoId)
	{
		this.repoId = assumeGreaterThanZero(repoId, "repoId");
	}

	@Override
	@JsonValue // note: annotating just the repoId member worked "often" which was very annoying
	public int getRepoId()
	{
		return repoId;
	}

	public static boolean equals(@Nullable final DataEntryTabId id1, @Nullable final DataEntryTabId id2)
	{
		return Objects.equals(id1, id2);
	}
}
