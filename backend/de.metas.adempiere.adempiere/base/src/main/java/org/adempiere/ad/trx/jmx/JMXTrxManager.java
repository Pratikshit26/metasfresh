package org.adempiere.ad.trx.jmx;

 


import java.lang.ref.WeakReference;
import java.sql.SQLException;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.jmx.IJMXNameAware;

import de.metas.util.Check;

public class JMXTrxManager implements JMXTrxManagerMBean, IJMXNameAware
{
	private WeakReference<ITrxManager> trxManagerRef;
	private final String jmxName;

	public JMXTrxManager(final ITrxManager trxManager)
	{
		super();
		Check.assumeNotNull(trxManager, "trxManager not null");
		trxManagerRef = new WeakReference<ITrxManager>(trxManager);

		this.jmxName = ITrxManager.class.getName()
				+ ":type=" + trxManager.getClass().getSimpleName();
	}

	private final ITrxManager getTrxManager()
	{
		final ITrxManager trxManager = trxManagerRef.get();
		if (trxManager == null)
		{
			throw new AdempiereException("TrxManager service expired");
		}
		return trxManager;
	}

	@Override
	public final String getJMXName()
	{
		return jmxName;
	}

	@Override
	public void setDebugTrxCloseStacktrace(boolean debugTrxCloseStacktrace)
	{
		getTrxManager().setDebugTrxCloseStacktrace(debugTrxCloseStacktrace);

	}

	@Override
	public boolean isDebugTrxCloseStacktrace()
	{
		return getTrxManager().isDebugTrxCloseStacktrace();
	}

	@Override
	public void setDebugTrxCreateStacktrace(boolean debugTrxCreateStacktrace)
	{
		getTrxManager().setDebugTrxCreateStacktrace(debugTrxCreateStacktrace);
	}

	@Override
	public boolean isDebugTrxCreateStacktrace()
	{
		return getTrxManager().isDebugTrxCreateStacktrace();
	}

	@Override
	public void setDebugClosedTransactions(boolean enabled)
	{
		getTrxManager().setDebugClosedTransactions(enabled);
	}

	@Override
	public boolean isDebugClosedTransactions()
	{
		return getTrxManager().isDebugClosedTransactions();
	}

	@Override
	public String[] getActiveTransactionInfos()
	{
		final List<ITrx> trxs = getTrxManager().getActiveTransactionsList();
		return toStringArray(trxs);
	}

	@Override
	public String[] getDebugClosedTransactionInfos()
	{
		final List<ITrx> trxs = getTrxManager().getDebugClosedTransactions();
		return toStringArray(trxs);
	}

	private final String[] toStringArray(final List<ITrx> trxs)
	{
		if (trxs == null || trxs.isEmpty())
		{
			return new String[] {};
		}

		final String[] arr = new String[trxs.size()];
		for (int i = 0; i < trxs.size(); i++)
		{
			final ITrx trx = trxs.get(0);
			if (trx == null)
			{
				arr[i] = "null";
			}
			else
			{
				arr[i] = trx.toString();
			}
		}

		return arr;
	}

	@Override
	public void rollbackAndCloseActiveTrx(final String trxName)
	{
		final ITrxManager trxManager = getTrxManager();
		if (trxManager.isNull(trxName))
		{
			throw new IllegalArgumentException("Only not null transactions are allowed: " + trxName);
		}

		final ITrx trx = trxManager.getTrx(trxName);
		if (trxManager.isNull(trx))
		{
			// shall not happen because getTrx is already throwning an exception
			throw new IllegalArgumentException("No transaction was found for: " + trxName);
		}

		boolean rollbackOk = false;
		try
		{
			rollbackOk = trx.rollback(true);
		}
		catch (SQLException e)
		{
			throw new RuntimeException("Could not rollback '" + trx + "' because: " + e.getLocalizedMessage(), e);
		}
		
		if (!rollbackOk)
		{
			throw new RuntimeException("Could not rollback '" + trx + "' for unknown reason");
		}
	}
	
	@Override
	public void setDebugConnectionBackendId(boolean debugConnectionBackendId)
	{
		getTrxManager().setDebugConnectionBackendId(debugConnectionBackendId);
	}

	@Override
	public boolean isDebugConnectionBackendId()
	{
		return getTrxManager().isDebugConnectionBackendId();
	}

}
