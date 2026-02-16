package de.metas.invoicecandidate.internalbusinesslogic;

import static de.metas.common.util.CoalesceUtil.coalesceNotNull;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

import static de.metas.common.util.CoalesceUtil.coalesce;

/*
 * #%L
 * de.metas.swat.base
     
 * #L%
 */

@Value
public class DeliveredQtyItem
{
	Quantity qtyInStockUom;

	Quantity qtyNominal;

	Quantity qtyCatch;

	Quantity qtyOverride;

	/** default: {@code false} */
	boolean inDispute;

	/** Usually we ignore items where this is false; but sometimes we still need the items to exist none the less */
	boolean completedOrClosed;

	@Builder
	@JsonCreator
	private DeliveredQtyItem(
			@JsonProperty("qtyInStockUom") @NonNull final Quantity qtyInStockUom,
			@JsonProperty("qtyNominal") @NonNull final Quantity qtyNominal,
			@JsonProperty("qtyCatch") @Nullable final Quantity qtyCatch,
			@JsonProperty("qtyOverride") @Nullable final Quantity qtyOverride,
			@JsonProperty("completedOrClosed") final boolean completedOrClosed,
			@JsonProperty("inDispute") @Nullable final Boolean inDispute)
	{
		this.qtyInStockUom = qtyInStockUom;
		this.qtyNominal = qtyNominal;
		this.qtyCatch = qtyCatch;
		this.qtyOverride = qtyOverride;
		this.completedOrClosed = completedOrClosed;
		this.inDispute = coalesceNotNull(inDispute, false);
	}

}
