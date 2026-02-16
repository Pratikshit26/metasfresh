package de.metas.rest_api.v2.invoice;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.common.collect.ImmutableList;
import de.metas.rest_api.utils.MetasfreshId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.math.BigDecimal;

/*
 * #%L
 * de.metas.invoice.rest-api
     
 * #L%
 */

@Value
@Builder
public class SalesInvoicePaymentStatus
{
	@NonNull
	MetasfreshId invoiceId;

	@NonNull
	String invoiceDocumentNumber;

	@NonNull
	BigDecimal openAmt;

	boolean isPaid;

	@Schema(description = "3-letter ISO-code of the open amount's currency, like EUR or CHF")
	@NonNull
	String currency;

	@Schema(description = "2-letter docstatus of this invoice; e.g. CO = Completed, RE = Reversed")
	@NonNull
	String docStatus;

	@Singular
	@JsonInclude(Include.NON_EMPTY)
	ImmutableList<SalesInvoicePayment> payments;
}
