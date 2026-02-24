package org.adempiere.facet.impl;

 


import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.util.Check;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.facet.FacetCollectorRequestBuilder;
import org.adempiere.facet.IFacetCategory;
import org.adempiere.facet.IFacetCollector;
import org.adempiere.facet.IFacetCollectorResult;

import com.google.common.collect.ImmutableList;

public class CompositeFacetCollector<ModelType> extends AbstractFacetCollector<ModelType>
{
	private static final transient Logger logger = LogManager.getLogger(CompositeFacetCollector.class);

	private final Set<IFacetCollector<ModelType>> facetCollectors = new LinkedHashSet<>();

	public void addFacetCollector(final IFacetCollector<ModelType> facetCollector)
	{
		Check.assumeNotNull(facetCollector, "facetCollector not null");
		facetCollectors.add(facetCollector);
	}

	/** @return true if this composite has at least one collector */
	public boolean hasCollectors()
	{
		return !facetCollectors.isEmpty();
	}

	@Override
	public IFacetCollectorResult<ModelType> collect(final FacetCollectorRequestBuilder<ModelType> request)
	{
		final FacetCollectorResult.Builder<ModelType> aggregatedResult = FacetCollectorResult.builder();

		for (final IFacetCollector<ModelType> facetCollector : facetCollectors)
		{
			try
			{
				final IFacetCollectorResult<ModelType> result = facetCollector.collect(request);
				aggregatedResult.addFacetCollectorResult(result);
			}
			catch (Exception e)
			{
				final AdempiereException ex = new AdempiereException("Failed to collect facets from collector"
						+ "\n Collector: " + facetCollector
						+ "\n Request: " + request
						, e);
				logger.warn("Skip collector because it failed", ex);
			}
		}
		return aggregatedResult.build();
	}

	@Override
	public List<IFacetCategory> getAllFacetCategories()
	{
		// NOTE: teoretically we could cache this list, but i am thinking to Composite in Composite case, on which, caching approach will fail.

		final ImmutableList.Builder<IFacetCategory> aggregatedFacetCategories = ImmutableList.builder();
		for (final IFacetCollector<ModelType> facetCollector : facetCollectors)
		{
			final List<IFacetCategory> facetCategories = facetCollector.getAllFacetCategories();
			facetCategories.addAll(facetCategories);
		}
		return aggregatedFacetCategories.build();
	}
}
