package org.adempiere.tax.model.validator;

 


import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.compiere.model.I_C_Tax;
import org.compiere.model.ModelValidator;

import de.metas.tax.api.ITaxBL;
import de.metas.util.Services;

@Validator(I_C_Tax.class)
public class C_Tax
{
	@Init
	public void init()
	{
		final IProgramaticCalloutProvider programaticCalloutProvider = Services.get(IProgramaticCalloutProvider.class);
		programaticCalloutProvider.registerAnnotatedCallout(new org.adempiere.tax.callout.C_Tax());
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void setupIfIsWholeTax(final I_C_Tax tax)
	{
		Services.get(ITaxBL.class).setupIfIsWholeTax(tax);
	}
}
