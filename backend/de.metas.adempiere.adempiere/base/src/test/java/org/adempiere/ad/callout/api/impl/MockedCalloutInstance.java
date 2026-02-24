package org.adempiere.ad.callout.api.impl;

 


import java.util.UUID;

import org.adempiere.ad.callout.api.ICalloutExecutor;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.api.ICalloutInstance;
import org.junit.Ignore;

import de.metas.util.Check;

@Ignore
public class MockedCalloutInstance implements ICalloutInstance
{
	private final String id;

	public MockedCalloutInstance(final String id)
	{
		super();

		Check.assumeNotEmpty(id, "id not empty");
		this.id = id;
	}

	public MockedCalloutInstance()
	{
		this("MockedCalloutInstance-" + UUID.randomUUID());
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public void execute(ICalloutExecutor executor, ICalloutField field)
	{
	}

}
