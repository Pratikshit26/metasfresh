package de.metas.cache;

import org.adempiere.util.lang.impl.TableRecordReference;

import java.util.Collection;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

/**
 * Implementations should be thread-safe; so far, no implementation had to have any state, so I hope that won't be a problem.
 */
public interface CacheIndexDataAdapter<DataItemId, CacheKey, DataItem>
{
	DataItemId extractDataItemId(DataItem dataItem);

	Collection<CacheKey> extractCacheKeys(DataItem dataItem);

	/**
	 * @return all data records that make up the given dataIdem.
	 * Needed in order to know on which record-changes we would need to invalidate the given dataItem within the cache.
	 */
	Collection<TableRecordReference> extractRecordRefs(DataItem dataItem);

	default boolean isResetAll(final TableRecordReference recordRef)
	{
		return false;
	}
}
