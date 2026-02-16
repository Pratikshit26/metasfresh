package de.metas.invoice_gateway.spi.model.imp;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;

/*
 * #%L
 * metasfresh-invoice_gateway.spi
     
 * #L%
 */

@Value
@Builder
@ToString(exclude = "data")
public class ImportInvoiceResponseRequest
{
	String fileName;

	String mimeType;

	byte[] data;
}
