package org.adempiere.ad.persistence;

 


import java.lang.reflect.Method;

/**
 * Abstract implementation of {@link IModelClassInfo}.
 * 
 * @author tsa
 *
 */
/* package */abstract class AbstractModelMethodInfo implements IModelMethodInfo
{
	private final Method interfaceMethod;

	public AbstractModelMethodInfo(final Method interfaceMethod)
	{
		super();
		this.interfaceMethod = interfaceMethod;

	}

	@Override
	public Method getInterfaceMethod()
	{
		return interfaceMethod;
	}
}
