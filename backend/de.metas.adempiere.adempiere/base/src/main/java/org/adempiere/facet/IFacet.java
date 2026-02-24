package org.adempiere.facet;

 


import org.adempiere.ad.dao.IQueryFilter;

/**
 * The Facet.
 * 
 * A facet is a caracteristic of a model which can be categorized (see {@link IFacetCategory}) and which can be used to filter existing documents.
 * 
 * @author tsa
 *
 * @param <ModelType>
 */
public interface IFacet<ModelType>
{
	/** @return facet unique identifier */
	String getId();

	/** @return user friendly name */
	String getDisplayName();

	/** @return query filter implementation of this facet */
	IQueryFilter<ModelType> getFilter();

	/** @return facet's category; never returns null */
	IFacetCategory getFacetCategory();
}
