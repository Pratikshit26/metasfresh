package org.adempiere.ad.trx.api.impl;

 


import java.sql.Savepoint;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxSavepoint;

import de.metas.util.Check;

/**
 * JDBC {@link ITrxSavepoint} implementation
 * 
 * @author tsa
 * 
 */
public class JdbcTrxSavepoint implements ITrxSavepoint
{
	private final ITrx trx;
	private final Savepoint jdbcSavepoint;

	public JdbcTrxSavepoint(final ITrx trx, final Savepoint jdbcSavepoint)
	{
		super();

		Check.assumeNotNull(trx, "trx not null");
		this.trx = trx;

		Check.assumeNotNull(jdbcSavepoint, "jdbcSavepoint not null");
		this.jdbcSavepoint = jdbcSavepoint;
	}

	@Override
	public Savepoint getNativeSavepoint()
	{
		return jdbcSavepoint;
	}

	@Override
	public ITrx getTrx()
	{
		return trx;
	}
}
