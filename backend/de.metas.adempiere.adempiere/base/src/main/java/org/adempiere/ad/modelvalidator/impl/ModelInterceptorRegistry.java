package org.adempiere.ad.modelvalidator.impl;

 


import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.compiere.model.I_AD_Client;
import org.compiere.model.ModelValidationEngine;


public class ModelInterceptorRegistry implements IModelInterceptorRegistry
{
	@Override
	public void addModelInterceptor(final Object interceptorObj, final I_AD_Client client)
	{
		ModelValidationEngine.get().addModelValidator(interceptorObj, client);
	}

	@Override
	public void addModelInterceptor(final Object interceptorObj)
	{
		ModelValidationEngine.get().addModelValidator(interceptorObj);
	}
}
