package de.metas.edi.model.validator;

import de.metas.edi.api.EDIDocOutBoundLogService;
import de.metas.edi.model.I_C_Doc_Outbound_Log;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

/*
 * #%L
 * de.metas.edi
     
 * #L%
 */

@Interceptor(I_C_Doc_Outbound_Log.class)
@Component
public class C_Doc_Outbound_Log
{
	private final EDIDocOutBoundLogService ediDocOutBoundLogService;

	private C_Doc_Outbound_Log(@NonNull final EDIDocOutBoundLogService ediDocOutBoundLogService)
	{
		this.ediDocOutBoundLogService = ediDocOutBoundLogService;
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_NEW)
	public void setEdiExportStatusFromInvoice(@NonNull final I_C_Doc_Outbound_Log docOutboundLogRecord)
	{
		final TableRecordReference recordReference = TableRecordReference.ofReferenced(docOutboundLogRecord);

		final String ediExportStatusFromInvoiceRecord = ediDocOutBoundLogService.getEDIExportStatusFromInvoiceRecord(recordReference);
		if (ediExportStatusFromInvoiceRecord != null)
		{
			docOutboundLogRecord.setEDI_ExportStatus(ediExportStatusFromInvoiceRecord);
		}
	}
}
