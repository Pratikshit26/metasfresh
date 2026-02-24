package de.metas.cache.annotation;

 


import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * If a boolean parameter annonotated with this annotation is {@code true}, it triggers tje cache to be refreshed/reloaded using the method's return value.
 * If the annotated parameter is NOT true, the caching will work as usual.
 * 
 * NOTE: internally, this annotated parameter won't be part of the caching key, because makes no sense.
 * 
 * @author tsa
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Inherited
public @interface CacheReloadIfTrue
{
}
