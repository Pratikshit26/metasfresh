package de.metas.dataentry.data;

import de.metas.CreatedUpdatedInfo;
import de.metas.dataentry.DataEntryFieldId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@EqualsAndHashCode(callSuper = true)
@ToString
public class DataEntryRecordFieldString extends DataEntryRecordField<String>
{
	@Getter
	private final String value;

	public static DataEntryRecordFieldString of(
			@NonNull final DataEntryFieldId dataEntryFieldId,
			@NonNull final CreatedUpdatedInfo createdUpdatedInfo,
			@Nullable final String value)
	{
		return new DataEntryRecordFieldString(dataEntryFieldId, createdUpdatedInfo, value);
	}

	private DataEntryRecordFieldString(
			@NonNull final DataEntryFieldId dataEntryFieldRepoId,
			@NonNull final CreatedUpdatedInfo createdUpdatedInfo,
			@Nullable final String value)
	{
		super(dataEntryFieldRepoId, createdUpdatedInfo);
		this.value = value;
	}
}
