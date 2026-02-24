package org.adempiere.plaf;

 

/**
 * UI settings for standard report viewer.
 * 
 * @author metas-dev <dev@metasfresh.com>
 */
public class PrintViewerUI
{
	public static final String KEY_ZoomLevel = "org.compiere.print.View.ZoomLevel";
	public static final String DEFAULT_ZoomLevel = "150%";

	public static Object[] getUIDefaults()
	{
		return new Object[] {
				KEY_ZoomLevel, DEFAULT_ZoomLevel
		};
	}

}
