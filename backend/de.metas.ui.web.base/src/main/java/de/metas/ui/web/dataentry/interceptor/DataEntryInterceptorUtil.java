package de.metas.ui.web.dataentry.interceptor;

import org.springframework.stereotype.Component;

import de.metas.dataentry.model.I_DataEntry_Field;
import de.metas.dataentry.model.I_DataEntry_Line;
import de.metas.dataentry.model.I_DataEntry_ListValue;
import de.metas.dataentry.model.I_DataEntry_Section;
import de.metas.dataentry.model.I_DataEntry_SubTab;
import de.metas.dataentry.model.I_DataEntry_Tab;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.factory.DocumentDescriptorFactory;
import de.metas.ui.web.window.model.DocumentCollection;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
     
 * #L%
 */

@Component
public class DataEntryInterceptorUtil
{
	private final DocumentDescriptorFactory documentDescriptorFactory;

	private final DocumentCollection documentCollection;

	public DataEntryInterceptorUtil(
			@NonNull final DocumentDescriptorFactory documentDescriptorFactory,
			@NonNull final DocumentCollection documentCollection)
	{
		this.documentDescriptorFactory = documentDescriptorFactory;
		this.documentCollection = documentCollection;
	}

	public void resetCacheFor(@NonNull final I_DataEntry_ListValue dataEntryListValueRecord)
	{
		if (dataEntryListValueRecord.getDataEntry_Field_ID() > 0)
		{
			resetCacheFor(dataEntryListValueRecord.getDataEntry_Field());
		}
	}

	public void resetCacheFor(@NonNull final I_DataEntry_Field dataEntryFieldRecord)
	{
		if (dataEntryFieldRecord.getDataEntry_Line_ID() > 0)
		{
			resetCacheFor(dataEntryFieldRecord.getDataEntry_Line());
		}
	}

	public void resetCacheFor(@NonNull final I_DataEntry_Line dataEntryLineRecord)
	{
		if (dataEntryLineRecord.getDataEntry_Section_ID() > 0)
		{
			resetCacheFor(dataEntryLineRecord.getDataEntry_Section());
		}
	}

	public void resetCacheFor(@NonNull final I_DataEntry_Section dataEntrySectionRecord)
	{
		if (dataEntrySectionRecord.getDataEntry_SubTab_ID() > 0)
		{
			resetCacheFor(dataEntrySectionRecord.getDataEntry_SubTab());
		}
	}

	public void resetCacheFor(@NonNull final I_DataEntry_SubTab dataEntrySubGroupRecord)
	{
		if (dataEntrySubGroupRecord.getDataEntry_Tab_ID() > 0)
		{
			resetCacheFor(dataEntrySubGroupRecord.getDataEntry_Tab());
		}
	}

	public void resetCacheFor(@NonNull final I_DataEntry_Tab dataEntryGroupRecord)
	{
		final int windowId = dataEntryGroupRecord.getDataEntry_TargetWindow_ID();
		if (windowId > 0)
		{
			documentDescriptorFactory.invalidateForWindow(WindowId.of(windowId));

			final boolean forgetNotSavedDocuments = false;
			documentCollection.cacheReset(forgetNotSavedDocuments);
		}
	}

}
