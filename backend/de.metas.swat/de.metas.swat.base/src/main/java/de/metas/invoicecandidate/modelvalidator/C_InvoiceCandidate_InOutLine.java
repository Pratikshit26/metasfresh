package de.metas.invoicecandidate.modelvalidator;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.slf4j.MDC.MDCCloseable;
import org.springframework.stereotype.Component;

import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidate;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateRepository;
import de.metas.invoicecandidate.model.I_C_InvoiceCandidate_InOutLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.logging.TableRecordMDC;
import lombok.NonNull;

/*
 * #%L
 * de.metas.swat.base
     
 * #L%
 */

@Interceptor(I_C_InvoiceCandidate_InOutLine.class)
@Component
public class C_InvoiceCandidate_InOutLine
{
	private final InvoiceCandidateRepository invoiceCandidateRepository;

	private C_InvoiceCandidate_InOutLine(@NonNull final InvoiceCandidateRepository invoiceCandidateRepository)
	{
		this.invoiceCandidateRepository = invoiceCandidateRepository;
	}

	@ModelChange(
			timings = ModelValidator.TYPE_AFTER_CHANGE, //
			ifColumnsChanged = I_C_InvoiceCandidate_InOutLine.COLUMNNAME_QtyDeliveredInUOM_Override)
	public void updateInvoiceCandidate(@NonNull final I_C_InvoiceCandidate_InOutLine icIlaRecord)
	{
		final InvoiceCandidateId invoiceCandidateId = InvoiceCandidateId.ofRepoId(icIlaRecord.getC_Invoice_Candidate_ID());
		try (final MDCCloseable icMDC = TableRecordMDC.putTableRecordReference(I_C_Invoice_Candidate.Table_Name, invoiceCandidateId);)
		{
			// load the invoice candidate with all relevant data
			final InvoiceCandidate invoiceCandidate = invoiceCandidateRepository.getById(invoiceCandidateId);

			// store the invoice candidate with its computed results.
			invoiceCandidateRepository.save(invoiceCandidate);
		}
	}
}
