package org.adempiere.plaf;

 


import javax.swing.JComponent;
import javax.swing.JToolBar;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalToolBarUI;

public class AdempiereToolBarUI extends MetalToolBarUI
{
	/**
	 * The UI Class ID to bind this UI to
	 * See {@link JToolBar#getUIClassID()}.
	 */public static final String uiClassID = "ToolBarUI";

	public static ComponentUI createUI(final JComponent b)
	{
		return new AdempiereToolBarUI();
	}

	public static Object[] getUIDefaults()
	{
		return new Object[] {
				uiClassID, AdempiereToolBarUI.class.getName()
		};
	}

	@Override
	public void installUI(final JComponent c)
	{
		super.installUI(c);
		
		final JToolBar toolBar = (JToolBar)c;
		
		toolBar.putClientProperty("JToolBar.isRollover", Boolean.TRUE);
		toolBar.setBorderPainted(false);
		toolBar.setFloatable(false);
	}

}
