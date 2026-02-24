package org.compiere.swing.table;

 


import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Inherited
public @interface TableInfo
{
	/** <code>true</code> if we shall hide all columns (which are not annotated). */
	boolean defaultHideAll() default true;
}
