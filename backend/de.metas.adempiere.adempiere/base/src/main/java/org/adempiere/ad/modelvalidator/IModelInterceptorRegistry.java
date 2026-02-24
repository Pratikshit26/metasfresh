package org.adempiere.ad.modelvalidator;

 


import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.compiere.model.I_AD_Client;

import de.metas.util.ISingletonService;

import javax.annotation.Nullable;

/**
 * Service used to register model interceptors
 * 
 * @author tsa
 * 
 */
public interface IModelInterceptorRegistry extends ISingletonService
{
	/**
	 * Register model interceptor for all {@link I_AD_Client}s.
	 * 
	 * @param interceptorObj interceptor annotated with {@link Interceptor} or which is implementing {@link IModelInterceptor}.
	 */
	void addModelInterceptor(Object interceptorObj);

	/**
	 * Register model interceptor for given {@link I_AD_Client}.
	 * 
	 * If <code>client</code> is null, it will have the same effect as calling {@link #addModelInterceptor(Object)}.
	 * 
	 * @param interceptorObj interceptor annotated with {@link Interceptor} or which is implementing {@link IModelInterceptor}.
	 * @param client tenant on which we register given interceptor
	 */
	void addModelInterceptor(Object interceptorObj, @Nullable I_AD_Client client);
}
