package de.metas.phonecall.service;

import java.time.LocalDate;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import de.metas.phonecall.PhonecallSchemaVersion;
import de.metas.util.Check;
import de.metas.util.time.generator.DateSequenceGenerator;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@Value
public class PhonecallSchemaVersionRange
{
	public PhonecallSchemaVersion phonecallSchemaVersion;
	private LocalDate startDate;
	private LocalDate endDate;
	private DateSequenceGenerator dateSequenceGenerator;

	@Builder
	private PhonecallSchemaVersionRange(
			@NonNull final PhonecallSchemaVersion phonecallSchemaVersion,
			@NonNull final LocalDate startDate,
			@NonNull final LocalDate endDate,
			final DateSequenceGenerator dateSequenceGenerator)
	{
		Check.assume(startDate.compareTo(endDate) <= 0, "StartDate({}) <= EndDate({})", startDate, endDate);

		this.phonecallSchemaVersion = phonecallSchemaVersion;
		this.startDate = startDate;
		this.endDate = endDate;
		this.dateSequenceGenerator = dateSequenceGenerator;
	}

	public Set<LocalDate> generatePhonecallDates()
	{
		if (dateSequenceGenerator == null)
		{
			return ImmutableSet.of();
		}

		return dateSequenceGenerator.generate();
	}
}
