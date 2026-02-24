package org.adempiere.facet;

 


import java.util.List;
import java.util.Set;

/**
 * A modifiable and observable pool of {@link IFacet}s.
 * 
 * This is the main target which all facets BL will store their facets.
 * Implementations of this interface can be UI components which are displaying the facets to user.
 * 
 * @author tsa
 *
 * @param <ModelType>
 */
public interface IFacetsPool<ModelType>
{
	/**
	 * Set facet categories.
	 * Calling this method is not mandatory, but this method will make sure the categories are created in the right order.
	 * 
	 * This method will NOT delete existing categories or facets, it will just add the categories which are missing.
	 *
	 * @param facetCategories
	 */
	void setFacetCategories(List<IFacetCategory> facetCategories);

	/**
	 * Clears all existing facets and sets the given ones.
	 * 
	 * @param facets
	 */
	void setFacets(Set<IFacet<ModelType>> facets);

	void updateFrom(final IFacetCollectorResult<ModelType> result);

	/**
	 * 
	 * @return facets which are currently active/enabled.
	 */
	Set<IFacet<ModelType>> getActiveFacets();

	/**
	 * Add pool listener
	 * 
	 * @param listener
	 */
	void addListener(final IFacetsPoolListener listener);

	/**
	 * Remove pool listener
	 * 
	 * @param listener
	 */
	void removeListener(final IFacetsPoolListener listener);
}
