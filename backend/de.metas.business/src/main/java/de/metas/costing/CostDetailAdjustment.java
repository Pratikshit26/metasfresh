package de.metas.costing;

import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */

@Value
@Builder
public class CostDetailAdjustment
{
	@NonNull CostDetailId costDetailId;

	@NonNull Quantity qty;
	@NonNull CostAmount oldCostPrice;
	@NonNull CostAmount oldCostAmount;
	@NonNull CostAmount newCostPrice;
	@NonNull CostAmount newCostAmount;
}
