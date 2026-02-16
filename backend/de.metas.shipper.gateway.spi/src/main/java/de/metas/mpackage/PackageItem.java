/*
 * #%L
 * de.metas.swat.base
     
 * #L%
 */

package de.metas.mpackage;

import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class PackageItem
{
	@NonNull ProductId productId;
	@NonNull Quantity quantity;
	@NonNull OrderLineId orderLineId;
}
