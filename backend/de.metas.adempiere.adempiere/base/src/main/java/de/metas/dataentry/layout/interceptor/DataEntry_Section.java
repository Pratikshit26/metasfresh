package de.metas.dataentry.layout.interceptor;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.dataentry.model.I_DataEntry_Line;
import de.metas.dataentry.model.I_DataEntry_Section;
import de.metas.dataentry.model.I_DataEntry_Tab;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
     
 * #L%
 */

@Component
@Interceptor(I_DataEntry_Section.class)
@Callout(I_DataEntry_Section.class)
public class DataEntry_Section
{
	public DataEntry_Section()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteChildRecords(@NonNull final I_DataEntry_Section dataEntrySectionRecord)
	{
		Services.get(IQueryBL.class)
				.createQueryBuilder(I_DataEntry_Line.class)
				.addEqualsFilter(I_DataEntry_Line.COLUMN_DataEntry_Section_ID, dataEntrySectionRecord.getDataEntry_Section_ID())
				.create()
				.delete();
	}

	@CalloutMethod(columnNames = I_DataEntry_Section.COLUMNNAME_DataEntry_SubTab_ID)
	public void setSeqNo(@NonNull final I_DataEntry_Section dataEntrySectionRecord)
	{
		if (dataEntrySectionRecord.getDataEntry_SubTab_ID() <= 0)
		{
			return;
		}
		dataEntrySectionRecord.setSeqNo(maxSeqNo(dataEntrySectionRecord) + 10);
	}

	private int maxSeqNo(@NonNull final I_DataEntry_Section dataEntrySectionRecord)
	{
		return Services
				.get(IQueryBL.class)
				.createQueryBuilder(I_DataEntry_Section.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DataEntry_Section.COLUMN_DataEntry_SubTab_ID, dataEntrySectionRecord.getDataEntry_SubTab_ID())
				.create()
				.maxInt(I_DataEntry_Tab.COLUMNNAME_SeqNo);
	}
}
