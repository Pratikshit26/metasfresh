package org.adempiere.server.rpl.trx.api;

 


import java.util.Properties;

import org.adempiere.process.rpl.model.I_EXP_ReplicationTrx;
import org.adempiere.process.rpl.model.I_EXP_ReplicationTrxLine;

import de.metas.util.ISingletonService;

public interface IReplicationTrxBL extends ISingletonService
{
	/**
	 * <b>NOTE:</b> Assume selected <code>tableName</code> not ignored.<br>
	 * <br>
	 * Get (if available), or create {@link I_EXP_ReplicationTrx} for <code>replicationTrxName</code>.<br>
	 * Create new (empty) {@link I_EXP_ReplicationTrxLine}, and bind it to the selected {@link I_EXP_ReplicationTrx}.
	 *
	 * @param ctx
	 * @param tableName
	 * @param replicationTrxName
	 * @param trxName
	 * @return the new {@link I_EXP_ReplicationTrxLine}
	 */
	I_EXP_ReplicationTrxLine createAndMatchVoidReplicationTrxLine(Properties ctx, String tableName, String replicationTrxName, String trxName);

	/**
	 * Add table to ignore list. Ignored tables will not be included in transactions.
	 *
	 * @param tableName
	 */
	void addTableToIgnoreList(String tableName);

	/**
	 * @param tableName
	 * @return true if table is ignored, false otherwise
	 */
	boolean isTableIgnored(String tableName);
}
