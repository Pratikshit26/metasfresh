package org.adempiere.ad.trx.jmx;

 



public interface JMXTrxManagerMBean
{
	void setDebugTrxCloseStacktrace(boolean debugTrxCloseStacktrace);

	boolean isDebugTrxCloseStacktrace();

	void setDebugTrxCreateStacktrace(boolean debugTrxCreateStacktrace);

	boolean isDebugTrxCreateStacktrace();

	void setDebugClosedTransactions(boolean enabled);

	boolean isDebugClosedTransactions();

	String[] getActiveTransactionInfos();

	String[] getDebugClosedTransactionInfos();

	void rollbackAndCloseActiveTrx(String trxName);

	void setDebugConnectionBackendId(boolean debugConnectionBackendId);

	boolean isDebugConnectionBackendId();

}
