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
public class DataEntryRecordFieldYesNo extends DataEntryRecordField<Boolean>
{
	@Getter
	private final Boolean value;

	public static DataEntryRecordFieldYesNo of(
			@NonNull final DataEntryFieldId dataEntryFieldId,
			@NonNull final CreatedUpdatedInfo createdUpdatedInfo,
			@Nullable final Boolean value)
	{
		return new DataEntryRecordFieldYesNo(dataEntryFieldId, createdUpdatedInfo, value);
	}

	private DataEntryRecordFieldYesNo(
			@NonNull final DataEntryFieldId dataEntryFieldRepoId,
			@NonNull final CreatedUpdatedInfo createdUpdatedInfo,
			@Nullable final Boolean value)
	{
		super(dataEntryFieldRepoId, createdUpdatedInfo);
		this.value = value;
	}
}
