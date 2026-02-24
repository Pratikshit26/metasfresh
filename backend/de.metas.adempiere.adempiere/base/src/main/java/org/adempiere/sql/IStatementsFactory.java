package org.adempiere.sql;

 


import org.compiere.util.CCallableStatement;
import org.compiere.util.CPreparedStatement;
import org.compiere.util.CStatement;
import org.compiere.util.CStatementVO;

/**
 * Factory helper class used to create {@link CStatement}, {@link CPreparedStatement} and {@link CCallableStatement} instances.
 * 
 * @author tsa
 *
 */
public interface IStatementsFactory
{

	/**
	 *
	 * @param resultSetType
	 * @param resultSetConcurrency
	 * @param trxName
	 * @return CStatement proxy
	 */
	public abstract CStatement newCStatement(int resultSetType, int resultSetConcurrency, String trxName);

	/**
	 *
	 * @param resultSetType
	 * @param resultSetConcurrency
	 * @param sql
	 * @param trxName
	 * @return CPreparedStatement proxy
	 */
	public abstract CPreparedStatement newCPreparedStatement(int resultSetType, int resultSetConcurrency, String sql, String trxName);

	/**
	 *
	 * @param resultSetType
	 * @param resultSetConcurrency
	 * @param sql
	 * @param trxName
	 * @return CCallableStatement proxy
	 */
	public abstract CCallableStatement newCCallableStatement(int resultSetType, int resultSetConcurrency, String sql, String trxName);

	/**
	 *
	 * @param info
	 * @return CStatement proxy
	 */
	public abstract CStatement newCStatement(CStatementVO info);

	/**
	 *
	 * @param info
	 * @return CPreparedStatement proxy
	 */
	public abstract CPreparedStatement newCPreparedStatement(CStatementVO info);

	/**
	 *
	 * @param info
	 * @return CCallableStatement proxy
	 */
	public abstract CCallableStatement newCCallableStatement(CStatementVO info);

}
