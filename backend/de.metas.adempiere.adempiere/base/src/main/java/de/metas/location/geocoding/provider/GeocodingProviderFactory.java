/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

package de.metas.location.geocoding.provider;

import java.util.Optional;

import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import com.google.maps.GeoApiContext;

import de.metas.cache.CCache;
import de.metas.cache.CCache.CacheMapType;
import de.metas.location.geocoding.GeocodingConfig;
import de.metas.location.geocoding.GeocodingConfig.GoogleMapsConfig;
import de.metas.location.geocoding.GeocodingConfig.OpenStreetMapsConfig;
import de.metas.location.geocoding.GeocodingConfigRepository;
import de.metas.location.geocoding.GeocodingProvider;
import de.metas.location.geocoding.provider.googlemaps.GoogleMapsGeocodingProviderImpl;
import de.metas.location.geocoding.provider.openstreetmap.NominatimOSMGeocodingProviderImpl;
import lombok.NonNull;

@Service
public class GeocodingProviderFactory
{
	private final GeocodingConfigRepository configRepository;

	private final CCache<GeocodingConfig, GeocodingProvider> providers = CCache.<GeocodingConfig, GeocodingProvider>builder()
			.cacheMapType(CacheMapType.LRU)
			.initialCapacity(10)
			.build();

	public GeocodingProviderFactory(
			@NonNull final GeocodingConfigRepository configRepository)
	{
		this.configRepository = configRepository;
	}

	public Optional<GeocodingProvider> getProvider()
	{
		final GeocodingConfig config = configRepository.getGeocodingConfig().orElse(null);
		if (config == null)
		{
			return Optional.empty();
		}

		final GeocodingProvider provider = providers.getOrLoad(config, this::createProvider);
		return Optional.of(provider);
	}

	private GeocodingProvider createProvider(@NonNull final GeocodingConfig config)
	{
		final GeocodingProviderName providerName = config.getProviderName();
		if (GeocodingProviderName.GOOGLE_MAPS.equals(providerName))
		{
			return createGoogleMapsProvider(config.getGoogleMapsConfig());
		}
		else if (GeocodingProviderName.OPEN_STREET_MAPS.equals(providerName))
		{
			return createOSMProvider(config.getOpenStreetMapsConfig());
		}
		else
		{
			throw new AdempiereException("Unknown provider: " + providerName);
		}
	}

	@NonNull private GoogleMapsGeocodingProviderImpl createGoogleMapsProvider(@NonNull final GoogleMapsConfig config)
	{
		final String apiKey = config.getApiKey();
		final int cacheCapacity = config.getCacheCapacity();

		final GeoApiContext context = new GeoApiContext.Builder()
				.apiKey(apiKey)
				.build();

		return new GoogleMapsGeocodingProviderImpl(context, cacheCapacity);
	}

	@NonNull private NominatimOSMGeocodingProviderImpl createOSMProvider(@NonNull final OpenStreetMapsConfig config)
	{
		final String baseURL = config.getBaseURL();
		final int cacheCapacity = config.getCacheCapacity();
		final long millisBetweenRequests = config.getMillisBetweenRequests();
		return new NominatimOSMGeocodingProviderImpl(baseURL, millisBetweenRequests, cacheCapacity);
	}
}
