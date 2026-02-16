package de.metas.material.cockpit.availableforsales;

import de.metas.material.event.commons.AttributesKey;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;

/*
 * #%L
 * metasfresh-available-for-sales
     
 * #L%
 */

@Value
@Builder
public class AvailableForSalesResult
{
	@NonNull
	AvailableForSalesQuery availableForSalesQuery;

	@NonNull
	ProductId productId;

	@NonNull
	AttributesKey storageAttributesKey;

	@NonNull
	OrgId orgId;

	@NonNull
	Quantities quantities;

	@Value
	@Builder
	public static class Quantities
	{
		@NonNull
		BigDecimal qtyOnHandStock;

		@NonNull
		BigDecimal qtyToBeShipped;
	}
}
