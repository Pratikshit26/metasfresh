package org.adempiere.plaf;

 


import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicEditorPaneUI;

/**
 * UI for {@link JEditorPane}.
 *
 * @author tsa
 *
 */
public class AdempiereEditorPaneUI extends BasicEditorPaneUI
{
	/**
	 * The UI Class ID to bind this UI to
	 * See {@link JEditorPane#getUIClassID()}.
	 */
	public static final String uiClassID ="EditorPaneUI";
	
	// NOTE: factory method used by UIDefaults. Without this method, this class won't be used!
	public static ComponentUI createUI(final JComponent c)
	{
		return new AdempiereEditorPaneUI();
	}

	@Override
	public void installUI(final JComponent c)
	{
		super.installUI(c);

		// Let the JEditorPane components (HTML editing) use the same fonts that we are using in our theme (see task 09141 - Font Harmonization).
		c.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
	}
}
