package de.metas.dataentry.data;

import de.metas.CreatedUpdatedInfo;
import de.metas.dataentry.DataEntryFieldId;
import de.metas.dataentry.DataEntryListValueId;
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
public class DataEntryRecordFieldListValue extends DataEntryRecordField<DataEntryListValueId>
{
	@Getter
	private final DataEntryListValueId value;

	public static DataEntryRecordFieldListValue of(
			@NonNull final DataEntryFieldId dataEntryFieldId,
			@NonNull final CreatedUpdatedInfo createdUpdatedInfo,
			@Nullable final DataEntryListValueId value)
	{
		return new DataEntryRecordFieldListValue(dataEntryFieldId, createdUpdatedInfo, value);
	}

	private DataEntryRecordFieldListValue(
			@NonNull final DataEntryFieldId dataEntryFieldRepoId,
			@NonNull final CreatedUpdatedInfo createdUpdatedInfo,
			@Nullable final DataEntryListValueId listValueId)
	{
		super(dataEntryFieldRepoId, createdUpdatedInfo);
		this.value = listValueId;
	}
}
