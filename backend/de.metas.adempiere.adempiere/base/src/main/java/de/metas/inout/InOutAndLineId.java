package de.metas.inout;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class InOutAndLineId
{
	public static InOutAndLineId of(final InOutId inOutId, final InOutLineId inOutLineId)
	{
		return new InOutAndLineId(inOutId, inOutLineId);
	}

	public static InOutAndLineId ofRepoId(final int inOutRepoId, final int inOutLineRepoId)
	{
		return new InOutAndLineId(InOutId.ofRepoId(inOutRepoId), InOutLineId.ofRepoId(inOutLineRepoId));
	}

	public static InOutAndLineId ofRepoId(@NonNull final InOutId inOutId, final int inOutLineRepoId)
	{
		return new InOutAndLineId(inOutId, InOutLineId.ofRepoId(inOutLineRepoId));
	}

	@Nullable
	public static InOutAndLineId ofRepoIdOrNull(final int inOutRepoId, final int inOutLineRepoId)
	{
		final InOutId inoutId = InOutId.ofRepoIdOrNull(inOutRepoId);
		if (inoutId == null)
		{
			return null;
		}

		final InOutLineId inoutLineId = InOutLineId.ofRepoIdOrNull(inOutLineRepoId);
		if (inoutLineId == null)
		{
			return null;
		}

		return new InOutAndLineId(inoutId, inoutLineId);
	}

	@JsonProperty("inOutId")
	InOutId inOutId;
	@JsonProperty("inOutLineId")
	InOutLineId inOutLineId;

	@JsonCreator
	private InOutAndLineId(
			@JsonProperty("inOutId") @NonNull final InOutId inOutId,
			@JsonProperty("inOutLineId") @NonNull final InOutLineId inOutLineId)
	{
		this.inOutId = inOutId;
		this.inOutLineId = inOutLineId;
	}
}
