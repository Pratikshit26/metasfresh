package de.metas.request.api;

import de.metas.inout.model.I_M_InOutLine;
import de.metas.request.RequestId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_R_Request;
import org.eevolution.model.I_DD_OrderLine;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.swat.base
     
 * #L%
 */

public interface IRequestBL extends ISingletonService
{

	I_R_Request getById(@NonNull RequestId id);

	void save(@NonNull I_R_Request request);

	/**
	 * Create a new R_Request based on the given inout line.
	 * This request will contain information taken from the line and from its inout header:
	 * <li>inout
	 * <li>product
	 * <li>partner
	 * <li>dateDelivered
	 * <li>qualityNotice
	 * <li>org
	 * <li>linked sales rep of the org, etc.
	 * <p>
	 * Note that the quantities are not relevant in the requests. Therefore, the qualityDiscountPercent is not even set in the request.
	 * We can have requests with no quality notices, in case the base inout line was created with qualityDiscountPercent but with no quality notice.
	 */
	@Nullable
	I_R_Request createRequestFromInOutLineWithQualityIssues(I_M_InOutLine line);

	I_R_Request createRequestFromDDOrderLine(I_DD_OrderLine line);

	I_R_Request createRequestFromOrder(I_C_Order order);

	I_R_Request createRequest(RequestCandidate requestCandidate);
}
