package de.metas.rest_api.invoicecandidates.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.List;

/*
 * #%L
 * de.metas.business.rest-api
     
 * #L%
 */

@Value
@Builder
public class JsonCreateInvoiceCandidatesResponse
{
	@Schema
	@Singular
	List<JsonInvoiceCandidatesResponseItem> responseItems;
}
