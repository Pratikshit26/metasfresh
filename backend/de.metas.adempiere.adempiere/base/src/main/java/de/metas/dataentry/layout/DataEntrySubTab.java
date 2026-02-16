package de.metas.dataentry.layout;

import java.util.List;

import org.adempiere.exceptions.AdempiereException;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.dataentry.DataEntryFieldId;
import de.metas.dataentry.DataEntrySubTabId;
import de.metas.i18n.ITranslatableString;
import de.metas.util.GuavaCollectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
     
 * #L%
 */

@Value
@ToString(exclude = { "fieldsById" })
public class DataEntrySubTab
{
	DataEntrySubTabId id;

	ITranslatableString caption;
	ITranslatableString description;

	String internalName;

	ImmutableList<DataEntrySection> sections;

	@Getter(AccessLevel.NONE)
	@JsonIgnore
	ImmutableMap<DataEntryFieldId, DataEntryField> fieldsById;

	@Builder
	private DataEntrySubTab(
			@NonNull final DataEntrySubTabId id,
			@NonNull final ITranslatableString caption,
			@NonNull final ITranslatableString description,
			@NonNull final String internalName,
			@Singular final List<DataEntrySection> sections)
	{
		this.id = id;
		this.caption = caption;
		this.description = description;
		this.internalName = internalName;
		this.sections = ImmutableList.copyOf(sections);

		fieldsById = this.sections.stream()
				.flatMap(section -> section.getLines().stream())
				.flatMap(line -> line.getFields().stream())
				.collect(GuavaCollectors.toImmutableMapByKey(DataEntryField::getId));
	}

	public DataEntryField getFieldById(@NonNull final DataEntryFieldId fieldId)
	{
		final DataEntryField field = fieldsById.get(fieldId);
		if (field == null)
		{
			throw new AdempiereException("@NotFound@ " + fieldId + " in " + this);
		}
		return field;
	}
}
