package org.adempiere.server.rpl.trx.spi;

 


import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.server.rpl.trx.api.IReplicationIssueSolverBL;
import org.adempiere.server.rpl.trx.api.IReplicationIssueSolverParams;

/**
 * Implementors of this interface can be passed to
 * {@link IReplicationIssueSolverBL#solveReplicationIssues(org.adempiere.process.rpl.model.I_EXP_ReplicationTrx, Class, IReplicationIssueSolver, IReplicationIssueSolverParams)}.
 * 
 * TODO: add error handling. Suggestion: introduce a ReplicationIssueSolverException that can be thrown by {@link #solveIssues(IReplicationIssueAware, IReplicationIssueSolverParams)} and
 * that is handled by the issue-solver-processor.
 *
 * @param <T> the class of records to be solved. It is assumed that {@link InterfaceWrapperHelper#getTableName(Class)} is able to return something for the class
 * 
 */
public interface IReplicationIssueSolver<T extends IReplicationIssueAware>
{
	/**
	 * Solve the issue. Throw an exception if there is a problem.
	 *
	 * Saving database changes is the responsibility of implementors.
	 *
	 * @param recordWithIssues
	 * @param params
	 */
	void solveIssues(IReplicationIssueAware recordWithIssues, IReplicationIssueSolverParams params);
}
