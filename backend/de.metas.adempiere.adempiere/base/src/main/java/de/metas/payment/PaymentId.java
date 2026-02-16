package de.metas.payment;

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

/*
 * #%L
 * de.metas.business
     
 * #L%
 */

@Value
public class PaymentId implements RepoIdAware
{
	@JsonCreator
	public static PaymentId ofRepoId(final int repoId) {return new PaymentId(repoId);}

	@Nullable
	public static PaymentId ofRepoIdOrNull(final int repoId) {return repoId > 0 ? new PaymentId(repoId) : null;}

	public static Optional<PaymentId> optionalOfRepoId(final int repoId) {return Optional.ofNullable(ofRepoIdOrNull(repoId));}

	public static int toRepoId(final PaymentId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	public static ImmutableSet<Integer> toIntSet(@NonNull final Collection<PaymentId> ids)
	{
		if (ids.isEmpty())
		{
			return ImmutableSet.of();
		}

		return ids.stream().map(PaymentId::getRepoId).collect(ImmutableSet.toImmutableSet());
	}

	int repoId;

	private PaymentId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_Payment_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static boolean equals(@Nullable PaymentId id1, @Nullable PaymentId id2) {return Objects.equals(id1, id2);}
}
