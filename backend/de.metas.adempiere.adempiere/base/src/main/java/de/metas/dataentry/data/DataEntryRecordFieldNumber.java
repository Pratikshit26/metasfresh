package de.metas.dataentry.data;

import de.metas.CreatedUpdatedInfo;
import de.metas.dataentry.DataEntryFieldId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@EqualsAndHashCode(callSuper = true)
@ToString
public class DataEntryRecordFieldNumber extends DataEntryRecordField<BigDecimal>
{
	@Getter
	private final BigDecimal value;

	public static DataEntryRecordFieldNumber of(
			@NonNull final DataEntryFieldId dataEntryFieldId,
			@NonNull final CreatedUpdatedInfo createdUpdatedInfo,
			@Nullable final BigDecimal value)
	{
		return new DataEntryRecordFieldNumber(dataEntryFieldId, createdUpdatedInfo, value);
	}

	private DataEntryRecordFieldNumber(
			@NonNull final DataEntryFieldId dataEntryFieldRepoId,
			@NonNull final CreatedUpdatedInfo createdUpdatedInfo,
			@Nullable final BigDecimal value)
	{
		super(dataEntryFieldRepoId, createdUpdatedInfo);
		this.value = value;
	}
}
