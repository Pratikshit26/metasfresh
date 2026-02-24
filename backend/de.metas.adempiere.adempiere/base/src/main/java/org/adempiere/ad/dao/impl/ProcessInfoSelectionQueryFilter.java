package org.adempiere.ad.dao.impl;

 


import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.compiere.Adempiere;

import de.metas.process.ProcessInfo;
import de.metas.util.Check;

/**
 * Filters user selection provided by {@link ProcessInfo}.
 * 
 * @author tsa
 * 
 * @param <T>
 */
public class ProcessInfoSelectionQueryFilter<T> implements IQueryFilter<T>, ISqlQueryFilter
{
	private final ProcessInfo processInfo;

	public ProcessInfoSelectionQueryFilter(final ProcessInfo pi)
	{
		Check.assumeNotNull(pi, "process info not null");
		this.processInfo = pi;
	}

	@Override
	public String getSql()
	{
		return processInfo.getWhereClause();
	}

	@Override
	public List<Object> getSqlParams(Properties ctx)
	{
		return Collections.emptyList();
	}

	@Override
	public boolean accept(T model)
	{
		if (Adempiere.isUnitTestMode())
		{
			// NOTE: in Unit Test mode we accept everything... else we would fail
			return true;
		}

		throw new UnsupportedOperationException();
	}
}
