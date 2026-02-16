/*
 * #%L
 * de.metas.serviceprovider.base
     
 * #L%
 */

package de.metas.serviceprovider.timebooking.importer.failed;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class FailedTimeBookingId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static FailedTimeBookingId ofRepoId(final int repoId)
	{
		return new FailedTimeBookingId(repoId);
	}

	public static FailedTimeBookingId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? new FailedTimeBookingId(repoId) : null;
	}

	private FailedTimeBookingId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "S_FailedTimeBookingId_ID");
	}

	@JsonValue
	public int toJson()
	{
		return getRepoId();
	}
}
