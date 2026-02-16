package de.metas.handlingunits.generichumodel;

import static de.metas.util.Check.isEmpty;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableMap;

import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
     
 * #L%
 */

/** Please keep in sync with {@link X_M_HU_PI_Version#HU_UNITTYPE_AD_Reference_ID}. */
public enum HUType implements ReferenceListAwareEnum
{
	TransportUnit(X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit),

	LoadLogistiqueUnit(X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit),

	VirtualPI(X_M_HU_PI_Version.HU_UNITTYPE_VirtualPI);

	@Getter
	private String code;

	private HUType(@NonNull final String code)
	{
		this.code = code;
	}

	public static HUType ofCodeOrNull(@Nullable final String code)
	{
		if (isEmpty(code, true))
		{
			return null;
		}
		return ofCode(code);
	}

	public static HUType ofCode(@NonNull final String code)
	{
		HUType type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + HUType.class + " found for code: " + code);
		}
		return type;
	}

	private static final ImmutableMap<String, HUType> typesByCode = ReferenceListAwareEnums.indexByCode(values());

	public boolean isLU()
	{
		return this == LoadLogistiqueUnit;
	}

	public boolean isTU()
	{
		return this == TransportUnit;
	}

	public boolean isVHU()
	{
		return this == VirtualPI;
	}
}
