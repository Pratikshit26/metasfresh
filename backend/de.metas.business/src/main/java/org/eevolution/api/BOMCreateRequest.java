package org.eevolution.api;

import com.google.common.collect.ImmutableList;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeSetInstanceId;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */

@Value
public class BOMCreateRequest
{
	OrgId orgId;
	ProductId productId;
	String productValue;
	String productName;
	UomId uomId;
	BOMType bomType;
	BOMUse bomUse;
	@NonNull
	Instant validFrom;
	Boolean isActive;
	AttributeSetInstanceId attributeSetInstanceId;
	@Nullable
	ResourceId resourceId;
	ImmutableList<BOMLine> lines;

	@Builder
	private BOMCreateRequest(
			@NonNull final OrgId orgId,
			@NonNull final ProductId productId,
			@NonNull final String productValue,
			@NonNull final String productName,
			@NonNull final UomId uomId,
			@Nullable final BOMType bomType,
			@Nullable final BOMUse bomUse,
			@Nullable final Instant validFrom,
			@Nullable final Boolean isActive,
			@Nullable final AttributeSetInstanceId attributeSetInstanceId,
			@Nullable final ResourceId resourceId,
			@NonNull @Singular final ImmutableList<BOMLine> lines)
	{
		Check.assumeNotEmpty(lines, "lines is not empty");

		this.orgId = orgId;
		this.productId = productId;
		this.productValue = productValue;
		this.productName = productName;
		this.uomId = uomId;
		this.bomType = bomType;
		this.bomUse = bomUse;
		this.isActive = isActive;
		this.lines = lines;
		this.validFrom = validFrom != null ? validFrom : Instant.now();
		this.resourceId = resourceId;
		this.attributeSetInstanceId = attributeSetInstanceId;
	}

	@Value
	@Builder
	public static class BOMLine
	{
		@NonNull
		@Default
		BOMComponentType componentType = BOMComponentType.Component;

		@NonNull
		ProductId productId;

		@NonNull
		Quantity qty;

		@Nullable
		BOMIssueMethod issueMethod;

		@Nullable
		Boolean isQtyPercentage;

		@Nullable
		Integer line;

		@Nullable
		BigDecimal scrap;

		@Nullable
		String help;

		@Nullable
		AttributeSetInstanceId attributeSetInstanceId;
	}
}
