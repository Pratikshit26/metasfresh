package org.adempiere.ad.persistence;

 

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

class ModelClassInfo implements IModelClassInfo
{
	private final ModelClassIntrospector introspector;

	private final Class<?> modelClass;
	private final String tableName;

	private Map<Method, IModelMethodInfo> _modelMethodInfos;
	private final ReentrantLock modelMethodInfosLock = new ReentrantLock();

	private Set<String> _definedColumnNames = null;

	public ModelClassInfo(final ModelClassIntrospector introspector, @NonNull final Class<?> modelClass, final String tableName)
	{
		// shall not be null, but because it's created only from introspector, we are not checking it again
		this.introspector = introspector;

		this.modelClass = modelClass;

		// NOTE: could be null in case we are dealing with an interface which is not bounded to a TableName
		this.tableName = tableName;
	}

	@Override
	public String toString()
	{
		return "ModelClassInfo ["
				+ "modelClass=" + modelClass
				+ ", tableName=" + tableName
				+ "]";
	}

	@Override
	public Class<?> getModelClass()
	{
		return modelClass;
	}

	@Override
	public String getTableName()
	{
		return tableName;
	}

	@Override
	public final IModelMethodInfo getMethodInfo(final Method method)
	{
		modelMethodInfosLock.lock();
		try
		{
			final Map<Method, IModelMethodInfo> methodInfos = getMethodInfos0();

			IModelMethodInfo methodInfo = methodInfos.get(method);

			//
			// If methodInfo was not found, try to create it now
			if (methodInfo == null)
			{
				methodInfo = introspector.createModelMethodInfo(method);
				if (methodInfo == null)
				{
					throw new IllegalStateException("No method info was found for " + method + " in " + this);
				}

				methodInfos.put(method, methodInfo);
			}

			return methodInfo;
		}
		finally
		{
			modelMethodInfosLock.unlock();
		}
	}

	/**
	 * Gets the inner map of {@link Method} to {@link IModelMethodInfo}.
	 * 
	 * NOTE: this method is not thread safe
	 * 
	 * @return
	 */
	private final Map<Method, IModelMethodInfo> getMethodInfos0()
	{
		if (_modelMethodInfos == null)
		{
			_modelMethodInfos = introspector.createModelMethodInfos(getModelClass());
		}
		return _modelMethodInfos;
	}

	@Override
	public synchronized final Set<String> getDefinedColumnNames()
	{
		if (_definedColumnNames == null)
		{
			_definedColumnNames = findDefinedColumnNames();
		}
		return _definedColumnNames;
	}

	@SuppressWarnings("unchecked")
	private final Set<String> findDefinedColumnNames()
	{
		//
		// Collect all columnnames
		final ImmutableSet.Builder<String> columnNamesBuilder = ImmutableSet.builder();
		ReflectionUtils.getAllFields(modelClass, new Predicate<Field>()
		{

			@Override
			public boolean apply(final Field field)
			{
				final String fieldName = field.getName();
				if (fieldName.startsWith("COLUMNNAME_"))
				{
					final String columnName = fieldName.substring("COLUMNNAME_".length());
					columnNamesBuilder.add(columnName);
				}

				return false;
			}
		});

		return columnNamesBuilder.build();
	}
}
