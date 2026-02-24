/**
 * 
 */
package org.adempiere.model.tree.spi.impl;

 


import org.compiere.model.I_C_Channel;
import org.compiere.model.MTree;

/**
 * @author tsa
 *
 */
public class CampainTreeSupport extends DefaultPOTreeSupport
{
	@Override
	protected String getPrintColorSQL(String tableAlias)
	{
		return "x."+I_C_Channel.COLUMNNAME_AD_PrintColor_ID;
	}

	@Override
	protected String getNodeInfoFromSQL(MTree tree, String tableAlias)
	{
		return "C_Campaign "+tableAlias+" LEFT OUTER JOIN C_Channel x ON ("+tableAlias+".C_Channel_ID=x.C_Channel_ID)";
	}
}
