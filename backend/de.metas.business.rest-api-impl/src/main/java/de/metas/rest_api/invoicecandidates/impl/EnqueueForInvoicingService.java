package de.metas.rest_api.invoicecandidates.impl;

import de.metas.i18n.TranslatableStrings;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandidateEnqueueResult;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.PInstanceId;
import de.metas.rest_api.invoicecandidates.response.JsonEnqueueForInvoicingResponse;
import de.metas.rest_api.invoicecandidates.v2.request.JsonEnqueueForInvoicingRequest;
import de.metas.rest_api.v2.invoicecandidates.impl.InvoiceJsonConverters;
import de.metas.util.Services;
import de.metas.util.lang.ExternalHeaderIdWithExternalLineIds;
import de.metas.util.web.exception.InvalidEntityException;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * #%L
 * de.metas.business.rest-api-impl
     
 * #L%
 */

@Service
public class EnqueueForInvoicingService
{
	private final IADPInstanceDAO adPInstanceDAO = Services.get(IADPInstanceDAO.class);
	private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);

	public JsonEnqueueForInvoicingResponse enqueueForInvoicing(@NonNull final JsonEnqueueForInvoicingRequest request)
	{
		if (request.getInvoiceCandidates().isEmpty())
		{
			throw new InvalidEntityException(TranslatableStrings.constant("The request's invoiceCandidates array may not be empty"));
		}

		final List<ExternalHeaderIdWithExternalLineIds> headerAndLineIds = InvoiceJsonConverters.fromJson(request.getInvoiceCandidates());
		final PInstanceId pInstanceId = adPInstanceDAO.createSelectionId();

		invoiceCandBL.createSelectionForInvoiceCandidates(headerAndLineIds, pInstanceId);

		final IInvoiceCandidateEnqueueResult enqueueResult = invoiceCandBL
				.enqueueForInvoicing()
				.setInvoicingParams(InvoiceJsonConverters.createInvoicingParams(request))
				.setFailIfNothingEnqueued(true)
				.prepareAndEnqueueSelection(pInstanceId);

		return InvoiceJsonConverters.toJson(enqueueResult);
	}
}
