package org.adempiere.facet;

 


import java.util.List;
import java.util.Set;

/**
 * Result of an {@link IFacetCollector} execution.
 * 
 * @author tsa
 *
 * @param <ModelType>
 */
public interface IFacetCollectorResult<ModelType>
{
	/**
	 * Gets all facet categories.
	 * 
	 * NOTE:
	 * <ul>
	 * <li>collector can report more (or all categories) and NOT only the categories on which facets were found
	 * <li>we return a list because we want to preverve collector's desired order of sorting them
	 * </ul>
	 * 
	 * @return all facet categories
	 */
	List<IFacetCategory> getFacetCategories();

	/**
	 * @return currently collected facets
	 */
	Set<IFacet<ModelType>> getFacets();

	/**
	 * @return currently collected facets which are of given <code>facetCategory</code>
	 */
	Set<IFacet<ModelType>> getFacetsByCategory(final IFacetCategory facetCategory);
}
