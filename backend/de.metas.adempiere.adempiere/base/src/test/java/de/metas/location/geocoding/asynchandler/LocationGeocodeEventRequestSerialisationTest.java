/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

package de.metas.location.geocoding.asynchandler;

import de.metas.location.LocationId;
import de.metas.util.JSONObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LocationGeocodeEventRequestSerialisationTest
{

	@Test
	void testSerialisationDeserialisation()
	{
		final JSONObjectMapper<LocationGeocodeEventRequest> jsonObjectMapper = JSONObjectMapper.forClass(LocationGeocodeEventRequest.class);

		final String json = jsonObjectMapper.writeValueAsString(LocationGeocodeEventRequest.of(LocationId.ofRepoId(6)));
		final LocationGeocodeEventRequest deserialisedRequest = jsonObjectMapper.readValue(json);
		assertThat(deserialisedRequest).isEqualTo(LocationGeocodeEventRequest.of(LocationId.ofRepoId(6)));
	}

}
