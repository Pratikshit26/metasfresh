package de.metas.order;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.X_C_Order;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.swat.base
     
 * #L%
 */

@RequiredArgsConstructor
@Getter
public enum InvoiceRule implements ReferenceListAwareEnum
{
	AfterDelivery(X_C_Order.INVOICERULE_AfterDelivery),
	AfterOrderDelivered(X_C_Order.INVOICERULE_AfterOrderDelivered),
	CustomerScheduleAfterDelivery(X_C_Order.INVOICERULE_CustomerScheduleAfterDelivery),
	Immediate(X_C_Order.INVOICERULE_Immediate),
	OrderCompletelyDelivered(X_C_Order.INVOICERULE_OrderCompletelyDelivered),
	AfterPick(X_C_Order.INVOICERULE_AfterPick),
	;

	private static final ReferenceListAwareEnums.ValuesIndex<InvoiceRule> index = ReferenceListAwareEnums.index(values());

	@NonNull private final String code;

	@Nullable
	public static InvoiceRule ofNullableCode(@Nullable final String code) {return index.ofNullableCode(code);}

	@NonNull
	public static InvoiceRule ofCode(@NonNull final String code) {return index.ofCode(code);}

	@Nullable
	public static String toCodeOrNull(@Nullable final InvoiceRule type) {return type != null ? type.getCode() : null;}
}
