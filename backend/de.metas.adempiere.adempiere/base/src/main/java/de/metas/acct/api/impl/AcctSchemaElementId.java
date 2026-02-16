package de.metas.acct.api.impl;

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
public class AcctSchemaElementId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	private AcctSchemaElementId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_AcctSchema_Element_ID");
	}

	public static AcctSchemaElementId ofRepoId(final int repoId)
	{
		return new AcctSchemaElementId(repoId);
	}

	@Nullable
	public static AcctSchemaElementId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	public static int toRepoId(final AcctSchemaElementId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	public static boolean equals(@Nullable final AcctSchemaElementId id1, @Nullable final AcctSchemaElementId id2)
	{
		return Objects.equals(id1, id2);
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
