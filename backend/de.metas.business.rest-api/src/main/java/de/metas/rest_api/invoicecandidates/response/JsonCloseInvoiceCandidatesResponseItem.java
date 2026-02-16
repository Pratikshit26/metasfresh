package de.metas.rest_api.invoicecandidates.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.metas.common.rest_api.common.JsonExternalId;
import de.metas.common.rest_api.v1.JsonErrorItem;
import de.metas.rest_api.utils.MetasfreshId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business.rest-api
     
 * #L%
 */
@Value
@Builder
public class JsonCloseInvoiceCandidatesResponseItem
{
	@Schema
	JsonExternalId externalHeaderId;

	@Schema
	JsonExternalId externalLineId;

	@Schema(description = "The metasfresh-ID of the upserted record")
	@NonNull
	MetasfreshId metasfreshId;

	@Schema(type = "java.lang.String")
	CloseInvoiceCandidateStatus status;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Schema(type = "java.lang.String")
	JsonErrorItem error;

	public enum CloseInvoiceCandidateStatus
	{
		Closed("Closed"), Error("Error)");

		@Getter
		private final String code;

		CloseInvoiceCandidateStatus(final String code)
		{
			this.code = code;
		}
	}
}
