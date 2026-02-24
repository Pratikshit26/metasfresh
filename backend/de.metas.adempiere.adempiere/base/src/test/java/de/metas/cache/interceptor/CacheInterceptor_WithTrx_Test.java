package de.metas.cache.interceptor;

 


import java.util.UUID;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.proxy.impl.JavaAssistInterceptor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.cache.interceptor.CacheInterceptor;
import de.metas.cache.interceptor.testservices.ITestServiceWithCachedMethod;
import de.metas.cache.interceptor.testservices.impl.TestServiceWithCachedMethod;
import de.metas.util.Services;

/**
 * Tests {@link CacheInterceptor} when using database transactions.
 * 
 * @author tsa
 *
 */
public class CacheInterceptor_WithTrx_Test
{
	private ITrxManager trxManager;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		this.trxManager = Services.get(ITrxManager.class);
		JavaAssistInterceptor.FAIL_ON_ERROR = true;
	}

	@Test
	public void testCachedMethodWithTrx()
	{
		final TestServiceWithCachedMethod obj = (TestServiceWithCachedMethod)Services.get(ITestServiceWithCachedMethod.class);

		final int param1 = 1;
		final Object value1 = "value1";
		final Object value2 = "value2";

		final ITrx trx1 = trxManager.get("TrxName1", OnTrxMissingPolicy.CreateNew);
		final ITrx trx2 = trxManager.get("TrxName2", OnTrxMissingPolicy.CreateNew);

		{
			obj.cachedValueToReturn = value1;
			Assert.assertSame(value1, obj.cachedMethodWithTrx(param1, trx1.getTrxName()));
		}

		{
			obj.cachedValueToReturn = value2;
			Assert.assertSame(value2, obj.cachedMethodWithTrx(param1, trx2.getTrxName()));
		}

		final String value3 = "value3";
		obj.cachedValueToReturn = value3;

		// Make sure values were cached
		Assert.assertSame(value1, obj.cachedMethodWithTrx(param1, trx1.getTrxName()));
		Assert.assertSame(value2, obj.cachedMethodWithTrx(param1, trx2.getTrxName()));

		// close trx1
		trx1.close();
		// Value is no longer cached because transaction is closed
		// NOTE:
		// * we expect a warning in console
		// * if following assert fails, it means the transaction was not removed from TrxManager's map
		Assert.assertSame(value3, obj.cachedMethodWithTrx(param1, trx1.getTrxName()));
	}

	@Test
	public void testCachedMethodWithTrx_UsingThreadInherited()
	{
		final TestServiceWithCachedMethod obj = (TestServiceWithCachedMethod)Services.get(ITestServiceWithCachedMethod.class);

		final int param1 = 1;
		final Object value1 = "value1";

		final ITrx trx1 = trxManager.get("TrxName1", OnTrxMissingPolicy.CreateNew);
		obj.cachedValueToReturn = value1;
		Assert.assertSame(value1, obj.cachedMethodWithTrx(param1, trx1.getTrxName()));

		obj.cachedValueToReturn = UUID.randomUUID();

		//
		// Test using thread inherited
		trxManager.setThreadInheritedTrxName(trx1.getTrxName());
		Assert.assertSame(value1, obj.cachedMethodWithTrx(param1, ITrx.TRXNAME_ThreadInherited));
	}
	
	@Test
	public void testCachedMethodWithTrx_WhenTrxNotFound()
	{
		final TestServiceWithCachedMethod obj = (TestServiceWithCachedMethod)Services.get(ITestServiceWithCachedMethod.class);

		// NOTE: we expect a warning in console and the method shall be invoked directly (without being cached)
		obj.cachedValueToReturn = "value1";
		Assert.assertSame("value1", obj.cachedMethodWithTrx(1, "MissingTrxName"));
		
		// Make sure it's not cached
		obj.cachedValueToReturn = "value1New";
		Assert.assertSame("value1New", obj.cachedMethodWithTrx(1, "MissingTrxName"));
	}

}
