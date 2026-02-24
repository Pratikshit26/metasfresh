package de.metas.cache.interceptor.testservices.impl;

 


import java.util.UUID;

import org.adempiere.util.proxy.Cached;

import de.metas.cache.interceptor.testservices.ITestServiceWithPrivateCachedMethod;

public class TestServiceWithPrivateCachedMethod implements ITestServiceWithPrivateCachedMethod
{
	@Override
	public Object methodCallingPrivateCachedMethod(final int param1)
	{
		return cachedPrivateMethod(param1);
	}

	@Cached
	private Object cachedPrivateMethod(final int param1)
	{
		return UUID.randomUUID().toString();
	}
}
