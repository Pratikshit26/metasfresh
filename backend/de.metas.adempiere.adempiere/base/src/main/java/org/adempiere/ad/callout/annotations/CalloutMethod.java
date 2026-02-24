package org.adempiere.ad.callout.annotations;

 


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the annotated callout method shall be triggered when given columns were changed.
 * 
 * Annotated method shall have following signature:
 * 
 * <pre>
 * public void myCallout(I_C_MyModel model, ICalloutField field)
 * </pre>
 * 
 * where
 * <ul>
 * <li>model - is the model on which this callout applies (i.e. shall be the same class that you have used in {@link Callout} annotation)
 * <li>field - is the field on which this callout was triggered (useful to get the ColumnName etc)
 * </ul>
 * 
 * @author tsa
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface CalloutMethod
{
	/**
	 * Column names on which this method shall be bound
	 */
	String[] columnNames();

	/**
	 * Skip calling this callout if we are copying (with details).
	 */
	boolean skipIfCopying() default false;

	/**
	 * Skip calling this callout if it is called via another callout.
	 * Use case: you want a callout to do stuff only if invoked *directly* by a user (or business logic).
	 */
	boolean skipIfIndirectlyCalled() default false;
}
