package org.adempiere.mm.attributes.countryattribute;

 


import org.compiere.model.I_C_Country;

public interface ICountryAware
{

	int getAD_Client_ID();

	int getAD_Org_ID();

	boolean isSOTrx();

	I_C_Country getC_Country();

}
