package org.adempiere.ad.ui;

 


import java.awt.Color;

public abstract class TableColorProviderAdapter implements ITableColorProvider
{
	@Override
	public Color getForegroundColor(final ITable table, final int rowIndexModel)
	{
		return COLOR_NONE;
	}

	@Override
	public Color getBackgroundColor(final ITable table, final int rowIndexModel)
	{
		return COLOR_NONE;
	}

}
