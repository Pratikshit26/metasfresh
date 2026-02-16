package de.metas.acct.api;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
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

/**
 * Chart of Accounts ID (C_Element_ID)
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class ChartOfAccountsId implements RepoIdAware
{
	@JsonCreator
	public static ChartOfAccountsId ofRepoId(final int repoId)
	{
		return new ChartOfAccountsId(repoId);
	}

	@Nullable
	public static ChartOfAccountsId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	public static int toRepoId(final ChartOfAccountsId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	int repoId;

	private ChartOfAccountsId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_Element_ID (i.e. Chart of Accounts ID)");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static boolean equals(@Nullable final ChartOfAccountsId id1, @Nullable final ChartOfAccountsId id2)
	{
		return Objects.equals(id1, id2);
	}
}
