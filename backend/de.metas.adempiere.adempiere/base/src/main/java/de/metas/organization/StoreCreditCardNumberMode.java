package de.metas.organization;

import java.util.Arrays;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_AD_OrgInfo;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

public enum StoreCreditCardNumberMode
{
	STORE(X_AD_OrgInfo.STORECREDITCARDDATA_Speichern), //
	DONT_STORE(X_AD_OrgInfo.STORECREDITCARDDATA_NichtSpeichern), //
	LAST_4_DIGITS(X_AD_OrgInfo.STORECREDITCARDDATA_Letzte4Stellen) //
	;

	@Getter
	private String code;

	StoreCreditCardNumberMode(@NonNull final String code)
	{
		this.code = code;
	}

	public static StoreCreditCardNumberMode ofCode(@NonNull final String code)
	{
		StoreCreditCardNumberMode type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + StoreCreditCardNumberMode.class + " found for code: " + code);
		}
		return type;
	}

	private static final ImmutableMap<String, StoreCreditCardNumberMode> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), StoreCreditCardNumberMode::getCode);
}
