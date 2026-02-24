package org.adempiere.plaf;

 

/**
 * UI settings for VTreePanel (the tree which is displayed in main window and in standard windows which have a tree)
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class VTreePanelUI
{
	/** Shall we display the "search toolbar" on top (if true) or on bottom (if false) */
	public static final String KEY_SearchPanelAnchorOnTop = "VTreePanelUI.SearchPanelAnchorOnTop";
	public static final boolean DEFAULT_SearchPanelAnchorOnTop = true;

	public static Object[] getUIDefaults()
	{
		return new Object[] {
				KEY_SearchPanelAnchorOnTop, DEFAULT_SearchPanelAnchorOnTop
		};
	}

}
