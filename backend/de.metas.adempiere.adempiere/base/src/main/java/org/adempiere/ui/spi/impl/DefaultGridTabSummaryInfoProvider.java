package org.adempiere.ui.spi.impl;

 

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import org.adempiere.ui.api.IGridTabSummaryInfo;
import org.adempiere.ui.api.StringGridTabSummaryInfo;
import org.adempiere.ui.spi.IGridTabSummaryInfoProvider;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.GridTabVO;
import org.compiere.model.GridTable;
import org.compiere.util.DisplayType;
import org.slf4j.Logger;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Properties;

/**
 * Default provider of {@link IGridTabSummaryInfo}.
 * 
 * Used when no other special provider is defined for a certain table.
 * 
 * This default provider copies the behavior of the old adempiere's "GridTab.getTrxInfo".
 * 
 * It checks if there is an AD_Message in the tab, and builds the string accordingly if so.
 *
 */
public class DefaultGridTabSummaryInfoProvider implements IGridTabSummaryInfoProvider
{
	protected Logger logger = LogManager.getLogger(getClass());

	@Override
	public IGridTabSummaryInfo getSummaryInfo(final GridTab gridTab)
	{

		// defining variables
		final GridTabVO gridTabVO = gridTab.getGridTabVO();
		final Properties ctx = gridTab.getCtx();
		final GridTable gridTable = gridTab.getMTable();
		final int currentRow = gridTab.getCurrentRowNoCheck(); // NOTE: we are getting current row without checking because in some cases we got "Row index out of range" exception

		// metas: Method was changed to enable dynamic status line messages.
		if (gridTabVO.AD_Message_ID == null) // Does the Tab have a message?
		{
			return IGridTabSummaryInfo.NULL;
		}

		// Get message
		final AdMessageKey adMessageKey = Services.get(IMsgBL.class).getAdMessageKeyById(gridTabVO.AD_Message_ID).get();

		// Fill message
		String inStr = Services.get(IMsgBL.class).getMsg(ctx, adMessageKey);
		String token;
		StringBuffer outStr = new StringBuffer(); // new, empty outStr

		int i = inStr.indexOf('@'); // get beginning tag
		while (i != -1) // While there's another token
		{
			outStr.append(inStr.substring(0, i)); // outStr = outStr + (inStr up to Token)
			inStr = inStr.substring(i + 1, inStr.length()); // inStr = inStr - outStr

			int j = inStr.indexOf('@'); // get ending tag
			if (j < 0) // if no ending tag is found:
			{
				logger.error("No second tag: " + inStr);
				return IGridTabSummaryInfo.NULL;
			}
			token = inStr.substring(0, j); // String between tags

			GridField field = gridTab.getField(token); // Gets the GridField for this token.

			if (field != null) // Field found?
			{
				// The GridField could be obsolete, get the Current Value:
				Object value = null;
				for (int colIndex = 0; colIndex < gridTable.getColumnCount(); colIndex++) // iterate Column Index
				{
					if (gridTable.getColumnName(colIndex).equals(field.getColumnName())) 	// is Column Index correct?
					{
						value = gridTable.getValueAt(currentRow, colIndex);		// Get the current Value
						break;
					}
				}

				if (value == null)
				{
					outStr.append("");
				}
				else
				{
					// Format string depending on DisplayType
					final int dt = field.getDisplayType();
					final String valueStr;

					if (DisplayType.isNumeric(dt)) // Number
					{
						final NumberFormat fmt = DisplayType.getNumberFormat(dt);
						valueStr = fmt.format(value);
					}
					else if (DisplayType.isDate(dt)) // Date
					{
						final DateFormat fmt = DisplayType.getDateFormat(dt);
						valueStr = fmt.format(value);
					}
					else if (DisplayType.isLookup(dt) && field.getLookup() != null)
					{
						valueStr = field.getLookup().getDisplay(value); // Quick and Dirty solution, does not work in every case!
					}
					else
					{
						valueStr = value.toString();
					}

					outStr.append(valueStr);
				}
			}
			else
			// field not found
			{
				logger.info("No Field for token \"" + token + "\" found");
				outStr.append("@" + token + "@");
			}
			inStr = inStr.substring(j + 1, inStr.length()); // inStr = inStr - token
			i = inStr.indexOf('@'); // look for next token
		}

		// return message
		outStr.append(inStr); // add the rest of the string
		return new StringGridTabSummaryInfo(outStr.toString(), true); // translated=true
		// metas: end
	}
}
