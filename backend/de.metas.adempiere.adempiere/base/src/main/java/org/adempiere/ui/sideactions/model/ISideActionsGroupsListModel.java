package org.adempiere.ui.sideactions.model;

 


import javax.swing.ListModel;

import org.adempiere.exceptions.AdempiereException;

/**
 * Contains a list of side action groups ({@link ISideActionsGroupModel}s).
 * 
 * @author tsa
 *
 */
public interface ISideActionsGroupsListModel
{
	/**
	 * Add a group
	 * 
	 * @param group
	 */
	void addGroup(ISideActionsGroupModel group);

	/**
	 * @return groups list model
	 */
	ListModel<ISideActionsGroupModel> getGroups();

	/**
	 * @param id
	 * @return group identified by given group id or <code>null</code> if no group was found.
	 */
	ISideActionsGroupModel getGroupByIdOrNull(String id);

	/**
	 * @param id
	 * @return group identified by given group id; never returns null
	 * @throws AdempiereException if group was not found
	 */
	ISideActionsGroupModel getGroupById(String id);
}
