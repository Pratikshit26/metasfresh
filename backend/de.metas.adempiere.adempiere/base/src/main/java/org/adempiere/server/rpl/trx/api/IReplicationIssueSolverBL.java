package org.adempiere.server.rpl.trx.api;

 


import java.util.Map;

import org.adempiere.process.rpl.model.I_EXP_ReplicationTrx;
import org.adempiere.server.rpl.trx.spi.IReplicationIssueAware;
import org.adempiere.server.rpl.trx.spi.IReplicationIssueSolver;

import de.metas.util.ISingletonService;

/**
 * Business logic that helps solving replication issues. To use it, implement you specific solver and pass it to the {@link #solveReplicationIssues(IReplicationIssueSolver, I_EXP_ReplicationTrx)}
 * method.
 *
 * @author ts
 *
 */
public interface IReplicationIssueSolverBL extends ISingletonService
{
	IReplicationIssueSolverParams createParams(Map<String, Object> params);

	IReplicationTrxLinesProcessorResult solveReplicationIssues(I_EXP_ReplicationTrx rplTrx, Class<? extends IReplicationIssueAware> issueAwareType,
			IReplicationIssueSolver<? extends IReplicationIssueAware> issueSolver, IReplicationIssueSolverParams params);

}
