package de.metas.ui.web.order.products_proposal.model;

import de.metas.currency.Amount;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.i18n.ITranslatableString;
import de.metas.pricing.ProductPriceId;
import de.metas.product.ProductId;
import de.metas.ui.web.window.datatypes.LookupValue;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeSetInstanceId;

import javax.annotation.Nullable;
import java.time.LocalDate;

/*
 * #%L
 * metasfresh-webui-api
     
 * #L%
 */

@Value
@Builder
public class ProductsProposalRowAddRequest
{
	@NonNull
	LookupValue product;

	@NonNull
	@Default
	ProductASIDescription asiDescription = ProductASIDescription.NONE;

	@Nullable
	AttributeSetInstanceId asiId;

	@NonNull
	Amount priceListPrice;

	@Nullable
	Integer lastShipmentDays;

	@Nullable
	ProductPriceId copiedFromProductPriceId;

	@Nullable
	HUPIItemProductId packingMaterialId;

	@Nullable
	ITranslatableString packingDescription;

	@Nullable
	LocalDate lastQuotationDate;

	@Nullable
	Amount lastQuotationPrice;

	@Nullable
	LookupValue lastQuotationPriceUOM;

	@Nullable
	LookupValue incoterms;

	@Nullable
	Boolean quotationOrdered;

	public ProductId getProductId()
	{
		return getProduct().getIdAs(ProductId::ofRepoId);
	}
}
