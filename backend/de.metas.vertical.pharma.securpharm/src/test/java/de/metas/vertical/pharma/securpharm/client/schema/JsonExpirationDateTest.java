package de.metas.vertical.pharma.securpharm.client.schema;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.Month;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.metas.JsonObjectMapperHolder;

/*
 * #%L
 * metasfresh-pharma.securpharm
     
 * #L%
 */

public class JsonExpirationDateTest
{
	private ObjectMapper jsonObjectMapper;

	@Before
	public void init()
	{
		jsonObjectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
	}

	@Test
	public void test_ofJson()
	{
		assertThat(JsonExpirationDate.ofJson("210601").toLocalDate())
				.isEqualTo(LocalDate.of(2021, Month.JUNE, 1));
		assertThat(JsonExpirationDate.ofJson("2021-06-01").toLocalDate())
				.isEqualTo(LocalDate.of(2021, Month.JUNE, 1));

		assertThat(JsonExpirationDate.ofJson("220830").toLocalDate())
				.isEqualTo(LocalDate.of(2022, Month.AUGUST, 30));
		assertThat(JsonExpirationDate.ofJson("2022-08-30").toLocalDate())
				.isEqualTo(LocalDate.of(2022, Month.AUGUST, 30));

		assertThat(JsonExpirationDate.ofJson("220800").toLocalDate())
				.isEqualTo(LocalDate.of(2022, Month.AUGUST, 31));
		assertThat(JsonExpirationDate.ofJson("2022-08-31").toLocalDate())
				.isEqualTo(LocalDate.of(2022, Month.AUGUST, 31));
	}

	@Test
	public void test_ofLocalDate()
	{
		assertThat(JsonExpirationDate.ofLocalDate(LocalDate.of(2021, Month.JUNE, 1)).toJson())
				.isEqualTo("210601");

		assertThat(JsonExpirationDate.ofLocalDate(LocalDate.of(2022, Month.AUGUST, 30)).toJson())
				.isEqualTo("220830");

		assertThat(JsonExpirationDate.ofLocalDate(LocalDate.of(2022, Month.AUGUST, 31)).toJson())
				.isEqualTo("220800");
	}

	@Test
	public void testSerializedDeserialize() throws Exception
	{
		testSerializedDeserialize(JsonExpirationDate.ofJson("210601"));
		testSerializedDeserialize(JsonExpirationDate.ofJson("220800"));
	}

	public void testSerializedDeserialize(final JsonExpirationDate date) throws Exception
	{
		final String json = jsonObjectMapper.writeValueAsString(date);
		final JsonExpirationDate date2 = jsonObjectMapper.readValue(json, JsonExpirationDate.class);
		assertThat(date2).isEqualTo(date);
	}

}
