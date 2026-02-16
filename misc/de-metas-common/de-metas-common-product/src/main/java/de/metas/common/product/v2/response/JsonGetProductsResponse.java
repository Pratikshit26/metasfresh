package de.metas.common.product.v2.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.List;

/*
 * #%L
 * de.metas.business.rest-api-impl
     
 * #L%
 */

@Value
@Builder
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@JsonDeserialize(builder = JsonGetProductsResponse.JsonGetProductsResponseBuilder.class)
public class JsonGetProductsResponse
{
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("products")
	@Singular
	List<JsonProduct> products;
}
