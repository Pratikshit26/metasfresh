package de.metas.contracts.commission.commissioninstance.businesslogic;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.commission.Beneficiary;
import de.metas.util.JSONObjectMapper;

/*
 * #%L
 * de.metas.contracts
     
 * #L%
 */

class BeneficiaryTest
{
	@Test
	void serialize_deserialize()
	{
		final JSONObjectMapper<Beneficiary> objectMapper = JSONObjectMapper.forClass(Beneficiary.class);

		final Beneficiary original = Beneficiary.of(BPartnerId.ofRepoId(20));
		final String json = objectMapper.writeValueAsString(original);

		final Beneficiary result = objectMapper.readValue(json);

		assertThat(result).isEqualTo(original);
	}
}