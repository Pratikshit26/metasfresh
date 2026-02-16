package de.metas.dataentry.data;

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
public class DataEntryRecordId implements RepoIdAware
{
	@JsonCreator
	public static DataEntryRecordId ofRepoId(final int repoId)
	{
		return new DataEntryRecordId(repoId);
	}

	public static DataEntryRecordId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	int repoId;

	public DataEntryRecordId(final int repoId)
	{
		this.repoId = assumeGreaterThanZero(repoId, "DataEntry_Record_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
