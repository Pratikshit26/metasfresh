package org.adempiere.ad.trx.processor.api.impl;

 


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Ignore;

@Ignore
public class ItemProcessorResult
{
	private final List<AggregatedItem> aggregatedItems = new ArrayList<AggregatedItem>();

	public ItemProcessorResult(final AggregatedItem... aggregatedItems)
	{
		super();

		if (aggregatedItems != null && aggregatedItems.length > 0)
		{
			this.aggregatedItems.addAll(Arrays.asList(aggregatedItems));
		}
	}

	public void addAggregatedItem(final AggregatedItem aggregatedItem)
	{
		aggregatedItems.add(aggregatedItem);
	}

	public List<AggregatedItem> getAggregatedItem()
	{
		return new ArrayList<AggregatedItem>(aggregatedItems);
	}

	@Override
	public String toString()
	{
		return "ItemProcessorResult[" + aggregatedItems + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aggregatedItems == null) ? 0 : aggregatedItems.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemProcessorResult other = (ItemProcessorResult)obj;
		if (aggregatedItems == null)
		{
			if (other.aggregatedItems != null)
				return false;
		}
		else if (!aggregatedItems.equals(other.aggregatedItems))
			return false;
		return true;
	}
}
