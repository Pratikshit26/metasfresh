package de.metas.cache.interceptor;

 


import java.lang.reflect.Method;
import java.util.Set;

import org.adempiere.util.proxy.Cached;
import org.compiere.model.PO;

import com.google.common.collect.ImmutableSet;

import de.metas.util.Check;

/**
 * Extracts some properties (specified by {@link Cached#keyProperties()}) in case the target object is an {@link PO}.
 * 
 * @author tsa
 *
 */
class TargetPOKeyPropertiesPartDescriptor implements ICachedMethodPartDescriptor
{
	// private static final transient Logger logger = CLogMgt.getLogger(TargetPOKeyPropertiesPartDescriptor.class);

	public static final TargetPOKeyPropertiesPartDescriptor createIfApplies(Method method, Cached annotation)
	{
		final String[] keyProperties = annotation.keyProperties();
		if (keyProperties == null || keyProperties.length <= 0)
		{
			return null;
		}

		return new TargetPOKeyPropertiesPartDescriptor(ImmutableSet.copyOf(keyProperties));
	}

	private final Set<String> keyProperties;

	private TargetPOKeyPropertiesPartDescriptor(final Set<String> keyProperties)
	{
		super();
		Check.assumeNotEmpty(keyProperties, "keyProperties not empty");
		this.keyProperties = keyProperties;
	}

	@Override
	public void extractKeyParts(CacheKeyBuilder keyBuilder, Object targetObject, Object[] params)
	{
		//
		// include specified property values into the key
		for (final String keyProp : keyProperties)
		{
			if (targetObject instanceof PO)
			{
				final PO po = (PO)targetObject;
				if (po.get_ColumnIndex(keyProp) < 0)
				{
					final String msg = "Invalid keyProperty '" + keyProp
							+ "' for cached method " + targetObject.getClass() // + "." + constructorOrMethod.getName()
							+ ". Target PO has no such column; PO=" + po;
					throw new RuntimeException(msg);
				}
				final Object keyValue = po.get_Value(keyProp);
				keyBuilder.add(keyValue);
			}
			else
			{
				final StringBuilder getMethodName = new StringBuilder("get");
				getMethodName.append(keyProp.substring(0, 1).toUpperCase());
				getMethodName.append(keyProp.substring(1));
				try
				{
					final Method method = targetObject.getClass().getMethod(getMethodName.toString());
					final Object keyValue = method.invoke(targetObject);

					keyBuilder.add(keyValue);
				}
				catch (Exception e)
				{
					final String msg = "Invalid keyProperty '" + keyProp
							+ "' for cached method " + targetObject.getClass().getName() // + "." + constructorOrMethod.getName()
							+ ". Can't access getter method get" + keyProp + ". Exception " + e + "; message: " + e.getMessage();
					throw new RuntimeException(msg, e);
				}
			}
		}
	}
}
