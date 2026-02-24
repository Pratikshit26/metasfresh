package org.adempiere.ad.dao;

 


import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.DBException;
import org.compiere.model.IQuery;

import de.metas.util.ISingletonService;

public interface IQueryBuilderDAO extends ISingletonService
{
	<T> IQuery<T> create(IQueryBuilder<T> builder);

	/**
	 * Gets SQL WHERE clause of given query filter.
	 * 
	 * @param ctx
	 * @param filter query filter
	 * @param sqlParamsOut sql parameters (out); this parameter can be <code>null</code>, but in case the filters are providing some parameters then an exception will be thrown.
	 * @return sql where clause
	 * @throws DBException in case given filter has nonSQL parts
	 */
	<T> String getSql(Properties ctx, ICompositeQueryFilter<T> filter, List<Object> sqlParamsOut);
}
