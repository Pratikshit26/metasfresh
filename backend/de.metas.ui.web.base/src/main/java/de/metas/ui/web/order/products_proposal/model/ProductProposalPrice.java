package de.metas.ui.web.order.products_proposal.model;

import java.math.BigDecimal;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * metasfresh-webui-api
     
 * #L%
 */

@ToString
public class ProductProposalPrice
{
	@Getter
	private final BigDecimal userEnteredPriceValue;
	@Getter
	private final CurrencyCode currencyCode;

	private final Amount priceListPrice;
	private final ProductProposalCampaignPrice campaignPrice;

	@Getter
	private final boolean campaignPriceUsed;

	@Getter
	private final boolean priceListPriceUsed;

	@Builder(toBuilder = true)
	private ProductProposalPrice(
			@NonNull final Amount priceListPrice,
			@Nullable final ProductProposalCampaignPrice campaignPrice,
			@Nullable final BigDecimal userEnteredPriceValue)
	{
		this.priceListPrice = priceListPrice;
		this.campaignPrice = campaignPrice;

		//
		this.currencyCode = priceListPrice.getCurrencyCode();
		if (campaignPrice != null && !currencyCode.equals(campaignPrice.getCurrencyCode()))
		{
			throw new AdempiereException("" + campaignPrice + " and " + priceListPrice + " shall have the same currency");
		}

		//
		if (userEnteredPriceValue != null)
		{
			this.userEnteredPriceValue = userEnteredPriceValue;
		}
		else if (campaignPrice != null)
		{
			this.userEnteredPriceValue = campaignPrice.applyOn(priceListPrice).getAsBigDecimal();
		}
		else
		{
			this.userEnteredPriceValue = priceListPrice.getAsBigDecimal();
		}

		//
		this.priceListPriceUsed = this.priceListPrice.valueComparingEqualsTo(this.userEnteredPriceValue);
		this.campaignPriceUsed = this.campaignPrice != null
				&& !priceListPriceUsed
				&& this.campaignPrice.amountValueComparingEqualsTo(this.userEnteredPriceValue);
	}

	public Amount getUserEnteredPrice()
	{
		return Amount.of(getUserEnteredPriceValue(), getCurrencyCode());
	}

	public ProductProposalPrice withUserEnteredPriceValue(@NonNull final BigDecimal userEnteredPriceValue)
	{
		if (this.userEnteredPriceValue.equals(userEnteredPriceValue))
		{
			return this;
		}

		return toBuilder().userEnteredPriceValue(userEnteredPriceValue).build();
	}

	public ProductProposalPrice withPriceListPriceValue(@NonNull final BigDecimal priceListPriceValue)
	{
		return withPriceListPrice(Amount.of(priceListPriceValue, getCurrencyCode()));
	}

	public ProductProposalPrice withPriceListPrice(@NonNull final Amount priceListPrice)
	{
		if (this.priceListPrice.equals(priceListPrice))
		{
			return this;
		}

		return toBuilder().priceListPrice(priceListPrice).build();
	}
}
