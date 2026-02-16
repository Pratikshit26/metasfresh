package de.metas.bpartner.product.stats;

import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableMap;

import de.metas.bpartner.BPartnerId;
import de.metas.money.CurrencyId;
import de.metas.product.ProductId;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */

@Service
public class BPartnerProductStatsService
{
	private final BPartnerProductStatsRepository statsRepo;

	public BPartnerProductStatsService(
			@NonNull final BPartnerProductStatsRepository statsRepo)
	{
		this.statsRepo = statsRepo;
	}

	public ImmutableMap<ProductId, BPartnerProductStats> getByPartnerAndProducts(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final Set<ProductId> productIds)
	{
		return statsRepo.getByPartnerAndProducts(bpartnerId, productIds);
	}

	public List<BPartnerProductStats> getByProductIds(@NonNull final Set<ProductId> productIds, @Nullable final CurrencyId currencyId)
	{
		return statsRepo.getByProductIds(productIds, currencyId);
	}
}
