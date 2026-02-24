package de.metas.adempiere.util.cache.annotations;

 


import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Force the caching engine to consider this parameter even if it's mutable.
 * 
 * WARNING: Please use this annotation only if you really have to.
 * In most of the cases when you are using it, it's a dirty hack and you will burn in hell for that.  
 *  
 * @author tsa
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Inherited
public @interface CacheAllowMutable
{
}
