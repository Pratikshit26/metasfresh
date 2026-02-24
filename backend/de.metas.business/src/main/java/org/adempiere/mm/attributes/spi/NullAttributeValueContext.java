package org.adempiere.mm.attributes.spi;

 


public class NullAttributeValueContext implements IAttributeValueContext
{
	public static final transient NullAttributeValueContext instance = new NullAttributeValueContext();
	
	private NullAttributeValueContext()
	{
		super();
	}

	@Override
	public IAttributeValueContext copy()
	{
		return this;
	}

	@Override
	public Object setParameter(String parameterName, Object value)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T getParameter(String parameterName)
	{
		return null;
	}
}
