package de.metas.ui.web.view;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.Values;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import de.metas.ui.web.window.datatypes.json.JSONNullValue;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.util.NumberUtils;
import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.math.BigDecimal;

/*
 * #%L
 * metasfresh-webui-api
     
 * #L%
 */

@ToString
@EqualsAndHashCode
public final class ViewRowFieldNameAndJsonValues
{
	public static ViewRowFieldNameAndJsonValues ofMap(@NonNull final ImmutableMap<String, Object> map)
	{
		if (map.isEmpty())
		{
			return EMPTY;
		}

		return new ViewRowFieldNameAndJsonValues(map);
	}

	private static final ViewRowFieldNameAndJsonValues EMPTY = new ViewRowFieldNameAndJsonValues();

	private final ImmutableMap<String, Object> map;

	private ViewRowFieldNameAndJsonValues(@NonNull final ImmutableMap<String, Object> map)
	{
		this.map = map;
	}

	private ViewRowFieldNameAndJsonValues()
	{
		map = ImmutableMap.of();
	}

	public ImmutableSet<String> getFieldNames()
	{
		return map.keySet();
	}

	public Comparable<?> getAsComparable(
			@NonNull final String fieldName,
			@NonNull final JSONOptions jsonOpts)
	{
		final Object valueObj = map.get(fieldName);

		if (JSONNullValue.isNull(valueObj))
		{
			return null;
		}
		else if (valueObj instanceof ITranslatableString)
		{
			return ((ITranslatableString)valueObj).translate(jsonOpts.getAdLanguage());
		}
		else if (valueObj instanceof Comparable)
		{
			return (Comparable<?>)valueObj;
		}
		else
		{
			return valueObj.toString();
		}
	}

	public Object getAsJsonObject(
			@NonNull final String fieldName,
			@NonNull final JSONOptions jsonOpts)
	{
		final Object valueObj = map.get(fieldName);

		if (JSONNullValue.isNull(valueObj))
		{
			return null;
		}
		else if (valueObj instanceof ITranslatableString)
		{
			return ((ITranslatableString)valueObj).translate(jsonOpts.getAdLanguage());
		}
		else
		{
			return Values.valueToJsonObject(valueObj, jsonOpts);
		}
	}

	public int getAsInt(@NonNull final String fieldName, final int defaultValueIfNotFoundOrError)
	{
		final Object valueObj = map.get(fieldName);

		if (JSONNullValue.toNullIfInstance(valueObj) == null)
		{
			return defaultValueIfNotFoundOrError;
		}
		else if (valueObj instanceof Number)
		{
			return ((Number)valueObj).intValue();
		}
		else if (valueObj instanceof LookupValue)
		{
			return ((LookupValue)valueObj).getIdAsInt();
		}
		else if (valueObj instanceof JSONLookupValue)
		{
			return ((JSONLookupValue)valueObj).getKeyAsInt();
		}
		else
		{
			return NumberUtils.asInt(valueObj, defaultValueIfNotFoundOrError);
		}
	}

	public BigDecimal getAsBigDecimal(@NonNull final String fieldName, final BigDecimal defaultValueIfNotFoundOrError)
	{
		final Object valueObj = map.get(fieldName);

		if (JSONNullValue.isNull(valueObj))
		{
			return defaultValueIfNotFoundOrError;
		}
		else
		{
			return NumberUtils.asBigDecimal(valueObj, defaultValueIfNotFoundOrError);
		}
	}

	public boolean getAsBoolean(@NonNull final String fieldName, final boolean defaultValueIfNotFoundOrError)
	{
		final Object valueObj = map.get(fieldName);
		return StringUtils.toBoolean(valueObj, defaultValueIfNotFoundOrError);
	}

	public boolean isEmpty(@NonNull final String fieldName)
	{
		final Object valueObj = map.get(fieldName);
		return valueObj == null || JSONNullValue.isNull(valueObj) || valueObj.toString().isEmpty();
	}
}
