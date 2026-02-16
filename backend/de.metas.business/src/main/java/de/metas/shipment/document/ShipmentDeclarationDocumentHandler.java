package de.metas.shipment.document;

import de.metas.document.engine.DocStatus;
import de.metas.document.engine.DocumentHandler;
import de.metas.document.engine.DocumentTableFields;
import de.metas.document.engine.IDocument;
import de.metas.organization.InstantAndOrgId;
import de.metas.organization.OrgId;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Shipment_Declaration;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */

public class ShipmentDeclarationDocumentHandler implements DocumentHandler
{

	@Override
	public String getSummary(DocumentTableFields docFields)
	{
		return extractShipmentDeclaration(docFields).getDocumentNo();
	}

	@Override
	public String getDocumentInfo(DocumentTableFields docFields)
	{
		return getSummary(docFields);
	}

	@Override
	public InstantAndOrgId getDocumentDate(@NonNull final DocumentTableFields docFields)
	{
		final I_M_Shipment_Declaration record = extractShipmentDeclaration(docFields);
		return InstantAndOrgId.ofTimestamp(record.getDeliveryDate(), OrgId.ofRepoId(record.getAD_Org_ID()));
	}

	@Override
	public int getDoc_User_ID(DocumentTableFields docFields)
	{
		return extractShipmentDeclaration(docFields).getCreatedBy();
	}

	@Override
	public DocStatus completeIt(DocumentTableFields docFields)
	{
		final I_M_Shipment_Declaration shipmentDeclaration = extractShipmentDeclaration(docFields);
		shipmentDeclaration.setProcessed(true);
		shipmentDeclaration.setDocAction(IDocument.ACTION_ReActivate);

		return DocStatus.Completed;
	}

	@Override
	public void reactivateIt(DocumentTableFields docFields)
	{
		final I_M_Shipment_Declaration shipmentDeclaration = extractShipmentDeclaration(docFields);

		shipmentDeclaration.setProcessed(false);
		shipmentDeclaration.setDocAction(IDocument.ACTION_Complete);

	}

	private static I_M_Shipment_Declaration extractShipmentDeclaration(final DocumentTableFields docFields)
	{
		return InterfaceWrapperHelper.create(docFields, I_M_Shipment_Declaration.class);
	}
}
