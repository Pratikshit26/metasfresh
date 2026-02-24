package org.compiere.apps.search.impl;

 


import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.util.lang.ObjectUtils;
import org.compiere.apps.search.IGridTabRowBuilder;

/**
 * Implementation which groups several {@link IGridTabRowBuilder} and behave like one {@link IGridTabRowBuilder}.
 * 
 * @author tsa
 * 
 */
public class CompositeGridTabRowBuilder implements IGridTabRowBuilder
{
	private static final transient Logger logger = LogManager.getLogger(CompositeGridTabRowBuilder.class);

	private final List<IGridTabRowBuilder> builders = new ArrayList<IGridTabRowBuilder>();

	public void addGridTabRowBuilder(final IGridTabRowBuilder builder)
	{
		if (builder == null)
		{
			return;
		}
		if (builders.contains(builder))
		{
			return;
		}

		builders.add(builder);
	}

	@Override
	public void apply(final Object model)
	{
		for (final IGridTabRowBuilder builder : builders)
		{
			if (!builder.isValid())
			{
				logger.debug("Skip builder because it's not valid: {}", builder);
				continue;
			}

			builder.apply(model);
			logger.debug("Applied {} to {}", new Object[] { builder, model });
		}
	}

	@Override
	public boolean isCreateNewRecord()
	{
		boolean createNewRecord = true;

		for (final IGridTabRowBuilder builder : builders)
		{
			if (!builder.isValid())
			{
				createNewRecord = false;

				continue;
			}

			if (!builder.isCreateNewRecord())
			{
				createNewRecord = false;
			}
		}

		return createNewRecord;
	}

	/**
	 * @return true if at least one builder is valid
	 */
	@Override
	public boolean isValid()
	{
		for (final IGridTabRowBuilder builder : builders)
		{
			if (builder.isValid())
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public void setSource(Object model)
	{
		for (final IGridTabRowBuilder builder : builders)
		{
			builder.setSource(model);
		}
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}
}
