package org.adempiere.ui.sideactions.model;

 


import org.adempiere.util.lang.ObjectUtils;

public abstract class ToggableSideAction extends AbstractSideAction
{
	private boolean toggled;
	
	public ToggableSideAction()
	{
		super();
	}
	
	public ToggableSideAction(final String id)
	{
		super(id);
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public SideActionType getType()
	{
		return SideActionType.Toggle;
	}

	@Override
	public final void setToggled(boolean toggled)
	{
		this.toggled = toggled;
	}

	@Override
	public final boolean isToggled()
	{
		return toggled;
	}

	@Override
	public abstract String getDisplayName();

	@Override
	public abstract void execute();
}
