package de.metas.cache.interceptor.testservices.impl;

 


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import org.adempiere.util.proxy.Cached;
import org.junit.Ignore;

import de.metas.cache.interceptor.testservices.ITestServiceWithMutableMethodParameter;

@Ignore
public class TestServiceWithMutableMethodParameter implements ITestServiceWithMutableMethodParameter
{

	@Override
	@Cached
	public String methodWithMutableCachedParameter(
			Properties ctx // NOTE: we are not adding @CacheCtx by intention => this is a mutable generic parameter
			)
	{
		return UUID.randomUUID().toString();
	}

	@Override
	@Cached
	public String methodWithDate(Date date)
	{
		return UUID.randomUUID().toString();
	}

	@Override
	@Cached
	public String methodWithTimestamp(Timestamp date)
	{
		return UUID.randomUUID().toString();
	}

	@Override
	@Cached
	public String methodWithBigDecimal(BigDecimal bd)
	{
		return UUID.randomUUID().toString();
	}
}
