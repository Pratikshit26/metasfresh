package org.compiere.report.core;

 


import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.Env;

public abstract class AbstractRModelAggregatedValue implements IRModelAggregatedValue
{
	private final transient Logger logger = LogManager.getLogger(getClass());

	protected final Properties getCtx()
	{
		return Env.getCtx();
	}

	/**
	 * 
	 * @param valueObj
	 * @return {@link BigDecimal} value or {@link BigDecimal#ZERO} if the value is null or cannot be converted
	 */
	protected final BigDecimal toBigDecimalOrZero(final Object valueObj)
	{
		if (valueObj == null)
		{
			return BigDecimal.ZERO;
		}
		else if (valueObj instanceof BigDecimal)
		{
			return (BigDecimal)valueObj;
		}
		else
		{
			try
			{
				return new BigDecimal(valueObj.toString());
			}
			catch (Exception e)
			{
				logger.warn("Failed converting " + valueObj
						+ " (class " + valueObj.getClass() + ")"
						+ " to BigDecimal. Returning ZERO.", e);
			}

			return BigDecimal.ZERO;
		}
	}

	protected final boolean setRowValueOrNull(final IRModelMetadata metadata, final List<Object> row, final String columnName, final Object value)
	{
		final int columnIndex = metadata.getRColumnIndex(columnName);
		if (columnIndex < 0)
		{
			return false;
		}
		if (columnIndex >= row.size())
		{
			return false;
		}

		row.set(columnIndex, value);
		return true;
	}

	protected final Object getRowValueOrNull(final IRModelMetadata metadata, final List<Object> row, final String columnName)
	{
		if (row == null)
		{
			return null;
		}

		final int columnIndex = metadata.getRColumnIndex(columnName);
		if (columnIndex < 0)
		{
			return null;
		}
		if (columnIndex >= row.size())
		{
			return null;
		}

		final Object value = row.get(columnIndex);
		return value;
	}

	protected final <T> T getRowValueOrNull(final IRModelMetadata metadata, final List<Object> row, final String columnName, final Class<T> valueClass)
	{
		final Object valueObj = getRowValueOrNull(metadata, row, columnName);
		if (valueObj == null)
		{
			return null;
		}

		if (!valueClass.isAssignableFrom(valueObj.getClass()))
		{
			return null;
		}

		final T value = valueClass.cast(valueObj);
		return value;
	}

	/**
	 * 
	 * @param calculationCtx
	 * @param columnName
	 * @return true if the status of current calculation is about calculating the row for "columnName" group (subtotals)
	 */
	protected final boolean isGroupBy(final RModelCalculationContext calculationCtx, final String columnName)
	{
		final int groupColumnIndex = calculationCtx.getGroupColumnIndex();
		if (groupColumnIndex < 0)
		{
			// no grouping
			return false;
		}

		final IRModelMetadata metadata = calculationCtx.getMetadata();
		final int columnIndex = metadata.getRColumnIndex(columnName);
		if (columnIndex < 0)
		{
			return false;
		}

		return columnIndex == groupColumnIndex;
	}
}
