package de.metas.invoicecandidate.internalbusinesslogic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.swat.base
     
 * #L%
 */

@Value
public class OrderedData
{
	Quantity qty;

	Quantity qtyInStockUom;

	boolean orderFullyDelivered;

	@JsonCreator
	@Builder
	private OrderedData(
			@JsonProperty("qty") @NonNull final Quantity qty,
			@JsonProperty("qtyInStockUom") @NonNull final Quantity qtyInStockUom,
			@JsonProperty("orderFullyDelivered") final boolean orderFullyDelivered)
	{
		this.qty = qty;
		this.qtyInStockUom = qtyInStockUom;
		this.orderFullyDelivered = orderFullyDelivered;
	}

	@JsonIgnore
	public boolean isNegative()
	{
		final boolean negativeUomQty = getQty().signum() < 0;
		if (negativeUomQty)
		{
			return true;
		}

		// not sure if this happens in real live (it might, who knows!), but it does happen if a lazy dev sets up unit tests without uomQtys
		final boolean zeroUomQty = getQty().signum() == 0;
		final boolean negativeStockQty = getQtyInStockUom().signum() < 0;

		return zeroUomQty && negativeStockQty;
	}

}
