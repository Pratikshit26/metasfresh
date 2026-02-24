package org.adempiere.ad.service;

 


import java.util.List;

import org.compiere.model.I_AD_Table;

public interface ITableSequenceChecker
{

	/**
	 * If set to <code>true</code>, then it will verify that the <code>currentNext</code> value is fine.
	 * <p>
	 * <b>IMPORTANT</b> (task 08607): if native sequences are enabled, then it will <b>always</b> do a sequence check (no matter what was set here), because it will reset the native sequence and needs
	 * to make sure to reset it to a value that is not already in use.
	 * 
	 * @param sequenceRangeCheck
	 * @return
	 * @see org.compiere.util.DB#isUseNativeSequences()
	 */
	ITableSequenceChecker setSequenceRangeCheck(boolean sequenceRangeCheck);

	void run();

	ITableSequenceChecker setTables(List<I_AD_Table> tables);

	ITableSequenceChecker setTable(I_AD_Table table);

	ITableSequenceChecker setFailOnFirstError(boolean failOnFirstError);

	ITableSequenceChecker setTrxName(String trxName);

}
