package de.metas.dataentry.data;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import de.metas.CreatedUpdatedInfo;
import de.metas.common.util.time.SystemTime;
import org.junit.Test;

import de.metas.dataentry.DataEntryFieldId;
import de.metas.user.UserId;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

public class DataEntryRecordFieldDateTimeTest
{
	/**
	 * Create two different instances that consist of the same primitives; make sure the two instances are equal.
	 */
	@Test
	public void equals()
	{
		final long millis = SystemTime.millis();

		final ZonedDateTime time1 = ZonedDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.of("CET"));
		final ZonedDateTime time2 = ZonedDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.of("CET"));
		assertThat(time1).isEqualTo(time2);// guard

		final DataEntryFieldId id1 = DataEntryFieldId.ofRepoId(10);
		final DataEntryFieldId id2 = DataEntryFieldId.ofRepoId(10);
		assertThat(id1).isEqualTo(id2);// guard

		final ZonedDateTime createdTime1 = ZonedDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.of("CET"));
		final ZonedDateTime createdTime2 = ZonedDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.of("CET"));

		final ZonedDateTime updatedTime1 = ZonedDateTime.ofInstant(Instant.ofEpochMilli(millis + 100), ZoneId.of("CET"));
		final ZonedDateTime updatedTime2 = ZonedDateTime.ofInstant(Instant.ofEpochMilli(millis + 100), ZoneId.of("CET"));

		final CreatedUpdatedInfo createdUpdatedInfo1 = CreatedUpdatedInfo.of(createdTime1, UserId.ofRepoId(10), updatedTime1, UserId.ofRepoId(20));
		final CreatedUpdatedInfo createdUpdatedInfo2 = CreatedUpdatedInfo.of(createdTime2, UserId.ofRepoId(10), updatedTime2, UserId.ofRepoId(20));
		assertThat(createdUpdatedInfo1).isEqualTo(createdUpdatedInfo2); // guard

		final DataEntryRecordFieldDateTime value1 = DataEntryRecordFieldDateTime.of(id1, createdUpdatedInfo1, time1);
		final DataEntryRecordFieldDateTime value2 = DataEntryRecordFieldDateTime.of(id2, createdUpdatedInfo2, time2);

		// invoke the method under test
		assertThat(value1).isEqualTo(value2);
	}

}
