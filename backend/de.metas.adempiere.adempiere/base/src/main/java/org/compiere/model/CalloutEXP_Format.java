package org.compiere.model;

 


import java.util.Properties;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.util.Services;

public class CalloutEXP_Format extends CalloutEngine
{
	public String onAD_Table_ID(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		I_EXP_Format format = InterfaceWrapperHelper.create(mTab, I_EXP_Format.class);
		if (format.getAD_Table_ID() > 0)
		{
			String tableName = Services.get(IADTableDAO.class).retrieveTableName(format.getAD_Table_ID());
			format.setValue(tableName);
			format.setName(tableName);
		}
		return "";
	}

	public String setLineValueName(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		I_EXP_FormatLine line = InterfaceWrapperHelper.create(mTab, I_EXP_FormatLine.class);
		if (line.getEXP_EmbeddedFormat_ID() > 0
				&& X_EXP_FormatLine.TYPE_EmbeddedEXPFormat.equals(line.getType()))
		{
			I_EXP_Format format = line.getEXP_EmbeddedFormat();
			line.setValue(format.getValue());
			line.setName(format.getName());
		}
		else if (line.getAD_Column_ID() > 0)
		{
			MColumn column = MColumn.get(ctx, line.getAD_Column_ID());
			String columnName = column.getColumnName();
			line.setValue(columnName);
			line.setName(columnName);
			if (column.isMandatory())
			{
				line.setIsMandatory(true);
			}
		}
		return "";
	}
	
}
