/*
 * #%L
 * de.metas.shipper.gateway.dpd
     
 * #L%
 */
package de.metas.payment.esr.dataimporter;

import javax.annotation.Nullable;

import de.metas.payment.esr.model.X_ESR_ImportLine;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import de.metas.util.lang.ReferenceListAwareEnums.ValuesIndex;
import lombok.Getter;
import lombok.NonNull;

public enum ESRType implements ReferenceListAwareEnum
{
	TYPE_QRR(X_ESR_ImportLine.TYPE_QRR),
	TYPE_ESR(X_ESR_ImportLine.TYPE_ESR),
	TYPE_SCOR(X_ESR_ImportLine.TYPE_SCOR);

	@Getter
	private final String code;
	
	
	ESRType(@NonNull final String code)
	{
		this.code = code;
	}

	public static ESRType ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}
	
	@Nullable
	public static ESRType ofNullableCode(@Nullable final String code)
	{
		return index.ofNullableCode(code);
	}
	
	private static final ValuesIndex<ESRType> index = ReferenceListAwareEnums.index(values());

}
