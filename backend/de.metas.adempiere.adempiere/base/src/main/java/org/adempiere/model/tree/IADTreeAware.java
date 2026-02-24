package org.adempiere.model.tree;

 


import org.compiere.model.I_AD_Tree;

/**
 * Interface that allows us to access the tree specific fields of a given record.
 * 
 * @task 07393
 */
public interface IADTreeAware
{

	/** Column name AD_Tree_ID */
    public static final String COLUMNNAME_AD_Tree_ID = "AD_Tree_ID";

	/** Set Tree.
	  * Identifies a Tree
	  */
	public void setAD_Tree_ID (int AD_Tree_ID);

	/** Get Tree.
	  * Identifies a Tree
	  */
	public int getAD_Tree_ID();

	public I_AD_Tree getAD_Tree();
}
