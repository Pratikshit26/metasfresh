package de.metas.document;

 


import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import de.metas.common.util.pair.ImmutablePair;

import de.metas.util.ISingletonService;

/**
 * Generic service that allows us do add handlers (could also be called listeners for all I know) which add module specific aspects to the copying of records.
 * 
 * @author ts
 * 
 */
public interface ICopyHandlerBL extends ISingletonService
{
	/**
	 * Registers a copy handler and a filter for a given type. Note that handlers will be evaluated in the order of their registration. If a handler was already registered before, the method will do
	 * nothing.
	 * 
	 * @param <T> the type (like <code>I_C_Order</code> or <code>M_Product</code> we register the handler for).
	 * @param filter the given implementation shall decide if the given handler handler is to be applied for a given record or not.
	 * @param handler the given implementation will do some kind of copying.
	 * @see InterfaceWrapperHelper#getTableNameOrNull(Class)
	 */
	<T> void registerCopyHandler(Class<T> clazz, IQueryFilter<ImmutablePair<T, T>> filter, ICopyHandler<? extends T> handler);

	/**
	 * Invokes {@link ICopyHandler#copyPreliminaryValues(Object, Object)} for all applicable handlers, in the order of their registration.
	 * 
	 * @param from
	 * @param to
	 */
	<T> void copyPreliminaryValues(T from, T to);

	/**
	 * Invokes {@link ICopyHandler#copyValues(Object, Object)} for all applicable handlers, in the order of their registration.
	 * 
	 * @param from
	 * @param to
	 */
	<T> void copyValues(T from, T to);
	
	<T> IDocLineCopyHandler<T> getNullDocLineCopyHandler();

}
