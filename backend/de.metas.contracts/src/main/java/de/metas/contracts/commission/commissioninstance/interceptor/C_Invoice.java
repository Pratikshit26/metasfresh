package de.metas.contracts.commission.commissioninstance.interceptor;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.ModelValidator;
import org.slf4j.MDC.MDCCloseable;
import org.springframework.stereotype.Component;

import de.metas.logging.TableRecordMDC;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.contracts
     
 * #L%
 */

@Interceptor(I_C_Invoice.class)
@Component
public class C_Invoice
{
	private final C_InvoiceFacadeService invoiceFacadeService;
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	public C_Invoice(@NonNull final C_InvoiceFacadeService invoiceFacadeService)
	{
		this.invoiceFacadeService = invoiceFacadeService;
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE, ModelValidator.TIMING_AFTER_CLOSE })
	public void createCommissionInstanceForInvoice(@NonNull final I_C_Invoice invoiceRecord)
	{
		trxManager.getCurrentTrxListenerManagerOrAutoCommit()
				.runAfterCommit(() -> trxManager.runInNewTrx(() -> {
					try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(invoiceRecord))
					{
						invoiceFacadeService.syncInvoiceToCommissionInstance(invoiceRecord);
					}
				}));
	}
}
