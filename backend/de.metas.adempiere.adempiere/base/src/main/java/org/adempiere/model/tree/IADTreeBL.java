package org.adempiere.model.tree;

 


import java.util.List;

import org.compiere.model.MTreeNode;

import de.metas.util.ISingletonService;

public interface IADTreeBL extends ISingletonService
{

	/**
	 * For the given <code>parent</code> and list of <code>ids</code>, this method iterates the parent's child nodes (recursing if a child has {@link MTreeNode#isSummary()} <code>== true</code>) and
	 * sets invokes {@link MTreeNode#setDisplayed(boolean)} base on whether the node is contained in <code>ids</code> or not.
	 * 
	 * @param parent
	 * @param ids
	 */
	boolean filterIds(MTreeNode parent, List<Integer> ids);

}
