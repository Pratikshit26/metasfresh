package org.adempiere.server.rpl.trx.api;

 


import org.adempiere.server.rpl.trx.spi.IReplicationIssueAware;

/**
 * An instance of this is returned by
 * {@link IReplicationIssueSolverBL#solveReplicationIssues(org.adempiere.process.rpl.model.I_EXP_ReplicationTrx, Class, org.adempiere.server.rpl.trx.spi.IReplicationIssueSolver, IReplicationIssueSolverParams)}
 * .
 *
 * It contains the number of processed {@link IReplicationIssueAware}s.
 *
 */
public interface IReplicationTrxLinesProcessorResult
{

	/**
	 * Add a processed <code>issueAware</code>.
	 * 
	 * @param issueAware
	 */
	void addReplicationIssueAware(IReplicationIssueAware issueAware);

	/**
	 * 
	 * @return
	 */
	int getReplicationIssueAwareCount();
}
