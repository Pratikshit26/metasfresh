package de.metas.invoice_gateway.spi;

import de.metas.invoice_gateway.spi.model.imp.ImportInvoiceResponseRequest;
import de.metas.invoice_gateway.spi.model.imp.ImportedInvoiceResponse;

/*
 * #%L
 * metasfresh-invoice_gateway.spi
     
 * #L%
 */

/**
 * Note that currently the import client is not called from outside its respective implementation module.
 * Therefore we don't need to have a factory to create different implementations from e.g. de.metas.business
 */
public interface InvoiceImportClient
{
	public ImportedInvoiceResponse importInvoiceResponse(ImportInvoiceResponseRequest request);
}
