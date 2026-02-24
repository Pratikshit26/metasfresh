package org.adempiere.ad.ui;

 


import java.awt.Color;

/**
 * {@link ITable}'s color provider.
 * 
 * NOTE to developer: instead of extending this class, please extends the {@link TableColorProviderAdapter}
 * 
 * @author tsa
 *
 */
public interface ITableColorProvider
{
	/**
	 * Value returned when provider does not want to specify a color value for given parameters
	 */
	Color COLOR_NONE = null;

	/**
	 * @return foreground color or {@link #COLOR_NONE}
	 */
	Color getForegroundColor(final ITable table, final int rowIndexModel);

	/**
	 * @return background color or {@link #COLOR_NONE}
	 */
	Color getBackgroundColor(final ITable table, final int rowIndexModel);
}
