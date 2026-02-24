package org.adempiere.ui.sideactions.model;

 


/**
 * Base class for easily creating clickable side panel actions.
 * 
 * @author tsa
 *
 */
public abstract class ExecutableSideAction extends AbstractSideAction
{
	@Override
	public SideActionType getType()
	{
		return SideActionType.ExecutableAction;
	}

	@Override
	public final void setToggled(boolean toggled)
	{
		if (toggled)
		{
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public final boolean isToggled()
	{
		return false;
	}

	@Override
	public abstract String getDisplayName();

	@Override
	public abstract void execute();
}
