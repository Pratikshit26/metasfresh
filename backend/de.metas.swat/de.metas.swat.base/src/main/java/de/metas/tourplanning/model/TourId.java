/*
 * #%L
 * de.metas.swat.base
     
 * #L%
 */

package de.metas.tourplanning.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class TourId implements RepoIdAware
{
	final int repoId;

	private TourId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "TourId");
	}

	public static int toRepoId(@Nullable final TourId tourId)
	{
		if (tourId == null)
		{
			return 0;
		}
		return tourId.getRepoId();
	}

	@JsonValue
	@Override
	public int getRepoId()
	{
		return repoId;
	}

	@NonNull
	@JsonCreator
	public static TourId ofRepoId(final int repoId)
	{
		return new TourId(repoId);
	}

	@Nullable
	public static TourId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new TourId(repoId) : null;
	}
}
