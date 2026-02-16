/*
 * #%L
 * de.metas.serviceprovider.base
     
 * #L%
 */

package de.metas.serviceprovider.timebooking;

import de.metas.util.time.HmmUtils;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Duration;

@Value
public class Effort
{
	public final static Effort ZERO = new Effort(0);

	long seconds;

	@NonNull
	public static Effort ofNullable(@Nullable final String hmm)
	{
		final long seconds = hmm != null
				? HmmUtils.hmmToSeconds(hmm)
				: 0;

		return new Effort(seconds);
	}

	@NonNull
	public static Effort ofSeconds(final long seconds)
	{
		return new Effort(seconds);
	}

	@NonNull
	public static Effort ofDuration(@NonNull final Duration duration)
	{
		return ofSeconds(duration.getSeconds());
	}

	@NonNull
	public Effort addNullSafe(@Nullable final Effort effort)
	{
		final long secondsToAdd = effort != null
				? effort.getSeconds()
				: 0;

		final long secondsSum = getSeconds() + secondsToAdd;

		return new Effort(secondsSum);
	}

	@NonNull
	public String getHmm()
	{
		return HmmUtils.secondsToHmm(seconds);
	}

	@NonNull
	public Effort negate()
	{
		return new Effort(-seconds);
	}

	@NonNull
	public BigDecimal toHours()
	{
		final Duration duration = Duration.ofSeconds(seconds);

		return new BigDecimal(duration.toHours());
	}

	private Effort(final long seconds)
	{
		this.seconds = seconds;
	}
}
