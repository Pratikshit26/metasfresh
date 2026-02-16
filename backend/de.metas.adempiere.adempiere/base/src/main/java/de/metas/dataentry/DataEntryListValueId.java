package de.metas.dataentry;

import static de.metas.util.Check.assumeGreaterThanZero;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import de.metas.util.lang.RepoIdAware;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@Value
public class DataEntryListValueId implements RepoIdAware
{
	public static DataEntryListValueId ofRepoId(final int repoId)
	{
		return new DataEntryListValueId(repoId);
	}

	public static DataEntryListValueId ofRepoIdOrNull(final int repoId)
	{
		if (repoId <= 0)
		{
			return null;
		}
		return new DataEntryListValueId(repoId);
	}

	public static int getRepoId(@Nullable final DataEntryListValueId dataEntryListValueId)
	{
		if (dataEntryListValueId == null)
		{
			return 0;
		}
		return dataEntryListValueId.getRepoId();
	}

	int repoId;

	@JsonCreator
	public DataEntryListValueId(final int repoId)
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
