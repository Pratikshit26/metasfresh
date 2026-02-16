package de.metas.freighcost;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableList;

import de.metas.location.CountryId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.shipping.ShipperId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */

@Value
public class FreightCost
{
	FreightCostId id;
	String name;

	OrgId orgId;
	ProductId freightCostProductId;
	boolean defaultFreightCost;

	@Getter(AccessLevel.NONE)
	private ImmutableList<FreightCostShipper> shippers;

	@Builder
	private FreightCost(
			@NonNull final FreightCostId id,
			@NonNull final String name,
			@NonNull final OrgId orgId,
			@NonNull final ProductId freightCostProductId,
			final boolean defaultFreightCost,
			@NonNull final Collection<FreightCostShipper> shippers)
	{
		this.id = id;
		this.name = name;
		this.orgId = orgId;
		this.freightCostProductId = freightCostProductId;
		this.defaultFreightCost = defaultFreightCost;

		this.shippers = shippers.stream()
				.sorted(Comparator.comparing(FreightCostShipper::getValidFrom))
				.collect(ImmutableList.toImmutableList());
	}

	public Money getFreightRate(
			@NonNull final ShipperId shipperId,
			@NonNull final CountryId countryId,
			@NonNull final LocalDate date,
			@NonNull final Money shipmentValueAmt)
	{
		return getBreak(shipperId, countryId, date, shipmentValueAmt)
				.map(FreightCostBreak::getFreightRate)
				.orElseGet(shipmentValueAmt::toZero);
	}

	public Optional<FreightCostBreak> getBreak(
			@NonNull final ShipperId shipperId,
			@NonNull final CountryId countryId,
			@NonNull final LocalDate date,
			@NonNull final Money shipmentValueAmt)
	{
		final FreightCostShipper shipper = getShipperIfExists(shipperId, date).orElse(null);
		if (shipper == null)
		{
			return Optional.empty();
		}

		return shipper.getBreak(countryId, shipmentValueAmt);
	}

	public FreightCostShipper getShipper(@NonNull final ShipperId shipperId, @NonNull final LocalDate date)
	{

		Optional<FreightCostShipper> shipperIfExists = getShipperIfExists(shipperId, date);

		return shipperIfExists.orElseThrow(() -> new AdempiereException("@NotFound@ @M_FreightCostShipper_ID@ (@M_Shipper_ID@:" + shipperId + ", @M_FreightCost_ID@:" + getName() + ")"));
	}

	public Optional<FreightCostShipper> getShipperIfExists(@NonNull final ShipperId shipperId, @NonNull final LocalDate date)
	{
		return shippers.stream()
				.filter(shipper -> shipper.isMatching(shipperId, date))
				.sorted(Comparator.comparing(FreightCostShipper::getValidFrom))
				.findFirst();

	}

}
