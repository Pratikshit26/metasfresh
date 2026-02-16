package de.metas.dataentry.data;

import de.metas.CreatedUpdatedInfo;
import de.metas.dataentry.DataEntryFieldId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.time.LocalDate;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DataEntryRecordFieldDate extends DataEntryRecordField<LocalDate>
{
	@Getter
	private final LocalDate value;

	public static DataEntryRecordFieldDate of(
			@NonNull final DataEntryFieldId dataEntryFieldId,
			@NonNull final CreatedUpdatedInfo createdUpdatedInfo,
			@Nullable final LocalDate value)
	{
		return new DataEntryRecordFieldDate(
				dataEntryFieldId,
				createdUpdatedInfo,
				value);
	}

	private DataEntryRecordFieldDate(
			@NonNull final DataEntryFieldId dataEntryFieldId,
			@NonNull final CreatedUpdatedInfo createdUpdatedInfo,
			@Nullable final LocalDate value)
	{
		super(dataEntryFieldId, createdUpdatedInfo);
		this.value = value;
	}
}
