package org.adempiere.facet.impl;

 


import java.util.Properties;

import org.adempiere.facet.IFacetCategory;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.util.Env;

import de.metas.i18n.IMsgBL;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * Facet category. It is used to group facets and define common features.
 * 
 * To create a new instance, use {@link #builder()}.
 * 
 * @author tsa
 *
 */
public final class FacetCategory implements IFacetCategory
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final String displayName;
	private final boolean collapsed;
	private final boolean eagerRefresh;

	private FacetCategory(final Builder builder)
	{
		super();

		Check.assumeNotEmpty(builder.displayName, "displayName not empty");
		this.displayName = builder.displayName;
		this.collapsed = builder.collapsed;
		this.eagerRefresh = builder.eagerRefresh;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public String getDisplayName()
	{
		return displayName;
	}

	@Override
	public boolean isCollapsed()
	{
		return collapsed;
	}

	@Override
	public boolean isEagerRefresh()
	{
		return eagerRefresh;
	}

	public static class Builder
	{
		private String displayName;
		private boolean collapsed = false;
		private boolean eagerRefresh = false;

		private Builder()
		{
			super();
		}

		public FacetCategory build()
		{
			return new FacetCategory(this);
		}

		public Builder setDisplayName(final String displayName)
		{
			this.displayName = displayName;
			return this;
		}

		public Builder setDisplayNameAndTranslate(final String displayName)
		{
			final IMsgBL msgBL = Services.get(IMsgBL.class);
			final Properties ctx = Env.getCtx();
			this.displayName = msgBL.translate(ctx, displayName);
			return this;
		}

		public Builder setCollapsed(final boolean collapsed)
		{
			this.collapsed = collapsed;
			return this;
		}

		public Builder setEagerRefresh()
		{
			this.eagerRefresh = true;
			return this;
		}

	}
}
