package org.adempiere.mm.attributes.api.impl;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAwareFactory;
import org.adempiere.model.InterfaceWrapperHelper;

 


import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 * Dynamically wraps a given model to {@link IAttributeSetInstanceAware} if the given model has the column names required by {@link IAttributeSetInstanceAware}.
 * 
 * @author tsa
 *
 */
/* package */class GenericAttributeSetInstanceAwareFactory implements IAttributeSetInstanceAwareFactory
{
	private final transient Logger logger = LogManager.getLogger(getClass());

	@Override
	public IAttributeSetInstanceAware createOrNull(final Object referencedObj)
	{
		if (referencedObj == null)
		{
			return null;
		}

		if (!InterfaceWrapperHelper.hasModelColumnName(referencedObj, IAttributeSetInstanceAware.COLUMNNAME_M_Product_ID))
		{
			return null;
		}

		if (!InterfaceWrapperHelper.hasModelColumnName(referencedObj, IAttributeSetInstanceAware.COLUMNNAME_M_AttributeSetInstance_ID))
		{
			return null;
		}

		try
		{
			final IAttributeSetInstanceAware asiAware = InterfaceWrapperHelper.create(referencedObj, IAttributeSetInstanceAware.class);
			return asiAware;
		}
		catch (final Exception e)
		{
			final AdempiereException e2 = new AdempiereException("Cannot wrap " + referencedObj + " to " + IAttributeSetInstanceAware.class, e);
			logger.warn(e2.getLocalizedMessage(), e2);
		}

		return null;
	}
}
