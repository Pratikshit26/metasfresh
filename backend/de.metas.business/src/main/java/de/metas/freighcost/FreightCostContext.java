package de.metas.freighcost;

import java.time.LocalDate;

import javax.annotation.Nullable;

import de.metas.bpartner.BPartnerId;
import de.metas.location.CountryId;
import de.metas.money.Money;
import de.metas.order.DeliveryViaRule;
import de.metas.organization.OrgId;
import de.metas.shipping.ShipperId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */

@Value
public class FreightCostContext
{
	@NonNull
	OrgId shipFromOrgId;

	BPartnerId shipToBPartnerId;
	CountryId shipToCountryId;

	ShipperId shipperId;

	@NonNull
	LocalDate date;

	@NonNull
	FreightCostRule freightCostRule;

	@NonNull
	DeliveryViaRule deliveryViaRule;

	@Nullable
	Money manualFreightAmt;

	@Builder
	private FreightCostContext(
			@NonNull final OrgId shipFromOrgId,
			@Nullable final BPartnerId shipToBPartnerId,
			@Nullable final CountryId shipToCountryId,
			@Nullable final ShipperId shipperId,
			@NonNull final LocalDate date,
			@NonNull final FreightCostRule freightCostRule,
			@NonNull final DeliveryViaRule deliveryViaRule,
			@Nullable final Money manualFreightAmt)
	{
		this.shipFromOrgId = shipFromOrgId;
		this.shipToBPartnerId = shipToBPartnerId;
		this.shipToCountryId = shipToCountryId;
		this.shipperId = shipperId;
		this.date = date;
		this.freightCostRule = freightCostRule;
		this.deliveryViaRule = deliveryViaRule;

		if(freightCostRule.isFixPrice())
		{
			// Check.assumeNotNull(manualFreightAmt, "Parameter manualFreightAmt is not null"); // null is OK
			this.manualFreightAmt = manualFreightAmt;
		}
		else
		{
			this.manualFreightAmt = null;
		}
	}

}
