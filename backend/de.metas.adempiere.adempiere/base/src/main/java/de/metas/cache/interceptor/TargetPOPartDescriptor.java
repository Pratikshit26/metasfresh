package de.metas.cache.interceptor;

 


import java.lang.reflect.Method;

import org.adempiere.util.proxy.Cached;
import org.compiere.model.PO;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

/**
 * In case target object is an {@link PO} and {@link Cached#ignoreInstance()} is not set,
 * this method will extract TableId/ID/TrxName from {@link PO} and will add them to key builder.
 * 
 * @author tsa
 *
 */
class TargetPOPartDescriptor implements ICachedMethodPartDescriptor
{
	private static final transient Logger logger = LogManager.getLogger(TargetPOPartDescriptor.class);

	public static final TargetPOPartDescriptor createIfApplies(Method method, Cached annotation)
	{
		if (annotation.ignoreInstance())
		{
			logger.debug("not including the target object in the key");
			return null;
		}

		return instance;
	}

	public static final transient TargetPOPartDescriptor instance = new TargetPOPartDescriptor();

	@Override
	public void extractKeyParts(CacheKeyBuilder keyBuilder, Object targetObject, Object[] params)
	{
		if (targetObject instanceof PO && ((PO)targetObject).get_ID() > 0)
		{
			final PO po = (PO)targetObject;
			keyBuilder.add(po.get_Table_ID());
			keyBuilder.add(po.get_ID());
			keyBuilder.add(po.get_TrxName());
		}
	}
}
