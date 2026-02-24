package org.adempiere.context;

 


import java.util.Properties;

import org.adempiere.util.jmx.JMXRegistry;
import org.adempiere.util.jmx.JMXRegistry.OnJMXAlreadyExistsPolicy;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.util.Env;

/**
 * Provides a thread local context.<br>
 * Child threads get a fresh context, with the parent thread's context for default values.<br>
 * Therefore the child thread has all the parent's properties and can alter them for its own use (including an invocation of {@link Env#setCtx(Properties)}), but the alterations won't affect the
 * parent's context. Note that the child thread will even have read-access to context values that were set in the parent after it was spawned.<br>
 * The goal of this implementation is to avoid context pollution.
 * 
 * @author ts
 * @task http://dewiki908/mediawiki/index.php/08859_Fix_role_and_context_problem_around_Scheduler_%28102015832679%29
 */
public class ThreadLocalContextProvider implements ContextProvider
{
	private final ThreadLocalServerContext context = new ThreadLocalServerContext();
	
	public ThreadLocalContextProvider()
	{
		super();
		final JMXThreadLocalServerContextProvider jmxBean = new JMXThreadLocalServerContextProvider(this);
		JMXRegistry.get().registerJMX(jmxBean, OnJMXAlreadyExistsPolicy.Replace);
	}

	@Override
	public Properties getContext()
	{
		return context;
	}

	/**
	 * Invoke this method early on "entry"-threads like the main thread in case of the desktop client.<br>
	 * This is to make sure that the current thread gets its own thread-local context before any child threads are created.<br>
	 * Without calling this method the child thread would get its own empty context, which is not what we want.
	 * 
	 * @task 08859
	 */
	@Override
	public void init()
	{
		context.getDelegate();
	}

	@Override
	public IAutoCloseable switchContext(final Properties ctx)
	{
		return context.switchContext(ctx);
	}

	@Override
	public void reset()
	{
		context.dispose();
	}
	
	void setListener(final IContextProviderListener listener)
	{
		context.setListener(listener);
	}
}
