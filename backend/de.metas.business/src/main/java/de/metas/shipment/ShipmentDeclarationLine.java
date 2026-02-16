package de.metas.shipment;

import javax.annotation.Nullable;

import de.metas.inout.InOutLineId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */
@Data
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShipmentDeclarationLine
{
	@NonFinal
	ShipmentDeclarationLineId id;

	@NonFinal
	@Setter(AccessLevel.PACKAGE)
	int lineNo;

	@NonNull
	OrgId orgId;

	@NonNull
	InOutLineId shipmentLineId;

	@NonNull
	ProductId productId;

	@NonNull
	Quantity quantity;

	@Nullable
	String packageSize;

	public ShipmentDeclarationLine copyToNew()
	{
		return toBuilder()
				.id(null)
				.build();
	}
}
