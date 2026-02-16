package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.imp;

import de.metas.attachments.AttachmentEntryCreateRequest;
import de.metas.attachments.AttachmentEntryService;
import de.metas.attachments.AttachmentTags;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ITranslatableString;
import de.metas.invoice.InvoiceId;
import de.metas.invoice_gateway.spi.model.imp.ImportedInvoiceResponse;
import de.metas.security.permissions.Access;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Invoice;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.time.Instant;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/*
 * #%L
 * vertical-healthcare_ch.forum_datenaustausch_ch.invoice_base
     
 * #L%
 */

@Repository
public class InvoiceResponseRepo
{
	private static final AdMessageKey MSG_INVOICE_NOT_FOUND_2P = AdMessageKey.of("de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.imp.InvoiceResponseRepo_Invoice_Not_Found");
	private static final AdMessageKey MSG_INVOICE_NOT_FOUND_BY_ID_1P = AdMessageKey.of("de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.imp.InvoiceResponseRepo_Invoice_Not_Found_By_Id");

	private final AttachmentEntryService attachmentEntryService;

	public InvoiceResponseRepo(@NonNull final AttachmentEntryService attachmentEntryService)
	{
		this.attachmentEntryService = attachmentEntryService;
	}

	/**
	 * Persists the given {@code importedInvoiceResponse}, if a related invoice can be found.
	 * Lookup is done either via {@link ImportedInvoiceResponse#getInvoiceId()} or via {@link ImportedInvoiceResponse#getDocumentNumber()} and {@link ImportedInvoiceResponse#getInvoiceCreated()}.
	 *
	 * @throws InvoiceResponseRepoException if no related invoice is found.
	 */
	public InvoiceId save(@NonNull final ImportedInvoiceResponse importedInvoiceResponse)
	{
		final I_C_Invoice invoiceRecord = retrieveInvoiceRecord(importedInvoiceResponse);

		updateInvoiceRecord(importedInvoiceResponse, invoiceRecord);

		return InvoiceId.ofRepoId(invoiceRecord.getC_Invoice_ID());
	}

	@Nullable
	public InvoiceId retrieveInvoiceRecordByDocumentNoAndCreatedOrNull(@NonNull final ImportedInvoiceResponse importedInvoiceResponse)
	{
		final I_C_Invoice i_c_invoice = retrieveInvoiceRecordByDocumentNoAndCreatedOrNull(importedInvoiceResponse.getDocumentNumber(), importedInvoiceResponse.getInvoiceCreated());
		if (i_c_invoice != null)
		{
			return InvoiceId.ofRepoId(i_c_invoice.getC_Invoice_ID());
		}
		return null;
	}

	private I_C_Invoice retrieveInvoiceRecord(@NonNull final ImportedInvoiceResponse importedInvoiceResponse)
	{
		final I_C_Invoice invoiceRecord;
		if (importedInvoiceResponse.getInvoiceId() == null)
		{
			invoiceRecord = retrieveInvoiceRecordByDocumentNoAndCreated(importedInvoiceResponse.getDocumentNumber(), importedInvoiceResponse.getInvoiceCreated());
		}
		else
		{
			invoiceRecord = retrieveInvoiceRecordById(importedInvoiceResponse.getInvoiceId());
		}
		return invoiceRecord;
	}

	@NonNull
	private I_C_Invoice retrieveInvoiceRecordByDocumentNoAndCreated(@NonNull final String documentNo, @NonNull final Instant created)
	{
		final I_C_Invoice invoiceRecord = retrieveInvoiceRecordByDocumentNoAndCreatedOrNull(documentNo, created);

		if (invoiceRecord != null)
		{
			return invoiceRecord;
		}

		throw new InvoiceResponseRepoException(MSG_INVOICE_NOT_FOUND_2P,
				documentNo,
				created.getEpochSecond())
				.markAsUserValidationError();
	}

	@Nullable
	private I_C_Invoice retrieveInvoiceRecordByDocumentNoAndCreatedOrNull(@NonNull final String documentNo, @NonNull final Instant created)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice.COLUMN_DocumentNo, documentNo)
				.addEqualsFilter(I_C_Invoice.COLUMN_Created, created)
				.create()
				.setRequiredAccess(Access.WRITE)
				.firstOnly(I_C_Invoice.class);
	}

	private I_C_Invoice retrieveInvoiceRecordById(@NonNull final InvoiceId invoiceId)
	{
		final I_C_Invoice invoiceRecord;
		invoiceRecord = load(invoiceId, I_C_Invoice.class);
		if (invoiceRecord == null)
		{
			throw new InvoiceResponseRepoException(MSG_INVOICE_NOT_FOUND_BY_ID_1P, invoiceId.getRepoId())
					.markAsUserValidationError();
		}
		return invoiceRecord;
	}

	private void updateInvoiceRecord(@NonNull final ImportedInvoiceResponse response,
									 @NonNull final I_C_Invoice invoiceRecord)
	{
		invoiceRecord.setIsInDispute(ImportedInvoiceResponse.Status.REJECTED.equals(response.getStatus()));
		saveRecord(invoiceRecord);
	}

	private void attachFileToInvoiceRecord(
			@NonNull final ImportedInvoiceResponse response,
			@NonNull final I_C_Invoice invoiceRecord)
	{
		final AttachmentTags attachmentTags = AttachmentTags.builder()
				.tags(response.getAdditionalTags())
				.build();
		final AttachmentEntryCreateRequest attachmentEntryCreateRequest = AttachmentEntryCreateRequest
				.builderFromByteArray(response.getRequest().getFileName(), response.getRequest().getData())
				.tags(attachmentTags)
				.build();

		attachmentEntryService.createNewAttachment(invoiceRecord, attachmentEntryCreateRequest);
	}

	@SuppressWarnings("WeakerAccess")
	public static final class InvoiceResponseRepoException extends AdempiereException
	{
		private static final long serialVersionUID = -4024895067979792864L;

		public InvoiceResponseRepoException(@NonNull final ITranslatableString message)
		{
			super(message);
		}

		public InvoiceResponseRepoException(@NonNull final AdMessageKey adMessage, final Object... params)
		{
			super(adMessage, params);
		}

	}
}
