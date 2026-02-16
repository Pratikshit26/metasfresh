package de.metas.handlingunits.picking;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.job.model.HUInfo;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eevolution.api.PPOrderId;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.handlingunits.base
     
 * #L%
 */

@Value
@Builder
public class PickFrom
{
	public static PickFrom ofHuId(@NonNull final HuId huId)
	{
		return builder().huId(huId).build();
	}

	public static PickFrom ofHUInfo(@NonNull final HUInfo huInfo)
	{
		return ofHuId(huInfo.getId());
	}

	public static PickFrom ofPickingOrderId(@NonNull final PPOrderId pickingOrderId)
	{
		return builder().pickingOrderId(pickingOrderId).build();
	}

	@Nullable
	HuId huId;

	@Nullable
	PPOrderId pickingOrderId;

	@Builder
	private PickFrom(
			@Nullable HuId huId,
			@Nullable PPOrderId pickingOrderId)
	{
		this.pickingOrderId = pickingOrderId;
		this.huId = huId;
	}

	public boolean isPickFromHU()
	{
		return getHuId() != null;
	}

	public boolean isPickFromPickingOrder()
	{
		return getPickingOrderId() != null;
	}
}
