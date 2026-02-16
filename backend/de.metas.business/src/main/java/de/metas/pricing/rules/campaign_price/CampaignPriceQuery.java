package de.metas.pricing.rules.campaign_price;

import java.time.LocalDate;

import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.location.CountryId;
import de.metas.money.CurrencyId;
import de.metas.pricing.PricingSystemId;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */

@Value
@Builder
public class CampaignPriceQuery
{
	@NonNull
	BPartnerId bpartnerId;
	@NonNull
	BPGroupId bpGroupId;

	@NonNull
	ProductId productId;

	@NonNull
	CountryId countryId;

	@NonNull
	CurrencyId currencyId;

	@NonNull
	PricingSystemId pricingSystemId;

	@NonNull
	LocalDate date;

	public boolean isMatching(@NonNull final CampaignPrice price)
	{
		if (!ProductId.equals(price.getProductId(), getProductId()))
		{
			return false;
		}

		if (price.getBpartnerId() != null && !BPartnerId.equals(price.getBpartnerId(), getBpartnerId()))
		{
			return false;
		}

		if (price.getBpGroupId() != null && !BPGroupId.equals(price.getBpGroupId(), getBpGroupId()))
		{
			return false;
		}

		if (price.getPricingSystemId() != null && !PricingSystemId.equals(price.getPricingSystemId(), getPricingSystemId()))
		{
			return false;
		}

		if (!CountryId.equals(price.getCountryId(), getCountryId()))
		{
			return false;
		}

		if (!CurrencyId.equals(price.getCurrencyId(), getCurrencyId()))
		{
			return false;
		}

		if (!price.getValidRange().contains(getDate()))
		{
			return false;
		}

		return true;
	}
}
