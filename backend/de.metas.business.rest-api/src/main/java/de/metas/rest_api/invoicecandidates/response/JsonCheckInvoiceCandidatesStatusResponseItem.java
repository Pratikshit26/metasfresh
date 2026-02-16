package de.metas.rest_api.invoicecandidates.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.rest_api.common.JsonExternalId;
import de.metas.common.rest_api.common.JsonWorkPackageStatus;
import de.metas.rest_api.utils.MetasfreshId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/*
 * #%L
 * de.metas.business.rest-api
     
 * #L%
 */
@Value
public class JsonCheckInvoiceCandidatesStatusResponseItem
{
	@Schema
	JsonExternalId externalHeaderId;

	@Schema
	JsonExternalId externalLineId;

	@Schema
	MetasfreshId metasfreshId;

	@Schema
	@Nullable
	BigDecimal qtyEntered;

	@Schema
	@Nullable
	BigDecimal qtyToInvoice;

	@Schema
	@Nullable
	BigDecimal qtyInvoiced;

	@Schema
	@Nullable
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	LocalDate dateInvoiced;

	@Schema
	@Nullable
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	LocalDate dateToInvoice;

	@Schema
	boolean processed;

	@Schema
	@Nullable
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	List<JsonInvoiceStatus> invoices;

	@Schema
	@Nullable
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	List<JsonWorkPackageStatus> workPackages;

	@Builder
	@JsonCreator
	public JsonCheckInvoiceCandidatesStatusResponseItem(
			@JsonProperty("externalHeaderId") final JsonExternalId externalHeaderId,
			@JsonProperty("externalLineId") final JsonExternalId externalLineId,
			@JsonProperty("metasfreshId") final MetasfreshId metasfreshId,
			@JsonProperty("qtyEntered") @Nullable final BigDecimal qtyEntered,
			@JsonProperty("qtyToInvoice") @Nullable final BigDecimal qtyToInvoice,
			@JsonProperty("qtyInvoiced") @Nullable final BigDecimal qtyInvoiced,
			@JsonProperty("dateInvoiced") @Nullable final LocalDate dateInvoiced,
			@JsonProperty("dateToInvoice") @Nullable final LocalDate dateToInvoice,
			@JsonProperty("processed") final boolean processed,
			@JsonProperty("invoices") @Nullable final List<JsonInvoiceStatus> invoices,
			@JsonProperty("workPackages") @Nullable final List<JsonWorkPackageStatus> workPackages)
	{
		this.externalHeaderId = externalHeaderId;
		this.externalLineId = externalLineId;
		this.metasfreshId = metasfreshId;
		this.qtyEntered = qtyEntered;
		this.qtyToInvoice = qtyToInvoice;
		this.qtyInvoiced = qtyInvoiced;
		this.dateInvoiced = dateInvoiced;
		this.dateToInvoice = dateToInvoice;
		this.processed = processed;
		this.invoices = invoices;
		this.workPackages = workPackages;
	}
}
