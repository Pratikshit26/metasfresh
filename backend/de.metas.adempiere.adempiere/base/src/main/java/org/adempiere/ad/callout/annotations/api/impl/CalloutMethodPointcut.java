package org.adempiere.ad.callout.annotations.api.impl;

 


import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.api.ICalloutMethodPointcut;

import de.metas.util.Check;

public final class CalloutMethodPointcut implements ICalloutMethodPointcut
{
	@Getter
	private final Method method;

	@Getter
	private final Set<String> columnNames;

	@Getter
	private final Class<?> modelClass;

	@Getter
	private final boolean paramCalloutFieldRequired;

	@Getter
	private final boolean skipIfCopying;

	@Getter
	private final boolean skipIfIndirectlyCalled;

	public CalloutMethodPointcut(
			@NonNull final Class<?> modelClass,
			@NonNull final Method method,
			@NonNull final String[] columnNames,
			final boolean paramCalloutFieldRequired,
			final boolean skipIfCopying,
			final boolean skipIfIndirectlyCalled)
	{
		this.modelClass = modelClass;
		this.method = method;

		Check.assume(columnNames.length > 0, "columnNames not empty");

		this.columnNames = new HashSet<>(columnNames.length);
		for (final String columnName : columnNames)
		{
			Check.assumeNotNull(columnName, "columnName not null");
			this.columnNames.add(columnName);
		}

		this.paramCalloutFieldRequired = paramCalloutFieldRequired;

		this.skipIfCopying = skipIfCopying;
		this.skipIfIndirectlyCalled = skipIfIndirectlyCalled;
	}
}
