package org.adempiere.ad.ui;

 


import java.awt.Color;

public final class NullTableColorProvider implements ITableColorProvider
{
	public static final NullTableColorProvider instance = new NullTableColorProvider();

	private NullTableColorProvider()
	{
		super();
	}

	@Override
	public Color getForegroundColor(ITable table, int rowIndexModel)
	{
		return COLOR_NONE;
	}

	@Override
	public Color getBackgroundColor(ITable table, int rowIndexModel)
	{
		return COLOR_NONE;
	}

}
