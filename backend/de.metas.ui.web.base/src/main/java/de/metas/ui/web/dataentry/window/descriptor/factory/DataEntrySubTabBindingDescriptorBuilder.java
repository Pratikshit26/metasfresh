package de.metas.ui.web.dataentry.window.descriptor.factory;

import org.springframework.stereotype.Service;

import de.metas.dataentry.data.DataEntryRecordRepository;
import de.metas.ui.web.window.descriptor.DocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.DocumentEntityDataBindingDescriptor.DocumentEntityDataBindingDescriptorBuilder;
import de.metas.ui.web.window.model.DocumentsRepository;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
     
 * #L%
 */

@Service
public class DataEntrySubTabBindingDescriptorBuilder implements DocumentEntityDataBindingDescriptorBuilder
{
	private final DocumentEntityDataBindingDescriptor dataBinding;

	@Getter
	private final DataEntryWebuiTools dataEntryWebuiTools;

	public DataEntrySubTabBindingDescriptorBuilder(
			@NonNull final DataEntryRecordRepository dataEntryRecordRepository,
			@NonNull final DataEntryWebuiTools dataEntryWebuiTools)
	{
		this.dataEntryWebuiTools = dataEntryWebuiTools;

		final DataEntrySubTabBindingRepository dataEntrySubGroupBindingRepository //
				= new DataEntrySubTabBindingRepository(dataEntryRecordRepository, dataEntryWebuiTools);

		this.dataBinding = new DocumentEntityDataBindingDescriptor()
		{
			@Override
			public DocumentsRepository getDocumentsRepository()
			{
				return dataEntrySubGroupBindingRepository;
			}
		};

	}

	@Override
	public DocumentEntityDataBindingDescriptor getOrBuild()
	{
		return dataBinding;
	}

}
