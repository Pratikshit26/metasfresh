package org.adempiere.server.rpl.api.impl;

 


import java.sql.Timestamp;
import java.util.Objects;
import java.util.Properties;

import org.adempiere.server.rpl.exceptions.ReplicationException;
import org.compiere.util.Env;

public class ReplicationHelper
{
	public static final String MSG_XMLInvalidContext = "XMLInvalidContext";

	/**
	 * Method sets the given context values to the given context.
	 * 
	 * @param ctx the context to be updated
	 * @param name the name of the context value to be updated
	 * @param value the actual new value
	 * @param overwrite if <code>true</code> then the given <code>value</code> is set, even if there is already a different value. Otherwise, a {@link ReplicationException} is thrown.
	 * @throws ReplicationException if the name is already set to a different value.
	 */
	public static void setReplicationCtx(final Properties ctx,
			final String name,
			final Object value,
			final boolean overwrite)
	{
		if (value instanceof Integer)
		{
			final Integer valueInt = (Integer)value;
			final Integer valueOldInt = Env.containsKey(ctx, name) ? Env.getContextAsInt(ctx, name) : null;
			if (Objects.equals(valueInt, valueOldInt))
			{
				// nothing to do
				return;
			}
			else if (overwrite || valueOldInt == null)
			{
				Env.setContext(ctx, name, valueInt);
			}
			else
			{
				throw new ReplicationException(MSG_XMLInvalidContext)
						.setParameter("AttributeName", name)
						.setParameter("Value", valueInt)
						.setParameter("ValueOld", valueOldInt);
			}
		}
		else if (value instanceof Timestamp)
		{
			final Timestamp valueTS = (Timestamp)value;
			final Timestamp valueOldTS = Env.containsKey(ctx, name) ? Env.getContextAsDate(ctx, name) : null;
			if (Objects.equals(valueTS, valueOldTS))
			{
				// nothing to do
				return;
			}
			else if (overwrite || valueOldTS == null)
			{
				Env.setContext(ctx, name, valueTS);
			}
			else
			{
				throw new ReplicationException(MSG_XMLInvalidContext)
						.setParameter("AttributeName", name)
						.setParameter("Value", valueTS)
						.setParameter("ValueOld", valueOldTS);
			}
		}
		else
		{
			final String valueStr = value == null ? null : value.toString();
			final String valueOldStr = Env.containsKey(ctx, name) ? Env.getContext(ctx, name) : null;
			if (Objects.equals(valueStr, valueOldStr))
			{
				// nothing to do
				return;
			}
			else if (overwrite || valueOldStr == null)
			{
				Env.setContext(ctx, name, valueStr);
			}
			else
			{
				throw new ReplicationException(MSG_XMLInvalidContext)
						.setParameter("AttributeName", name)
						.setParameter("Value", valueStr)
						.setParameter("ValueOld", valueOldStr);
			}
		}
	}
}
