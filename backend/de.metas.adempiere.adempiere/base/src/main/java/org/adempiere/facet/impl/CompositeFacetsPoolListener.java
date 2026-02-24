package org.adempiere.facet.impl;

 


import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.concurrent.ThreadSafe;

import org.adempiere.facet.IFacet;
import org.adempiere.facet.IFacetsPoolListener;

import de.metas.util.Check;

@ThreadSafe
public class CompositeFacetsPoolListener implements IFacetsPoolListener
{
	private final CopyOnWriteArrayList<IFacetsPoolListener> listeners = new CopyOnWriteArrayList<>();

	public void addListener(final IFacetsPoolListener listener)
	{
		Check.assumeNotNull(listener, "listener not null");
		listeners.addIfAbsent(listener);
	}

	public void removeListener(final IFacetsPoolListener listener)
	{
		listeners.remove(listener);
	}
	
	@Override
	public void onFacetsInit()
	{
		for (final IFacetsPoolListener listener : listeners)
		{
			listener.onFacetsInit();
		}
	}


	@Override
	public void onFacetExecute(final IFacet<?> facet)
	{
		for (final IFacetsPoolListener listener : listeners)
		{
			listener.onFacetExecute(facet);
		}
	}
}
