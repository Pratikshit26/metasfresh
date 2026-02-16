package de.metas.dataentry.data;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.io.Resources;

import de.metas.CreatedUpdatedInfo;
import de.metas.dataentry.DataEntryFieldId;
import de.metas.dataentry.DataEntryListValueId;
import de.metas.user.UserId;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

public class DataEntryRecordTestConstants
{
	public static final LocalDate DATE = LocalDate.of(2019/* year */, 2/* month */, 12/* dayOfMonth */);

	public static final ZonedDateTime CREATED = ZonedDateTime.of(2019/* year */, 2/* month */, 12/* dayOfMonth */, 13/* hour */, 20/* minute */, 42/* second */, 2/* nanoOfSecond */, ZoneId.of("+01:00"));

	public static final ZonedDateTime UPDATED = ZonedDateTime.of(2019/* year */, 2/* month */, 12/* dayOfMonth */, 14/* hour */, 20/* minute */, 42/* second */, 2/* nanoOfSecond */, ZoneId.of("+01:00"));

	public static final CreatedUpdatedInfo CREATED_UPDATED_INFO = CreatedUpdatedInfo
			.createNew(UserId.ofRepoId(10), CREATED)
			.updated(UserId.ofRepoId(20), UPDATED);

	public static final ImmutableList<DataEntryRecordField<?>> SIMPLE_DATA_ENTRY_FIELD_DATA = createDataEntryFieldData(DATE);

	private static ImmutableList<DataEntryRecordField<?>> createDataEntryFieldData(@NonNull final LocalDate date)
	{
		return ImmutableList
				.<DataEntryRecordField<?>> builder()
				.add(DataEntryRecordFieldDate.of(DataEntryFieldId.ofRepoId(30), CREATED_UPDATED_INFO, date))
				.add(DataEntryRecordFieldListValue.of(DataEntryFieldId.ofRepoId(31), CREATED_UPDATED_INFO, DataEntryListValueId.ofRepoId(41)))
				.add(DataEntryRecordFieldNumber.of(DataEntryFieldId.ofRepoId(32), CREATED_UPDATED_INFO, new BigDecimal("0.23")))
				.add(DataEntryRecordFieldString.of(DataEntryFieldId.ofRepoId(33), CREATED_UPDATED_INFO, "stringFieldValue"))
				.add(DataEntryRecordFieldYesNo.of(DataEntryFieldId.ofRepoId(34), CREATED_UPDATED_INFO, true))
				.build();

	}

	public static final String SIMPLE_DATA_ENTRY_FIELD_DATA_JSON = loadJson();

	private static String loadJson()
	{
		final URL url = Resources.getResource("de/metas/dataentry/data/DataEntryRecordTestConstants_SIMPLE_DATA_ENTRY_FIELD_DATA.json");
		final String expectedString;
		try
		{
			expectedString = Resources.toString(url, Charsets.UTF_8);
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
		return expectedString;
	}
}
