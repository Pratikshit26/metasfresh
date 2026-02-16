package de.metas.handlingunits.expiry;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;
import java.util.OptionalInt;

import lombok.Getter;
import org.adempiere.mm.attributes.api.AttributeConstants;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
     
 * #L%
 */

final class UpdateMonthsUntilExpiryCommand
{
	// services
	private final HUWithExpiryDatesRepository huWithExpiryDatesRepository;

	@Getter
	private final LocalDate today;

	@Builder
	public UpdateMonthsUntilExpiryCommand(@NonNull final HUWithExpiryDatesRepository huWithExpiryDatesRepository,
			@NonNull final LocalDate today)
	{
		this.huWithExpiryDatesRepository = huWithExpiryDatesRepository;
		this.today = today;
	}

	static OptionalInt computeMonthsUntilExpiry(@NonNull final IAttributeStorage huAttributes, @NonNull final LocalDate today)
	{
		final LocalDate bestBeforeDate = huAttributes.getValueAsLocalDate(AttributeConstants.ATTR_BestBeforeDate);
		if (bestBeforeDate == null)
		{
			return OptionalInt.empty();
		}

		final int monthsUntilExpiry = (int)ChronoUnit.MONTHS.between(today, bestBeforeDate);
		return OptionalInt.of(monthsUntilExpiry);
	}

	public static class UpdateMonthsUntilExpiryCommandBuilder
	{
		public UpdateMonthsResult execute()
		{
			final UpdateMonthsUntilExpiryCommand cmd = build();

			final Iterator<HuId> huIdIterator = huWithExpiryDatesRepository.getAllWithBestBeforeDate();

			final UpdateAttributesHelper helper = new UpdateAttributesHelper();

			return helper.execute(huIdIterator, huAttributes -> {
				if (!huAttributes.hasAttribute(AttributeConstants.ATTR_MonthsUntilExpiry))
				{
					return false;
				}

				final OptionalInt monthsUntilExpiry = computeMonthsUntilExpiry(huAttributes, cmd.getToday());
				final int monthsUntilExpiryOld = huAttributes.getValueAsInt(AttributeConstants.ATTR_MonthsUntilExpiry);
				if (monthsUntilExpiry.orElse(0) == monthsUntilExpiryOld)
				{
					return false;
				}

				huAttributes.setSaveOnChange(true);

				if (monthsUntilExpiry.isPresent())
				{
					huAttributes.setValue(AttributeConstants.ATTR_MonthsUntilExpiry, monthsUntilExpiry.getAsInt());
				}
				else
				{
					huAttributes.setValue(AttributeConstants.ATTR_MonthsUntilExpiry, null);
				}

				huAttributes.saveChangesIfNeeded();

				return true;
			});
		}

	}
}
