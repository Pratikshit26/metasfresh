package de.metas.customs.process;

import de.metas.bpartner.BPartnerContactId;
import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_InOut;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.customs.CustomsInvoice;
import de.metas.customs.CustomsInvoiceService;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.user.UserId;
import de.metas.util.Check;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */

public class M_InOut_Create_CustomsInvoice extends JavaProcess implements IProcessPrecondition
{

	public final CustomsInvoiceService customsInvoiceService = SpringContextHolder.instance.getBean(CustomsInvoiceService.class);

	@Param(parameterName = "C_BPartner_ID")
	private BPartnerId p_BPartnerId;

	@Param(parameterName = "C_BPartner_Location_ID")
	private int p_C_BPartner_Location_ID;

	@Param(parameterName = "AD_User_ID")
	private UserId p_ContactId;

	@Param(parameterName = "IsComplete")
	private boolean p_IsComplete;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final IQueryFilter<I_M_InOut> queryFilter = getProcessInfo()
				.getQueryFilterOrElse(ConstantQueryFilter.of(false));

		final BPartnerLocationId bpartnerLocationId = BPartnerLocationId.ofRepoId(p_BPartnerId, p_C_BPartner_Location_ID);
		final BPartnerContactId bPartnerContactId = p_ContactId != null ? BPartnerContactId.of(p_BPartnerId, p_ContactId) : null;

		final CustomsInvoice customsInvoice = customsInvoiceService.generateNewCustomsInvoice(bpartnerLocationId, bPartnerContactId, queryFilter);

		if (p_IsComplete && !Check.isEmpty(customsInvoice.getLines()))
		{
			customsInvoiceService.completeCustomsInvoice(customsInvoice);
		}

		return MSG_OK;

	}

}
