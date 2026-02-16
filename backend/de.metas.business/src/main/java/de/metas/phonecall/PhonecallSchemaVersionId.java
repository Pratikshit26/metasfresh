package de.metas.phonecall;

import javax.annotation.Nullable;

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
public class PhonecallSchemaVersionId implements RepoIdAware
{
	int repoId;

	@NonNull
	PhonecallSchemaId phonecallSchemaId;

	public static PhonecallSchemaVersionId ofRepoId(@NonNull final PhonecallSchemaId phonecallSchemaId, final int phonecallSchemaVersionId)
	{
		return new PhonecallSchemaVersionId(phonecallSchemaId, phonecallSchemaVersionId);
	}

	public static PhonecallSchemaVersionId ofRepoId(final int phonecallSchemaId, final int phonecallSchemaVersionId)
	{
		return new PhonecallSchemaVersionId(PhonecallSchemaId.ofRepoId(phonecallSchemaId), phonecallSchemaVersionId);
	}

	public static PhonecallSchemaVersionId ofRepoIdOrNull(
			@Nullable final PhonecallSchemaId phonecallSchemaId,
			final int phonecallSchemaVersionId)
	{
		return phonecallSchemaId != null && phonecallSchemaVersionId > 0 ? ofRepoId(phonecallSchemaId, phonecallSchemaVersionId) : null;
	}

	private PhonecallSchemaVersionId(@NonNull final PhonecallSchemaId phonecallSchemaId, final int phonecallSchemaVersionId)
	{
		this.repoId = Check.assumeGreaterThanZero(phonecallSchemaVersionId, "phonecallSchemaVersionId");
		this.phonecallSchemaId = phonecallSchemaId;
	}
}
