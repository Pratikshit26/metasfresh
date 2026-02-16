package de.metas.customs;

import java.util.ArrayList;
import java.util.Optional;

import com.google.common.collect.ImmutableList;

import de.metas.inout.InOutAndLineId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */
@Data
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomsInvoiceLine
{
	@NonFinal
	CustomsInvoiceLineId id;

	@NonFinal
	@Setter(AccessLevel.PACKAGE)
	int lineNo;

	@NonNull
	ProductId productId;

	@NonNull
	Quantity quantity;

	@NonNull
	OrgId orgId;

	@NonNull
	Money lineNetAmt;

	@Getter(AccessLevel.NONE)
	@Default
	private final ArrayList<CustomsInvoiceLineAlloc> allocations = new ArrayList<>();

	public ImmutableList<CustomsInvoiceLineAlloc> getAllocations()
	{
		return ImmutableList.copyOf(allocations);
	}

	public void addAllocation(CustomsInvoiceLineAlloc alloc)
	{
		allocations.add(alloc);
	}

	public void removeAllocation(@NonNull final CustomsInvoiceLineAlloc alloc)
	{
		allocations.remove(alloc);
	}

	public Optional<CustomsInvoiceLineAlloc> getAllocationByInOutLineId(@NonNull final InOutAndLineId inoutAndLineId)
	{
		return allocations.stream()
				.filter(alloc -> alloc.getInoutAndLineId().equals(inoutAndLineId))
				.findFirst();
	}
}
