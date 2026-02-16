package de.metas.ui.web.dataentry.window.descriptor.factory;

import javax.annotation.Nullable;

import de.metas.dataentry.DataEntryFieldId;
import de.metas.dataentry.FieldType;
import de.metas.ui.web.window.descriptor.DocumentFieldDataBindingDescriptor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
     
 * #L%
 */

@Value
@Builder
public class DataEntryFieldBindingDescriptor implements DocumentFieldDataBindingDescriptor
{

	/** If this instance represents a "real" dataEntry file, then this is the {@link #dataEntryFieldId}'s repoId as string */
	@NonNull
	String columnName;

	boolean mandatory;

	/** May be null for the parent link and documentId fields */
	@Nullable
	DataEntryFieldId dataEntryFieldId;

	@NonNull
	FieldType fieldType;
}
