package de.metas.invoicecandidate.internalbusinesslogic;

import de.metas.quantity.Quantity;
import de.metas.quantity.StockQtyAndUOMQty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.swat.base
     
 * #L%
 */

@Value
public class ToInvoiceData
{
	/** Excluding possible receipt quantities with quality issues */
	StockQtyAndUOMQty qtysRaw;

	/** Computed including possible receipt quality issues, not overridden by a possible qtyToInvoice override. */
	StockQtyAndUOMQty qtysCalc;

	StockQtyAndUOMQty qtysEffective;

	Quantity qtyInPriceUom;

	@Builder
	private ToInvoiceData(
			@NonNull final StockQtyAndUOMQty qtysRaw,
			@NonNull final StockQtyAndUOMQty qtysCalc,
			@NonNull final StockQtyAndUOMQty qtysEffective,
			@NonNull final Quantity qtyInPriceUom)
	{
		this.qtysRaw = qtysRaw;
		this.qtysCalc = qtysCalc;
		this.qtysEffective = qtysEffective;
		this.qtyInPriceUom = qtyInPriceUom;
	}
}
