package de.metas.material.event.pporder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.EqualsAndHashCode;

import javax.annotation.Nullable;

/*
 * #%L
 * metasfresh-material-event
     
 * #L%
 */

@EqualsAndHashCode
public class MaterialDispoGroupId
{
	@JsonCreator
	public static MaterialDispoGroupId ofInt(final int value)
	{
		return new MaterialDispoGroupId(value);
	}

	public static MaterialDispoGroupId ofIntOrNull(@Nullable final Integer value)
	{
		return value != null && value > 0 ? ofInt(value) : null;
	}

	public static MaterialDispoGroupId ofIdOrNull(final RepoIdAware id)
	{
		return id != null ? ofIntOrNull(id.getRepoId()) : null;
	}

	private final int value;

	private MaterialDispoGroupId(final int value)
	{
		Check.assumeGreaterThanZero(value, "value");
		this.value = value;
	}

	@Override
	public String toString()
	{
		return String.valueOf(value);
	}

	@JsonValue
	public int toInt()
	{
		return value;
	}

	public static int toInt(@Nullable final MaterialDispoGroupId groupId) {return groupId != null ? groupId.toInt() : -1;}
}
