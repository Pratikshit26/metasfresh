package de.metas.ui.web.order.products_proposal.campaign_price;

import java.util.Optional;

import de.metas.product.ProductId;
import de.metas.ui.web.order.products_proposal.model.ProductProposalCampaignPrice;

/*
 * #%L
 * metasfresh-webui-api
     
 * #L%
 */

final class NullCampaignPriceProvider implements CampaignPriceProvider
{
	public static final transient NullCampaignPriceProvider instance = new NullCampaignPriceProvider();

	private NullCampaignPriceProvider()
	{
		super();
	}

	@Override
	public Optional<ProductProposalCampaignPrice> getCampaignPrice(final ProductId productId)
	{
		return Optional.empty();
	}
}
