package de.metas.rest_api.v1.product.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import de.metas.product.ProductId;
import de.metas.rest_api.utils.JsonCreatedUpdatedInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;

/*
 * #%L
 * de.metas.business.rest-api-impl
     
 * #L%
 */

@Value
@Builder
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class JsonProduct
{
	@Schema(type = "java.lang.Integer", //
			description = "This translates to `M_Product.M_Product_ID`.")
	@NonNull
	ProductId id;

	@Schema(description = "This translates to `M_Product.Value`.")
	@NonNull
	String productNo;

	@NonNull
	String name;

	@Nullable
	String description;

	@Schema(description = "This translates to `M_Product.UPC`.<br>Note that different bPartners may assign different EANs to the same product")
	@Nullable
	@JsonInclude(Include.NON_EMPTY)
	String ean;

	@Schema(description = "This translates to `M_Product.ExternalId`.")
	@Nullable
	@JsonInclude(Include.NON_EMPTY)
	String externalId;

	@Schema(description = "This is the `C_UOM.UOMSymbol` of the product's unit of measurement.")
	@NonNull
	String uom;

	@NonNull
	@Singular
	List<JsonProductBPartner> bpartners;

	@NonNull
	JsonCreatedUpdatedInfo createdUpdatedInfo;
}
