package de.metas.invoicecandidate.externallyreferenced;

import de.metas.i18n.TranslatableStrings;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.util.lang.ExternalId;
import lombok.Builder;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.business.rest-api-impl
     
 * #L%
 */

@Value
public class InvoiceCandidateLookupKey
{
	InvoiceCandidateId invoiceCandidateId;
	ExternalId externalHeaderId;
	ExternalId externalLineId;

	@Builder(toBuilder = true)
	private InvoiceCandidateLookupKey(
			@Nullable final InvoiceCandidateId invoiceCandidateId,
			@Nullable final ExternalId externalHeaderId,
			@Nullable final ExternalId externalLineId)
	{
		this.invoiceCandidateId = invoiceCandidateId;
		this.externalHeaderId = externalHeaderId;
		this.externalLineId = externalLineId;

		if (invoiceCandidateId == null)
		{
			if (externalHeaderId == null || externalLineId == null)
			{
				throw new AdempiereException(TranslatableStrings.constant("If invoiceCandidateId is unspecified, then both externalHeaderId and externalLineId need to be specified"))
						.appendParametersToMessage()
						.setParameter("invoiceCandidateLookupKey", this);
			}
		}
	}
}
