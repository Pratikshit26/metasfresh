package org.adempiere.sql.impl;

 


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.compiere.util.CStatementVO;

/* package */final class CStatementProxy extends AbstractCStatementProxy<Statement>
{
	public CStatementProxy(final int resultSetType, final int resultSetConcurrency, final String trxName)
	{
		super(new CStatementVO(resultSetType, resultSetConcurrency, trxName));
	}

	public CStatementProxy(final CStatementVO vo)
	{
		super(vo);
	}

	@Override
	protected final Statement createStatement(final Connection conn, final CStatementVO vo) throws SQLException
	{
		final Statement stmt = conn.createStatement(vo.getResultSetType(), vo.getResultSetConcurrency());
		return stmt;
	}
}
