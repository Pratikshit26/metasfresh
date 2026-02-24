package de.metas.cache.interceptor;

 


import java.lang.reflect.Method;

import org.compiere.util.Util.ArrayKey;
import org.junit.Assert;
import org.junit.Test;

import de.metas.cache.annotation.CacheReloadIfTrue;
import de.metas.cache.interceptor.CacheIntrospectionException;
import de.metas.cache.interceptor.CachedMethodDescriptor;
import de.metas.cache.interceptor.testservices.impl.TestServiceWithCacheReload_InvalidMethods;

/**
 * Tests if Cached methods which are using {@link CacheReloadIfTrue} are correctly introspected.
 * 
 * @author tsa
 *
 */
public class CacheReloadIfTrueInstrospectionTest
{
	@Test
	public void test_introspection_methodWithBooleanFlag() throws Exception
	{
		final TestServiceWithCacheReload_InvalidMethods testObj = new TestServiceWithCacheReload_InvalidMethods();

		final Method method = testObj.getClass().getMethod("methodWithBooleanFlag", String.class, Boolean.class);
		final CachedMethodDescriptor methodDescriptor = new CachedMethodDescriptor(method);

		// Make sure the "cache reload" flag is not part of the caching key
		final ArrayKey key1 = methodDescriptor.createKeyBuilder(testObj, new Object[] { "id1", false }).buildKey();
		final ArrayKey key2 = methodDescriptor.createKeyBuilder(testObj, new Object[] { "id1", true }).buildKey();
		Assert.assertEquals(key1, key2);
	}

	@Test
	public void test_introspection_methodWithBooleanPrimitiveFlag() throws Exception
	{
		final TestServiceWithCacheReload_InvalidMethods testObj = new TestServiceWithCacheReload_InvalidMethods();

		final Method method = testObj.getClass().getMethod("methodWithBooleanPrimitiveFlag", String.class, boolean.class);
		final CachedMethodDescriptor methodDescriptor = new CachedMethodDescriptor(method);

		// Make sure the "cache reload" flag is not part of the caching key
		final ArrayKey key1 = methodDescriptor.createKeyBuilder(testObj, new Object[] { "id1", false }).buildKey();
		final ArrayKey key2 = methodDescriptor.createKeyBuilder(testObj, new Object[] { "id1", true }).buildKey();
		Assert.assertEquals(key1, key2);
	}

	@Test(expected = CacheIntrospectionException.class)
	public void test_introspection_invalidMethod1() throws Exception
	{
		final TestServiceWithCacheReload_InvalidMethods testObj = new TestServiceWithCacheReload_InvalidMethods();

		final Method method = testObj.getClass().getMethod("invalidMethod1", String.class, Object.class);
		new CachedMethodDescriptor(method); // shall throw exception
	}

}
