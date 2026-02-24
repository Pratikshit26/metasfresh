package org.adempiere.ad.trx.api;

 


import org.adempiere.ad.trx.exceptions.TrxNotFoundException;

/**
 * Specifies how we shall handle the case when no {@link ITrx} was found for a given transaction name (trxName).
 * 
 * @author tsa
 *
 */
public enum OnTrxMissingPolicy
{
	/**
	 * Create a new transaction in case transaction does not exist
	 */
	CreateNew,
	/**
	 * Throw {@link TrxNotFoundException} in case the transaction does not exist
	 */
	Fail,
	/**
	 * Return {@link ITrx#TRX_None} in case the transaction does not exist
	 */
	ReturnTrxNone,
}
