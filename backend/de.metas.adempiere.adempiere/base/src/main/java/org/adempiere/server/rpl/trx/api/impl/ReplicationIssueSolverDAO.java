package org.adempiere.server.rpl.trx.api.impl;

 


import java.util.Properties;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.process.rpl.model.I_EXP_ReplicationTrxLine;
import org.adempiere.server.rpl.trx.api.IReplicationIssueSolverDAO;
import org.adempiere.server.rpl.trx.spi.IReplicationIssueAware;

import de.metas.util.Check;
import de.metas.util.Services;

public class ReplicationIssueSolverDAO implements IReplicationIssueSolverDAO
{
	@Override
	public IReplicationIssueAware retrieveReplicationIssueAware(final I_EXP_ReplicationTrxLine trxLine)
	{
		Check.assumeNotNull(trxLine, "trxLine not null");

		final Properties ctx = InterfaceWrapperHelper.getCtx(trxLine);
		final String trxName = InterfaceWrapperHelper.getTrxName(trxLine);

		final int adTableId = trxLine.getAD_Table_ID();
		final String tableName = Services.get(IADTableDAO.class).retrieveTableName(adTableId);
		final int recordId = trxLine.getRecord_ID();

		final IReplicationIssueAware issueAware = InterfaceWrapperHelper.create(ctx, tableName, recordId, IReplicationIssueAware.class, trxName);
		return issueAware;
	}
}
