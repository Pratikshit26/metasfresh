package org.adempiere.ad.persistence;

 


import java.lang.reflect.Method;

/**
 * Handles model implementation direct method invocation.
 * 
 * @author tsa
 *
 */
/* package */class InvokeParentMethodInfo extends AbstractModelMethodInfo
{

	public InvokeParentMethodInfo(final Method interfaceMethod)
	{
		super(interfaceMethod);
	}

	@Override
	public Object invoke(IModelInternalAccessor model, Object[] methodArgs) throws Exception
	{
		final Method method = getInterfaceMethod();
		return model.invokeParent(method, methodArgs);
	}

}
