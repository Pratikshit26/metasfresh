package org.adempiere.mm.attributes.spi;

 


public interface IAttributeValueContext
{
	IAttributeValueContext copy();
	
	Object setParameter(String parameterName, Object value);

	<T> T getParameter(String parameterName);
}
