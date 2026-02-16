/*
 * #%L
 * de.metas.issue.tracking.everhour
     
 * #L%
 */

package de.metas.issue.tracking.everhour.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.issue.tracking.everhour.api.model.Task;
import de.metas.issue.tracking.everhour.api.model.TimeRecord;
import org.junit.Test;

import java.io.IOException;

import static de.metas.issue.tracking.everhour.api.TestConstants.MOCK_DATE;
import static de.metas.issue.tracking.everhour.api.TestConstants.MOCK_ID;
import static de.metas.issue.tracking.everhour.api.TestConstants.MOCK_TIME;
import static de.metas.issue.tracking.everhour.api.TestConstants.MOCK_URL;
import static de.metas.issue.tracking.everhour.api.TestConstants.MOCK_USER_ID;
import static org.assertj.core.api.Assertions.assertThat;

public class TestPOJOs
{
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	public void task_serialize_deserialize() throws IOException
	{
		testSerializeDeserializeObject(getMockTask());
	}

	@Test
	public void timeRecord_serialize_deserialize() throws IOException
	{
		testSerializeDeserializeObject(getMockTimeRecord());
	}

	private TimeRecord getMockTimeRecord()
	{
		return TimeRecord.builder()
				.id(MOCK_ID)
				.date(MOCK_DATE)
				.userId(MOCK_USER_ID)
				.task(getMockTask())
				.time(MOCK_TIME)
				.build();
	}

	private Task getMockTask()
	{
		return Task.builder()
				.id(MOCK_ID)
				.url(MOCK_URL)
				.build();
	}

	private void testSerializeDeserializeObject(final Object value) throws IOException
	{
		final Class<?> valueClass = value.getClass();
		final String json = objectMapper.writeValueAsString(value);
		final Object value2 = objectMapper.readValue(json, valueClass);
		assertThat(value2).isEqualTo(value);
	}
}
