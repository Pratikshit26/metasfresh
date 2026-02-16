package de.metas.rest_api.v1.ordercandidates.impl;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.pricing.PricingSystemId;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

/*
 * #%L
 * de.metas.business.rest-api-impl
     
 * #L%
 */

@Value
@Builder
public class ProductPriceCreateRequest
{
	@NonNull
	BPartnerLocationId bpartnerAndLocationId;

	/** If null, the system will attempt to get the pricing system id from the bartner's mater data. */
	@Nullable
	final PricingSystemId pricingSystemId;

	@NonNull
	ZonedDateTime date;

	@NonNull
	ProductId productId;

	@NonNull
	UomId uomId;

	@NonNull
	BigDecimal priceStd;
}
