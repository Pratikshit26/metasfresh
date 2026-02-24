package de.metas.materialtransaction;

 


import java.util.List;

import org.compiere.model.I_M_Transaction;

import de.metas.util.ISingletonService;

public interface IMTransactionDAO extends ISingletonService
{

	List<I_M_Transaction> retrieveReferenced(Object referencedModel);

	I_M_Transaction retrieveReversalTransaction(Object referencedModelReversal, I_M_Transaction originalTrx);

}
