package de.metas.order;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_C_Order;

import javax.annotation.Nullable;
import java.util.Arrays;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */

public enum DeliveryViaRule implements ReferenceListAwareEnum
{
	Pickup(X_C_Order.DELIVERYVIARULE_Pickup), //
	Delivery(X_C_Order.DELIVERYVIARULE_Delivery), //
	Shipper(X_C_Order.DELIVERYVIARULE_Shipper),
	NormalPost(X_C_Order.DELIVERYVIARULE_Normalpost),
	LuftPost(X_C_Order.DELIVERYVIARULE_Luftpost)//
	;

	@Getter
	private final String code;

	DeliveryViaRule(@NonNull final String code)
	{
		this.code = code;
	}

	@Nullable
	public static DeliveryViaRule ofNullableCode(@Nullable final String code)
	{
		final DeliveryViaRule defaultWhenNull = null;
		return ofNullableCodeOr(code, defaultWhenNull);
	}

	@Nullable
	public static DeliveryViaRule ofNullableCodeOr(@Nullable final String code, @Nullable final DeliveryViaRule defaultWhenNull)
	{
		return code != null ? ofCode(code) : defaultWhenNull;
	}

	public static DeliveryViaRule ofCode(@NonNull final String code)
	{
		final DeliveryViaRule type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + DeliveryViaRule.class + " found for code: " + code);
		}
		return type;
	}

	private static final ImmutableMap<String, DeliveryViaRule> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), DeliveryViaRule::getCode);

	public boolean isShipper()
	{
		return this == Shipper;
	}

	@Nullable
	public static String toCodeOrNull(@Nullable final DeliveryViaRule type)
	{
		return type != null ? type.getCode() : null;
	}
}
