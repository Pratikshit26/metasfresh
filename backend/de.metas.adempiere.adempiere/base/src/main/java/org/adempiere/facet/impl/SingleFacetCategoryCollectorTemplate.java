package org.adempiere.facet.impl;

 

import com.google.common.collect.ImmutableList;
import de.metas.util.collections.IncludeExcludeListPredicate;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.facet.FacetCollectorRequestBuilder;
import org.adempiere.facet.IFacet;
import org.adempiere.facet.IFacetCategory;
import org.adempiere.facet.IFacetCollector;
import org.adempiere.facet.IFacetCollectorResult;

import java.util.List;

/**
 * {@link IFacetCollector} template implementation for the case when we are collecting facets only for a facet category.
 * 
 * Compared with other more generic implementations or even with {@link IFacetCollector} interface, this implementation will require the developer,
 * to implement only what is actually needed. For anything else this implementation will take care.
 * 
 * Hence, you need to:
 * <ul>
 * <li>provide the facet category will constructing this object
 * <li>implement {@link #collectFacets(IQueryBuilder)}.
 * </ul>
 * 
 * @author tsa
 *
 * @param <ModelType>
 */
public abstract class SingleFacetCategoryCollectorTemplate<ModelType> extends AbstractFacetCollector<ModelType>
{
	private final IFacetCategory facetCategory;
	private final List<IFacetCategory> allFacetCategories;

	public SingleFacetCategoryCollectorTemplate(final @NonNull IFacetCategory facetCategory)
	{
		this.facetCategory = facetCategory;

		this.allFacetCategories = ImmutableList.of(facetCategory);
	}

	@Override
	public final IFacetCollectorResult<ModelType> collect(final FacetCollectorRequestBuilder<ModelType> request)
	{
		final FacetCollectorResult.Builder<ModelType> result = FacetCollectorResult.<ModelType> builder();

		//
		// If our facet category is not required to be collected, do nothing
		if (!request.acceptFacetCategory(facetCategory))
		{
			return result.build();
		}

		// Add the category, just to make sure we have it, even if there are no facets
		result.addFacetCategory(facetCategory);

		//
		// Gets data source, from where we have to collect the facets.
		// When retriving the source records, we shall exclude the filters of this facet category,
		// because they are against our purpose, which is: collect facets for this category, even if we filtered by some of them.
		final IQueryBuilder<ModelType> queryBuilder = request
				.getSourceToUse(IncludeExcludeListPredicate.ofExcludes(facetCategory));

		//
		// Collect the facets and add them to result
		final List<IFacet<ModelType>> facets = collectFacets(queryBuilder);
		if (facets != null && !facets.isEmpty())
		{
			result.addFacets(facets);
		}

		// Build the result and return it
		return result.build();
	}

	/**
	 * Collect the actual facets.
	 * 
	 * @param queryBuilder data source query filter which already narrows the result to exactly what we have to collect. <br>
	 *            IMPORTANT: it is safe to directly change the given query builder, because it was already copied.
	 * @return collected facets
	 */
	protected abstract List<IFacet<ModelType>> collectFacets(final IQueryBuilder<ModelType> queryBuilder);

	@Override
	public final List<IFacetCategory> getAllFacetCategories()
	{
		return allFacetCategories;
	}

	public final IFacetCategory getFacetCategory()
	{
		return facetCategory;
	}

}
