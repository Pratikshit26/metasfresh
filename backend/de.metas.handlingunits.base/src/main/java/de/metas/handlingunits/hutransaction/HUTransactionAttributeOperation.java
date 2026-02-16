package de.metas.handlingunits.hutransaction;

import java.util.Arrays;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.handlingunits.model.X_M_HU_Trx_Attribute;
import de.metas.order.DeliveryRule;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
     
 * #L%
 */

public enum HUTransactionAttributeOperation implements ReferenceListAwareEnum
{
	SAVE(X_M_HU_Trx_Attribute.OPERATION_Save), //
	DROP(X_M_HU_Trx_Attribute.OPERATION_Drop) //
	;

	@Getter
	private final String code;

	HUTransactionAttributeOperation(@NonNull final String code)
	{
		this.code = code;
	}

	public static HUTransactionAttributeOperation ofCode(@NonNull final String code)
	{
		final HUTransactionAttributeOperation type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + HUTransactionAttributeOperation.class + " found for code: " + code);
		}
		return type;
	}

	private static final ImmutableMap<String, HUTransactionAttributeOperation> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), HUTransactionAttributeOperation::getCode);

	public static String toCodeOrNull(final DeliveryRule type)
	{
		return type != null ? type.getCode() : null;
	}
}
