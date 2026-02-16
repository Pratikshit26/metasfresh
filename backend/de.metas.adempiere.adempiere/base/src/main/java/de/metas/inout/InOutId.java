package de.metas.inout;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */

@Value
public class InOutId implements RepoIdAware
{
	@JsonCreator
	public static InOutId ofRepoId(final int repoId)
	{
		return new InOutId(repoId);
	}

	public static InOutId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new InOutId(repoId) : null;
	}

	public static Optional<InOutId> optionalOfRepoId(final int repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	public static int toRepoId(@Nullable final InOutId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	int repoId;

	private InOutId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "M_InOut_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static boolean equals(@Nullable InOutId id1, @Nullable InOutId id2) {return Objects.equals(id1, id2);}
}
