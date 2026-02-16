/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

package de.metas.location.geocoding;

import de.metas.cache.CCache;
import de.metas.location.geocoding.GeocodingConfig.GoogleMapsConfig;
import de.metas.location.geocoding.GeocodingConfig.OpenStreetMapsConfig;
import de.metas.location.geocoding.provider.GeocodingProviderName;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_GeocodingConfig;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.Optional;

@Repository
public class GeocodingConfigRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, Optional<GeocodingConfig>> cache = CCache.<Integer, Optional<GeocodingConfig>>builder()
			.tableName(I_GeocodingConfig.Table_Name)
			.build();

	public Optional<GeocodingConfig> getGeocodingConfig()
	{
		return cache.getOrLoad(0, this::retrieveGeocodingConfig);
	}

	private Optional<GeocodingConfig> retrieveGeocodingConfig()
	{
		final I_GeocodingConfig record = queryBL.createQueryBuilderOutOfTrx(I_GeocodingConfig.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnly(I_GeocodingConfig.class);

		return toGeocodingConfig(record);
	}

	private static Optional<GeocodingConfig> toGeocodingConfig(@Nullable final I_GeocodingConfig record)
	{
		if (record == null)
		{
			return Optional.empty();
		}

		final GeocodingProviderName providerName = GeocodingProviderName.ofNullableCode(record.getGeocodingProvider());

		final GoogleMapsConfig googleMapsConfig;
		final OpenStreetMapsConfig openStreetMapsConfig;
		if (providerName == null)
		{
			return Optional.empty();
		}
		else if (GeocodingProviderName.GOOGLE_MAPS.equals(providerName))
		{
			googleMapsConfig = GoogleMapsConfig.builder()
					.apiKey(record.getgmaps_ApiKey())
					.cacheCapacity(record.getcacheCapacity())
					.build();
			openStreetMapsConfig = null;

		}
		else if (GeocodingProviderName.OPEN_STREET_MAPS.equals(providerName))
		{
			googleMapsConfig = null;
			openStreetMapsConfig = OpenStreetMapsConfig.builder()
					.baseURL(record.getosm_baseURL())
					.millisBetweenRequests(record.getosm_millisBetweenRequests())
					.cacheCapacity(record.getcacheCapacity())
					.build();
		}
		else
		{
			throw new AdempiereException("Unknown provider: " + providerName);
		}

		final GeocodingConfig geocodingConfig = GeocodingConfig.builder()
				.providerName(providerName)
				.googleMapsConfig(googleMapsConfig)
				.openStreetMapsConfig(openStreetMapsConfig)
				.build();
		return Optional.of(geocodingConfig);
	}
}
