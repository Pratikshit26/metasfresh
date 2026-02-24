package org.adempiere.ad.trx.api.impl;

 


import org.compiere.util.TrxRunnable2;
import org.junit.Ignore;

@Ignore
public class MockedTrxRunnable2 implements TrxRunnable2
{
	// Config
	private boolean throwExceptionOnExecution = false;
	private boolean throwExceptionOnCatch = true;
	private boolean returnValueOnCatch = true;

	// Status
	private boolean executed = false;
	private String lastTrxName = null;

	public MockedTrxRunnable2()
	{
		super();
	}

	public boolean isThrowExceptionOnExecution()
	{
		return throwExceptionOnExecution;
	}

	public void setThrowExceptionOnExecution(boolean throwExceptionOnExecution)
	{
		this.throwExceptionOnExecution = throwExceptionOnExecution;
	}

	public boolean isThrowExceptionOnCatch()
	{
		return throwExceptionOnCatch;
	}

	public void setThrowExceptionOnCatch(boolean throwExceptionOnCatch)
	{
		this.throwExceptionOnCatch = throwExceptionOnCatch;
	}

	public boolean isReturnValueOnCatch()
	{
		return returnValueOnCatch;
	}

	public void setReturnValueOnCatch(boolean returnValueOnCatch)
	{
		this.returnValueOnCatch = returnValueOnCatch;
	}

	@Override
	public void run(String localTrxName) throws MockedTrxRunnableException
	{
		this.lastTrxName = localTrxName;
		this.executed = true;
		if (throwExceptionOnExecution)
		{
			throw new MockedTrxRunnableException("Thrown exception because throwExceptionOnExecution=true");
		}
	}

	@Override
	public boolean doCatch(Throwable e) throws Throwable
	{
		if (throwExceptionOnCatch)
		{
			throw e;
		}

		return returnValueOnCatch;
	}

	@Override
	public void doFinally()
	{
	}

	public boolean isExecuted()
	{
		return executed;
	}

	public String getLastTrxName()
	{
		return lastTrxName;
	}
}
