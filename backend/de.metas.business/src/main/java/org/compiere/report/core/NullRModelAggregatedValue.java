package org.compiere.report.core;

 


import java.util.List;

public final class NullRModelAggregatedValue implements IRModelAggregatedValue
{
	public static final transient NullRModelAggregatedValue instance = new NullRModelAggregatedValue();

	private NullRModelAggregatedValue()
	{
		super();
	};

	@Override
	public void reset()
	{
		// nothing
	}

	@Override
	public void add(final RModelCalculationContext calculationCtx, final List<Object> row, final Object columnValue)
	{
		// nothing
	}

	@Override
	public Object getAggregatedValue(RModelCalculationContext calculationCtx, List<Object> groupRow)
	{
		return null;
	}

}
