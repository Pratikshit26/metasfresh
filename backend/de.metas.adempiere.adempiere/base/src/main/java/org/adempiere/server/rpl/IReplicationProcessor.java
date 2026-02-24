/**
 *
 */
package org.adempiere.server.rpl;

 


import org.compiere.model.I_IMP_Processor;

/**
 * Interface to be passed to implementors of {@link IImportProcessor}. Behind this interface there is a core server thread that awakes in fixed intervals and makes sure that the import processed
 * configured in {@link I_IMP_Processor} is run. Note that <code>IImportProcessor</code> implementors can start their own threads.
 *
 * @author tsa
 *
 */
public interface IReplicationProcessor
{

	public org.compiere.model.I_IMP_Processor getMImportProcessor();

	/**
	 * This method is supposed to be used by an implementor of {@link IImportProcessor} to indicate whether that implementor is still running.
	 *
	 * If an <code>IImportProcessor</code> sets this to <code>false</code>, then the this replication processor's server thread will create and start a new <code>IImportProcessor</code> instance.
	 * Therefore it is important to set this to false also in the case of exceptions.
	 *
	 * @param isProcessRunning
	 */
	public void setProcessRunning(boolean isProcessRunning);

	public boolean isProcessRunning();

	public String getServerInfo();
}
