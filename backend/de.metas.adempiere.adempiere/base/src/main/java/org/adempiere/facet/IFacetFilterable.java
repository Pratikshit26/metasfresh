package org.adempiere.facet;

 


import java.util.Collection;
import java.util.function.Predicate;

import org.adempiere.ad.dao.IQueryBuilder;

/**
 * A data model which is can be filtered by {@link IFacet}s.
 * 
 * @author tsa
 *
 * @param <ModelType>
 */
public interface IFacetFilterable<ModelType>
{
	/** Reset filterable to its initial state */
	void reset();

	/** Filter underlying data by given facets */
	void filter(Collection<IFacet<ModelType>> facets);

	/**
	 * Creates an {@link IQueryBuilder} which will retrieve the underlying records of this filterable.
	 * 
	 * @param onlyFacetCategoriesPredicate optional predicate which specifies which {@link IFacetCategory} filters to be considered when retrieving the data; If null, all current
	 *            {@link IFacetCategory} filters will be applied.
	 * @return filtered data
	 */
	IQueryBuilder<ModelType> createQueryBuilder(Predicate<IFacetCategory> onlyFacetCategoriesPredicate);
}
