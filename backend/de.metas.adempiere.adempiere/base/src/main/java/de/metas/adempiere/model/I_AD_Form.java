package de.metas.adempiere.model;

 


public interface I_AD_Form extends org.compiere.model.I_AD_Form
{
	public static final String COLUMNNAME_IsOneInstanceOnly = "IsOneInstanceOnly";

	public void setIsOneInstanceOnly(boolean IsOneInstanceOnly);

	public boolean isOneInstanceOnly();

}
