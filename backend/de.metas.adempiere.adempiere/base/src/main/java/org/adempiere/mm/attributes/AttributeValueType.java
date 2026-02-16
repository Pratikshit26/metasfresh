package org.adempiere.mm.attributes;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import de.metas.util.lang.ReferenceListAwareEnums.ValuesIndex;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_M_Attribute;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@Getter
@RequiredArgsConstructor
public enum AttributeValueType implements ReferenceListAwareEnum
{
	STRING(X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40),
	NUMBER(X_M_Attribute.ATTRIBUTEVALUETYPE_Number),
	DATE(X_M_Attribute.ATTRIBUTEVALUETYPE_Date),
	LIST(X_M_Attribute.ATTRIBUTEVALUETYPE_List),
	;

	@NonNull private final String code;

	public static AttributeValueType ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	private static final ValuesIndex<AttributeValueType> index = ReferenceListAwareEnums.index(values());

	public interface CaseMapper<T>
	{
		T string();

		T number();

		T date();

		T list();
	}

	public <T> T map(@NonNull final CaseMapper<T> mapper)
	{
		switch (this)
		{
			case STRING:
			{
				return mapper.string();
			}
			case NUMBER:
			{
				return mapper.number();
			}
			case DATE:
			{
				return mapper.date();
			}
			case LIST:
			{
				return mapper.list();
			}
			default:
			{
				throw new AdempiereException("Unsupported value type: " + this);
			}
		}
	}

	public boolean isList() {return LIST.equals(this);}
}
