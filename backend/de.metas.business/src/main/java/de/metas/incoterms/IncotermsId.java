package de.metas.incoterms;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.acct.api.AccountId;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */
@Value
public class IncotermsId implements RepoIdAware
{
	@JsonCreator
	public static IncotermsId ofRepoId(final int repoId)
	{
		return new IncotermsId(repoId);
	}

	public static IncotermsId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	public static int toRepoId(@Nullable final IncotermsId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	int repoId;

	private IncotermsId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_Incoterms_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

}
