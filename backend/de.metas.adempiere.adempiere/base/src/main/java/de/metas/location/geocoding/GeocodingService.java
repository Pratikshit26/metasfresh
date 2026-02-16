package de.metas.location.geocoding;

import de.metas.location.geocoding.provider.GeocodingProviderFactory;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@Service
@RequiredArgsConstructor
public class GeocodingService
{
	@NonNull private final GeocodingProviderFactory providersFactory;

	public Optional<GeographicalCoordinates> findBestCoordinates(@NonNull final GeoCoordinatesRequest request)
	{
		final GeocodingProvider provider = getProvider();
		return provider.findBestCoordinates(request);
	}

	@NonNull
	private GeocodingProvider getProvider()
	{
		return providersFactory.getProvider().orElseThrow(() -> new AdempiereException("No Provider Selected"));
	}

	public boolean isProviderConfigured()
	{
		return providersFactory.getProvider().isPresent();
	}
}
