package de.metas.dataentry.layout;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import com.google.common.collect.ImmutableList;

import de.metas.dataentry.DataEntryFieldId;
import de.metas.dataentry.FieldType;
import de.metas.i18n.ITranslatableString;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
     
 * #L%
 */

@Value
public class DataEntryField
{
	DataEntryFieldId id;

	ITranslatableString caption;
	ITranslatableString description;

	FieldType type;

	boolean mandatory;

	boolean availableInApi;

	/** empty, unless type=list */
	ImmutableList<DataEntryListValue> listValues;

	@Builder
	private DataEntryField(
			@NonNull final DataEntryFieldId id,
			@NonNull final ITranslatableString caption,
			@NonNull final ITranslatableString description,
			@NonNull final FieldType type,
			final boolean mandatory,
			final boolean availableInApi,
			@Singular final List<DataEntryListValue> listValues)
	{
		this.id = id;
		this.caption = caption;
		this.description = description;
		this.type = type;
		this.mandatory = mandatory;
		this.availableInApi = availableInApi;
		this.listValues = ImmutableList.copyOf(listValues);
	}

	public Optional<DataEntryListValue> getFirstListValueMatching(@NonNull final Predicate<DataEntryListValue> predicate)
	{
		return listValues.stream()
				.filter(predicate)
				.findFirst();
	}
}
