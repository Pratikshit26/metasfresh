package de.metas.phonecall;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@Value
public class PhonecallSchemaVersionLineId implements RepoIdAware
{
	@NonNull
	PhonecallSchemaVersionId versionId;

	int repoId;

	public static PhonecallSchemaVersionLineId ofRepoId(@NonNull final PhonecallSchemaVersionId versionId, final int lineId)
	{
		return new PhonecallSchemaVersionLineId(versionId, lineId);
	}

	private PhonecallSchemaVersionLineId(
			@NonNull final PhonecallSchemaVersionId versionId,
			final int lineId)
	{
		this.versionId = versionId;
		this.repoId = Check.assumeGreaterThanZero(lineId, "lineId");
	}
}
