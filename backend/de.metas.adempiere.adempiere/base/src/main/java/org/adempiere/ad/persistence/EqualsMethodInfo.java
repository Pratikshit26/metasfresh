package org.adempiere.ad.persistence;

 


import java.lang.reflect.Method;

/**
 * Handles Object.equals() method.
 * 
 * @author tsa
 *
 */
/* package */class EqualsMethodInfo extends AbstractModelMethodInfo
{

	public EqualsMethodInfo(final Method interfaceMethod)
	{
		super(interfaceMethod);
	}

	@Override
	public Object invoke(IModelInternalAccessor model, Object[] methodArgs) throws Exception
	{
		return model.invokeEquals(methodArgs);
	}

}
