package de.metas.dataentry;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import de.metas.util.JSONObjectMapper;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

public class DataEntryFieldIdTest
{

	@Test
	public void serializeToJSON()
	{
		final JSONObjectMapper<DataEntryFieldId> objectMapper = JSONObjectMapper.forClass(DataEntryFieldId.class);
		final DataEntryFieldId dataEntryFieldId = DataEntryFieldId.ofRepoId(30);

		final String result = objectMapper.writeValueAsString(dataEntryFieldId);
		assertThat(result).isEqualTo("30");
	}

	@Test
	public void deserializeFromJSON()
	{
		final JSONObjectMapper<DataEntryFieldId> objectMapper = JSONObjectMapper.forClass(DataEntryFieldId.class);

		final DataEntryFieldId result = objectMapper.readValue("30");
		assertThat(result).isEqualTo(DataEntryFieldId.ofRepoId(30));
	}

}
