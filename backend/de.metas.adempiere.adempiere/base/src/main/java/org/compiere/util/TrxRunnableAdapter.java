package org.compiere.util;

 


/**
 * Implement what you need adapter for {@link TrxRunnable} / {@link TrxRunnable2}.
 * 
 * @author tsa
 *
 */
public abstract class TrxRunnableAdapter implements TrxRunnable2
{

	// makes no sense to allow developer to leave this method empty
	@Override
	public abstract void run(String localTrxName) throws Exception;

	/**
	 * At this level is just throwing the exception which will cause the transaction/savepoint to be rolled back and exception propagated to upper level.
	 */
	@Override
	public boolean doCatch(Throwable e) throws Throwable
	{
		throw e;
	}

	@Override
	public void doFinally()
	{
		// nothing
	}

}
