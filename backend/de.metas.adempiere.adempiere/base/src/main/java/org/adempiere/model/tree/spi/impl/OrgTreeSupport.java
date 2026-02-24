/**
 * 
 */
package org.adempiere.model.tree.spi.impl;

 


import org.compiere.model.I_AD_OrgType;
import org.compiere.model.MTree;

/**
 * @author tsa
 *
 */
public class OrgTreeSupport extends DefaultPOTreeSupport
{
	@Override
	protected String getPrintColorSQL(String tableAlias)
	{
		return "x."+I_AD_OrgType.COLUMNNAME_AD_PrintColor_ID;
	}

	@Override
	protected String getNodeInfoFromSQL(MTree tree, String tableAlias)
	{
		return "AD_Org "+tableAlias+" INNER JOIN AD_OrgInfo i ON ("+tableAlias+".AD_Org_ID=i.AD_Org_ID) "
		+ "LEFT OUTER JOIN AD_OrgType x ON (i.AD_OrgType_ID=x.AD_OrgType_ID)";
	}
}
