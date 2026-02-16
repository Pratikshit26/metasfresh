/*
 * #%L
 * de.metas.shipper.gateway.dhl
     
 * #L%
 */

package de.metas.shipper.gateway.dhl.model;

import de.metas.currency.Amount;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class DhlCustomsItem
{
	@NonNull String itemDescription;
	int packagedQuantity;
	@NonNull Amount itemValue;
	@NonNull BigDecimal weightInKg;
}
