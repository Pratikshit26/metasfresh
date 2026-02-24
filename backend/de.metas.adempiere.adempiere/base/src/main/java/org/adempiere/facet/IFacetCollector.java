package org.adempiere.facet;

 


import java.util.List;

/**
 * Implementations of this interface are responsible of collecting {@link IFacet}s from different sources.
 * 
 * @author tsa
 *
 * @param <ModelType>
 */
public interface IFacetCollector<ModelType>
{
	/**
	 * Returns a new request builder which will assist you to collect the facets.
	 * 
	 * @return facets collecting request builder
	 */
	FacetCollectorRequestBuilder<ModelType> collect();

	/**
	 * Execute the request and collect the result.
	 * 
	 * NOTE: don't call it directly. It's called only by API.
	 * 
	 * @param request
	 * @return facet collect result
	 */
	IFacetCollectorResult<ModelType> collect(FacetCollectorRequestBuilder<ModelType> request);

	/**
	 * Gets all facet categories of which this collector is aware.
	 * 
	 * @return all categories (using collector's desired order)
	 */
	List<IFacetCategory> getAllFacetCategories();
}
