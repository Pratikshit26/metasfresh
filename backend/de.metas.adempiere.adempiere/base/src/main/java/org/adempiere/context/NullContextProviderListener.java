package org.adempiere.context;

import java.io.Serializable;

 


import java.util.Properties;

@SuppressWarnings("serial")
public final class NullContextProviderListener implements IContextProviderListener, Serializable
{
	public static final transient NullContextProviderListener instance = new NullContextProviderListener();

	private NullContextProviderListener()
	{
		super();
	}

	@Override
	public void onContextCreated(final Properties ctx)
	{
	}

	@Override
	public void onChildContextCreated(final Properties ctx, final Properties childCtx)
	{
	}

	@Override
	public void onContextCheckOut(final Properties ctx)
	{
	}

	@Override
	public void onContextCheckIn(final Properties ctxNew, final Properties ctxOld)
	{
	}
}
