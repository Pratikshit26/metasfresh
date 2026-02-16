package de.metas.invoicecandidate.internalbusinesslogic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.pricing.InvoicableQtyBasedOn;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.StockQtyAndUOMQty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.List;

import static de.metas.common.util.CoalesceUtil.coalesce;

/*
 * #%L
 * de.metas.swat.base
     
 * #L%
 */

@Value
public class ShipmentData
{
	ProductId productId;

	Quantity qtyInStockUom;

	Quantity qtyNominal;

	Quantity qtyCatch;

	List<DeliveredQtyItem> deliveredQtyItems;

	@Builder
	@JsonCreator
	private ShipmentData(
			@JsonProperty("productId") @NonNull ProductId productId,
			@JsonProperty("qtyInStockUom") @NonNull Quantity qtyInStockUom,
			@JsonProperty("qtyNominal") @NonNull Quantity qtyNominal,
			@JsonProperty("qtyCatch") @Nullable Quantity qtyCatch,
			@JsonProperty("deliveredQtyItems") @Singular List<DeliveredQtyItem> deliveredQtyItems)
	{
		this.productId = productId;
		this.qtyInStockUom = qtyInStockUom;
		this.qtyNominal = qtyNominal;
		this.qtyCatch = qtyCatch;
		this.deliveredQtyItems = deliveredQtyItems;
	}

	public StockQtyAndUOMQty computeInvoicableQtyDelivered(@NonNull final InvoicableQtyBasedOn invoicableQtyBasedOn)
	{
		final Quantity deliveredInUom;
		switch (invoicableQtyBasedOn)
		{
			case CatchWeight:
				deliveredInUom = coalesce(getQtyCatch(), getQtyNominal());
				break;
			case NominalWeight:
				deliveredInUom = getQtyNominal();
				break;
			default:
				throw new AdempiereException("Unexpected InvoicableQtyBasedOn=" + invoicableQtyBasedOn);
		}

		return StockQtyAndUOMQty.builder()
				.productId(productId)
				.stockQty(qtyInStockUom)
				.uomQty(deliveredInUom).build();
	}
}
