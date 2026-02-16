package de.metas.currency.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.currency.ConversionTypeMethod;
import de.metas.currency.CurrencyConversionType;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;

import java.time.Instant;
import java.util.Collection;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

final class CurrencyConversionTypesMap
{
	private final ImmutableList<CurrencyConversionTypeRouting> routings;
	private final ImmutableMap<CurrencyConversionTypeId, CurrencyConversionType> typesById;
	private final ImmutableMap<ConversionTypeMethod, CurrencyConversionType> typesByMethod;

	@Builder
	private CurrencyConversionTypesMap(
			final Collection<CurrencyConversionTypeRouting> routings,
			final Collection<CurrencyConversionType> types)
	{
		this.routings = ImmutableList.copyOf(routings);
		typesById = Maps.uniqueIndex(types, CurrencyConversionType::getId);
		typesByMethod = Maps.uniqueIndex(types, CurrencyConversionType::getMethod);
	}

	public CurrencyConversionType getById(@NonNull final CurrencyConversionTypeId id)
	{
		final CurrencyConversionType conversionType = typesById.get(id);
		if (conversionType == null)
		{
			throw new AdempiereException("@NotFound@ @C_ConversionType_ID@: " + id);
		}
		return conversionType;
	}

	public CurrencyConversionType getByMethod(@NonNull final ConversionTypeMethod method)
	{
		final CurrencyConversionType conversionType = typesByMethod.get(method);
		if (conversionType == null)
		{
			throw new AdempiereException("@NotFound@ @C_ConversionType_ID@: " + method);
		}
		return conversionType;
	}

	@NonNull
	public CurrencyConversionType getDefaultConversionType(
			@NonNull final ClientId adClientId,
			@NonNull final OrgId adOrgId,
			@NonNull final Instant date)
	{
		final CurrencyConversionTypeRouting bestMatchingRouting = routings.stream()
				.filter(routing -> routing.isMatching(adClientId, adOrgId, date))
				.min(CurrencyConversionTypeRouting.moreSpecificFirstComparator())
				.orElseThrow(() -> new AdempiereException("@NotFound@ @C_ConversionType_ID@")
						.setParameter("adClientId", adClientId)
						.setParameter("adOrgId", adOrgId)
						.setParameter("date", date)
						.appendParametersToMessage());

		return getById(bestMatchingRouting.getConversionTypeId());
	}
}
