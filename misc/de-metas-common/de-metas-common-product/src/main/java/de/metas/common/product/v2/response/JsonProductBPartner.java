package de.metas.common.product.v2.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business.rest-api-impl
     
 * #L%
 */

@Value
@Builder
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@JsonDeserialize(builder = JsonProductBPartner.JsonProductBPartnerBuilder.class)
public class JsonProductBPartner
{
	@Schema(description = "This translates to `C_BPartner_ID`.")
	@NonNull
	@JsonProperty("bPartnerId")
	JsonMetasfreshId bpartnerId;

	@JsonProperty("productNo")
	String productNo;

	@JsonProperty("productName")
	String productName;

	@JsonProperty("productDescription")
	String productDescription;

	@JsonProperty("productCategory")
	String productCategory;

	@JsonProperty("ean")
	String ean;

	@JsonProperty("vendor")
	boolean vendor;

	@JsonProperty("currentVendor")
	boolean currentVendor;

	@JsonProperty("customer")
	boolean customer;

	@JsonProperty("leadTimeInDays")
	int leadTimeInDays;

	@JsonProperty("excludedFromSale")
	boolean excludedFromSale;

	@JsonProperty("exclusionFromSaleReason")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String exclusionFromSaleReason;

	@JsonProperty("excludedFromPurchase")
	boolean excludedFromPurchase;

	@JsonProperty("exclusionFromPurchaseReason")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String exclusionFromPurchaseReason;

	@JsonProperty("productId")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	JsonMetasfreshId productId;
}
