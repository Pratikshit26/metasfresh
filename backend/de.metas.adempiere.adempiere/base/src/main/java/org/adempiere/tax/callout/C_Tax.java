package org.adempiere.tax.callout;

 


import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.compiere.model.I_C_Tax;

import de.metas.tax.api.ITaxBL;
import de.metas.util.Services;

@Callout(I_C_Tax.class)
public class C_Tax
{
	@CalloutMethod(columnNames = { I_C_Tax.COLUMNNAME_IsWholeTax })
	public void setupIfIsWholeTax(final I_C_Tax tax, final ICalloutField field)
	{
		Services.get(ITaxBL.class).setupIfIsWholeTax(tax);
	}

}
