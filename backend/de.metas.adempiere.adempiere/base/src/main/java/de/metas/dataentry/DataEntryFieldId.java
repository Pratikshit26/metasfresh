package de.metas.dataentry;

import static de.metas.util.Check.assumeGreaterThanZero;

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
public class DataEntryFieldId implements RepoIdAware
{
	public static DataEntryFieldId ofRepoId(final int repoId)
	{
		return new DataEntryFieldId(repoId);
	}

	public static DataEntryFieldId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	int repoId;

	@JsonCreator
	private DataEntryFieldId(final int repoId)
	{
		this.repoId = assumeGreaterThanZero(repoId, "repoId");
	}

	@Override
	@JsonValue // note: annotating just the repoId member worked "often" which was very annoying
	public int getRepoId()
	{
		return repoId;
	}
}
