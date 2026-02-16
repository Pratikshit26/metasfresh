package de.metas.inventory;

import java.util.Objects;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableMap;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */

/**
 * NOTE to developers: Please keep in sync with list reference "HUAggregationType" {@code AD_Reference_ID=540976}
 */

public enum HUAggregationType implements ReferenceListAwareEnum
{
	SINGLE_HU("S"), //
	MULTI_HU("M") //
	;

	@Getter
	private final String code;

	HUAggregationType(final String code)
	{
		this.code = code;
	}

	public static HUAggregationType ofCode(@NonNull final String code)
	{
		final HUAggregationType type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + HUAggregationType.class + " found for code: " + code);
		}
		return type;
	}

	public static HUAggregationType ofNullableCode(@Nullable final String code)
	{
		return code != null ? ofCode(code) : null;
	}

	public static String toCodeOrNull(@Nullable final HUAggregationType type)
	{
		return type != null ? type.getCode() : null;
	}

	private static final ImmutableMap<String, HUAggregationType> typesByCode = ReferenceListAwareEnums.indexByCode(values());

	public static boolean equals(@Nullable final HUAggregationType o1, @Nullable final HUAggregationType o2)
	{
		return Objects.equals(o1, o2);
	}

}
