package de.metas.ui.web.order.products_proposal.service;

import de.metas.currency.Currency;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.money.CurrencyId;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeSetInstanceId;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/*
 * #%L
 * metasfresh-webui-api
     
 * #L%
 */

@Value
@Builder
public class OrderLine
{
	@NonNull
	OrderLineId orderLineId;

	@NonNull
	ProductId productId;

	@Nullable
	HUPIItemProductId packingMaterialId;
	boolean packingMaterialWithInfiniteCapacity;

	@NonNull
	BigDecimal priceActual;
	
	@NonNull
	BigDecimal priceEntered;

	@NonNull
	Currency currency;

	@NonNull
	BigDecimal qtyEnteredCU;

	@NonNull
	CurrencyId currencyId;

	@NonNull
	UomId uomId;

	@Nullable
	UomId priceUomId;

	@NonNull
	AttributeSetInstanceId asiId;

	int qtyEnteredTU;

	String description;

	public boolean isMatching(
			@NonNull final ProductId productId,
			@Nullable final HUPIItemProductId packingMaterialId)

	{
		return ProductId.equals(this.productId, productId)
				&& HUPIItemProductId.equals(
						HUPIItemProductId.nullToVirtual(this.packingMaterialId),
				HUPIItemProductId.nullToVirtual(packingMaterialId));
	}

	boolean isMatching(@NonNull final ProductId productId)
	{
		return ProductId.equals(this.productId, productId);
	}
}
