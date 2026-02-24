package de.metas.cache.interceptor.testservices.impl;

 


import org.adempiere.util.proxy.Cached;

import de.metas.cache.annotation.CacheReloadIfTrue;
import de.metas.cache.interceptor.testservices.ITestServiceWithCacheReload;

public class TestServiceWithCacheReload implements ITestServiceWithCacheReload
{
	public Object cachedValueToReturn = null;

	@Override
	@Cached
	public Object getReload(String id, @CacheReloadIfTrue boolean cacheReload)
	{
		return cachedValueToReturn;
	}

}
