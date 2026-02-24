package org.adempiere.ad.wrapper;

 


import java.lang.reflect.Method;
import java.util.Set;

import org.adempiere.ad.persistence.IModelInternalAccessor;
import org.adempiere.exceptions.AdempiereException;

/**
 * Implementation of {@link IModelInternalAccessor} which wraps a {@link POJOWrapper}.
 * 
 * @author tsa
 *
 */
class POJOModelInternalAccessor implements IModelInternalAccessor
{
	private final POJOWrapper pojoWrapper;

	POJOModelInternalAccessor(final POJOWrapper pojoWrapper)
	{
		super();
		this.pojoWrapper = pojoWrapper;
	}

	@Override
	public Set<String> getColumnNames()
	{
		return pojoWrapper.getColumnNames();
	}

	@Override
	public int getColumnIndex(final String propertyName)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("POJOWrapper has no supported for column indexes");
	}

	@Override
	public boolean isVirtualColumn(final String columnName)
	{
		return pojoWrapper.isCalculated(columnName);
	}

	@Override
	public Object getValue(final String propertyName, final int idx, final Class<?> returnType)
	{
		return getValue(propertyName, returnType);
	}

	@Override
	public Object getValue(final String propertyName, final Class<?> returnType)
	{
		return pojoWrapper.getValue(propertyName, returnType);
	}

	@Override
	public boolean setValue(final String propertyName, final Object value)
	{
		pojoWrapper.setValue(propertyName, value);
		return true;
	}

	@Override
	public boolean setValueNoCheck(String columnName, Object value)
	{
		pojoWrapper.setValue(columnName, value);
		return true;
	}

	@Override
	public Object getReferencedObject(final String propertyName, final Method interfaceMethod) throws Exception
	{
		return pojoWrapper.getReferencedObject(propertyName, interfaceMethod);
	}

	@Override
	public void setValueFromPO(final String idPropertyName, final Class<?> parameterType, final Object value)
	{
		final String propertyName;
		if (idPropertyName.endsWith("_ID"))
		{
			propertyName = idPropertyName.substring(0, idPropertyName.length() - 3);
		}
		else
		{
			throw new AdempiereException("Invalid idPropertyName: " + idPropertyName);
		}
		pojoWrapper.setReferencedObject(propertyName, value);
	}

	@Override
	public boolean invokeEquals(final Object[] methodArgs)
	{
		return pojoWrapper.invokeEquals(methodArgs);
	}

	@Override
	public Object invokeParent(final Method method, final Object[] methodArgs) throws Exception
	{
		throw new IllegalStateException("Invoking parent method is not supported");
	}

	@Override
	public boolean isKeyColumnName(final String columnName)
	{
		return pojoWrapper.isKeyColumnName(columnName);
	}

	@Override
	public boolean isCalculated(final String columnName)
	{
		return pojoWrapper.isCalculated(columnName);
	}

	@Override
	public boolean hasColumnName(String columnName)
	{
		return pojoWrapper.hasColumnName(columnName);
	}
}
