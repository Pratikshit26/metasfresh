package de.metas.invoicecandidate.api;

import java.util.List;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * de.metas.swat.base
     
 * #L%
 */

@Value
@Builder
public class InvoiceCandidateMultiQuery
{
	@Singular
	List<InvoiceCandidateQuery> queries;
}
