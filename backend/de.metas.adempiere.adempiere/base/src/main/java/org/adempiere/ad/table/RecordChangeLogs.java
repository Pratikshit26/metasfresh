package org.adempiere.ad.table;

import de.metas.user.UserId;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.TimeUtil;

import java.sql.Timestamp;
import java.util.List;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

public class RecordChangeLogs
{
	public static <T> RecordChangeLog of(
			@NonNull final T model,
			@NonNull final Class<T> modelClass,
			@NonNull final List<RecordChangeLogEntry> entries)
	{
		final RecordChangeLog.RecordChangeLogBuilder builder = RecordChangeLog.builder().entries(entries);

		final Integer createdBy = InterfaceWrapperHelper.getValueOrNull(model, InterfaceWrapperHelper.COLUMNNAME_Created);
		builder.createdByUserId(UserId.ofRepoId(createdBy));

		final Timestamp created = InterfaceWrapperHelper.getValueOrNull(model, InterfaceWrapperHelper.COLUMNNAME_Created);
		builder.createdTimestamp(TimeUtil.asInstant(created));

		final Integer updatedBy = InterfaceWrapperHelper.getValueOrNull(model, InterfaceWrapperHelper.COLUMNNAME_UpdatedBy);
		builder.lastChangedByUserId(UserId.ofRepoId(updatedBy));

		final Timestamp updated = InterfaceWrapperHelper.getValueOrNull(model, InterfaceWrapperHelper.COLUMNNAME_Updated);
		builder.createdTimestamp(TimeUtil.asInstant(updated));

		final String tableName = InterfaceWrapperHelper.getTableName(modelClass);
		builder.tableName(tableName);

		final int id = InterfaceWrapperHelper.getId(model);
		final ComposedRecordId recordId = ComposedRecordId.singleKey(InterfaceWrapperHelper.getKeyColumnName(modelClass), id);
		builder.recordId(recordId);

		return builder.build();
	}
}
