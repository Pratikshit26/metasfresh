package org.adempiere.server.rpl.trx.api.impl;

 


import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorContext;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutor;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutorService;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.process.rpl.model.I_EXP_ReplicationTrx;
import org.adempiere.process.rpl.model.I_EXP_ReplicationTrxLine;
import org.adempiere.process.rpl.model.X_EXP_ReplicationTrxLine;
import org.adempiere.server.rpl.trx.api.IReplicationIssueSolverBL;
import org.adempiere.server.rpl.trx.api.IReplicationIssueSolverParams;
import org.adempiere.server.rpl.trx.api.IReplicationTrxDAO;
import org.adempiere.server.rpl.trx.api.IReplicationTrxLinesProcessorResult;
import org.adempiere.server.rpl.trx.spi.IReplicationIssueAware;
import org.adempiere.server.rpl.trx.spi.IReplicationIssueSolver;
import org.adempiere.util.api.Params;

import de.metas.util.Services;

public class ReplicationIssueSolverBL implements IReplicationIssueSolverBL
{
	@Override
	public IReplicationIssueSolverParams createParams(final Map<String, Object> params)
	{
		return new ReplicationIssueSolverParams(Params.ofMap(params));
	}

	@Override
	public IReplicationTrxLinesProcessorResult solveReplicationIssues(
			final I_EXP_ReplicationTrx rplTrx,
			final Class<? extends IReplicationIssueAware> issueAwareType,
			final IReplicationIssueSolver<? extends IReplicationIssueAware> issueSolver,
			final IReplicationIssueSolverParams params
			)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(rplTrx);
		final String tableName = InterfaceWrapperHelper.getTableName(issueAwareType);

		final Iterator<I_EXP_ReplicationTrxLine> trxLines = Services.get(IReplicationTrxDAO.class)
				.retrieveReplicationTrxLines(rplTrx, tableName, X_EXP_ReplicationTrxLine.REPLICATIONTRXSTATUS_NichtVollstaendigImportiert);

		final ITrxItemProcessorExecutorService executorService = Services.get(ITrxItemProcessorExecutorService.class);
		final ITrxItemProcessorContext processorCtx = executorService.createProcessorContext(ctx, ITrx.TRX_None);

		final ReplicationTrxLinesProcessor processor = new ReplicationTrxLinesProcessor();
		processor.setParams(params);
		processor.setReplicationIssueSolver(issueSolver);

		final ITrxItemProcessorExecutor<I_EXP_ReplicationTrxLine, IReplicationTrxLinesProcessorResult> executor = executorService.createExecutor(processorCtx, processor);
		final IReplicationTrxLinesProcessorResult result = executor.execute(trxLines);

		return result;
	}
}
