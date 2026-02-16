package org.eevolution.api;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import de.metas.util.lang.ReferenceListAwareEnums.ValuesIndex;
import lombok.Getter;
import lombok.NonNull;
import org.eevolution.model.X_PP_Product_BOM;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */

public enum BOMType implements ReferenceListAwareEnum
{
	CurrentActive(X_PP_Product_BOM.BOMTYPE_CurrentActive),
	MakeToOrder(X_PP_Product_BOM.BOMTYPE_Make_To_Order),
	// Previous(X_PP_Product_BOM.BOMTYPE_Previous),
	PreviousSpare(X_PP_Product_BOM.BOMTYPE_PreviousSpare),
	// Future(X_PP_Product_BOM.BOMTYPE_Future),
	// Verwaltung(X_PP_Product_BOM.BOMTYPE_Verwaltung),
	// Repair(X_PP_Product_BOM.BOMTYPE_Repair),
	// ProductConfigure(X_PP_Product_BOM.BOMTYPE_ProductConfigure),
	// MakeToKit(X_PP_Product_BOM.BOMTYPE_Make_To_Kit)
	;

	@Getter
	private final String code;

	BOMType(@NonNull final String code)
	{
		this.code = code;
	}

	public static BOMType ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	@Nullable
	public static BOMType ofNullableCode(@Nullable final String code)
	{
		return index.ofNullableCode(code);
	}

	private static final ValuesIndex<BOMType> index = ReferenceListAwareEnums.index(values());
}
