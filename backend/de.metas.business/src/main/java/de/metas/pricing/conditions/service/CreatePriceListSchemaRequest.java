/*
 * #%L
 * de.metas.business
     
 * #L%
 */

package de.metas.pricing.conditions.service;

import de.metas.bpartner.BPartnerId;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.pricing.conditions.PricingConditionsId;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxCategoryId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Value
@Builder
public class CreatePriceListSchemaRequest
{
	@NonNull
	PricingConditionsId discountSchemaId;

	int seqNo;

	@Nullable
	ProductId productId;
	@Nullable
	ProductCategoryId productCategoryId;
	@Nullable
	BPartnerId bPartnerId;

	@Nullable
	TaxCategoryId taxCategoryId;
	@Nullable
	TaxCategoryId taxCategoryTargetId;

	@NonNull
	LocalDate conversionDate;
	@NonNull
	CurrencyConversionTypeId conversionTypeId;

	@NonNull
	BigDecimal limit_AddAmt;
	@NonNull
	String limit_Base;
	@NonNull
	BigDecimal limit_Discount;
	@NonNull
	BigDecimal limit_MaxAmt;
	@NonNull
	BigDecimal limit_MinAmt;
	@NonNull
	String limit_Rounding;

	@NonNull
	BigDecimal list_AddAmt;
	@NonNull
	String list_Base;
	@NonNull
	BigDecimal list_Discount;
	@NonNull
	BigDecimal list_MaxAmt;
	@NonNull
	BigDecimal list_MinAmt;
	@NonNull
	String list_Rounding;

	@NonNull
	BigDecimal std_AddAmt;
	@NonNull
	String std_Base;
	@NonNull
	BigDecimal std_Discount;
	@NonNull
	BigDecimal std_MaxAmt;
	@NonNull
	BigDecimal std_MinAmt;
	@NonNull
	String std_Rounding;
}
