package org.adempiere.ad.dao;

 


import java.util.List;
import java.util.Properties;

public interface ISqlQueryUpdater<T> extends IQueryUpdater<T>
{
	String getSql(final Properties ctx, final List<Object> params);
}
