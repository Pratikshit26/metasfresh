package de.metas.cache.interceptor.testservices.impl;

 


import org.adempiere.util.proxy.Cached;

import de.metas.cache.annotation.CacheReloadIfTrue;

/** Class used to test if {@link CacheReloadIfTrue} annotated parameters are correctly validated */
public class TestServiceWithCacheReload_InvalidMethods
{
	@Cached
	public Object methodWithBooleanFlag(final String id, @CacheReloadIfTrue Boolean flag)
	{
		return id;
	}

	@Cached
	public Object methodWithBooleanPrimitiveFlag(final String id, @CacheReloadIfTrue boolean flag)
	{
		return id;
	}

	@Cached
	public Object invalidMethod1(String id, @CacheReloadIfTrue Object flag)
	{
		return id;
	}

}
