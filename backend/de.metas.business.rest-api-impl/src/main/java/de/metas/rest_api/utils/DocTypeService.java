package de.metas.rest_api.utils;

import de.metas.common.ordercandidates.v1.request.JsonOLCandCreateRequest.OrderDocType;
import de.metas.document.DocBaseAndSubType;
import de.metas.document.DocBaseType;
import de.metas.document.DocSubType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_AD_Org;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.business.rest-api-impl
     
 * #L%
 */

@Service
public class DocTypeService
{
	private final IOrgDAO orgsDAO = Services.get(IOrgDAO.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);

	@Nullable
	public DocTypeId getInvoiceDocTypeId(
			@Nullable final DocBaseAndSubType docBaseAndSubType,
			@NonNull final OrgId orgId)
	{
		if (docBaseAndSubType == null)
		{
			return null;
		}

		final DocBaseType docBaseType = docBaseAndSubType.getDocBaseType();
		final DocSubType docSubType = docBaseAndSubType.getDocSubType();

		final I_AD_Org orgRecord = orgsDAO.getById(orgId);
		final DocTypeQuery query = DocTypeQuery
				.builder()
				.docBaseType(docBaseType)
				.docSubType(docSubType)
				.adClientId(orgRecord.getAD_Client_ID())
				.adOrgId(orgRecord.getAD_Org_ID())
				.build();

		return docTypeDAO.getDocTypeId(query);
	}

	public DocTypeId getOrderDocTypeId(final OrderDocType orderDocType, OrgId orgId)
	{
		if (orderDocType == null)
		{
			return null;
		}

		final DocBaseType docBaseType = DocBaseType.SalesOrder;
		final DocSubType docSubType;

		if (OrderDocType.PrepayOrder.equals(orderDocType))
		{
			docSubType = DocSubType.PrepayOrder;
		}
		else
		{
			docSubType = DocSubType.StandardOrder;
		}

		final I_AD_Org orgRecord = orgsDAO.getById(orgId);

		final DocTypeQuery query = DocTypeQuery
				.builder()
				.docBaseType(docBaseType)
				.docSubType(docSubType)
				.adClientId(orgRecord.getAD_Client_ID())
				.adOrgId(orgRecord.getAD_Org_ID())
				.build();

		return docTypeDAO.getDocTypeId(query);
	}
}
