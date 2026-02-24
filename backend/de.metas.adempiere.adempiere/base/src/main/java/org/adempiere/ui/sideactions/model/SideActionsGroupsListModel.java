package org.adempiere.ui.sideactions.model;

 


import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;

import de.metas.util.Check;

/**
 * {@link ISideActionsGroupsListModel} default implemetation.
 * 
 * @author tsa
 *
 */
public class SideActionsGroupsListModel implements ISideActionsGroupsListModel
{
	private final DefaultListModel<ISideActionsGroupModel> groups = new DefaultListModel<>();
	private final Map<String, ISideActionsGroupModel> groupId2group = new HashMap<>();

	public SideActionsGroupsListModel()
	{
		super();
	}

	@Override
	public void addGroup(final ISideActionsGroupModel group)
	{
		Check.assumeNotNull(group, "group not null");
		final String groupId = group.getId();
		this.groups.addElement(group);
		this.groupId2group.put(groupId, group);
	}

	@Override
	public ListModel<ISideActionsGroupModel> getGroups()
	{
		return this.groups;
	}

	@Override
	public ISideActionsGroupModel getGroupByIdOrNull(final String id)
	{
		return groupId2group.get(id);
	}

	@Override
	public final ISideActionsGroupModel getGroupById(final String id)
	{
		final ISideActionsGroupModel group = getGroupByIdOrNull(id);
		Check.assumeNotNull(group, "group shall exist for ID={}", id);
		return group;
	}
}
