package org.adempiere.ad.table;

import java.util.List;

import org.adempiere.ad.table.LogEntriesRepository;
import org.adempiere.ad.table.RecordChangeLogEntry;
import org.adempiere.util.lang.impl.TableRecordReference;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder.ListMultimapBuilder;

import lombok.NonNull;

/*
 * #%L
 * de.metas.business.rest-api-impl
     
 * #L%
 */

public class MockLogEntriesRepository implements LogEntriesRepository
{
	private final ListMultimap<TableRecordReference, RecordChangeLogEntry> returnValues = ListMultimapBuilder.hashKeys().arrayListValues().build();

	@Override
	public ImmutableListMultimap<TableRecordReference, RecordChangeLogEntry> getLogEntriesForRecordReferences(
			@NonNull final LogEntriesQuery logEntriesQuery)
	{
		final ImmutableListMultimap.Builder<TableRecordReference, RecordChangeLogEntry> result = ImmutableListMultimap.builder();

		for (final TableRecordReference tableRecordReference : logEntriesQuery.getTableRecordReferences())
		{
			final List<RecordChangeLogEntry> logEntries = returnValues.get(tableRecordReference);
			result.putAll(tableRecordReference, logEntries);
		}

		return result.build();
	}

	public void add(
			@NonNull final TableRecordReference tableRecordReference,
			@NonNull final RecordChangeLogEntry recordChangeLogEntry)
	{
		returnValues.put(tableRecordReference, recordChangeLogEntry);
	}
}
