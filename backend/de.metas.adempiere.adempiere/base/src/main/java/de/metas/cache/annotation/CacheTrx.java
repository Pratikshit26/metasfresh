package de.metas.cache.annotation;

 


import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.adempiere.util.proxy.Cached;

/**
 * Use this annotation on a method parameter if the method has been annotated
 * with {@link Cached}, and the parameter is the actual transaction name (i.e. trxName).
 *
 * metasfresh can use this method to provide a transaction-local cache.
 * 
 * @author Teo Sarca
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Inherited
public @interface CacheTrx
{
}
