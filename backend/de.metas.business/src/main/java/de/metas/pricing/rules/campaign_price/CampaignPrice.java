package de.metas.pricing.rules.campaign_price;

import com.google.common.collect.Range;
import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.location.CountryId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.pricing.InvoicableQtyBasedOn;
import de.metas.pricing.PricingSystemId;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.LocalDate;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */

@Value
@Builder
public class CampaignPrice
{
	@NonNull
	ProductId productId;

	@Nullable
	BPartnerId bpartnerId;
	@Nullable
	BPGroupId bpGroupId;

	@Nullable
	PricingSystemId pricingSystemId;

	@NonNull
	CountryId countryId;

	@NonNull
	Range<LocalDate> validRange;

	@Nullable
	Money priceList;

	@NonNull
	Money priceStd;

	@NonNull
	UomId priceUomId;

	@NonNull
	TaxCategoryId taxCategoryId;

	@NonNull
	@Default
	InvoicableQtyBasedOn invoicableQtyBasedOn = InvoicableQtyBasedOn.NominalWeight;

	public LocalDate getValidFrom()
	{
		return getValidRange().lowerEndpoint();
	}

	public CurrencyId getCurrencyId()
	{
		return getPriceStd().getCurrencyId();
	}
}
