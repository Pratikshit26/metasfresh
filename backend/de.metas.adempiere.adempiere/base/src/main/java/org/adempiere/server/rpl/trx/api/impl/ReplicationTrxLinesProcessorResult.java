package org.adempiere.server.rpl.trx.api.impl;

 


import org.adempiere.server.rpl.trx.api.IReplicationTrxLinesProcessorResult;
import org.adempiere.server.rpl.trx.spi.IReplicationIssueAware;

/* package */class ReplicationTrxLinesProcessorResult implements IReplicationTrxLinesProcessorResult
{
	private int countAll = 0;

	@Override
	public void addReplicationIssueAware(final IReplicationIssueAware issueAware)
	{
		countAll++;
	}

	@Override
	public int getReplicationIssueAwareCount()
	{
		return countAll;
	}
}
