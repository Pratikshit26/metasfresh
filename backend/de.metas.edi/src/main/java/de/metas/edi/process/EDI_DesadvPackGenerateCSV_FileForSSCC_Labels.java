/*
 * #%L
 * de.metas.edi
     
 * #L%
 */

package de.metas.edi.process;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.edi.api.impl.pack.EDIDesadvPackId;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.esb.edi.model.I_EDI_Desadv_Pack;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryFilter;

import java.util.List;
import java.util.stream.Collectors;

public class EDI_DesadvPackGenerateCSV_FileForSSCC_Labels extends EDI_GenerateCSV_FileForSSCC_Labels
{

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.getSelectionSize().isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		else if (context.getSelectionSize().isMoreThanOneSelected())
		{
			final IQueryFilter<I_EDI_Desadv_Pack> selectedRecordsFilter = context.getQueryFilter(I_EDI_Desadv_Pack.class);

			final int differentConfigsSize = queryBL
					.createQueryBuilder(I_EDI_Desadv_Pack.class)
					.filter(selectedRecordsFilter)
					.andCollect(I_EDI_Desadv.COLUMN_EDI_Desadv_ID, I_EDI_Desadv.class)
					.create()
					.list()
					.stream()
					.map(ediDesadv -> BPartnerId.ofRepoId(ediDesadv.getC_BPartner_ID()))
					.map(bpartnerId -> zebraConfigRepository.retrieveZebraConfigId(bpartnerId, zebraConfigRepository.getDefaultZebraConfigId()))
					.collect(Collectors.toSet())
					.size();

			if (differentConfigsSize > 1)
			{
				return ProcessPreconditionsResolution.rejectWithInternalReason(msgBL.getTranslatableMsgText(EDI_GenerateCSV_FileForSSCC_Labels.MSG_DIFFERENT_ZEBRA_CONFIG_NOT_SUPPORTED));
			}
		}
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final IQueryFilter<I_EDI_Desadv_Pack> selectedRecordsFilter = getProcessInfo()
				.getQueryFilterOrElse(ConstantQueryFilter.of(false));

		final List<EDIDesadvPackId> list = queryBL
				.createQueryBuilder(I_EDI_Desadv_Pack.class)
				.filter(selectedRecordsFilter)
				.create()
				.stream()
				.map(I_EDI_Desadv_Pack::getEDI_Desadv_Pack_ID)
				.map(EDIDesadvPackId::ofRepoId)
				.collect(ImmutableList.toImmutableList());

		if (!Check.isEmpty(list))
		{
			generateCSV_FileForSSCC_Labels(list);
		}

		return MSG_OK;
	}
}
