/**
 * 
 */
package org.adempiere.model.tree;

 


import org.compiere.model.PO;

/**
 * @author tsa
 *
 */
public class TreeListenerAdapter implements ITreeListener
{
	@Override
	public void onNodeDeleted(PO po)
	{
	}

	@Override
	public void onNodeInserted(PO po)
	{
	}

	@Override
	public void onParentChanged(int AD_Table_ID, int nodeId, int newParentId, int oldParentId, String trxName)
	{
	}
}
