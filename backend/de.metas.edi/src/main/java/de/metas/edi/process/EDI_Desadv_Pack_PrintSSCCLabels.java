package de.metas.edi.process;

import com.google.common.collect.ImmutableSet;
import de.metas.edi.api.IDesadvBL;
import de.metas.edi.api.impl.pack.EDIDesadvPackId;
import de.metas.esb.edi.model.I_EDI_Desadv_Pack;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.report.ReportResultData;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;

import java.util.Set;

/*
 * #%L
 * de.metas.edi
     
 * #L%
 */

public class EDI_Desadv_Pack_PrintSSCCLabels extends JavaProcess implements IProcessPrecondition
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IDesadvBL desadvBL = Services.get(IDesadvBL.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.getSelectionSize().isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final IQueryFilter<I_EDI_Desadv_Pack> selectedRecordsFilter = getProcessInfo()
				.getQueryFilterOrElse(ConstantQueryFilter.of(false));

		final Set<EDIDesadvPackId> list = queryBL
				.createQueryBuilder(I_EDI_Desadv_Pack.class)
				.filter(selectedRecordsFilter)
				.create()
				.stream()
				.map(I_EDI_Desadv_Pack::getEDI_Desadv_Pack_ID)
				.map(EDIDesadvPackId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());

		final ReportResultData reportResult = desadvBL.printSSCC18_Labels(getCtx(), list);
		getProcessInfo().getResult().setReportData(reportResult);

		return MSG_OK;
	}
}
