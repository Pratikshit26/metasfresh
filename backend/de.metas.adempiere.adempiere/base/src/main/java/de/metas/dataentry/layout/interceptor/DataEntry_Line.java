package de.metas.dataentry.layout.interceptor;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.dataentry.model.I_DataEntry_Field;
import de.metas.dataentry.model.I_DataEntry_Line;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
     
 * #L%
 */

@Component
@Interceptor(I_DataEntry_Line.class)
public class DataEntry_Line
{
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteChildRecords(@NonNull final I_DataEntry_Line dataEntryLineRecord)
	{
		Services.get(IQueryBL.class)
				.createQueryBuilder(I_DataEntry_Field.class)
				.addEqualsFilter(I_DataEntry_Field.COLUMN_DataEntry_Line_ID, dataEntryLineRecord.getDataEntry_Line_ID())
				.create()
				.delete();
	}
}
