package org.adempiere.ad.persistence;

 


import java.lang.reflect.Method;

/**
 * Stores meta data informations about a model class method.
 * 
 * @author tsa
 *
 */
public interface IModelMethodInfo
{
	/**
	 * Gets wrapped interface method.
	 * 
	 * @return interface method
	 */
	Method getInterfaceMethod();

	/**
	 * Invoke wrapped method using given <code>model</code>.
	 * 
	 * @param model
	 * @param methodArgs
	 * @return
	 * @throws Exception
	 */
	Object invoke(IModelInternalAccessor model, Object[] methodArgs) throws Exception;

}
