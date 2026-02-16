package de.metas.invoicecandidate.internalbusinesslogic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.money.Money;
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
public class InvoicedData
{

	StockQtyAndUOMQty qtys;

	Money netAmount;

	@JsonCreator
	@Builder
	private InvoicedData(
			@JsonProperty("qtys") @NonNull final StockQtyAndUOMQty qtys,
			@JsonProperty("netAmount") @NonNull final Money netAmount)
	{
		this.qtys = qtys;
		this.netAmount = netAmount;
	}
}
