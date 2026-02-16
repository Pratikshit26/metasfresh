package de.metas.pricing;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.X_M_ProductPrice;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */

@RequiredArgsConstructor
public enum InvoicableQtyBasedOn implements ReferenceListAwareEnum
{
	CatchWeight(X_M_ProductPrice.INVOICABLEQTYBASEDON_CatchWeight),
	NominalWeight(X_M_ProductPrice.INVOICABLEQTYBASEDON_Nominal),
	;

	@NonNull private final String code;

	private static final ReferenceListAwareEnums.ValuesIndex<InvoicableQtyBasedOn> index = ReferenceListAwareEnums.index(values());

	@JsonCreator
	public static InvoicableQtyBasedOn ofCode(@NonNull final String code) {return index.ofCodeOrName(code);}

	public static InvoicableQtyBasedOn ofNullableCode(@Nullable final String code) {return index.ofNullableCode(code);}

	@NonNull
	public static InvoicableQtyBasedOn ofNullableCodeOrNominal(@Nullable final String code)
	{
		final InvoicableQtyBasedOn type = index.ofNullableCode(code);
		return type != null ? type : NominalWeight;
	}

	@JsonValue
	public @NonNull String getCode() {return code;}

	public boolean isCatchWeight() {return CatchWeight.equals(this);}
}
