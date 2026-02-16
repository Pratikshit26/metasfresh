package de.metas.ui.web.dataentry.window.descriptor.factory;

import de.metas.ui.web.window.descriptor.DocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.DocumentEntityDataBindingDescriptor.DocumentEntityDataBindingDescriptorBuilder;
import de.metas.ui.web.window.model.DocumentsRepository;

/*
 * #%L
 * metasfresh-webui-api
     
 * #L%
 */

/**
 * "Empty" descriptor builder. Data entry groups have no records of themselves. Only their sub groups have.
 */
public class DataEntryTabBindingDescriptorBuilder implements DocumentEntityDataBindingDescriptorBuilder
{
	public static final transient DataEntryTabBindingDescriptorBuilder instance = new DataEntryTabBindingDescriptorBuilder();

	private static final DocumentEntityDataBindingDescriptor dataBinding = new DocumentEntityDataBindingDescriptor()
	{
		@Override
		public DocumentsRepository getDocumentsRepository()
		{
			throw new UnsupportedOperationException("DocumentEntityDataBindingDescriptor " + this + " has no DocumentsRepository");
		}
	};

	@Override
	public DocumentEntityDataBindingDescriptor getOrBuild()
	{
		return dataBinding;
	}

}
