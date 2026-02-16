/*
 * #%L
 * de.metas.edi
     
 * #L%
 */

package de.metas.edi.process;

import com.google.common.collect.ImmutableList;
import de.metas.edi.api.impl.pack.EDIDesadvPackId;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.esb.edi.model.I_EDI_Desadv_Pack;
import de.metas.util.Check;
import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryFilter;

import java.util.List;

public class EDI_DesadvGenerateCSV_FileForSSCC_Labels extends EDI_GenerateCSV_FileForSSCC_Labels
{
	@Override protected String doIt() throws Exception
	{
		final IQueryFilter<I_EDI_Desadv> selectedRecordsFilter = getProcessInfo()
				.getQueryFilterOrElse(ConstantQueryFilter.of(false));

		final List<EDIDesadvPackId> list = queryBL
				.createQueryBuilder(I_EDI_Desadv.class)
				.filter(selectedRecordsFilter)
				.andCollectChildren(I_EDI_Desadv_Pack.COLUMN_EDI_Desadv_ID)
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
