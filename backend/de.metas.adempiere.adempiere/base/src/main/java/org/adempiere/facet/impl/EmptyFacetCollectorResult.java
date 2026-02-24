package org.adempiere.facet.impl;

 


import java.util.List;
import java.util.Set;

import org.adempiere.facet.IFacet;
import org.adempiere.facet.IFacetCategory;
import org.adempiere.facet.IFacetCollectorResult;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

/**
 * Empty immutable {@link IFacetCollectorResult}.
 * 
 * @author tsa
 *
 * @param <ModelType>
 */
class EmptyFacetCollectorResult<ModelType> implements IFacetCollectorResult<ModelType>
{
	public static final transient EmptyFacetCollectorResult<Object> instance = new EmptyFacetCollectorResult<>();

	public static final <ModelType> EmptyFacetCollectorResult<ModelType> getInstance()
	{
		@SuppressWarnings("unchecked")
		final EmptyFacetCollectorResult<ModelType> instanceCasted = (EmptyFacetCollectorResult<ModelType>)instance;
		return instanceCasted;
	}

	@Override
	public List<IFacetCategory> getFacetCategories()
	{
		return ImmutableList.of();
	}

	@Override
	public Set<IFacet<ModelType>> getFacets()
	{
		return ImmutableSet.of();
	}

	@Override
	public Set<IFacet<ModelType>> getFacetsByCategory(IFacetCategory facetCategory)
	{
		return ImmutableSet.of();
	}

}
