package de.metas.document;

 


/**
 * Document copy handler, provides DocLineCopyHandler access
 * 
 * @author al
 * 
 * @param <HT> header type (document type)
 * @param <LT> line type (document line type)
 */
public interface IDocCopyHandler<HT extends Object, LT extends Object> extends ICopyHandler<HT>
{
	/**
	 * @return implementation of the {@link IDocLineCopyHandler} for this document handler.
	 *         <p>
	 *         If your implementation dies not need a line handler, then please return the result of {@link ICopyHandlerBL#getNullDocLineCopyHandler()}.
	 */
	IDocLineCopyHandler<LT> getDocLineCopyHandler();
}
