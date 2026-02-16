package de.metas.shipment;

import java.time.LocalDate;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.DocTypeId;
import de.metas.inout.InOutId;
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
public class ShipmentDeclaration
{
	@NonFinal
	ShipmentDeclarationId id;

	@NonNull
	String documentNo;

	@NonNull
	OrgId orgId;

	@NonNull
	InOutId shipmentId;

	@NonNull
	BPartnerLocationId bpartnerAndLocationId;

	@Nullable
	UserId userId;

	@NonNull
	DocTypeId docTypeId;

	@NonNull
	LocalDate shipmentDate;

	@NonNull
	String docAction;

	@NonNull
	String docStatus;

	@Nullable
	@NonFinal
	ShipmentDeclarationId baseShipmentDeclarationId;

	@Nullable
	@NonFinal
	ShipmentDeclarationId correctionShipmentDeclarationId;

	@NonNull
	ImmutableList<ShipmentDeclarationLine> lines;

	public void updateLineNos()
	{
		int nextLineNo = 10;
		for (ShipmentDeclarationLine line : lines)
		{
			line.setLineNo(nextLineNo);
			nextLineNo += 10;
		}
	}

	public ShipmentDeclaration copyToNew(
			@NonNull final DocTypeId newDocTypeId,
			@NonNull final String newDocAction)
	{
		final ImmutableList<ShipmentDeclarationLine> newLines = getLines()
				.stream()
				.map(ShipmentDeclarationLine::copyToNew)
				.collect(ImmutableList.toImmutableList());

		return toBuilder()
				.id(null)
				.docTypeId(newDocTypeId)
				.docAction(newDocAction)
				.lines(newLines)
				.build();
	}
}
