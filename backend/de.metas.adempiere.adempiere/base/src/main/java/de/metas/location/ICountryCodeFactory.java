/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

package de.metas.location;

import de.metas.util.ISingletonService;
import lombok.NonNull;

public interface ICountryCodeFactory extends ISingletonService
{
	CountryCode getCountryCodeByAlpha2(@NonNull final String countryCodeAlpha2);
}
