package org.adempiere.ad.table;

import static org.assertj.core.api.Assertions.assertThat;
import java.sql.Timestamp;

import org.adempiere.ad.table.RecordChangeLogEntryLoader.SqlWithParams;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

class SqlWithParamsTest
{
	@Test
	void test()
	{
		final SqlWithParams result = SqlWithParams
				.createEmpty()
				.add(new SqlWithParams("string1", ImmutableList.of(1, 2, "3")))
				.add(new SqlWithParams("string2", ImmutableList.of(4, 5, Timestamp.valueOf("2019-08-22 11:01:23"))))
				.withFinalOrderByClause("ORDER BY whatever");

		assertThat(result.getSql()).isEqualTo("string1\n UNION\nstring2\nORDER BY whatever");
		assertThat(result.getSqlParams()).containsExactly(1,2,"3",4,5,Timestamp.valueOf("2019-08-22 11:01:23"));
	}

}
