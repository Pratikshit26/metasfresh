package de.metas.handlingunits.pporder.api;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.quantity.Quantity;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.handlingunits.base
     
 * #L%
 */

@Value
public class MaterialTrackingWithQuantity
{
	I_M_Material_Tracking materialTrackingRecord;

	/** It's OK to add quantities; we don't sum them up in here*/
	List<Quantity> quantities;

	public MaterialTrackingWithQuantity(@NonNull final I_M_Material_Tracking materialTrackingRecord)
	{
		this.materialTrackingRecord = materialTrackingRecord;

		this.quantities = new ArrayList<Quantity>();
	}

	public void addQuantity(Quantity quantity)
	{
		quantities.add(quantity);
	}

	public ImmutableList<Quantity> getQuantities()
	{
		return ImmutableList.copyOf(quantities);
	}
}
