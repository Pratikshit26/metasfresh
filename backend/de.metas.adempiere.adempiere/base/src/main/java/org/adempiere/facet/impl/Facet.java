package org.adempiere.facet.impl;

 


import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.facet.IFacet;
import org.adempiere.facet.IFacetCategory;
import org.adempiere.util.lang.ObjectUtils;

import de.metas.util.Check;

/**
 * {@link IFacet} implementation.
 * 
 * To create a new instance, use {@link #builder()}.
 * 
 * @author tsa
 *
 * @param <ModelType>
 */
public class Facet<ModelType> implements IFacet<ModelType>
{
	public static final <ModelType> Builder<ModelType> builder()
	{
		return new Builder<>();
	}

	private final String id;
	private final String displayName;
	private final IQueryFilter<ModelType> filter;
	private final IFacetCategory facetCategory;

	private Facet(final Builder<ModelType> builder)
	{
		super();

		this.displayName = builder.displayName;
		Check.assumeNotEmpty(displayName, "displayName not empty");

		this.filter = builder.filter; // null is accepted

		this.facetCategory = builder.facetCategory;
		Check.assumeNotNull(facetCategory, "facetCategory not null");

		//
		// Set the predefined ID or build it based on display name
		if (builder.id == null)
		{
			id = facetCategory.getDisplayName() + "_" + displayName;
		}
		else
		{
			id = builder.id;
		}
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public String getDisplayName()
	{
		return displayName;
	}

	@Override
	public IQueryFilter<ModelType> getFilter()
	{
		return filter;
	}

	@Override
	public IFacetCategory getFacetCategory()
	{
		return facetCategory;
	}

	public static class Builder<ModelType>
	{
		private String id = null;
		private String displayName;
		private IQueryFilter<ModelType> filter;
		private IFacetCategory facetCategory;

		private Builder()
		{
			super();
		}

		public Facet<ModelType> build()
		{
			return new Facet<>(this);
		}

		public Builder<ModelType> setId(final String id)
		{
			this.id = id;
			return this;
		}

		public Builder<ModelType> setDisplayName(final String displayName)
		{
			this.displayName = displayName;
			return this;
		}

		public Builder<ModelType> setFilter(final IQueryFilter<ModelType> filter)
		{
			this.filter = filter;
			return this;
		}

		public Builder<ModelType> setFacetCategory(final IFacetCategory facetCategory)
		{
			this.facetCategory = facetCategory;
			return this;
		}
	}
}
