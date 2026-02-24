/**
 * 
 */
package org.adempiere.model.tree.spi.impl;

 


import org.compiere.model.I_M_Product_Category;
import org.compiere.model.MTree;

/**
 * @author tsa
 *
 */
public class ProductTreeSupport extends DefaultPOTreeSupport
{
	@Override
	protected String getPrintColorSQL(String tableAlias)
	{
		return "x." + I_M_Product_Category.COLUMNNAME_AD_PrintColor_ID;
	}

	@Override
	protected String getNodeInfoFromSQL(MTree tree, String tableAlias)
	{
		return "M_Product " + tableAlias + " INNER JOIN M_Product_Category x ON (" + tableAlias + ".M_Product_Category_ID=x.M_Product_Category_ID)";
	}
}
