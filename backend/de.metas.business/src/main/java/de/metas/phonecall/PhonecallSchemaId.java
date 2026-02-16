package de.metas.phonecall;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@Value
public class PhonecallSchemaId implements RepoIdAware
{
	@JsonCreator
	public static PhonecallSchemaId ofRepoId(final int repoId)
	{
		return new PhonecallSchemaId(repoId);
	}

	public static PhonecallSchemaId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	int repoId;

	private PhonecallSchemaId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_Phonecall_Schema_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
