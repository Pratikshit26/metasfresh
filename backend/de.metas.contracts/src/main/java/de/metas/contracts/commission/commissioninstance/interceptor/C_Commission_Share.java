package de.metas.contracts.commission.commissioninstance.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.contracts.commission.model.I_C_Commission_Share;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.contracts
     
 * #L%
 */

@Interceptor(I_C_Commission_Share.class)
@Component
public class C_Commission_Share
{
	private final IInvoiceCandidateHandlerBL invoiceCandidateHandlerBL = Services.get(IInvoiceCandidateHandlerBL.class);

	@ModelChange(//
			timings = ModelValidator.TYPE_AFTER_CHANGE, //
			ifColumnsChanged = { I_C_Commission_Share.COLUMNNAME_PointsSum_Forecasted, I_C_Commission_Share.COLUMNNAME_PointsSum_Invoiceable, I_C_Commission_Share.COLUMNNAME_PointsSum_Invoiced })
	public void invalidateIc(@NonNull final I_C_Commission_Share shareRecord)
	{
		invoiceCandidateHandlerBL.invalidateCandidatesFor(shareRecord);
	}
}
