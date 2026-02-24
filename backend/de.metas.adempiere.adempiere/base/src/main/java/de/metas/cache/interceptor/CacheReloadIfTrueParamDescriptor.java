package de.metas.cache.interceptor;

 


import java.lang.annotation.Annotation;

import de.metas.cache.annotation.CacheReloadIfTrue;

/**
 * Handles {@link CacheReloadIfTrue} annotation.
 * 
 * @author tsa
 *
 */
public class CacheReloadIfTrueParamDescriptor implements ICachedMethodPartDescriptor
{
	private final int parameterIndex;

	public CacheReloadIfTrueParamDescriptor(final Class<?> parameterType, final int parameterIndex, final Annotation annotation)
	{
		super();

		this.parameterIndex = parameterIndex;

		if (Boolean.class == parameterType)
		{
			// OK, nothing to do
		}
		else if (boolean.class == parameterType)
		{
			// OK, nothing to do
		}
		else
		{
			throw new CacheIntrospectionException("Parameter has unsupported type")
					.setParameter(parameterIndex, parameterType);
		}
	}

	@Override
	public void extractKeyParts(final CacheKeyBuilder keyBuilder, final Object targetObject, final Object[] params)
	{
		final Boolean cacheReloadFlagObj = (Boolean)params[parameterIndex];
		final boolean cacheReloadFlag = cacheReloadFlagObj == null ? false : cacheReloadFlagObj;

		if (cacheReloadFlag)
		{
			keyBuilder.setCacheReload();
		}
	}

}
