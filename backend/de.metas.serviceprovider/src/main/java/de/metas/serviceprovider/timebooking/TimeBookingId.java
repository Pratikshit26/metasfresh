/*
 * #%L
 * de.metas.serviceprovider.base
     
 * #L%
 */

package de.metas.serviceprovider.timebooking;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class TimeBookingId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static TimeBookingId ofRepoId(final int repoId)
	{
		return new TimeBookingId(repoId);
	}

	public static TimeBookingId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? new TimeBookingId(repoId) : null;
	}

	private TimeBookingId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "S_TimeBooking_ID");
	}

	@JsonValue
	public int toJson()
	{
		return getRepoId();
	}
}
