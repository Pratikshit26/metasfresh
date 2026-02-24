/**
 * 
 */
package org.adempiere.model.tree;

 


import org.compiere.model.PO;

/**
 * @author tsa
 *
 */
public interface ITreeListener
{

	void onNodeInserted(PO po);

	void onNodeDeleted(PO po);

	void onParentChanged(int AD_Table_ID, int nodeId, int newParentId, int oldParentId, String trxName);

}
