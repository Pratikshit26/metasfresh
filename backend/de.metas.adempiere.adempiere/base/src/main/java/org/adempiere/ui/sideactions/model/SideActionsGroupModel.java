package org.adempiere.ui.sideactions.model;

 


import java.util.Collections;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;

import com.google.common.collect.ImmutableList;

import de.metas.util.collections.ListUtils;

/**
 * {@link ISideActionsGroupModel} default implementation.
 *
 * @author tsa
 *
 */
public class SideActionsGroupModel implements ISideActionsGroupModel
{
	private final String id;
	private final String title;
	private final boolean defaultCollapsed;
	private final DefaultListModel<ISideAction> actions;

	public SideActionsGroupModel(final String id, final String title, final boolean defaultCollapsed)
	{
		super();

		this.id = id;
		this.title = title;
		this.defaultCollapsed = defaultCollapsed;
		actions = new DefaultListModel<>();
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public String getTitle()
	{
		return title;
	}

	@Override
	public boolean isDefaultCollapsed()
	{
		return defaultCollapsed;
	}

	@Override
	public ListModel<ISideAction> getActions()
	{
		return actions;
	}

	@Override
	public List<ISideAction> getActionsList()
	{
		final ISideAction[] actionsArr = new ISideAction[actions.getSize()];
		actions.copyInto(actionsArr);
		return ImmutableList.copyOf(actionsArr);
	}

	@Override
	public void removeAllActions()
	{
		actions.clear();
	}

	@Override
	public void addAction(final ISideAction action)
	{
		if (actions.contains(action))
		{
			return;
		}

		actions.addElement(action);
	}

	@Override
	public void removeAction(final int index)
	{
		actions.remove(index);
	}

	@Override
	public void setActions(final Iterable<? extends ISideAction> actions)
	{
		this.actions.clear();

		if (actions != null)
		{
			final List<ISideAction> actionsList = ListUtils.copyAsList(actions);
			Collections.sort(actionsList, ISideAction.COMPARATOR_ByDisplayName);

			for (final ISideAction action : actionsList)
			{
				this.actions.addElement(action);
			}
		}
	}
}
