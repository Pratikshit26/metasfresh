package org.adempiere.util.trxConstraints.api;

 


import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxSavepoint;
import org.compiere.util.Trx;

import de.metas.util.ISingletonService;

/**
 * Service contains methods to be called from {@link Trx} on certain events to check against {@link ITrxConstraints}. Implementers of this service are responsible for the enforcement of the
 * transaction constraints
 * 
 * @see org.compiere.util.DB#getConstraints()
 * @see ITrxConstraints
 */
public interface IOpenTrxBL extends ISingletonService
{
	void onCommit(ITrx trx);

	void onClose(ITrx trx);

	void onNewTrx(ITrx trx);

	void onRollback(ITrx trx);

	void onSetSavepoint(ITrx trx, ITrxSavepoint savepoint);

	void onReleaseSavepoint(ITrx trx, ITrxSavepoint savepoint);

	/**
	 * If Trx with the the given trxName hasn't been closed yet, this method returns the stack trace of the trx creation or last commit/rollback.
	 */
	String getCreationStackTrace(String trxName);

	void onTimeOutChange(ITrx trx);
}
