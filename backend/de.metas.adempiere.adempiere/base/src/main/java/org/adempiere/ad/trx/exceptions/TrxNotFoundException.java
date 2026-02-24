package org.adempiere.ad.trx.exceptions;

 


import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;

/**
 * Exception thrown when {@link ITrx} was not found for a given transaction name.
 * 
 * @author tsa
 *
 */
public class TrxNotFoundException extends TrxException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 662721300736919676L;

	public TrxNotFoundException(final ITrxManager trxManager, final String trxName)
	{
		super(buildMsg(trxManager, trxName));
	}

	public TrxNotFoundException(final String message)
	{
		super(message);
	}

	private static final String buildMsg(final ITrxManager trxManager, final String trxName)
	{
		final StringBuilder sb = new StringBuilder();

		sb.append("No transaction was found for trxName='").append(trxName).append("'.");

		if (trxManager != null)
		{
			sb.append("\n Active transactions: ").append(trxManager.getActiveTransactionsList());
		}

		return sb.toString();
	}

}
