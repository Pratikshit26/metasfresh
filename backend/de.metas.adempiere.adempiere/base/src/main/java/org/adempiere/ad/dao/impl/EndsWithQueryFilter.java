/**
 * 
 */
package org.adempiere.ad.dao.impl;

 

import lombok.NonNull;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * @author cg
 *
 */
public class EndsWithQueryFilter<T> implements IQueryFilter<T>, ISqlQueryFilter
{
	private final String columnName;
	private final String endsWithString;

	public EndsWithQueryFilter(@NonNull final String columnName, @NonNull final String endsWithString)
	{
		this.columnName = columnName;
		this.endsWithString = endsWithString;
	}

	@Override
	public String getSql()
	{
		buildSql();
		return sqlWhereClause;
	}

	@Override
	public List<Object> getSqlParams(Properties ctx)
	{
		buildSql();
		return sqlParams;
	}

	@Override
	public boolean accept(T model)
	{
		final Object value = InterfaceWrapperHelper.getValueOrNull(model, columnName);
		if (value == null)
		{
			return false;
		}
		else  if (value instanceof String)
		{
			return ((String)value).endsWith(endsWithString);

		}
		else
		{
			throw new IllegalArgumentException("Invalid '" + columnName + "' value for " + model);
		}
	}

	private boolean sqlBuilt = false;
	private String sqlWhereClause = null;
	private List<Object> sqlParams = null;

	private void buildSql()
	{
		if (sqlBuilt)
		{
			return;
		}

		final String sqlWhereClause = columnName
				+ " LIKE "
				+ "'%'||? ";
		this.sqlParams = Collections.singletonList(endsWithString);

		this.sqlWhereClause = sqlWhereClause;
		this.sqlBuilt = true;
	}
}
