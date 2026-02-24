package org.adempiere.ad.trx.processor.spi;

 


/**
 * An {@link ITrxItemProcessor} extension which can also separate the items to chunks.
 * 
 * @author tsa
 * 
 * @param <IT> input type
 * @param <RT> result type
 */
public interface ITrxItemChunkProcessor<IT, RT> extends ITrxItemProcessor<IT, RT>
{
	/**
	 * 
	 * @param item
	 * @return true if given item is part of current chunk
	 */
	boolean isSameChunk(IT item);

	/**
	 * Start a new chunk
	 * 
	 * @param item
	 */
	void newChunk(IT item);

	/**
	 * Called by API when chunk is completed
	 */
	void completeChunk();

	/**
	 * Called by API when a chunk was canceled.
	 * 
	 * A chunk is canceled when for any reason chunk could not be completed (e.g. transaction commit failed, item processing failed etc).
	 */
	void cancelChunk();
}
