package de.metas.rest_api.invoicecandidates.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

/*
 * #%L
 * de.metas.business.rest-api
     
 * #L%
 */
@Value
public class JsonCheckInvoiceCandidatesStatusResponse
{
	List<JsonCheckInvoiceCandidatesStatusResponseItem> invoiceCandidates;

	@Builder
	@JsonCreator
	public JsonCheckInvoiceCandidatesStatusResponse(@JsonProperty("invoiceCandidates") @NonNull final List<JsonCheckInvoiceCandidatesStatusResponseItem> invoiceCandidates)
	{
		this.invoiceCandidates = invoiceCandidates;
	}
}
