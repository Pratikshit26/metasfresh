package de.metas.cache.interceptor;

 


/**
 * Partial handler for a {@link CachedMethodDescriptor}.
 * 
 * Implementations of this interface are handling a method parameter, target object instance in some cases etc.
 * 
 * @author tsa
 *
 */
interface ICachedMethodPartDescriptor
{
	/**
	 * Extract cache keys from given target object which was invoked with given parameters.
	 * 
	 * @param keyBuilder key builder where to append the extracted keys
	 * @param targetObject target object
	 * @param params method invocation parameters
	 */
	void extractKeyParts(CacheKeyBuilder keyBuilder, Object targetObject, Object[] params);
}
