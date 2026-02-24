package org.adempiere.plaf;

import java.awt.Color;

import javax.swing.plaf.ColorUIResource;

 

/**
 * UI settings for Calendar date and time picker.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class CalendarUI
{
	public static final String KEY_DayPanel_Background = "Calendar.DayPanel.Background";
	
	public static final String KEY_DayPanel_WeekLabel_Background = "Calendar.DayPanel.WeekLabel.Background";
	public static final String KEY_DayPanel_WeekLabel_Foreground = "Calendar.DayPanel.WeekLabel.Foreground";
	
	public static final String KEY_DayButton_Regular_Background = "Calendar.DayButton.Regular.Background";
	public static final String KEY_DayButton_Regular_Foreground = "Calendar.DayButton.Regular.Foreground";
	//
	public static final String KEY_DayButton_Current_Background = "Calendar.DayButton.Current.Background";
	public static final String KEY_DayButton_Current_Foreground = "Calendar.DayButton.Current.Foreground";
	//
	public static final String KEY_DayButton_SetCurrent_Background = "Calendar.DayButton.SetCurrent.Background";
	public static final String KEY_DayButton_SetCurrent_Foreground = "Calendar.DayButton.SetCurrent.Foreground";
	//
	public static final String KEY_DayButton_Cancel_Background = "Calendar.DayButton.Cancel.Background";
	public static final String KEY_DayButton_Cancel_Foreground = "Calendar.DayButton.Cancel.Foreground";

	public static Object[] getUIDefaults()
	{
		return new Object[] {
				KEY_DayPanel_Background, new ColorUIResource(Color.WHITE)
				//
				, KEY_DayPanel_WeekLabel_Background, MetasFreshTheme.LOGO_TEXT_COLOR
				, KEY_DayPanel_WeekLabel_Foreground, new ColorUIResource(Color.WHITE)
				//
				, KEY_DayButton_Regular_Background, new ColorUIResource(Color.WHITE)
				, KEY_DayButton_Regular_Foreground, new ColorUIResource(Color.BLACK)
				//
				, KEY_DayButton_Current_Background, MetasFreshTheme.COLOR_Green
				, KEY_DayButton_Current_Foreground, new ColorUIResource(Color.BLACK)
				//
				, KEY_DayButton_SetCurrent_Background, MetasFreshTheme.COLOR_Green
				, KEY_DayButton_SetCurrent_Foreground, new ColorUIResource(Color.BLACK)
				//
				, KEY_DayButton_Cancel_Background, MetasFreshTheme.COLOR_RED_METAS
				, KEY_DayButton_Cancel_Foreground, new ColorUIResource(Color.WHITE)
		};
	}
}
