package org.adempiere.util;

 


import org.reflections.scanners.AbstractScanner;

public class ClassnameScanner extends AbstractScanner
{
	public ClassnameScanner()
	{
		super();
	}

	@Override
	@SuppressWarnings({ "unchecked" })
	public void scan(final Object cls)
	{
		final String classNameFQ = getMetadataAdapter().getClassName(cls);
		final String className = getClassName(classNameFQ);

		if (acceptResult(className))
		{
			getStore().put(className, classNameFQ);
		}
	}

	private final String getClassName(final String classNameFQ)
	{
		String className = classNameFQ;

		//
		// Get from last "." to the end
		{
			final int idx = className.lastIndexOf(".");
			if (idx >= 0)
			{
				className = className.substring(idx + 1);
			}
		}

		return className;
	}
}
