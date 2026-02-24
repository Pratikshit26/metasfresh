package org.adempiere.mm.attributes.api;

 


import org.adempiere.mm.attributes.spi.IAttributeValueContext;

import de.metas.util.Check;

public final class CurrentAttributeValueContextProvider
{
	private static final ThreadLocal<IAttributeValueContext> currentAttributesContextRef = new ThreadLocal<IAttributeValueContext>();

	private CurrentAttributeValueContextProvider()
	{
		super();
	}

	/**
	 * @return {@link IAttributeValueContext} available or <code>null</code>
	 */
	public static IAttributeValueContext getCurrentAttributesContextOrNull()
	{
		return currentAttributesContextRef.get();
	}

	public static IAttributeValueContext setCurrentAttributesContext(final IAttributeValueContext attributesContext)
	{
		final IAttributeValueContext attributesContextOld = currentAttributesContextRef.get();

		currentAttributesContextRef.set(attributesContext);
		return attributesContextOld;
	}

	/**
	 * Makes sure current attribute context is not set (i.e. null)
	 */
	public static void assertNoCurrentContext()
	{
		final IAttributeValueContext currentAttributesContext = getCurrentAttributesContextOrNull();
		Check.assumeNull(currentAttributesContext, "currentAttributesContext shall be null but it was {}", currentAttributesContext);
	}

}
