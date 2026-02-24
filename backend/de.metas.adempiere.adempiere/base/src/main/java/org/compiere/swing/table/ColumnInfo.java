package org.compiere.swing.table;

 


import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.compiere.util.DisplayType;

/**
 * Used to add hints to java beans getter about how this column will be displayed.
 * 
 * @author tsa
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
@Inherited
public @interface ColumnInfo
{
	String columnName();

	String displayName() default "";

	/** @return true if the {@link #columnName()} shall be translated, false if is already translated */
	boolean translate() default true;

	boolean hidden() default false;

	int seqNo() default Integer.MAX_VALUE;

	String lookupTableName() default "";

	String lookupColumnName() default "";

	/**
	 * @return optional prototype value used to calculate the column width
	 */
	String prototypeValue() default "";

	/**
	 * @return optional display type
	 * @see DisplayType
	 */
	int displayType() default -1;

	/** @return true if this is a selection column which shall be rendered as a checkbox */
	boolean selectionColumn() default false;
}
