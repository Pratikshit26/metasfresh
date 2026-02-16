package de.metas.bpartner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.user.UserId;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

public class BPartnerContactIdTest
{
	@Test
	public void test_standardCase()
	{
		final BPartnerContactId contactId = BPartnerContactId.ofRepoId(1, 2);
		assertThat(contactId.getBpartnerId()).isEqualTo(BPartnerId.ofRepoId(1));
		assertThat(contactId.getUserId()).isEqualTo(UserId.ofRepoId(2));
	}

	@Test
	public void systemUserIsNotAValidContact()
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(1);

		assertThat(BPartnerContactId.ofRepoIdOrNull(bpartnerId, 123)).isNotNull();
		assertThat(BPartnerContactId.ofRepoIdOrNull(bpartnerId, UserId.SYSTEM.getRepoId())).isNull();
	}

	@Test
	public void test_toJson_ofJsonString()
	{
		final BPartnerContactId bpContactId = BPartnerContactId.ofRepoId(123, 456);
		assertThat(BPartnerContactId.ofJsonString(bpContactId.toJson())).isEqualTo(bpContactId);
	}

	@Test
	public void testSerializeDeserialize() throws JsonProcessingException
	{
		final BPartnerContactId bpContactId = BPartnerContactId.ofRepoId(123, 456);

		final ObjectMapper jsonObjectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
		final String json = jsonObjectMapper.writeValueAsString(bpContactId);
		final BPartnerContactId bpContactIdDeserialized = jsonObjectMapper.readValue(json, BPartnerContactId.class);
		assertThat(bpContactIdDeserialized).isEqualTo(bpContactId);
	}

}
