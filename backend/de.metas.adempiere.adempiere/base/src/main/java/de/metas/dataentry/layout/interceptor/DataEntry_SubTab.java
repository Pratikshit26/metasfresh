package de.metas.dataentry.layout.interceptor;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.dataentry.model.I_DataEntry_Section;
import de.metas.dataentry.model.I_DataEntry_SubTab;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
     
 * #L%
 */

@Component
@Interceptor(I_DataEntry_SubTab.class)
public class DataEntry_SubTab
{
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteChildRecords(@NonNull final I_DataEntry_SubTab dataEntrySubGroupRecord)
	{
		Services.get(IQueryBL.class)
				.createQueryBuilder(I_DataEntry_Section.class)
				.addEqualsFilter(I_DataEntry_Section.COLUMN_DataEntry_SubTab_ID, dataEntrySubGroupRecord.getDataEntry_SubTab_ID())
				.create()
				.delete();
	}
}
