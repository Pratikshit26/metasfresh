package org.adempiere.plaf;

 

/**
 * UI settings for embedded find panel.
 * 
 * If you are looking for the collapsible component which is used to wrap the find panel in standard windows, check {@link AdempiereTaskPaneUI}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class FindPanelUI
{
	public static final String KEY_StandardWindow_Height = "FindPanel.StandardWindow.Height";
	// NOTE: task 08592: the width prior to this task was 160; changing it in order to add yet another search field to the invoice candidate window.
	public static final int DEFAULT_StandardWindow_Height = 200;
	//
	public static final String KEY_Dialog_Height = "FindPanel.Dialog.Height";
	public static final int DEFAULT_Dialog_Height = 200;

	public static Object[] getUIDefaults()
	{
		return new Object[] {
				KEY_StandardWindow_Height, DEFAULT_StandardWindow_Height
				, KEY_Dialog_Height, DEFAULT_Dialog_Height
		};
	}

}
