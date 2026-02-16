package de.metas.order.compensationGroup;

import de.metas.order.OrderId;
import de.metas.product.ProductCategoryId;
import de.metas.product.acct.api.ActivityId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */

@Value
@Builder
public class GroupCreateRequest
{
	@NonNull
	OrderId orderId;

	@NonNull
	String name;

	@Builder.Default
	boolean isNamePrinted = true;

	@Nullable
	ActivityId activityId;

	@Nullable
	ProductCategoryId productCategoryId;

	@Nullable
	GroupTemplateId groupTemplateId;

	@Nullable
	GroupCompensationOrderBy groupCompensationOrderBy;
}
