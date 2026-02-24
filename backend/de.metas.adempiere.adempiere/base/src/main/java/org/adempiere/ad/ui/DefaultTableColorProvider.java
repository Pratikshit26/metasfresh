package org.adempiere.ad.ui;

 


import java.awt.Color;
import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.plaf.AdempierePLAF;
import org.compiere.util.Env;

public class DefaultTableColorProvider extends TableColorProviderAdapter
{
	/** Color Column Index of Model */
	private int colorColumnIndex = -1;
	/** Color Column compare data */
	private Object colorDataCompare = Env.ZERO;

	@Override
	public Color getForegroundColor(ITable table, int rowIndexModel)
	{
		final int cCode = getRelativeForegroundColor(table, rowIndexModel);
		if (cCode == 0)
		{
			return COLOR_NONE;								// Black
		}
		else if (cCode < 0)
		{
			return AdempierePLAF.getTextColor_Issue();		// Red
		}
		else
		{
			return AdempierePLAF.getTextColor_OK();		// Blue
		}
	}

	/**
	 * Get ColorCode for Row.
	 * 
	 * <pre>
	 * If numerical value in compare column is
	 * 	negative = -1,
	 *      positive = 1,
	 *      otherwise = 0
	 *  If Timestamp
	 * </pre>
	 * 
	 * @param table
	 * @param rowIndexModel
	 * @return color code
	 */
	private int getRelativeForegroundColor(final ITable table, final int rowIndexModel)
	{
		if (colorColumnIndex < 0)
		{
			return 0;
		}

		Object data = table.getModelValueAt(rowIndexModel, colorColumnIndex);
		int cmp = 0;

		// We need to have a Number
		if (data == null)
		{
			return 0;
		}

		try
		{
			if (data instanceof Timestamp)
			{
				if (colorDataCompare == null || !(colorDataCompare instanceof Timestamp))
					colorDataCompare = new Timestamp(System.currentTimeMillis());
				cmp = ((Timestamp)colorDataCompare).compareTo((Timestamp)data);
			}
			else
			{
				if (colorDataCompare == null || !(colorDataCompare instanceof BigDecimal))
					colorDataCompare = Env.ZERO;
				if (!(data instanceof BigDecimal))
					data = new BigDecimal(data.toString());
				cmp = ((BigDecimal)colorDataCompare).compareTo((BigDecimal)data);
			}
		}
		catch (Exception e)
		{
			return 0;
		}

		if (cmp > 0)
		{
			return -1;
		}
		if (cmp < 0)
		{
			return 1;
		}
		return 0;
	}

	public int getColorColumnIndex()
	{
		return colorColumnIndex;
	}

	public void setColorColumnIndex(int colorColumnIndexModel)
	{
		this.colorColumnIndex = colorColumnIndexModel;
	}

	public Object getColorDataCompare()
	{
		return colorDataCompare;
	}

	public void setColorDataCompare(Object colorDataCompare)
	{
		this.colorDataCompare = colorDataCompare;
	}
}
