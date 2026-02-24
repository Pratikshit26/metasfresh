package org.adempiere.mm.attributes.countryattribute;

 


/**
 * Implementations are responsible for creating {@link ICountryAware} objects from given models.
 * 
 * @author tsa
 *
 */
public interface ICountryAwareFactory
{
	/**
	 * Creates an {@link ICountryAware} object from given model.
	 * 
	 * NOTE: it's up to implementation to decide if the given model will be wrapped or a new plain object will be created.
	 * 
	 * @param model
	 * @return {@link ICountryAware} or null
	 */
	ICountryAware createCountryAware(final Object model);
}
