package de.metas.adempiere.model;

 



public interface IPOReferenceAware
{
	public static final String COLUMNNAME_Record_ID = "Record_ID";
	
	int getRecord_ID();
	
	void setRecord_ID(int Record_ID);
	
	public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";
	
	int getAD_Table_ID();
	
	void setAD_Table_ID(int AD_Table_ID);
}
