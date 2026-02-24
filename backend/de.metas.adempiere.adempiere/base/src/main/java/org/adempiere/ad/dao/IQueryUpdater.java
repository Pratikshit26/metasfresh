package org.adempiere.ad.dao;

 

@FunctionalInterface
public interface IQueryUpdater<T>
{
	boolean MODEL_UPDATED = true;
	boolean MODEL_SKIPPED = false;

	/**
	 * Update given model.
	 * 
	 * If this method return <code>false</code> (i.e. model was not updated), the model won't be saved.
	 * 
	 * @param model
	 * @return true if model was updated. Or better use {@link #MODEL_UPDATED} and {@link #MODEL_SKIPPED}
	 */
	boolean update(final T model);
}
