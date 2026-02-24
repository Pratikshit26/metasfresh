package de.metas.cache.interceptor.testservices.impl;

 


import java.util.UUID;

import org.adempiere.util.proxy.Cached;
import org.junit.Ignore;

import de.metas.cache.annotation.CacheCtx;
import de.metas.cache.interceptor.testservices.ITestServiceWithInvalidMethod;

@Ignore
public class TestServiceWithInvalidMethod implements ITestServiceWithInvalidMethod
{
	@Override
	@Cached
	public String invalidCachCtxParam(
			@CacheCtx String ctx // NOTE: CacheCtx cannot be applied to String
	)
	{
		return UUID.randomUUID().toString();
	}
}
