package org.adempiere.ad.wrapper;

 


import org.adempiere.ad.dao.IQueryFilter;

/**
 * 
 * @author tsa
 *
 * @param <T>
 * 
 * @deprecated Please use {@link IQueryFilter}
 */
@Deprecated
public interface IPOJOFilter<T> extends IQueryFilter<T>
{
	@Override
	boolean accept(T pojo);
}
