package de.metas.rest_api.v1.invoice;

import com.google.common.collect.ImmutableList;
import lombok.Value;

/*
 * #%L
 * de.metas.invoice.rest-api
     
 * #L%
 */

@Value
public class SalesInvoicePaymentStatusResponse
{
	ImmutableList<SalesInvoicePaymentStatus> response;
}
