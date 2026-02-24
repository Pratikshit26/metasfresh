package org.adempiere.context;

 


import java.util.UUID;

import org.adempiere.util.jmx.IJMXNameAware;

public class JMXThreadLocalServerContextProvider implements JMXThreadLocalServerContextProviderMBean, IJMXNameAware
{
	private final String jmxName;
	private final TraceContextProviderListener contextProviderListener;

	public JMXThreadLocalServerContextProvider(final ThreadLocalContextProvider contextProvider)
	{
		super();

		this.jmxName = ThreadLocalContextProvider.class.getName() + ":type=Instance-" + UUID.randomUUID();

		this.contextProviderListener = new TraceContextProviderListener();
		contextProvider.setListener(contextProviderListener);
	}

	@Override
	public String getJMXName()
	{
		return jmxName;
	}

	@Override
	public String[] getActiveContextsInfo()
	{
		return contextProviderListener.getActiveContextsInfo();
	}

}
