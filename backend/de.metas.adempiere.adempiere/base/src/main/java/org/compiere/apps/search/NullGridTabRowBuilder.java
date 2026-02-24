package org.compiere.apps.search;

 


import org.adempiere.util.lang.ObjectUtils;

/**
 * Null implementation of {@link IGridTabRowBuilder} which mainly does nothing
 * 
 * @author tsa
 * 
 */
public final class NullGridTabRowBuilder implements IGridTabRowBuilder
{
	public static final NullGridTabRowBuilder instance = new NullGridTabRowBuilder();

	private NullGridTabRowBuilder()
	{
		super();
	}

	/**
	 * Does nothing.
	 */
	@Override
	public void apply(Object model)
	{
		// nothing
	}

	/**
	 * @return false
	 */
	@Override
	public boolean isCreateNewRecord()
	{
		return false;
	}

	@Override
	public void setSource(Object model)
	{
		// nothing
	}

	/**
	 * @return always false
	 */
	@Override
	public boolean isValid()
	{
		return false;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}
}
