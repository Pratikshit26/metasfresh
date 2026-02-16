package org.adempiere.mm.attributes.listeners.expiry;

import java.util.List;

import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.api.IModelAttributeSetInstanceListener;

import com.google.common.collect.ImmutableList;

import de.metas.order.grossprofit.model.I_C_OrderLine;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.fresh.base
     
 * #L%
 */

public class OrderLineMonthsUntilExpiryModelASIListener implements IModelAttributeSetInstanceListener
{
	private final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);

	private static final ImmutableList<String> SOURCE_COLUMN_NAMES = ImmutableList.of(I_C_OrderLine.COLUMNNAME_M_Product_ID);

	@Override
	public String getSourceTableName()
	{
		return I_C_OrderLine.Table_Name;
	}

	@Override
	public List<String> getSourceColumnNames()
	{
		return SOURCE_COLUMN_NAMES;
	}

	@Override
	public void modelChanged(final Object model)
	{
		attributeSetInstanceBL.updateASIAttributeFromModel(AttributeConstants.ATTR_MonthsUntilExpiry, model);
	}
}
