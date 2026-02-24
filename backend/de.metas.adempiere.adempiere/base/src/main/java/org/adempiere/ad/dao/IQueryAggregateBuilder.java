package org.adempiere.ad.dao;

 


import java.math.BigDecimal;
import java.util.List;

import org.adempiere.ad.dao.impl.SumQueryAggregateColumnBuilder;
import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.adempiere.model.ModelColumn;

/**
 * Aggregates a list of source models, based on a given model column.
 *
 * As a result you will get the list of models from model column. Each of those models (target models) will have set dynamic attributes for the aggregated values that we calculated along.
 *
 * @author tsa
 *
 * @param <SourceModelType>
 * @param <TargetModelType>
 */
public interface IQueryAggregateBuilder<SourceModelType, TargetModelType>
{
	/**
	 * Aggregate source models and return resulting target models.
	 *
	 * Please note that resulting target models will have the aggregated values as dynamic attributes.
	 *
	 * @return target/aggregated models
	 */
	List<TargetModelType> aggregate();

	/**
	 * Creates an COUNT-IF aggregations.
	 *
	 * e.g. We use this aggregation when we want to count how many source models where wich matched the filter that we specified in the COUNT-IF aggregation.
	 *
	 * @param dynAttribute dynamic attribute which we will set to retrieved target models, and which will contain the value of this aggregation
	 * @return COUNT-IF aggregation
	 */
	IQueryAggregateColumnBuilder<SourceModelType, TargetModelType, Integer> countIf(ModelDynAttributeAccessor<TargetModelType, Integer> dynAttribute);

	/**
	 * Create a SUM aggregation.
	 *
	 * @param dynAttribute dynamic attribute which we will set to retrieved target models, and which will contain the value of this aggregation
	 * @param amountColumn
	 * @return SUM aggregation
	 */
	SumQueryAggregateColumnBuilder<SourceModelType, TargetModelType> sum(ModelDynAttributeAccessor<TargetModelType, BigDecimal> dynAttribute, ModelColumn<SourceModelType, ?> amountColumn);
}
