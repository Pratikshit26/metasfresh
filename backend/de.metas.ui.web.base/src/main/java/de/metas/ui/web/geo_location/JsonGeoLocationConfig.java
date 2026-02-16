/*
 * #%L
 * metasfresh-webui-api
     
 * #L%
 */

package de.metas.ui.web.geo_location;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
class JsonGeoLocationConfig
{
	@JsonProperty("provider")
	@Nullable
	private final JsonGeoLocationProvider provider;

	@JsonProperty("googleMapsApiKey")
	@Nullable
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String googleMapsApiKey;

}
