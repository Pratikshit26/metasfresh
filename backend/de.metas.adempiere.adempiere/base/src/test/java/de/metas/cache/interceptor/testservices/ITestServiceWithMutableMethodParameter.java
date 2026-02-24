package de.metas.cache.interceptor.testservices;

 


import java.math.BigDecimal;
import java.util.Properties;

import de.metas.util.ISingletonService;

public interface ITestServiceWithMutableMethodParameter extends ISingletonService
{
	public String methodWithMutableCachedParameter(final Properties ctx);

	public String methodWithDate(final java.util.Date date);

	public String methodWithTimestamp(final java.sql.Timestamp date);
	
	public String methodWithBigDecimal(final BigDecimal bd);
}
