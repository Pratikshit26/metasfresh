package org.adempiere.ad.modelvalidator.impl;

 


import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.compiere.model.I_AD_Client;

public class PlainModelInterceptorRegistry implements IModelInterceptorRegistry
{

	@Override
	public void addModelInterceptor(Object interceptorObj, I_AD_Client client)
	{
		POJOLookupMap.get().addModelValidator(interceptorObj, client);
	}

	@Override
	public void addModelInterceptor(Object interceptorObj)
	{
		final I_AD_Client client = null;  // register it for all clients
		POJOLookupMap.get().addModelValidator(interceptorObj, client);
	}

}
