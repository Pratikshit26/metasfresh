package de.metas.rest_api.invoicecandidates.response;

import de.metas.common.rest_api.common.JsonExternalId;
import de.metas.rest_api.utils.MetasfreshId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business.rest-api
     
 * #L%
 */

@Value
@Builder
public class JsonInvoiceCandidatesResponseItem
{
	@Schema(type = "java.lang.String")
	JsonExternalId externalHeaderId;

	@Schema(type = "java.lang.String")
	JsonExternalId externalLineId;

	@Schema(type = "java.lang.Long", description = "The metasfresh-ID of the upserted record")
	@NonNull
	MetasfreshId metasfreshId;
}
