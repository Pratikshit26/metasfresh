package org.compiere.swing;

 


import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

/** Convenient implement what you need adapter for {@link PopupMenuListener} */
public abstract class PopupMenuListenerAdapter implements PopupMenuListener
{
	@Override
	public void popupMenuWillBecomeVisible(PopupMenuEvent e)
	{
	}

	@Override
	public void popupMenuWillBecomeInvisible(PopupMenuEvent e)
	{
	}

	@Override
	public void popupMenuCanceled(PopupMenuEvent e)
	{
	}
}
