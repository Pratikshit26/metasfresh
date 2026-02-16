package de.metas.dataentry.layout;

import java.util.List;

import com.google.common.collect.ImmutableList;

import de.metas.dataentry.DataEntrySectionId;
import de.metas.i18n.ITranslatableString;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@Value
public class DataEntrySection
{
	DataEntrySectionId id;

	ITranslatableString caption;
	ITranslatableString description;

	String internalName;

	boolean initiallyClosed;

	boolean availableInApi;

	ImmutableList<DataEntryLine> lines;

	@Builder
	private DataEntrySection(
			@NonNull final DataEntrySectionId id,
			@NonNull final ITranslatableString caption,
			@NonNull final ITranslatableString description,
			@NonNull final String internalName,
			final boolean initiallyClosed,
			final boolean availableInApi,
			@Singular final List<DataEntryLine> lines)
	{
		this.id = id;
		this.caption = caption;
		this.description = description;
		this.internalName = internalName;
		this.initiallyClosed = initiallyClosed;
		this.availableInApi = availableInApi;
		this.lines = ImmutableList.copyOf(lines);
	}
}
