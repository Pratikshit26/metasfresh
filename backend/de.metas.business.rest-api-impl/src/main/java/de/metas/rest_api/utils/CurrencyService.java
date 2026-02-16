package de.metas.rest_api.utils;

import javax.annotation.Nullable;

import org.springframework.stereotype.Service;

import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
import de.metas.money.CurrencyId;
import de.metas.util.Check;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.business.rest-api-impl
     
 * #L%
 */

@Service
public class CurrencyService
{
	@Nullable
	public CurrencyId getCurrencyId(@Nullable final String currencyCodeStr)
	{
		if (Check.isEmpty(currencyCodeStr, true))
		{
			return null;
		}

		final CurrencyCode currencyCode = CurrencyCode.ofThreeLetterCode(currencyCodeStr);

		final ICurrencyDAO currenciesRepo = Services.get(ICurrencyDAO.class);
		return currenciesRepo.getByCurrencyCode(currencyCode).getId();
	}
}
