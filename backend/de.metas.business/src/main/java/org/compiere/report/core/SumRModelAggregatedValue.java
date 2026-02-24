package org.compiere.report.core;

 


import java.math.BigDecimal;
import java.util.List;

public class SumRModelAggregatedValue extends AbstractRModelAggregatedValue
{
	private BigDecimal currentValue = BigDecimal.ZERO;

	@Override
	public void reset()
	{
		currentValue = BigDecimal.ZERO;
	}

	@Override
	public void add(final RModelCalculationContext calculationCtx, final List<Object> row, final Object columnValue)
	{
		final BigDecimal valueToAddBD = toBigDecimalOrZero(columnValue);
		currentValue = currentValue.add(valueToAddBD);
	}

	@Override
	public Object getAggregatedValue(final RModelCalculationContext calculationCtx, final List<Object> groupRow)
	{
		return currentValue;
	}
}
