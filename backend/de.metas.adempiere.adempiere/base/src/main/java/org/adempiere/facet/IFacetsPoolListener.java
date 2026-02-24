package org.adempiere.facet;

 


/**
 * {@link IFacetsPool} listeners.
 * 
 * Instead of implementing this interface, consider extending {@link FacetsPoolListenerAdapter}.
 * 
 * @author tsa
 *
 */
public interface IFacetsPoolListener
{
	/**
	 * Called when a new set of facets are set in the pool
	 */
	void onFacetsInit();

	/**
	 * Called when some facet is executed (e.g. become active/inactive, an executable facet is clicked etc).
	 * 
	 * @param facet
	 */
	void onFacetExecute(final IFacet<?> facet);
}
