package org.adempiere.model.tree.impl;

 


import java.util.List;

import org.adempiere.model.tree.IADTreeBL;
import org.compiere.model.MTreeNode;

import de.metas.util.Check;

public class ADTreeBL implements IADTreeBL
{
	@Override
	public boolean filterIds(final MTreeNode node, final List<Integer> ids)
	{
		Check.assumeNotNull(ids, "Param 'ids' is not null");

		boolean atLeastOneChildIsDisplayed = false;

		if (node == null)
		{
			return false; // nothing to do
		}

		for (final MTreeNode currentChild : node.getChildrenAll())
		{
				// recurse
				atLeastOneChildIsDisplayed = filterIds(currentChild, ids) || atLeastOneChildIsDisplayed;
		}
		
		if (ids.contains(node.getNode_ID()) || atLeastOneChildIsDisplayed)
		{
			node.setDisplayed(true);
			atLeastOneChildIsDisplayed = true;
		}
		else
		{
			node.setDisplayed(false);
		}	
		return atLeastOneChildIsDisplayed;
	}
}
