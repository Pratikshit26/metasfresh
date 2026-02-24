package org.adempiere.server.rpl.trx.api.impl;

 


import org.adempiere.process.rpl.model.I_EXP_ReplicationTrxLine;
import org.adempiere.server.rpl.api.impl.ImportHelper;
import org.compiere.model.PO;

/**
 * Class to bundle the results of an attempt to lookup a certain PO for a given replication EXP_Format_Line.
 *
 * @see ImportHelper
 */
public class POReplicationTrxLineDraft
{
	private final PO poDraft;
	private final I_EXP_ReplicationTrxLine trxLineDraft;
	private final boolean lookup;

	/**
	 * Creates a POReplicationTrxLineDraft with <code>trxLine=null</code> and <code>doLookup=false</code>
	 *
	 * @param po
	 */
	public POReplicationTrxLineDraft(final PO po)
	{
		poDraft = po;
		trxLineDraft = null;
		lookup = false;
	}

	public POReplicationTrxLineDraft(final PO po, final I_EXP_ReplicationTrxLine trxLine, final boolean doLookup)
	{
		poDraft = po;
		trxLineDraft = trxLine;
		lookup = doLookup;
	}

	public PO getPODraft()
	{
		return poDraft;
	}

	public I_EXP_ReplicationTrxLine getTrxLineDraftOrNull()
	{
		return trxLineDraft;
	}

	public boolean isDoLookup()
	{
		return lookup;
	}
}
