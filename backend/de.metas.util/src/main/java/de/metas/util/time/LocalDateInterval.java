/*
 * #%L
 * de.metas.util
     
 * #L%
 */

package de.metas.util.time;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.LocalDate;
import java.util.ArrayList;

@Value
@Builder
public class LocalDateInterval
{
	@NonNull
	LocalDate startDate;

	@NonNull
	LocalDate endDate;

	public static LocalDateInterval of(@NonNull final LocalDate startDate,
			                           @NonNull final LocalDate endDate)
	{
		return new LocalDateInterval(startDate, endDate);
	}

	/**
	 *  Divides this interval in multiple intervals using the specified step.
	 *  startDate = LocalDate.of(2020, 4, 1);
	 *  endDate = LocalDate.of(2020, 4, 14);
	 *  LocalDateInterval dateInterval = LocalDateInterval.of(startDate, endDate);
	 *
	 *  dateInterval.divideUsingStep(7)
	 *   == [ (LocalDate.of(2020, 4, 1), LocalDate.of(2020, 4, 7)),
	 *        (LocalDate.of(2020, 4, 8), LocalDate.of(2020, 4, 14)) ]
	 *
	 * @param step the step to be used
	 * @return a list with the intervals obtained
	 */
	@NonNull
	public ImmutableList<LocalDateInterval> divideUsingStep(final int step)
	{
		final ArrayList<LocalDateInterval> intervals = new ArrayList<>();

		LocalDate intervalStartDate = startDate;
		LocalDate intervalEndDate = intervalStartDate.plusDays(step - 1);

		while (intervalEndDate.isBefore(endDate))
		{
			intervals.add(LocalDateInterval.of(intervalStartDate, intervalEndDate));

			intervalStartDate = intervalStartDate.plusDays(step);
			intervalEndDate = intervalEndDate.plusDays(step);
		}

		intervals.add(new LocalDateInterval(intervalStartDate, endDate));

		return ImmutableList.copyOf(intervals);
	}
}
