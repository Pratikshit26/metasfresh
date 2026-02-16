package de.metas.dataentry.data;

import de.metas.CreatedUpdatedInfo;
import de.metas.dataentry.DataEntryFieldId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DataEntryRecordFieldDateTime extends DataEntryRecordField<ZonedDateTime>
{
	@Getter
	private final ZonedDateTime value;

	public static DataEntryRecordFieldDateTime of(
			@NonNull final DataEntryFieldId dataEntryFieldId,
			@NonNull final CreatedUpdatedInfo createdUpdatedInfo,
			@Nullable final ZonedDateTime value)
	{
		return new DataEntryRecordFieldDateTime(
				dataEntryFieldId,
				createdUpdatedInfo,
				value);
	}

	private DataEntryRecordFieldDateTime(
			@NonNull final DataEntryFieldId dataEntryFieldId,
			@NonNull final CreatedUpdatedInfo createdUpdatedInfo,
			@Nullable final ZonedDateTime value)
	{
		super(dataEntryFieldId, createdUpdatedInfo);
		this.value = value;
	}
}
