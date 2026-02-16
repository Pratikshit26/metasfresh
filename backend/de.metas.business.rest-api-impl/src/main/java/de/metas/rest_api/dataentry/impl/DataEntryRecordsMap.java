package de.metas.rest_api.dataentry.impl;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.dataentry.DataEntrySubTabId;
import de.metas.dataentry.data.DataEntryRecord;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.business.rest-api
     
 * #L%
 */

@ToString
final class DataEntryRecordsMap
{
	public static DataEntryRecordsMap of(@NonNull final Collection<DataEntryRecord> records)
	{
		if (records.isEmpty())
		{
			return EMPTY;
		}

		return new DataEntryRecordsMap(records);
	}

	private static final DataEntryRecordsMap EMPTY = new DataEntryRecordsMap();

	private final ImmutableMap<DataEntrySubTabId, DataEntryRecord> map;

	private DataEntryRecordsMap(@NonNull final Collection<DataEntryRecord> records)
	{
		map = Maps.uniqueIndex(records, DataEntryRecord::getDataEntrySubTabId);
	}

	private DataEntryRecordsMap()
	{
		map = ImmutableMap.of();
	}

	public Set<DataEntrySubTabId> getSubTabIds()
	{
		return map.keySet();
	}

	public Optional<DataEntryRecord> getBySubTabId(final DataEntrySubTabId id)
	{
		final DataEntryRecord record = map.get(id);
		return Optional.ofNullable(record);
	}

}
