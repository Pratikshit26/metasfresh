package org.adempiere.ad.dao;

import javax.annotation.Nullable;
import java.math.BigDecimal;

 


/**
 * Extension of {@link ICompositeQueryUpdater} which is also capable of executing the update (see {@link #execute()}).
 * 
 * @author tsa
 *
 * @param <T>
 */
public interface ICompositeQueryUpdaterExecutor<T> extends ICompositeQueryUpdater<T>
{
	/**
	 * Execute this update
	 * 
	 * @return how many rows were updated
	 */
	int execute();

	/**
	 * Sets if the update shall be directly on underlying database or the records shall be updated one by one using the persistence engine API.
	 */
	ICompositeQueryUpdaterExecutor<T> setExecuteDirectly(final boolean executeDirectly);

	@Override
	ICompositeQueryUpdaterExecutor<T> addQueryUpdater(IQueryUpdater<T> updater);

	@Override
	ICompositeQueryUpdaterExecutor<T> addSetColumnValue(String columnName, @Nullable Object value);

	@Override
	ICompositeQueryUpdaterExecutor<T> addAddValueToColumn(String columnName, BigDecimal valueToAdd);
	
	@Override
	ICompositeQueryUpdaterExecutor<T> addAddValueToColumn(String columnName, BigDecimal valueToAdd, IQueryFilter<T> onlyWhenFilter);
}
