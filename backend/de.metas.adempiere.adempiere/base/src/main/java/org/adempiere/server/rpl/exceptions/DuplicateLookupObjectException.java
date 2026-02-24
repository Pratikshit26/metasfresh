package org.adempiere.server.rpl.exceptions;

 

import lombok.Getter;
import org.adempiere.process.rpl.model.I_EXP_ReplicationTrxLine;
import org.compiere.model.PO;

import java.util.List;

/**
 * Exception meant to contain data for failed lookups.
 * 
 * @author ts
 * @author al
 */
public final class DuplicateLookupObjectException extends ReplicationException
{
	private static final long serialVersionUID = 5099228399627874129L;

	@Getter
	private final List<PO> lookedUpPOs;
	
	private final I_EXP_ReplicationTrxLine trxLineDraft;
	
	@Getter
	private final boolean doLookup;

	/**
	 * Constructs a {@link DuplicateLookupObjectException} with <code>lookedUpPOs=null</code>, <code>trxLineDraft=null</code>, and <code>doLookup=false</code>
	 */
	public DuplicateLookupObjectException(final String adMessage)
	{
		this(adMessage, null, null, false);
	}

	public DuplicateLookupObjectException(final String adMessage, final List<PO> lookedUpPOs, final I_EXP_ReplicationTrxLine trxLineDraft, final boolean doLookup)
	{
		super(adMessage);
		this.lookedUpPOs = lookedUpPOs;
		this.trxLineDraft = trxLineDraft;
		this.doLookup = doLookup;
	}

	public I_EXP_ReplicationTrxLine getTrxLineDraftOrNull()
	{
		return trxLineDraft;
	}

}
