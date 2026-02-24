package org.adempiere.ad.persistence.modelgen;

 


import java.util.List;

import org.adempiere.util.lang.ObjectUtils;

import com.google.common.collect.ImmutableList;

import de.metas.util.Check;

/**
 * AD_Reference/AD_Ref_List related meta data.
 * 
 * @author tsa
 *
 */
public class ListInfo
{
	static Builder builder()
	{
		return new Builder();
	}

	private final int adReferenceId;
	private final String name;
	private final ImmutableList<ListItemInfo> items;

	private ListInfo(final Builder builder)
	{
		super();

		this.adReferenceId = builder.adReferenceId;

		Check.assumeNotEmpty(builder.name, "name not empty");
		this.name = builder.name;

		this.items = builder.items.build();
		// Check.assumeNotEmpty(items, "items not empty"); // allow empty list
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public int getAD_Reference_ID()
	{
		return adReferenceId;
	}

	public String getName()
	{
		return name;
	}

	public List<ListItemInfo> getItems()
	{
		return items;
	}

	public static class Builder
	{
		private Integer adReferenceId;
		private String name;
		private ImmutableList.Builder<ListItemInfo> items = ImmutableList.builder();

		private Builder()
		{
			super();
		}

		public ListInfo build()
		{
			return new ListInfo(this);
		}

		public Builder setAD_Reference_ID(final int adReferenceId)
		{
			this.adReferenceId = adReferenceId;
			return this;
		}

		public Builder setName(String name)
		{
			this.name = name;
			return this;
		}

		public Builder addItem(final String value, final String name, final String valueName)
		{
			final ListItemInfo item = new ListItemInfo(value, name, valueName);
			items.add(item);
			return this;
		}
	}
}
