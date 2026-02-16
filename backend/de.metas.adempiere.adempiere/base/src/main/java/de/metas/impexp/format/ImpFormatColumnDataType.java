package de.metas.impexp.format;

import java.util.Arrays;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_AD_ImpFormat_Row;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

public enum ImpFormatColumnDataType implements ReferenceListAwareEnum
{
	String(X_AD_ImpFormat_Row.DATATYPE_String), //
	Date(X_AD_ImpFormat_Row.DATATYPE_Date), //
	Number(X_AD_ImpFormat_Row.DATATYPE_Number), //
	Constant(X_AD_ImpFormat_Row.DATATYPE_Constant), //
	YesNo(X_AD_ImpFormat_Row.DATATYPE_YesNo)
	;

	private final String code;

	private ImpFormatColumnDataType(@NonNull final String code)
	{
		this.code = code;
	}

	@Override
	@JsonValue
	public String getCode()
	{
		return code;
	}

	@JsonCreator
	public static ImpFormatColumnDataType ofCode(@NonNull final String code)
	{
		ImpFormatColumnDataType type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + ImpFormatColumnDataType.class + " found for code: " + code);
		}
		return type;
	}

	private static final ImmutableMap<String, ImpFormatColumnDataType> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), ImpFormatColumnDataType::getCode);
}
