package de.metas.rest_api.v1.ordercandidates.impl;

import de.metas.common.ordercandidates.v1.request.JsonOLCandCreateBulkRequest;
import de.metas.util.JSONObjectMapper;
import lombok.NonNull;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

/*
 * #%L
 * de.metas.ordercandidate.rest-api
     
 * #L%
 */

public class JsonOLCandCreateBulkRequestTest
{
	@Test
	public void test_realJson() throws IOException
	{
		final JsonOLCandCreateBulkRequest bulkRequest = JsonOLCandUtil.loadJsonOLCandCreateBulkRequest("/de/metas/rest_api/v1/ordercandidates/impl/" + "JsonOLCandCreateBulkRequest.json");
		testSerializeDeserialize(bulkRequest, JSONObjectMapper.forClass(JsonOLCandCreateBulkRequest.class));
	}

	private <T> void testSerializeDeserialize(
			@NonNull final T obj,
			@NonNull JSONObjectMapper<T> jsonObjectMapper) throws IOException
	{
		// System.out.println("object: " + obj);
		final String json = jsonObjectMapper.writeValueAsString(obj);
		// System.out.println("json: " + json);

		final Object objDeserialized = jsonObjectMapper.readValue(json);
		// System.out.println("object deserialized: " + objDeserialized);

		assertThat(objDeserialized).isEqualTo(obj);
	}
}
