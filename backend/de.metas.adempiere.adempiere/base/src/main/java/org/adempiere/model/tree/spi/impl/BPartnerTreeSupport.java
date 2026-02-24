package org.adempiere.model.tree.spi.impl;

 


import org.compiere.model.I_C_BP_Group;
import org.compiere.model.MTree;

/**
 * 
 * @author tsa
 *
 */
public class BPartnerTreeSupport extends DefaultPOTreeSupport
{
	@Override
	protected String getPrintColorSQL(String tableAlias)
	{
		return "x."+I_C_BP_Group.COLUMNNAME_AD_PrintColor_ID;
	}

	@Override
	protected String getNodeInfoFromSQL(MTree tree, String tableAlias)
	{
		return "C_BPartner "+tableAlias+" INNER JOIN C_BP_Group x ON ("+tableAlias+".C_BP_Group_ID=x.C_BP_Group_ID)";
	}
}
