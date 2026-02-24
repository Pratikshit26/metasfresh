package de.metas.cache.interceptor;

 


import java.util.ArrayList;
import java.util.List;

import org.adempiere.util.lang.ObjectUtils;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

/**
 * Helper class used to collect key parts and other informations from {@link ICachedMethodPartDescriptor}s,
 * and then builds the cache key.
 * 
 * @author tsa
 *
 */
final class CacheKeyBuilder
{
	private final List<Object> keyParts = new ArrayList<>();
	private String trxName;
	private boolean skipCaching;
	private boolean cacheReload = false;

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public final ArrayKey buildKey()
	{
		return Util.mkKey(keyParts.toArray());
	}

	public void add(final Object keyPart)
	{
		keyParts.add(keyPart);
	}

	public void setTrxName(String trxName)
	{
		this.trxName = trxName;
	}

	public String getTrxName()
	{
		return trxName;
	}

	public void setSkipCaching()
	{
		this.skipCaching = true;
	}

	public boolean isSkipCaching()
	{
		return skipCaching;
	}

	/**
	 * Advices the caching engine to refresh the cached value, instead of checking the cache.
	 * 
	 * NOTE: this option will have NO affect if {@link #isSkipCaching()}.
	 */
	public void setCacheReload()
	{
		this.cacheReload = true;
	}

	/** @return true if instead the underlying method shall be invoked and cache shall be refreshed with that value */
	public boolean isCacheReload()
	{
		return cacheReload;
	}
}
