package org.compiere.apps.search;

 


import java.util.Collections;
import java.util.Set;

/**
 * Null implementation of {@link IInfoWindowGridRowBuilders} which does nothing.
 * 
 * It is always empty and does not support adding {@link IGridTabRowBuilder}s.
 * 
 * @author tsa
 * 
 */
public final class NullInfoWindowGridRowBuilders implements IInfoWindowGridRowBuilders
{
	public static final NullInfoWindowGridRowBuilders instance = new NullInfoWindowGridRowBuilders();

	private NullInfoWindowGridRowBuilders()
	{
		super();
	}

	/**
	 * @return {@link NullGridTabRowBuilder} always
	 */
	@Override
	public IGridTabRowBuilder getGridTabRowBuilder(int recordId)
	{
		return NullGridTabRowBuilder.instance;
	}

	/**
	 * @return empty set
	 */
	@Override
	public Set<Integer> getRecordIds()
	{
		return Collections.emptySet();
	}

	/**
	 * @throws UnsupportedOperationException
	 */
	@Override
	public void addGridTabRowBuilder(int recordId, IGridTabRowBuilder builder)
	{
		throw new UnsupportedOperationException("Not supported");
	}
}
