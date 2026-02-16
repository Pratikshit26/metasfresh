package de.metas.rest_api.invoicecandidates.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.rest_api.utils.MetasfreshId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.LocalDate;

/*
 * #%L
 * de.metas.business.rest-api
     
 * #L%
 */
@Value
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class JsonInvoiceStatus
{
	MetasfreshId metasfreshId;

	@NonNull
	String documentNo;

	@NonNull
	String docStatus;

	LocalDate dateInvoiced;

	boolean pdfAvailable ;

	@Builder
	@JsonCreator
	public JsonInvoiceStatus(
			@JsonProperty("metasfreshId") final MetasfreshId metasfreshId,
			@JsonProperty("documentNo") @NonNull final String documentNo,
			@JsonProperty("docStatus") @NonNull final String docStatus,
			@JsonProperty("dateInvoiced") final LocalDate dateInvoiced,
			@JsonProperty("pdfAvailable") final boolean pdfAvailable)
	{
		this.metasfreshId = metasfreshId;
		this.documentNo = documentNo;
		this.docStatus = docStatus;
		this.dateInvoiced = dateInvoiced;
		this.pdfAvailable = pdfAvailable;
	}
}
