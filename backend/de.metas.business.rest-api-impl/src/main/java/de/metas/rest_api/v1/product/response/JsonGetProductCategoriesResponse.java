package de.metas.rest_api.v1.product.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import de.metas.common.rest_api.v1.JsonErrorItem;
import de.metas.rest_api.utils.JsonErrors;
import lombok.Builder;
import lombok.NonNull;
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
public class JsonGetProductCategoriesResponse
{
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Singular
	List<JsonProductCategory> productCategories;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Singular
	List<JsonErrorItem> errors;

	public static JsonGetProductCategoriesResponse error(@NonNull final Throwable throwable, @NonNull final String adLanguage)
	{
		return builder()
				.error(JsonErrors.ofThrowable(throwable, adLanguage))
				.build();
	}
}
