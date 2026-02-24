package org.adempiere.ad.persistence;

 


import java.lang.reflect.Method;
import java.math.BigDecimal;

import org.compiere.model.PO;

/**
 * Value getter handler.
 * 
 * e.g. org.compiere.model.I_C_Invoice.getGrandTotal()
 * 
 * @author tsa
 *
 */
/* package */class ValueGetterMethodInfo extends AbstractModelMethodInfo
{
	private static final Object DEFAULTVALUE_NotSupported = new Object();

	private final String propertyName;
	private final Class<?> returnType;
	private final Object defaultValue;

	public ValueGetterMethodInfo(final Method interfaceMethod, final String propertyName)
	{
		super(interfaceMethod);
		this.propertyName = propertyName;
		this.returnType = interfaceMethod.getReturnType();

		//
		// Default Value
		if (returnType == int.class)
		{
			this.defaultValue = Integer.valueOf(0);
		}
		else if (returnType == BigDecimal.class)
		{
			this.defaultValue = BigDecimal.ZERO;
		}
		else if (PO.class.isAssignableFrom(returnType))
		{
			// TODO: figure out which is this case
			this.defaultValue = DEFAULTVALUE_NotSupported;
		}
		else
		{
			this.defaultValue = null;
		}
	}

	@Override
	public Object invoke(final IModelInternalAccessor model, final Object[] methodArgs) throws Exception
	{
		Object value = null;
		final int idx = model.getColumnIndex(propertyName);
		if (idx >= 0)
		{
			value = model.getValue(propertyName, idx, returnType);
		}

		if (value != null)
		{
			return value;
		}

		if (defaultValue == DEFAULTVALUE_NotSupported)
		{
			throw new IllegalArgumentException("Method default value not supported - " + getInterfaceMethod());
		}
		return defaultValue;
	}

}
