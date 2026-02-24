package de.metas.document.impl;

 


import de.metas.document.ICopyHandlerBL;
import de.metas.document.IDocLineCopyHandler;

/**
 * A copy handler that does nothing. If registered as handler, it is never invoked. Use {@link ICopyHandlerBL#getNullDocLineCopyHandler()} to get an instance.
 * 
 * @author ts
 *
 * @param <LT> ignored
 */
public final class NullDocLineCopyHandler<LT> implements IDocLineCopyHandler<LT>
{
	/* package */final static NullDocLineCopyHandler<?> instance = new NullDocLineCopyHandler<>();

	private NullDocLineCopyHandler()
	{
	}

	/**
	 * Does nothing.
	 */
	@Override
	public void copyPreliminaryValues(LT from, LT to)
	{
		// does nothing
	}

	/**
	 * Does nothing.
	 */
	@Override
	public void copyValues(LT from, LT to)
	{
		// does nothing
	}

	/**
	 * Throws an {@link UnsupportedOperationException}.
	 */
	@Override
	public Class<LT> getSupportedItemsClass()
	{
		throw new UnsupportedOperationException();
	}
}
