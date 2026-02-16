package de.metas.cache;

import de.metas.cache.model.CacheInvalidateRequest;
import org.adempiere.util.lang.impl.TableRecordReference;

import java.util.Collection;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

/**
 * Provide an implementation to the {@link CCache} to enable the cache to be "selective".
 * With an implementation provided, {@link CacheInvalidateRequest}s that are only about particular records don't have to cause the full cache to be reset.
 *
 * @param CK the type of the caching keys
 */
@FunctionalInterface
public interface CachingKeysMapper<CK>
{
	/**
	 * Called by the cache to provide a possibly empty collection of cache keys for the given table record reference.
	 * Allows {@link CCache#resetForRecordId(TableRecordReference)} to only remove a limited number of cached entries.
	 * <p>
	 * Implementors can assume that
	 * <li>the given {@code recordRef} is never {@code null} and
	 * <li>every key this method returns will be invalidated in the cache.
	 */
	Collection<CK> computeCachingKeys(TableRecordReference recordRef);

	/** 
	 * If this method returns <code>true</code>, then the whole cache needs resetting.
	 * 
	 * IMPORTANT: make sure to return true if the caching-key to reset can't be extracted by {@link #computeCachingKeys(TableRecordReference)}.
	 * This might be the case if the record in question was deleted.
	 */
	default boolean isResetAll(final TableRecordReference recordRef)
	{
		return false;
	}

}
