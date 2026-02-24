package org.adempiere.context;

 


import java.util.Properties;

interface IContextProviderListener
{
	void onContextCreated(final Properties ctx);

	void onChildContextCreated(final Properties ctx, final Properties childCtx);

	void onContextCheckOut(Properties ctx);

	void onContextCheckIn(Properties ctxNew, Properties ctxOld);
}
