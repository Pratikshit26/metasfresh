package de.metas.location;

import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@Value
@Builder
public class CountrySequences
{
	@NonNull OrgId orgId;
	@Nullable String adLanguage;
	@NonNull AddressDisplaySequence addressDisplaySequence;
	@NonNull AddressDisplaySequence localAddressDisplaySequence;

	public void assertDisplaySequencesValid()
	{
		addressDisplaySequence.assertValid();
		localAddressDisplaySequence.assertValid();
	}

}
