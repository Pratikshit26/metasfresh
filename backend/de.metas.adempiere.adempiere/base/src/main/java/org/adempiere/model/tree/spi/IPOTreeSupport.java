/**
 * 
 */
package org.adempiere.model.tree.spi;

 

import org.compiere.model.GridTab;
import org.compiere.model.MTree;
import org.compiere.model.MTreeNode;
import org.compiere.model.MTree_Base;
import org.compiere.model.PO;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 
 * NOTE: implementations of this interface are stateful
 * 
 * @author tsa
 *
 */
public interface IPOTreeSupport
{
	int UNKNOWN_ParentID = -100;
	int UNKNOWN_TreeID = -100;

	/**
	 * Returns the AD_Tree_ID for the given <code>po</code>
	 */
	int getAD_Tree_ID(PO po);

	/**
	 * This method returns the tree node ID of the given <code>po</code>'s parent, if it can be deducted from the po (which is for example the case with product categories).
	 * <p>
	 * Note that the default implementation returns {@link #UNKNOWN_ParentID}
	 */
	int getParent_ID(PO po);

	int getOldParent_ID(PO po);

	boolean isParentChanged(PO po);

	@Nullable String getParentIdSQL();

	String getTreeType();

	void setParent_ID(MTree_Base tree, int nodeId, int parentId, String trxName);

	String getNodeInfoSelectSQL(MTree tree, final List<Object> sqlParams);

	/**
	 * Where Clause for selecting records from PO table
	 * 
	 * @return SQL Where Clause or null
	 */
	@Nullable String getWhereClause(MTree_Base tree);

	MTreeNode getNodeInfo(GridTab gridTab);

	/**
	 * Advice the implementation to not enforce role access while loading the {@link MTreeNode}.
	 * 
	 * @see #loadNodeInfo(MTree, ResultSet)
	 */
	void disableRoleAccessCheckWhileLoading();

	/**
	 * Load {@link MTreeNode}.
	 * 
	 * @return loaded tree node or null if load could not be loaded or the role does not have access to that node
	 */
	@Nullable MTreeNode loadNodeInfo(MTree tree, ResultSet rs) throws SQLException;

	/**
	 * To be called by the API!
	 */
	void setTableName(String tableName);
}
