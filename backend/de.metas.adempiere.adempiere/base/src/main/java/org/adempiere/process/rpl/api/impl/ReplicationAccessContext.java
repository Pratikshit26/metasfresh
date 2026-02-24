package org.adempiere.process.rpl.api.impl;

 


import org.adempiere.process.rpl.api.IReplicationAccessContext;

public class ReplicationAccessContext implements IReplicationAccessContext
{
	private final int limit;
	private final boolean isApplyAccessFilter;

	public ReplicationAccessContext(final int limit, final boolean isApplyAccessFilter)
	{
		super();

		this.limit = limit;
		this.isApplyAccessFilter = isApplyAccessFilter;
	}

	@Override
	public int getLimit()
	{
		return limit;
	}

	@Override
	public boolean isApplyAccessFilter()
	{
		return isApplyAccessFilter;
	}
}
