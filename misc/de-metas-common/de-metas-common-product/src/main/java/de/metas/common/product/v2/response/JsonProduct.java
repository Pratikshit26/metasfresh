package de.metas.common.product.v2.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.metas.common.product.v2.response.alberta.JsonAlbertaProductInfo;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;

/*
 * #%L
 * de.metas.business.rest-api-impl
     
 * #L%
 */

@Value
@Builder
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@JsonDeserialize(builder = JsonProduct.JsonProductBuilder.class)
public class JsonProduct
{
	@Schema(minLength = 1,
			description = "This translates to `M_Product.M_Product_ID`.")
	@NonNull
	@JsonProperty("id")
	JsonMetasfreshId id;

	@Schema(description = "This translates to `M_Product.M_Product_Category_ID`.")
	@NonNull
	@JsonProperty("productCategoryId")
	JsonMetasfreshId productCategoryId;

	@Schema(description = "This translates to `M_Product.Value`.")
	@NonNull
	@JsonProperty("productNo")
	String productNo;

	@NonNull
	@JsonProperty("name")
	String name;

	@Nullable
	@JsonProperty("description")
	String description;

	@Schema(description = "This translates to `M_Product.UPC`.<br>Note that different bPartners may assign different EANs to the same product")
	@Nullable
	@JsonInclude(Include.NON_EMPTY)
	@JsonProperty("ean")
	String ean;

	@Schema(description = "This translates to `M_Product.ExternalId`.")
	@Nullable
	@JsonInclude(Include.NON_EMPTY)
	@JsonProperty("externalId")
	String externalId;

	@Schema(description = "This is the `C_UOM.UOMSymbol` of the product's unit of measurement.")
	@NonNull
	@JsonProperty("uom")
	String uom;

	@Schema(description = "This translates to `M_Product.Manufacturer_ID`.")
	@Nullable
	@JsonProperty("manufacturerId")
	JsonMetasfreshId manufacturerId;

	@Schema(description = "This translates to `C_BPartner.Name` of the product's manufacturer.")
	@Nullable
	@JsonProperty("manufacturerName")
	String manufacturerName;

	@Schema(description = "This translates to `M_Product.ManufacturerArticleNumber`.")
	@Nullable
	@JsonProperty("manufacturerNumber")
	String manufacturerNumber;

	@Schema(description = "This translates to `M_Product.DiscontinuedFrom`.")
	@Nullable
	@JsonProperty("discontinuedFrom")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	LocalDate discontinuedFrom;

	@Schema(description = "This translates to `M_Product.M_SectionCode_ID.Value`.")
	@Nullable
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("sectionCode")
	String sectionCode;

	@Schema(description = "This translates to `M_Product.SAP_ProductHierarchy`.")
	@Nullable
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("sapProductHierarchy")
	String sapProductHierarchy;

	@NonNull
	@Singular
	@JsonProperty("bpartners")
	List<JsonProductBPartner> bpartners;

	@Nullable
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("albertaProductInfo")
	JsonAlbertaProductInfo albertaProductInfo;
}
