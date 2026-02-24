package org.compiere.apps.search;

 


/**
 * Implementation of this interface are used for applying customized configurations to a given grid tab record.
 * 
 * Mainly used from callouts.
 * 
 * @author tsa
 * 
 */
public interface IGridTabRowBuilder
{
	/**
	 * Apply customizations for given model
	 * 
	 * @param model
	 */
	void apply(final Object model);

	/**
	 * 
	 * @return true if this builder can set all data in order to have a valid new record; false if this will customize existing created records
	 */
	boolean isCreateNewRecord();

	void setSource(Object model);

	/**
	 * 
	 * @return true if this builder is well defined and it has all informations to be able to perform on given model. i.e. you can safely call {@link #apply(Object)}.
	 */
	boolean isValid();
}
