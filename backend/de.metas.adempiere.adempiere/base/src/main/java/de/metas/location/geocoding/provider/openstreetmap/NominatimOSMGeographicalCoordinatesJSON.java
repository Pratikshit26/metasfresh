/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

package de.metas.location.geocoding.provider.openstreetmap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonDeserialize(builder = NominatimOSMGeographicalCoordinatesJSON.NominatimOSMGeographicalCoordinatesJSONBuilder.class)
class NominatimOSMGeographicalCoordinatesJSON
{
	private String display_name;
	private String lat;
	private String lon;

	@JsonIgnoreProperties(ignoreUnknown = true) // the annotation to ignore properties should be set on the deserializer method (on the builder), and not on the base class
	@JsonPOJOBuilder(withPrefix = "")
	public static class NominatimOSMGeographicalCoordinatesJSONBuilder
	{
	}
}
