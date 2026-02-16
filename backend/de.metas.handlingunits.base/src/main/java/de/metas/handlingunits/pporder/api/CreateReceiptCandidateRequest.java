package de.metas.handlingunits.pporder.api;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.PickingCandidateId;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.adempiere.warehouse.LocatorId;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

/*
 * #%L
 * de.metas.handlingunits.base
     
 * #L%
 */

@Data
@Builder
public final class CreateReceiptCandidateRequest
{
	@NonNull
	private final PPOrderId orderId;
	/**
	 * BY/CO-Product order BOM line
	 */
	@Nullable
	private final PPOrderBOMLineId orderBOMLineId;
	@NonNull
	private final OrgId orgId;

	@NonNull
	private final ZonedDateTime date;

	@NonNull
	private final LocatorId locatorId;

	@NonNull
	private final HuId topLevelHUId;

	@NonNull
	private final ProductId productId;

	@NonNull
	private Quantity qtyToReceive;

	@Nullable
	private final PickingCandidateId pickingCandidateId;

	private final boolean alreadyProcessed;

	public static class CreateReceiptCandidateRequestBuilder
	{
		public CreateReceiptCandidateRequestBuilder addQtyToReceive(@NonNull final Quantity qtyToAdd)
		{
			final Quantity qtyToReceiveNew = qtyToReceive != null
					? qtyToReceive = qtyToReceive.add(qtyToAdd)
					: qtyToAdd;
			return qtyToReceive(qtyToReceiveNew);
		}
	}
}
