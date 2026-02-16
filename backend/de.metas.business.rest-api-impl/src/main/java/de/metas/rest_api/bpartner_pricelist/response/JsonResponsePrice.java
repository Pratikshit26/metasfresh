package de.metas.rest_api.bpartner_pricelist.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import de.metas.currency.CurrencyCode;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxCategoryId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;

/*
 * #%L
 * de.metas.business.rest-api-impl
     
 * #L%
 */

@Value
@Builder
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class JsonResponsePrice
{
	@Schema(type = "java.lang.Integer",
			description = "This translates to `M_Product.M_Product_ID`.")
	@NonNull
	ProductId productId;

	@NonNull
	String productCode;

	@NonNull
	BigDecimal price;

	@Schema(minLength = 1,
			type = "java.lang.String", //
			description = "Currency code (3 letters)")
	@NonNull
	CurrencyCode currencyCode;

	@Schema(type = "java.lang.Integer", //
			description = "This translates to `C_TaxCategory_ID`.")
	@NonNull
	TaxCategoryId taxCategoryId;
}
