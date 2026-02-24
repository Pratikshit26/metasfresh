package org.compiere.report.core;

 


import java.util.List;

public interface IRModelAggregatedValue
{
	/**
	 * Reset to initial value
	 */
	void reset();

	/**
	 * Aggregate given column value
	 */
	void add(RModelCalculationContext calculationCtx, List<Object> row, Object columnValue);

	Object getAggregatedValue(final RModelCalculationContext calculationCtx, final List<Object> groupRow);

}
