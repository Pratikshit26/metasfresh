package org.adempiere.appdict.validation.spi;

 

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Util;

import java.lang.reflect.Method;

public abstract class AbstractADValidator<T> implements IADValidator<T>
{
	protected Method validateJavaMethodName(final String classname, final Class<?> parentClass, final String methodName)
	{
		final Class<?> clazz = Util.validateJavaClassname(classname, parentClass);
		for (final Method m : clazz.getMethods())
		{
			if (methodName.equals(m.getName()))
			{
				return m;
			}
		}

		throw new AdempiereException("Method '" + methodName + "' not found in " + clazz);
	}
}
