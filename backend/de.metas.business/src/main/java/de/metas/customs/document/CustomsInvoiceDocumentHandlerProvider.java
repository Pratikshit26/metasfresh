package de.metas.customs.document;

import org.compiere.model.I_C_Customs_Invoice;
import org.springframework.stereotype.Component;

import de.metas.document.engine.DocumentHandler;
import de.metas.document.engine.DocumentHandlerProvider;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */
@Component
public class CustomsInvoiceDocumentHandlerProvider implements DocumentHandlerProvider
{

	@Override
	public String getHandledTableName()
	{
		return I_C_Customs_Invoice.Table_Name;
	}

	@Override
	public DocumentHandler provideForDocument(final Object model_NOTUSED)
	{
		return new CustomsInvoiceDocumentHandler();
	}
}
