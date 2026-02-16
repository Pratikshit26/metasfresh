package de.metas.material.cockpit.availableforsales;

import de.metas.material.event.commons.AttributesKey;
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
/** Similar to {@code AvailableForSalesResult}, but without a reference to the originating query
 *
 */
public class AvailableForSalesLookupBucketResult
{
	int queryNo;

	ProductId productId;

	@NonNull
	AttributesKey storageAttributesKey;

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
