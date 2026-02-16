package de.metas.dataentry;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

public enum FieldType
{
	SUB_TAB_ID(Integer.class),
	PARENT_LINK_ID(Integer.class),

	CREATED_UPDATED_INFO(String.class),

	TEXT(String.class),

	LONG_TEXT(String.class),

	NUMBER(BigDecimal.class),

	DATE(LocalDate.class),

	LIST(DataEntryListValueId.class),

	YESNO(Boolean.class);

	@Getter
	private final Class<?> clazz;

	private FieldType(Class<?> clazz)
	{
		this.clazz = clazz;
	}
}
