package de.metas.ui.web.pattribute;

import java.util.Map;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMap;

import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.model.Document.DocumentValuesSupplier;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * metasfresh-webui-api
     
 * #L%
 */

@ToString
final class ASIDocumentValuesSupplier implements DocumentValuesSupplier
{
	private static final String VERSION = "0";
	private final Supplier<DocumentId> documentIdSupplier;
	private final Map<String, Object> values;

	@Builder
	private ASIDocumentValuesSupplier(
			@NonNull final Supplier<DocumentId> documentIdSupplier,
			@Nullable final Map<String, Object> values)
	{
		this.documentIdSupplier = documentIdSupplier;
		this.values = values != null ? values : ImmutableMap.of();
	}

	@Override
	public DocumentId getDocumentId()
	{
		return documentIdSupplier.get();
	}

	@Override
	public String getVersion()
	{
		return VERSION;
	}

	@Override
	public Object getValue(final DocumentFieldDescriptor fieldDescriptor)
	{
		final String fieldName = fieldDescriptor.getFieldName();
		if (values.containsKey(fieldName))
		{
			return values.get(fieldName);
		}
		else
		{
			return NO_VALUE;
		}
	}

}
