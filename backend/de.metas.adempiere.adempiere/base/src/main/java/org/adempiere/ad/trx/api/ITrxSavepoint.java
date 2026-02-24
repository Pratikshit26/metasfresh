package org.adempiere.ad.trx.api;

 



/**
 * Transaction savepoint
 * 
 * @author tsa
 * 
 */
public interface ITrxSavepoint
{
	/**
	 * 
	 * @return transaction on which this savepoint was created
	 */
	ITrx getTrx();

	/**
	 * Implementation dependent savepoint instance.
	 * 
	 * @return native savepoint
	 */
	Object getNativeSavepoint();
}
