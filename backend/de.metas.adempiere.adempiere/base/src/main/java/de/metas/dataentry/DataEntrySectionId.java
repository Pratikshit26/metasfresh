package de.metas.dataentry;

import static de.metas.util.Check.assumeGreaterOrEqualToZero;

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
public class DataEntrySectionId implements RepoIdAware
{
	public static final DataEntrySectionId DEFAULT = DataEntrySectionId.ofRepoId(0);

	public static DataEntrySectionId ofRepoId(final int repoId)
	{
		return new DataEntrySectionId(repoId);
	}

	public static DataEntrySectionId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	int repoId;

	@JsonCreator
	public DataEntrySectionId(final int repoId)
	{
		this.repoId = assumeGreaterOrEqualToZero(repoId, "repoId");
	}

	@Override
	@JsonValue // note: annotating just the repoId member worked "often" which was very annoying
	public int getRepoId()
	{
		return repoId;
	}
}
