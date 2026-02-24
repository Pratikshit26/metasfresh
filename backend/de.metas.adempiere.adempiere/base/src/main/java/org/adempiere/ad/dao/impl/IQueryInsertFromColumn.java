package org.adempiere.ad.dao.impl;

 

import java.util.List;

import org.adempiere.ad.dao.IQueryInsertExecutor;

import javax.annotation.Nullable;

/**
 * {@link IQueryInsertExecutor}'s source column.
 * 
 * @author tsa
 *
 */
interface IQueryInsertFromColumn
{
	/**
	 * @param sqlParams output SQL parameters list or NULL if the parameters shall be embedded in the generated SQL code
	 * @return SQL code to be used when the INSERT sql is built
	 */
	String getSql(@Nullable List<Object> sqlParams);

	/**
	 * Database decoupled/nonSQL implementation
	 * 
	 * @return true if <code>toModel</code> was updated
	 */
	boolean update(Object toModel, String toColumnName, Object fromModel);

}
