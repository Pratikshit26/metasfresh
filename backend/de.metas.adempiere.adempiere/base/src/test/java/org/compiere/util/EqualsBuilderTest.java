package org.compiere.util;

 


import java.math.BigDecimal;

import org.adempiere.util.lang.EqualsBuilder;
import org.junit.Assert;
import org.junit.Test;

public class EqualsBuilderTest
{
	/**
	 * Makes sure {@link EqualsBuilder#append(BigDecimal, BigDecimal)} method is used
	 * and {@link BigDecimal}s are compared using {@link BigDecimal#compareTo(BigDecimal)} instead of {@link BigDecimal#equals(Object)}.
	 */
	@Test
	public void test_BigDecimals()
	{
		BigDecimal bd1 = new BigDecimal("10.0");
		BigDecimal bd2 = new BigDecimal("10.00000");
		Assert.assertNotEquals(bd1, bd2);
		Assert.assertTrue(new EqualsBuilder()
				.append(bd1, bd2)
				.isEqual());
	}
}
