/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

package de.metas.banking;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableSet;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Value
public class BankStatementId implements RepoIdAware
{
	int repoId;

	@NonNull
	@JsonCreator
	public static BankStatementId ofRepoId(final int repoId)
	{
		return new BankStatementId(repoId);
	}

	@Nullable
	public static BankStatementId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	public static Optional<BankStatementId> optionalOfRepoId(final int repoId) {return Optional.ofNullable(ofRepoIdOrNull(repoId));}

	public static Set<Integer> toIntSet(final Collection<BankStatementId> bankStatementIds)
	{
		if (bankStatementIds == null || bankStatementIds.isEmpty())
		{
			return ImmutableSet.of();
		}
		else
		{
			return bankStatementIds
					.stream()
					.map(BankStatementId::getRepoId)
					.collect(ImmutableSet.toImmutableSet());
		}
	}

	private BankStatementId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_BankStatement_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static boolean equals(@Nullable BankStatementId id1, @Nullable BankStatementId id2)
	{
		return Objects.equals(id1, id2);
	}

	public static int toRepoId(@Nullable BankStatementId id) {return id != null ? id.getRepoId() : -1;}
}
