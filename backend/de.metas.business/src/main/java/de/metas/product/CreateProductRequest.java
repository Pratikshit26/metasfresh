package de.metas.product;

import de.metas.organization.OrgId;
import de.metas.sectionCode.SectionCodeId;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.LocalDate;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */

@Value
@Builder
public class CreateProductRequest
{
	@NonNull
	OrgId orgId;

	@Nullable
	String productValue;

	@NonNull
	String productName;
	@NonNull
	ProductCategoryId productCategoryId;

	@NonNull
	String productType;

	@NonNull
	UomId uomId;

	boolean purchased;
	boolean sold;

	@Nullable
	Boolean bomVerified;

	@Nullable
	ProductPlanningSchemaSelector planningSchemaSelector;

	@Nullable
	String ean;

	@Nullable
	String gtin;

	@Nullable
	String description;

	@Nullable
	Boolean discontinued;

	@Nullable
	LocalDate discontinuedFrom;

	@Nullable
	Boolean active;

	@Nullable
	Boolean stocked;

	@Nullable
	SectionCodeId sectionCodeId;

	@Nullable
	String sapProductHierarchy;

	@Nullable
	String guaranteeMonths;

	@Nullable
	String warehouseTemperature;
}
