package de.metas.project;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableSet;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import de.metas.util.lang.RepoIdAwares;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */

@Value
public class ProjectId implements RepoIdAware
{
	public static ProjectId ofRepoId(final int repoId)
	{
		return new ProjectId(repoId);
	}

	@Nullable
	public static ProjectId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? new ProjectId(repoId) : null;
	}

	@Nullable
	public static ProjectId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new ProjectId(repoId) : null;
	}

	@Nullable
	public static ProjectId ofNullableObject(@Nullable final Object object) {return object != null ? ofObject(object) : null;}

	@NonNull
	@JsonCreator
	public static ProjectId ofObject(@NonNull final Object object) {return RepoIdAwares.ofObject(object, ProjectId.class);}

	public static ImmutableSet<ProjectId> ofRepoIds(@NonNull final Collection<Integer> repoIds)
	{
		if (repoIds.isEmpty())
		{
			return ImmutableSet.of();
		}
		return repoIds.stream().map(ProjectId::ofRepoIdOrNull).filter(Objects::nonNull).collect(ImmutableSet.toImmutableSet());
	}

	public static int toRepoId(@Nullable final ProjectId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	int repoId;

	private ProjectId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_Project_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static boolean equals(@Nullable final ProjectId id1, @Nullable final ProjectId id2)
	{
		return Objects.equals(id1, id2);
	}
}
