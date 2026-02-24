package org.adempiere.server.rpl.interfaces;

 


import org.adempiere.process.rpl.requesthandler.model.I_IMP_RequestHandler;

/**
 * @author ts
 */
public interface I_EXP_Format extends org.compiere.model.I_EXP_Format
{
	// @formatter:off
	String RplImportMode_RecordExists = "L"; 
	String RplImportMode_RecordIsNew = "I";
	// @formatter:on

	// @formatter:off
	String COLUMNNAME_RplImportMode = "RplImportMode";
	String getRplImportMode();
	void setRplImportMode(String RplImportMode);
	// @formatter:on

	// t.schoneberg@metas.de, 03132: adding handler reference
	// @formatter:off
	String COLUMNNAME_IMP_RequestHandler_ID = "IMP_RequestHandler_ID";
	I_IMP_RequestHandler getIMP_RequestHandler();
	int getIMP_RequestHandler_ID();
	void setIMP_RequestHandler_ID(int IMP_RequestHandler_ID);
	// @formatter:on
	// end of t.schoneberg@metas.de, 03132

	// @formatter:off
	String COLUMNNAME_IsAlwaysFlagWithIssue = "IsAlwaysFlagWithIssue";
	boolean isAlwaysFlagWithIssue();
	void setIsAlwaysFlagWithIssue(boolean IsAlwaysFlagWithIssue);
	// @formatter:on
}
