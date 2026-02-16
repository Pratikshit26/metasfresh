package de.metas.edi.api;

import de.metas.document.archive.DocOutboundLogId;
import de.metas.document.archive.api.IDocOutboundDAO;
import de.metas.edi.model.I_C_Doc_Outbound_Log;
import de.metas.edi.model.I_C_Invoice;
import de.metas.invoice.InvoiceId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.load;

/*
 * #%L
 * de.metas.edi
     
 * #L%
 */

@Service
public class EDIDocOutBoundLogService
{
	private final IDocOutboundDAO docOutboundDAO = Services.get(IDocOutboundDAO.class);

	/**
	 * @param recordReference if this in an {@link I_C_Invoice}, then set the {@code C_Doc_Outbound_Log.EDI_ExportStatus} of all referencing log records to the invoice's current status.
	 * @return changed log record or {@code null}
	 */
	public Optional<I_C_Doc_Outbound_Log> setEdiExportStatusFromInvoiceRecord(@NonNull final TableRecordReference recordReference)
	{
		if (!I_C_Invoice.Table_Name.equals(recordReference.getTableName()))
		{
			return Optional.empty();
		}

		final List<de.metas.document.archive.model.I_C_Doc_Outbound_Log> logs = docOutboundDAO.retrieveLog(recordReference);
		if (logs.isEmpty())
		{
			return Optional.empty();
		}
		else
		{
			final I_C_Doc_Outbound_Log logRecord = create(logs.get(0), I_C_Doc_Outbound_Log.class);
			if (logRecord != null)
			{
				logRecord.setEDI_ExportStatus(getEDIExportStatusFromInvoiceRecord(recordReference));
			}
			return Optional.ofNullable(logRecord);
		}
	}

	@Nullable
	public String getEDIExportStatusFromInvoiceRecord(final @NonNull TableRecordReference recordReference)
	{
		if (!I_C_Invoice.Table_Name.equals(recordReference.getTableName()))
		{
			return null;
		}
		final I_C_Invoice invoiceRecord = recordReference.getModel(I_C_Invoice.class);

		return invoiceRecord.getEDI_ExportStatus();
	}

	public I_C_Doc_Outbound_Log retreiveById(@NonNull final DocOutboundLogId docOutboundLogId)
	{
		return load(docOutboundLogId, I_C_Doc_Outbound_Log.class);
	}

	public I_C_Invoice retreiveById(@NonNull final InvoiceId invoiceId)
	{
		return load(invoiceId, I_C_Invoice.class);
	}

	public void save(@NonNull final de.metas.edi.model.I_C_Doc_Outbound_Log docOutboundLog)
	{
		InterfaceWrapperHelper.save(docOutboundLog);
	}
}
