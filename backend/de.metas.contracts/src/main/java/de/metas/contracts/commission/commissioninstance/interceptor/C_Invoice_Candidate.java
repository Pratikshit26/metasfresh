package de.metas.contracts.commission.commissioninstance.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.slf4j.MDC.MDCCloseable;
import org.springframework.stereotype.Component;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.logging.TableRecordMDC;
import lombok.NonNull;

/*
 * #%L
 * de.metas.contracts
     
 * #L%
 */

@Interceptor(I_C_Invoice_Candidate.class)
@Component
public class C_Invoice_Candidate
{
	private final C_Invoice_CandidateFacadeService invoiceCandidateFacadeService;

	public C_Invoice_Candidate(@NonNull final C_Invoice_CandidateFacadeService invoiceCandidateFacadeService)
	{
		this.invoiceCandidateFacadeService = invoiceCandidateFacadeService;
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, // we aren't interested in "after-new", because prior to the first revalidation, the ICs isn't in a valid state anyways
			ifColumnsChanged = { /* keep in sync with the columns from InvoiceCandidateRecordHelper */
					I_C_Invoice_Candidate.COLUMNNAME_NetAmtInvoiced,
					I_C_Invoice_Candidate.COLUMNNAME_NetAmtToInvoice,
					I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoiceInUOM,
					I_C_Invoice_Candidate.COLUMNNAME_QtyInvoicedInUOM,
					I_C_Invoice_Candidate.COLUMNNAME_PriceActual })
	public void createOrUpdateCommissionInstance(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		try (final MDCCloseable icRecordMDC = TableRecordMDC.putTableRecordReference(icRecord))
		{
			invoiceCandidateFacadeService.syncICToCommissionInstance(icRecord, false/* candidateDeleted */);
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteCommissionInstance(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		try (final MDCCloseable icRecordMDC = TableRecordMDC.putTableRecordReference(icRecord))
		{
			invoiceCandidateFacadeService.syncICToCommissionInstance(icRecord, true/* candidateDeleted */);
		}
	}

}
