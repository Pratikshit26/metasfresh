package org.compiere.report.core;

 


import java.math.BigDecimal;
import java.util.List;

public class CountRModelAggregatedValue extends AbstractRModelAggregatedValue
{
	private int counter = 0;

	@Override
	public void reset()
	{
		counter = 0;
	}

	@Override
	public void add(final RModelCalculationContext calculationCtx, final List<Object> row, final Object columnValue)
	{
		counter++;
	}

	@Override
	public Object getAggregatedValue(final RModelCalculationContext calculationCtx, final List<Object> groupRow)
	{
		return BigDecimal.valueOf(counter);
	}

}
