/*
 * #%L
 * de.metas.edi
     
 * #L%
 */

package de.metas.edi.process;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.ZebraConfigId;
import de.metas.edi.api.ZebraConfigRepository;
import de.metas.edi.api.ZebraPrinterService;
import de.metas.edi.api.impl.pack.EDIDesadvPackId;
import de.metas.edi.api.impl.pack.EDIDesadvPackRepository;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.i18n.AdMessageKey;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.report.ReportResultData;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.SpringContextHolder;

import java.util.List;
import java.util.stream.Collectors;

public abstract class EDI_GenerateCSV_FileForSSCC_Labels extends JavaProcess implements IProcessPrecondition
{
	protected final IQueryBL queryBL = Services.get(IQueryBL.class);
	protected final ZebraConfigRepository zebraConfigRepository = SpringContextHolder.instance.getBean(ZebraConfigRepository.class);

	protected static final AdMessageKey MSG_DIFFERENT_ZEBRA_CONFIG_NOT_SUPPORTED = AdMessageKey.of("WEBUI_ZebraConfigError");

	private final ZebraPrinterService zebraPrinterService = SpringContextHolder.instance.getBean(ZebraPrinterService.class);
	private final EDIDesadvPackRepository EDIDesadvPackRepository = SpringContextHolder.instance.getBean(EDIDesadvPackRepository.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.getSelectionSize().isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		else if (context.getSelectionSize().isMoreThanOneSelected())
		{
			final IQueryFilter<I_EDI_Desadv> selectedRecordsFilter = context.getQueryFilter(I_EDI_Desadv.class);
			final int differentConfigsSize = queryBL
					.createQueryBuilder(I_EDI_Desadv.class)
					.filter(selectedRecordsFilter)
					.create()
					.list()
					.stream()
					.map(ediDesadv -> BPartnerId.ofRepoId(ediDesadv.getC_BPartner_ID()))
					.map(bpartnerId -> zebraConfigRepository.retrieveZebraConfigId(bpartnerId, zebraConfigRepository.getDefaultZebraConfigId()))
					.collect(Collectors.toSet())
					.size();

			if (differentConfigsSize > 1)
			{
				return ProcessPreconditionsResolution.rejectWithInternalReason(msgBL.getTranslatableMsgText(MSG_DIFFERENT_ZEBRA_CONFIG_NOT_SUPPORTED));
			}

		}
		return ProcessPreconditionsResolution.accept();
	}

	void generateCSV_FileForSSCC_Labels(@NonNull final List<EDIDesadvPackId> desadvPackIDsToPrint)
	{
		final BPartnerId bPartnerId = EDIDesadvPackRepository.retrieveBPartnerFromEdiDesadvPack(desadvPackIDsToPrint.get(0));
		final ZebraConfigId zebraConfigId = zebraConfigRepository.retrieveZebraConfigId(bPartnerId, zebraConfigRepository.getDefaultZebraConfigId());
		final ReportResultData reportResultData = zebraPrinterService
				.createCSV_FileForSSCC18_Labels(desadvPackIDsToPrint, zebraConfigId, getProcessInfo().getPinstanceId());

		getProcessInfo().getResult().setReportData(reportResultData);
	}
}