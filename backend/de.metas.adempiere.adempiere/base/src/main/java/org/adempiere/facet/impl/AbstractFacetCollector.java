package org.adempiere.facet.impl;

 


import org.adempiere.facet.FacetCollectorRequestBuilder;
import org.adempiere.facet.IFacetCollector;

/**
 * Abstract {@link IFacetCollector} which implements general business logic and helper methods.
 * 
 * NOTE to developer: don't add specific BL here, keep it as generic as posible.
 * 
 * @author tsa
 *
 * @param <ModelType>
 */
public abstract class AbstractFacetCollector<ModelType> implements IFacetCollector<ModelType>
{
	@Override
	public final FacetCollectorRequestBuilder<ModelType> collect()
	{
		return new FacetCollectorRequestBuilder<ModelType>(this);
	}
}
