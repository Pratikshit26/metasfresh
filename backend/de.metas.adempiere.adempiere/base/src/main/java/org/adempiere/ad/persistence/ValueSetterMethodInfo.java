package org.adempiere.ad.persistence;

 


import java.lang.reflect.Method;

/**
 * Value setter handler.
 * 
 * e.g. org.compiere.model.I_C_Invoice.setGrandTotal(BigDecimal)
 * 
 * @author tsa
 *
 */
/* package */class ValueSetterMethodInfo extends AbstractModelMethodInfo
{

	private final String propertyName;

	public ValueSetterMethodInfo(final Method interfaceMethod, final String propertyName)
	{
		super(interfaceMethod);

		this.propertyName = propertyName;
	}

	@Override
	public Object invoke(final IModelInternalAccessor model, final Object[] methodArgs)
	{
		final Object value = methodArgs[0];
		model.setValue(propertyName, value);
		return null;
	}

}
