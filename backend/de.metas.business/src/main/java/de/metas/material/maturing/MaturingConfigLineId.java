package de.metas.material.maturing;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

/*
 * #%L
 * metasfresh-material-planning
     
 * #L%
 */

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@Value
public class MaturingConfigLineId implements RepoIdAware
{
	@JsonCreator
	@NonNull
	public static MaturingConfigLineId ofRepoId(final int repoId)
	{
		return new MaturingConfigLineId(repoId);
	}

	@Nullable
	public static MaturingConfigLineId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? ofRepoId(repoId) : null;
	}

	public static int toRepoId(@Nullable final MaturingConfigLineId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	int repoId;

	private MaturingConfigLineId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "M_Maturing_Configuration_Line_ID");
	}

	@JsonValue
	public int toJson()
	{
		return getRepoId();
	}

	public static boolean equals(@Nullable final MaturingConfigLineId id1, @Nullable final MaturingConfigLineId id2)
	{
		return Objects.equals(id1, id2);
	}

}
