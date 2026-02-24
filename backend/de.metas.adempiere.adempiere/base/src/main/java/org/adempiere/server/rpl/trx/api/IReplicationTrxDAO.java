package org.adempiere.server.rpl.trx.api;

 


import java.util.Iterator;
import java.util.Properties;

import org.adempiere.exceptions.DBException;
import org.adempiere.process.rpl.model.I_EXP_ReplicationTrx;
import org.adempiere.process.rpl.model.I_EXP_ReplicationTrxLine;

import de.metas.util.ISingletonService;

public interface IReplicationTrxDAO extends ISingletonService
{
	/**
	 * Replication default lookup marker
	 */
	String COLUMNNAME_IsReplicationLookupDefault = "IsReplicationLookupDefault";

	/**
	 * @param ctx
	 * @param replicationTrxName
	 * @param trxName
	 *
	 * @return the existing ReplicationTrx for replicationTrxName.
	 *
	 * @throws DBException if than one record is found
	 */
	I_EXP_ReplicationTrx retrieveReplicationTrxByName(Properties ctx, String replicationTrxName, String trxName);

	/**
	 * Retrieve replication transaction lines.
	 *
	 * Lines are ordered by AD_Table_ID/Record_ID.
	 *
	 * @param rplTrx
	 * @param tableName
	 * @param status
	 * @return
	 */
	Iterator<I_EXP_ReplicationTrxLine> retrieveReplicationTrxLines(final I_EXP_ReplicationTrx rplTrx, final String tableName, final String status);
}
