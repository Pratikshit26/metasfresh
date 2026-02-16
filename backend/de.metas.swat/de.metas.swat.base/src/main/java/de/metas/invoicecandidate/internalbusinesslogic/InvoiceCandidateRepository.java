package de.metas.invoicecandidate.internalbusinesslogic;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import org.springframework.stereotype.Repository;

import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import lombok.NonNull;

/*
 * #%L
 * de.metas.swat.base
     
 * #L%
 */

@Repository
public class InvoiceCandidateRepository
{
	private final InvoiceCandidateRecordService invoiceCandidateRecordService;

	public InvoiceCandidateRepository(@NonNull final InvoiceCandidateRecordService invoiceCandidateRecordService)
	{
		this.invoiceCandidateRecordService = invoiceCandidateRecordService;

	}

	public InvoiceCandidate getById(@NonNull final InvoiceCandidateId invoiceCandidateId)
	{
		final I_C_Invoice_Candidate invoiceCandidateRecord = load(invoiceCandidateId, I_C_Invoice_Candidate.class);
		return invoiceCandidateRecordService.ofRecord(invoiceCandidateRecord);
	}

	public InvoiceCandidateId save(@NonNull final InvoiceCandidate invoiceCandidate)
	{
		final I_C_Invoice_Candidate invoiceCandidateRecord = load(invoiceCandidate.getId(), I_C_Invoice_Candidate.class);

		invoiceCandidateRecordService.updateRecord(invoiceCandidate, invoiceCandidateRecord);

		saveRecord(invoiceCandidateRecord);
		return InvoiceCandidateId.ofRepoId(invoiceCandidateRecord.getC_Invoice_Candidate_ID());
	}



}
