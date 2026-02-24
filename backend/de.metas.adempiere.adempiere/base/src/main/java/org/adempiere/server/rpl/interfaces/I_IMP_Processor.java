package org.adempiere.server.rpl.interfaces;

 


/**
 * Interface with additional columns
 * 
 * @author ts
 *
 */
public interface I_IMP_Processor extends org.compiere.model.I_IMP_Processor
{
	public static final String COLUMNNAME_IsLogOnlyImportErrors = "IsLogOnlyImportErrors";

	public boolean isLogOnlyImportErrors();

	public void setIsLogOnlyImportErrors(boolean IsLogOnlyImportErrors);
}
