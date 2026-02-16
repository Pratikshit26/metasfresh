package de.metas.product;

import de.metas.product.model.X_M_Product_PlanningSchema;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */

@Getter
@RequiredArgsConstructor
public enum OnMaterialReceiptWithDestWarehouse implements ReferenceListAwareEnum
{
	CREATE_MOVEMENT(X_M_Product_PlanningSchema.ONMATERIALRECEIPTWITHDESTWAREHOUSE_CreateMovement), //
	CREATE_DISTRIBUTION_ORDER(X_M_Product_PlanningSchema.ONMATERIALRECEIPTWITHDESTWAREHOUSE_CreateDistributionOrder) //
	;

	private static final ReferenceListAwareEnums.ValuesIndex<OnMaterialReceiptWithDestWarehouse> index = ReferenceListAwareEnums.index(values());

	@NonNull private final String code;

	public static OnMaterialReceiptWithDestWarehouse ofCode(@NonNull final String code) {return index.ofCode(code);}

	@Nullable
	public static OnMaterialReceiptWithDestWarehouse ofNullableCode(@Nullable final String code) {return index.ofNullableCode(code);}

}
