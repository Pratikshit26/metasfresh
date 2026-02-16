package de.metas.impexp.format;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_AD_ImpFormat;

import java.util.Arrays;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

public enum ImpFormatType implements ReferenceListAwareEnum
{
	FIXED_POSITION(X_AD_ImpFormat.FORMATTYPE_FixedPosition, "text/plain", ".txt"), //
	COMMA_SEPARATED(X_AD_ImpFormat.FORMATTYPE_CommaSeparated, "text/csv", ".csv"), //
	SEMICOLON_SEPARATED(X_AD_ImpFormat.FORMATTYPE_SemicolonSeparated, "text/csv", ".csv"), //
	TAB_SEPARATED(X_AD_ImpFormat.FORMATTYPE_TabSeparated, "text/tab-separated-values", ".tsv"), //
	XML(X_AD_ImpFormat.FORMATTYPE_XML, "text/xml", ".xml") //
	;

	private final String code;
	@Getter private final String contentType;
	@Getter private final String fileExtensionIncludingDot;

	ImpFormatType(
			@NonNull final String code,
			@NonNull final String contentType,
			@NonNull final String fileExtensionIncludingDot)
	{
		this.code = code;
		this.contentType = contentType;
		this.fileExtensionIncludingDot = fileExtensionIncludingDot;
	}

	@Override
	@JsonValue
	public String getCode()
	{
		return code;
	}

	public char getCellDelimiterChar()
	{
		switch (this)
		{
			case COMMA_SEPARATED:
				return ',';
			case SEMICOLON_SEPARATED:
				return ';';
			case TAB_SEPARATED:
				return '\t';
			default:
				throw new AdempiereException("Cannot find delimiter for " + this);
		}
	}

	@JsonCreator
	public static ImpFormatType ofCode(@NonNull final String code)
	{
		ImpFormatType type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + ImpFormatType.class + " found for code: " + code);
		}
		return type;
	}

	private static final ImmutableMap<String, ImpFormatType> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), ImpFormatType::getCode);
}
