package org.adempiere.ad.dao.impl;

 


import java.util.Comparator;

import org.adempiere.ad.dao.IQueryOrderBy;

public abstract class AbstractQueryOrderBy implements IQueryOrderBy
{

	@Override
	public <T> Comparator<T> getComparator(Class<T> modelClass)
	{
		@SuppressWarnings("unchecked")
		final Comparator<T> cmp = (Comparator<T>)getComparator();
		return cmp;
	}

}
