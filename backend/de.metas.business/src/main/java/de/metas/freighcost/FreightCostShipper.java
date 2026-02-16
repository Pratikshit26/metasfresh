package de.metas.freighcost;

import com.google.common.collect.ImmutableList;
import de.metas.location.CountryId;
import de.metas.money.Money;
import de.metas.shipping.ShipperId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */

@Value
public class FreightCostShipper
{
	FreightCostShipperId id;
	ShipperId shipperId;
	LocalDate validFrom;
	@Getter(AccessLevel.NONE)
	ImmutableList<FreightCostBreak> breaks;

	@Builder
	private FreightCostShipper(
			@NonNull final FreightCostShipperId id,
			@NonNull final ShipperId shipperId,
			@NonNull final LocalDate validFrom,
			@NonNull final Collection<FreightCostBreak> breaks)
	{
		this.id = id;
		this.shipperId = shipperId;
		this.validFrom = validFrom;

		final Comparator<FreightCostBreak> shipmentValueAmtMax = Comparator.comparing(freightCostBreak -> freightCostBreak.getShipmentValueAmtMax().toBigDecimal());
		final Comparator<FreightCostBreak> seqNo = Comparator.comparing(FreightCostBreak::getSeqNo, Comparator.nullsLast(Comparator.naturalOrder()));
		this.breaks = breaks.stream()
				.sorted(shipmentValueAmtMax.thenComparing(seqNo))
				.collect(ImmutableList.toImmutableList());
	}

	boolean isMatching(@NonNull final ShipperId shipperId, @NonNull final LocalDate date)
	{
		return this.shipperId.equals(shipperId)
				&& this.validFrom.compareTo(date) <= 0;
	}

	public boolean isShipToCountry(@NonNull final CountryId countryId)
	{
		return breaks.stream().anyMatch(freightCostBreak -> freightCostBreak.isCountryMatching(countryId));
	}

	Optional<FreightCostBreak> getBreak(@NonNull final CountryId countryId, @NonNull final Money shipmentValueAmt)
	{
		// assumes that the breaks are ordered by getShipmentValueAmt ascending
		for (final FreightCostBreak freightCostBreak : breaks)
		{
			if (freightCostBreak.isMatching(countryId, shipmentValueAmt))
			{
				return Optional.of(freightCostBreak);
			}
		}

		return Optional.empty();
	}
}
