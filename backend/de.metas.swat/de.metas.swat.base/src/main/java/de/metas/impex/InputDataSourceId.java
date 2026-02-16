package de.metas.impex;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Optional;

/*
 * #%L
 * de.metas.swat.base
     
 * #L%
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class InputDataSourceId implements RepoIdAware
{

	int repoId;

	@JsonCreator
	public static InputDataSourceId ofRepoId(final int repoId)
	{
		return new InputDataSourceId(repoId);
	}

	@Nullable
	public static InputDataSourceId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new InputDataSourceId(repoId) : null;
	}

	public static Optional<InputDataSourceId> optionalOfRepoId(final int repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	public static int toRepoId(@Nullable final InputDataSourceId inputDataSourceId)
	{
		return toRepoIdOr(inputDataSourceId, -1);
	}

	public static int toRepoIdOr(@Nullable final InputDataSourceId inputDataSourceId, final int defaultValue)
	{
		return inputDataSourceId != null ? inputDataSourceId.getRepoId() : defaultValue;
	}

	private InputDataSourceId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "repoId");
	}

	@JsonValue
	public int toJson()
	{
		return getRepoId();
	}
}
