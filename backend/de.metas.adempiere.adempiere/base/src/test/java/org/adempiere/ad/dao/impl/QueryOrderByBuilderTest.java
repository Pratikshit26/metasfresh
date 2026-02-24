package org.adempiere.ad.dao.impl;

 


import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class QueryOrderByBuilderTest
{
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void testWithOneColumn()
	{
		testWithOneColumn("MyColumnName ASC NULLS FIRST", "MyColumnName", Direction.Ascending, Nulls.First);
		testWithOneColumn("MyColumnName ASC NULLS LAST", "MyColumnName", Direction.Ascending, Nulls.Last);
		testWithOneColumn("MyColumnName DESC NULLS FIRST", "MyColumnName", Direction.Descending, Nulls.First);
		testWithOneColumn("MyColumnName DESC NULLS LAST", "MyColumnName", Direction.Descending, Nulls.Last);
	}

	private void testWithOneColumn(final String expectedSql,
			final String columnName, final Direction direction, final Nulls nulls)
	{
		final QueryOrderByBuilder<?> builder = new QueryOrderByBuilder<>();
		builder.addColumn(columnName, direction, nulls);

		final String actualSql = builder.createQueryOrderBy().getSql();

		Assert.assertEquals("Invalid order by SQL", expectedSql, actualSql);
	}

	@Test
	public void testBackwardCompatibility()
	{
		final String actualSql = new QueryOrderByBuilder<Object>()
				.addColumn("MyColumnName")
				.createQueryOrderBy()
				.getSql();

		// NOTE: keeping backward compatibility
		// i.e. postgresql 9.1. specifications:
		// "By default, null values sort as if larger than any non-null value;
		// that is, NULLS FIRST is the default for DESC order, and NULLS LAST otherwise."
		//
		// see http://www.postgresql.org/docs/9.1/static/queries-order.html
		final String expectedSql = "MyColumnName ASC NULLS LAST";
		Assert.assertEquals("Invalid order by SQL", expectedSql, actualSql);
	}

	/**
	 * Test {@link QueryOrderByBuilder#addColumn(String, boolean)}
	 */
	@Test
	public void testBackwardCompatibility_addColumn()
	{
		// NOTE: keeping backward compatibility
		// i.e. postgresql 9.1. specifications:
		// "By default, null values sort as if larger than any non-null value;
		// that is, NULLS FIRST is the default for DESC order, and NULLS LAST otherwise."
		//
		// see http://www.postgresql.org/docs/9.1/static/queries-order.html

		testBackwardCompatibility_addColumn("MyColumnName ASC NULLS LAST", "MyColumnName", true);
		testBackwardCompatibility_addColumn("MyColumnName DESC NULLS FIRST", "MyColumnName", false);
	}

	private void testBackwardCompatibility_addColumn(final String expectedSql,
			String columnName, boolean asc)
	{
		final String actualSql = new QueryOrderByBuilder<Object>()
				.addColumn("MyColumnName", asc)
				.createQueryOrderBy()
				.getSql();

		Assert.assertEquals("Invalid order by SQL", expectedSql, actualSql);
	}

}
