package de.metas.invoice_gateway.spi.model.imp;

import de.metas.invoice.InvoiceId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.List;
import java.util.Map;

/*
 * #%L
 * metasfresh-invoice_gateway.spi
     
 * #L%
 */

/**
 * Contains the response from a 3rd party that reacted to an invoice with we send from metasfresh.
 */
@Value
@Builder(toBuilder = true)
public class ImportedInvoiceResponse
{
	public enum Status
	{
		ACCEPTET, PENDING, REJECTED
	}

	@Nullable
	InvoiceId invoiceId;

	@NonNull
	String documentNumber; 		// invoiceNumber

	@NonNull
	Instant invoiceCreated;

	@NonNull
	Instant invoiceResponse;

	Status status;

	ImportInvoiceResponseRequest request;

	@Singular
	Map<String, String> additionalTags;

	String client;

	String invoiceRecipient;

	List<RejectedError> reason;

	String explanation;

	String responsiblePerson;

	String phone;

	String email;

	String billerEan;

	int billerOrg;

	@Value
	public static class RejectedError
	{
		@NonNull
		String code;

		@NonNull
		String text;

		@Override public String toString()
		{
			return code + ": " + text + ";";
		}
	}
}
