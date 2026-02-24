package org.adempiere.server.rpl.trx.api;

 


import org.adempiere.process.rpl.model.I_EXP_ReplicationTrxLine;
import org.adempiere.server.rpl.trx.spi.IReplicationIssueAware;

import de.metas.util.ISingletonService;

public interface IReplicationIssueSolverDAO extends ISingletonService
{

	IReplicationIssueAware retrieveReplicationIssueAware(I_EXP_ReplicationTrxLine trxLine);

}
