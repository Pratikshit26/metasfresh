package de.metas.customs;

import java.time.LocalDate;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.DocTypeId;
import de.metas.document.engine.DocStatus;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.user.UserId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
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
public class CustomsInvoice
{
	@NonFinal
	CustomsInvoiceId id;
	@NonFinal
	UserId createdBy;
	@NonFinal
	UserId lastUpdatedBy;

	@NonNull
	String documentNo;

	@NonNull
	OrgId orgId;

	@NonNull
	BPartnerLocationId bpartnerAndLocationId;

	@NonNull
	String bpartnerAddress;

	@Nullable
	UserId userId;

	@NonNull
	CurrencyId currencyId;

	@NonNull
	DocTypeId docTypeId;

	@NonNull
	LocalDate invoiceDate;

	@NonNull
	String docAction;

	@NonNull
	DocStatus docStatus;

	@NonNull
	ImmutableList<CustomsInvoiceLine> lines;

	public void updateLineNos()
	{
		int nextLineNo = 10;
		for (CustomsInvoiceLine line : lines)
		{
			line.setLineNo(nextLineNo);
			nextLineNo += 10;
		}
	}
}
