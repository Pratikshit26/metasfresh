package de.metas.currency.impl;

import de.metas.money.CurrencyConversionTypeId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.service.ClientId;

import java.time.Instant;
import java.util.Comparator;
import java.util.Objects;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@Value
@Builder
class CurrencyConversionTypeRouting
{
	@NonNull
	ClientId clientId;

	@NonNull
	OrgId orgId;

	@NonNull
	Instant validFrom;

	@NonNull
	CurrencyConversionTypeId conversionTypeId;

	public boolean isMatching(
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId,
			@NonNull final Instant date)
	{
		return (this.clientId.isSystem() || ClientId.equals(this.clientId, clientId))
				&& (this.orgId.isAny() || OrgId.equals(this.orgId, orgId))
				&& this.validFrom.compareTo(date) <= 0;
	}

	public static Comparator<CurrencyConversionTypeRouting> moreSpecificFirstComparator()
	{
		return (routing1, routing2) -> {
			if (Objects.equals(routing1, routing2))
			{
				return 0;
			}
			else if (routing1.isMoreSpecificThan(routing2))
			{
				return -1;
			}
			else
			{
				return +1;
			}
		};
	}

	public boolean isMoreSpecificThan(@NonNull final CurrencyConversionTypeRouting other)
	{
		if (!ClientId.equals(this.getClientId(), other.getClientId()))
		{
			return this.getClientId().isRegular();
		}

		if (!OrgId.equals(this.getOrgId(), other.getOrgId()))
		{
			return this.getOrgId().isRegular();
		}

		return this.getValidFrom().compareTo(other.getValidFrom()) > 0;
	}
}
