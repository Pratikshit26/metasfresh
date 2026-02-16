/*
 * #%L
 * de.metas.business
     
 * #L%
 */

package de.metas.requisition.callout;

import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.document.sequence.impl.IDocumentNoInfo;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_M_Requisition;

@Callout(I_M_Requisition.class)
public class M_Requisition
{
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);

	@CalloutMethod(columnNames = I_M_Requisition.COLUMNNAME_C_DocType_ID)
	public void onDocTypeChanged(final I_M_Requisition requisition)
	{
		final DocTypeId docTypeId = DocTypeId.ofRepoIdOrNull(requisition.getC_DocType_ID());
		if (docTypeId == null)
		{
			return;
		}

		final I_C_DocType docType = Services.get(IDocTypeDAO.class).getRecordById(docTypeId);
		final IDocumentNoInfo documentNoInfo = Services.get(IDocumentNoBuilderFactory.class)
				.createPreliminaryDocumentNoBuilder()
				.setNewDocType(docType)
				.setOldDocumentNo(requisition.getDocumentNo())
				.setDocumentModel(requisition)
				.buildOrNull();

		if (documentNoInfo != null && documentNoInfo.isDocNoControlled())
		{
			requisition.setDocumentNo(documentNoInfo.getDocumentNo());
		}
	}

	@CalloutMethod(columnNames = I_M_Requisition.COLUMNNAME_M_Warehouse_ID)
	public void onWarehouseChanged(@NonNull final I_M_Requisition requisition)
	{
			WarehouseId.optionalOfRepoId(requisition.getM_Warehouse_ID())
					.map(warehouseDAO::getById)
					.ifPresent(warehouse -> requisition.setWarehouse_Location_ID(warehouse.getC_BPartner_Location_ID()) );
	}
}
