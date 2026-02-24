package de.metas.adempiere.util.cache.annotations;

 


import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Instruct the caching engine to cache the method results ONLY if annotated parameter is NULL.
 * 
 * @author tsa
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Inherited
public @interface CacheSkipIfNotNull
{

}
