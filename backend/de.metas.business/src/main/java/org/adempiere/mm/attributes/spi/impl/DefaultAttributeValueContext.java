package org.adempiere.mm.attributes.spi.impl;

 


import java.util.HashMap;
import java.util.Map;

import org.adempiere.mm.attributes.spi.IAttributeValueContext;

public class DefaultAttributeValueContext implements IAttributeValueContext
{
	private final Map<String, Object> parameters;
	
	public DefaultAttributeValueContext()
	{
		this(null);
	}

	protected DefaultAttributeValueContext(final Map<String, Object> parameters)
	{
		super();

		if (parameters == null || parameters.isEmpty())
		{
			this.parameters = new HashMap<String, Object>();
		}
		else
		{
			this.parameters = new HashMap<String, Object>(parameters);
		}
	}
	
	@Override
	public IAttributeValueContext copy()
	{
		return new DefaultAttributeValueContext(parameters);
	}

	@Override
	public final Object setParameter(final String parameterName, final Object value)
	{
		final Object valueOld = parameters.put(parameterName, value);
		return valueOld;
	}

	@Override
	public final <T> T getParameter(final String parameterName)
	{
		@SuppressWarnings("unchecked")
		final T value = (T)parameters.get(parameterName);
		return value;
	}

	protected final Map<String, Object> getParameters()
	{
		return new HashMap<String, Object>(parameters);
	}
}
