package de.metas.shipment;

import javax.annotation.Nullable;

import de.metas.document.DocTypeId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */

@Value
public class ShipmentDeclarationConfig
{
	ShipmentDeclarationConfigId id;
	String name;
	int documentLinesNumber;
	DocTypeId docTypeId;
	DocTypeId docTypeCorrectionId;

	@Builder
	private ShipmentDeclarationConfig(
			@NonNull final ShipmentDeclarationConfigId id,
			@NonNull final String name,
			final int documentLinesNumber,
			@NonNull final DocTypeId docTypeId,
			@Nullable final DocTypeId docTypeCorrectionId)
	{
		Check.assume(documentLinesNumber > 0, "documentLinesNumber > 0");

		this.id = id;
		this.name = name;
		this.documentLinesNumber = documentLinesNumber;
		this.docTypeId = docTypeId;
		this.docTypeCorrectionId = docTypeCorrectionId;
	}
}
