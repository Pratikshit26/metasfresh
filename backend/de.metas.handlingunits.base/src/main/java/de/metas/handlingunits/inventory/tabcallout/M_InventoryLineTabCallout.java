package de.metas.handlingunits.inventory.tabcallout;

import org.adempiere.ad.callout.api.ICalloutRecord;
import org.adempiere.ad.ui.spi.TabCalloutAdapter;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Inventory;

import de.metas.document.DocBaseAndSubType;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.model.I_M_InventoryLine;
import de.metas.inventory.HUAggregationType;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
     
 * #L%
 */

public class M_InventoryLineTabCallout extends TabCalloutAdapter
{
	@Override
	public void onNew(@NonNull final ICalloutRecord calloutRecord)
	{
		final InventoryService inventoryService = SpringContextHolder.instance.getBean(InventoryService.class);

		final I_M_InventoryLine inventoryLineRecord = calloutRecord.getModel(I_M_InventoryLine.class);
		final I_M_Inventory inventoryRecord = inventoryLineRecord.getM_Inventory();

		final DocBaseAndSubType docBaseAndSubType = inventoryService.extractDocBaseAndSubTypeOrNull(inventoryRecord);
		if (docBaseAndSubType == null)
		{
			return;
		}

		final HUAggregationType huAggregationType = InventoryService.computeHUAggregationType(docBaseAndSubType);
		inventoryLineRecord.setHUAggregationType(huAggregationType.getCode());
	}
}
