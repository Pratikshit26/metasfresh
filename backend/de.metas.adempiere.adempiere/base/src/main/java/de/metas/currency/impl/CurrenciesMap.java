package de.metas.currency.impl;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyCode;
import de.metas.money.CurrencyId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.List;
import java.util.Optional;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

final class CurrenciesMap
{
	private final ImmutableMap<CurrencyId, Currency> currenciesById;
	private final ImmutableMap<CurrencyCode, Currency> currenciesByCode;

	public CurrenciesMap(@NonNull final List<Currency> currencies)
	{
		currenciesById = Maps.uniqueIndex(currencies, Currency::getId);
		currenciesByCode = Maps.uniqueIndex(currencies, Currency::getCurrencyCode);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("count", currenciesById.size())
				.toString();
	}

	@NonNull
	public Currency getById(@NonNull final CurrencyId id)
	{
		final Currency currency = currenciesById.get(id);
		if (currency == null)
		{
			throw new AdempiereException("@NotFound@ @C_Currency_ID@: " + id);
		}
		return currency;
	}

	public Currency getByCurrencyCode(@NonNull final CurrencyCode currencyCode)
	{
		final Currency currency = currenciesByCode.get(currencyCode);
		if (currency == null)
		{
			throw new AdempiereException("@NotFound@ @C_Currency_ID@ @ISO_Code@: " + currencyCode);
		}
		return currency;
	}

	public Optional<Currency> getByCurrencyCodeIfExists(@NonNull final CurrencyCode currencyCode)
	{
		return Optional.ofNullable(currenciesByCode.get(currencyCode));
	}

}
