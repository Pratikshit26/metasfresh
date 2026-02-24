package org.adempiere.ad.callout.annotations.api;

 


import java.lang.reflect.Method;
import java.util.Set;

import org.adempiere.ad.callout.api.ICalloutField;

public interface ICalloutMethodPointcut
{

	Set<String> getColumnNames();

	Method getMethod();

	Class<?> getModelClass();

	/** @return true if when invoking the callout method we need to provide the {@link ICalloutField} parameter too */
	boolean isParamCalloutFieldRequired();

	/** @return true if we shall skip invoking this callout if we are in record copying mode */
	boolean isSkipIfCopying();

	/** @return true if we shall skip invoking this callout if it's called not directly but via another callout */
	boolean isSkipIfIndirectlyCalled();
}
