package org.adempiere.ad.callout.model.validator;

 


import java.util.Properties;

import org.adempiere.ad.callout.api.IADColumnCalloutDAO;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_ColumnCallout;
import org.compiere.model.MColumn;
import org.compiere.model.ModelValidator;

import de.metas.util.Services;

@Validator(I_AD_ColumnCallout.class)
public class AD_ColumnCallout
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void setSeqNo(final I_AD_ColumnCallout columnCallout)
	{
		if (columnCallout.getSeqNo() != 0)
		{
			return;
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(columnCallout);
		final int adColumnId = columnCallout.getAD_Column_ID();
		final int lastSeqNo = Services.get(IADColumnCalloutDAO.class).retrieveColumnCalloutLastSeqNo(ctx, adColumnId);
		final int seqNo = lastSeqNo + 10;
		columnCallout.setSeqNo(seqNo);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }
			, ifColumnsChanged = I_AD_ColumnCallout.COLUMNNAME_AD_Column_ID)
	public void setAD_Table_ID(final I_AD_ColumnCallout columnCallout)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(columnCallout);
		final String trxName = InterfaceWrapperHelper.getTrxName(columnCallout);
		final int adColumnId = columnCallout.getAD_Column_ID();
		final int AD_Table_ID = MColumn.getTable_ID(ctx, adColumnId, trxName);
		columnCallout.setAD_Table_ID(AD_Table_ID);
	}

}
