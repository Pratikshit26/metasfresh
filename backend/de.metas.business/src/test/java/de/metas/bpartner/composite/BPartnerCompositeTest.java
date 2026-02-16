package de.metas.bpartner.composite;

import de.metas.bpartner.BPartnerBankAccountId;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.money.CurrencyId;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */

class BPartnerCompositeTest
{

	@Test
	void extractContact()
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(10);
		final BPartnerContactId bpartnerContactId = BPartnerContactId.ofRepoId(bpartnerId, 10);

		final BPartnerContact contact = BPartnerContact.builder()
				.id(bpartnerContactId)
				.build();

		final BPartnerLocation location = BPartnerLocation.builder()
				.id(BPartnerLocationId.ofRepoId(bpartnerId, 10))
				.build();

		final BPartnerBankAccount bankAccount = BPartnerBankAccount.builder()
				.id(BPartnerBankAccountId.ofRepoId(bpartnerId, 10))
				.iban("IBAN")
				.currencyId(CurrencyId.ofRepoId(123))
				.build();

		final BPartnerComposite bpartnerComposite = BPartnerComposite.builder()
				.bpartner(BPartner.builder().id(bpartnerId).build())
				.contact(contact)
				.location(location)
				.bankAccount(bankAccount)
				.build();

		// invoke the method under test
		final Optional<BPartnerContact> result = bpartnerComposite.extractContact(bpartnerContactId);

		assertThat(result).isPresent();
		assertThat(result.get()).isEqualTo(contact);
	}

	@Test
	void extractBillLocation()
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(10);

		final BPartnerLocation location = BPartnerLocation.builder()
				.id(BPartnerLocationId.ofRepoId(bpartnerId, 10))
				.locationType(BPartnerLocationType.builder().billTo(true).billToDefault(false).build())
				.build();
		final BPartnerLocation location2 = BPartnerLocation.builder()
				.id(BPartnerLocationId.ofRepoId(bpartnerId, 10))
				.locationType(BPartnerLocationType.builder().billTo(true).billToDefault(true).build())
				.build();

		final BPartnerComposite bpartnerComposite = BPartnerComposite.builder()
				.location(location)
				.location(location2)
				.build();

		// invoke the method under test
		final Optional<BPartnerLocation> result = bpartnerComposite.extractBillToLocation();

		assertThat(result).isPresent();
		assertThat(result.get()).isEqualTo(location2);
	}

}
