package org.adempiere.ad.trx.processor.api.impl;

 


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Ignore;

@Ignore
public class AggregatedItem
{
	private final String groupKey;
	private final List<Item> items = new ArrayList<Item>();

	private final String trxName;

	public AggregatedItem(final String groupKey, final String trxName, final Item... items)
	{
		super();
		this.groupKey = groupKey;
		this.trxName = trxName;

		if (items != null && items.length > 0)
		{
			this.items.addAll(Arrays.asList(items));
		}
	}

	public void addItem(Item item)
	{
		items.add(item);
	}

	public String getGroupKey()
	{
		return groupKey;
	}

	public String getTrxName()
	{
		return trxName;
	}

	public List<Item> getItems()
	{
		return new ArrayList<Item>(items);
	}

	public String toString()
	{
		return "AggregatedItem[" + groupKey
				+ ", trxName=" + trxName
				+ ", items: " + items
				+ "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((groupKey == null) ? 0 : groupKey.hashCode());
		result = prime * result + ((items == null) ? 0 : items.hashCode());
		result = prime * result + ((trxName == null) ? 0 : trxName.hashCode());
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
		AggregatedItem other = (AggregatedItem)obj;
		if (groupKey == null)
		{
			if (other.groupKey != null)
				return false;
		}
		else if (!groupKey.equals(other.groupKey))
			return false;
		if (items == null)
		{
			if (other.items != null)
				return false;
		}
		else if (!items.equals(other.items))
			return false;
		if (trxName == null)
		{
			if (other.trxName != null)
				return false;
		}
		else if (!trxName.equals(other.trxName))
			return false;
		return true;
	}
}
