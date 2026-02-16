/*
 * #%L
 * de.metas.util
     
 * #L%
 */

package de.metas.util.time;

import lombok.NonNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HmmUtils
{
	private static final Pattern hmmPattern = Pattern.compile("^-?[0-9]+:[0-5][0-9]$");

	@NonNull
	public static String secondsToHmm(final long seconds)
	{
		final long hours = seconds / 3600;
		final long hoursRemainder = seconds - (hours * 3600);
		final long mins = hoursRemainder < 0 ? -( hoursRemainder / 60 ) : hoursRemainder / 60;

		return hours + ":" + (mins < 10L ? "0" + mins : mins);
	}

	public static long hmmToSeconds(@NonNull final String hmm)
	{
		if (!matches(hmm))
		{
			throw new RuntimeException("Wrong format! Was expecting a value in format: " + hmmPattern.toString() + ", but received: " + hmm);
		}

		final String[] parts = hmm.split(":");

		final long hours = Long.parseLong(parts[0]);
		final long minutes = Long.parseLong(parts[1]);

		return (hours * 3600) + (minutes * 60);
	}

	public static boolean matches(@NonNull final String hmmValue)
	{
		final Matcher matcher = hmmPattern.matcher(hmmValue);

		return matcher.matches();
	}
}
