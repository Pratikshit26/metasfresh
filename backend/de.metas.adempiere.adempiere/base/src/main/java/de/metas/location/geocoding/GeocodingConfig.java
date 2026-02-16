/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

package de.metas.location.geocoding;

import de.metas.location.geocoding.provider.GeocodingProviderName;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GeocodingConfig
{
	@NonNull
	GeocodingProviderName providerName;

	OpenStreetMapsConfig openStreetMapsConfig;
	GoogleMapsConfig googleMapsConfig;

	@Value
	@Builder
	public static class OpenStreetMapsConfig
	{
		@NonNull final String baseURL;
		int cacheCapacity;
		long millisBetweenRequests;
	}

	@Value
	@Builder
	public static class GoogleMapsConfig
	{
		@NonNull
		String apiKey;
		int cacheCapacity;
	}

}
