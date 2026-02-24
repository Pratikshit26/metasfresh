package org.compiere.apps.search.impl;

 


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.compiere.apps.search.IGridTabRowBuilder;
import org.compiere.apps.search.IInfoWindowGridRowBuilders;
import org.compiere.apps.search.NullGridTabRowBuilder;
import org.compiere.util.Env;

/**
 * Default implementation of {@link IInfoWindowGridRowBuilders}
 * 
 * @author tsa
 * 
 */
public class InfoWindowGridRowBuilders implements IInfoWindowGridRowBuilders
{
	private final Map<Integer, CompositeGridTabRowBuilder> builders = new HashMap<Integer, CompositeGridTabRowBuilder>();

	public InfoWindowGridRowBuilders()
	{

	}

	@Override
	public void addGridTabRowBuilder(final int recordId, final IGridTabRowBuilder builder)
	{
		CompositeGridTabRowBuilder recordBuilders = builders.get(recordId);
		if (recordBuilders == null)
		{
			recordBuilders = new CompositeGridTabRowBuilder();
			builders.put(recordId, recordBuilders);
		}

		recordBuilders.addGridTabRowBuilder(builder);
	}

	@Override
	public Set<Integer> getRecordIds()
	{
		final Set<Integer> recordIds = new HashSet<Integer>();
		for (final Map.Entry<Integer, CompositeGridTabRowBuilder> e : builders.entrySet())
		{
			final CompositeGridTabRowBuilder builder = e.getValue();
			if (!builder.isValid())
			{
				continue;
			}
			
			if (!builder.isCreateNewRecord())
			{
				continue;
			}

			final Integer recordId = e.getKey();
			recordIds.add(recordId);

		}
		return recordIds;
	}

	@Override
	public IGridTabRowBuilder getGridTabRowBuilder(final int recordId)
	{
		final CompositeGridTabRowBuilder recordBuilders = builders.get(recordId);
		if (recordBuilders == null)
		{
			return NullGridTabRowBuilder.instance;
		}

		return recordBuilders;
	}

	private static final String createContextName(final int windowNo)
	{
		final String ctxName = windowNo + "|" + InfoWindowGridRowBuilders.class.getName();
		return ctxName;
	}

	public void saveToContext(final Properties ctx, final int windowNo)
	{
		final String ctxName = createContextName(windowNo);
		Env.put(ctx, ctxName, this);
	}

	/**
	 * Gets the builders from context and then it clears the context
	 * 
	 * @param ctx
	 * @param windowNo
	 * @return builders or null
	 */
	public static IInfoWindowGridRowBuilders getFromContextOrNull(final Properties ctx, final int windowNo)
	{
		final String ctxName = createContextName(windowNo);
		final IInfoWindowGridRowBuilders builders = Env.getAndRemove(ctx, ctxName);
		return builders;
	}

	@Override
	public String toString()
	{
		return String.format("InfoWindowGridRowBuilders [builders=%s]", builders);
	}
}
