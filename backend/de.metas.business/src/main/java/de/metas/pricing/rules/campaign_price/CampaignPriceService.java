package de.metas.pricing.rules.campaign_price;

import java.util.Optional;

import org.springframework.stereotype.Service;

import de.metas.currency.Amount;
import de.metas.money.MoneyService;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */

@Service
public class CampaignPriceService
{
	private final CampaignPriceRepository campaignPriceRepository;
	private final MoneyService moneyService;

	public CampaignPriceService(
			@NonNull final CampaignPriceRepository campaignPriceRepository,
			@NonNull final MoneyService moneyService)
	{
		this.campaignPriceRepository = campaignPriceRepository;
		this.moneyService = moneyService;
	}

	public Optional<CampaignPrice> getCampaignPrice(@NonNull final CampaignPriceQuery query)
	{
		return campaignPriceRepository.getCampaignPrice(query);
	}

	public Amount getPriceStdAsAmount(@NonNull final CampaignPrice campaignPrice)
	{
		return moneyService.toAmount(campaignPrice.getPriceStd());
	}
}
