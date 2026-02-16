/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

package de.metas.location.geocoding.provider;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_GeocodingConfig;

import com.google.common.collect.ImmutableMap;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;

public enum GeocodingProviderName implements ReferenceListAwareEnum
{
	GOOGLE_MAPS(X_GeocodingConfig.GEOCODINGPROVIDER_GoogleMaps), OPEN_STREET_MAPS(X_GeocodingConfig.GEOCODINGPROVIDER_OpenStreetMaps);

	@Getter
	private final String code;

	GeocodingProviderName(final String providerName)
	{
		this.code = providerName;
	}

	@NonNull public static GeocodingProviderName ofCode(@NonNull final String code)
	{
		final GeocodingProviderName type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + GeocodingProviderName.class + " found for code: " + code);
		}
		return type;
	}

	@Nullable
	public static GeocodingProviderName ofNullableCode(@Nullable final String code)
	{
		return code != null ? ofCode(code) : null;
	}

	private static final ImmutableMap<String, GeocodingProviderName> typesByCode = ReferenceListAwareEnums.indexByCode(values());
}
