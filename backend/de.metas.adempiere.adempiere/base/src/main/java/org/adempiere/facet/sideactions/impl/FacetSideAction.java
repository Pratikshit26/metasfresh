package org.adempiere.facet.sideactions.impl;

 


import org.adempiere.facet.IFacet;
import org.adempiere.ui.sideactions.model.ISideAction;
import org.adempiere.ui.sideactions.model.ISideActionExecuteDelegate;
import org.adempiere.ui.sideactions.model.ToggableSideAction;

import de.metas.util.Check;

/**
 * Wraps an {@link IFacet} to a toggable {@link ISideAction}.
 * 
 * An {@link ISideActionExecuteDelegate} is used to delegate the {@link ISideAction#execute()} call.
 * 
 * @author tsa
 *
 * @param <ModelType>
 */
/* package */final class FacetSideAction<ModelType> extends ToggableSideAction
{
	public static final <ModelType> FacetSideAction<ModelType> cast(final ISideAction action)
	{
		Check.assumeNotNull(action, "action not null");
		@SuppressWarnings("unchecked")
		final FacetSideAction<ModelType> facetAction = (FacetSideAction<ModelType>)action;
		return facetAction;
	}

	private final IFacet<ModelType> facet;
	private final ISideActionExecuteDelegate executeDelegate;

	public FacetSideAction(final IFacet<ModelType> facet, final ISideActionExecuteDelegate executeDelegate)
	{
		super(facet.getId());

		Check.assumeNotNull(facet, "facet not null");
		this.facet = facet;

		Check.assumeNotNull(executeDelegate, "executeDelegate not null");
		this.executeDelegate = executeDelegate;
	}

	/** @return underlying facet; never returns null */
	public final IFacet<ModelType> getFacet()
	{
		return facet;
	}

	/** @return underlying facet's ID */
	public final String getFacetId()
	{
		return facet.getId();
	}

	@Override
	public String getDisplayName()
	{
		return facet.getDisplayName();
	}

	@Override
	public void execute()
	{
		executeDelegate.execute(this);
	}
}
