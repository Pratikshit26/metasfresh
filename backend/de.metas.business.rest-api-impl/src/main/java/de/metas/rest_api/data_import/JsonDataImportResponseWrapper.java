package de.metas.rest_api.data_import;

import java.util.List;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.ImmutableList;

import de.metas.common.rest_api.v1.JsonErrorItem;
import de.metas.rest_api.utils.JsonErrors;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business.rest-api-impl
     
 * #L%
 */

/** Wraps the actual response and the error */
@Value
public class JsonDataImportResponseWrapper
{
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Nullable
	List<JsonErrorItem> errors;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Nullable
	JsonDataImportResponse response;

	public static JsonDataImportResponseWrapper ok(final JsonDataImportResponse response)
	{
		final List<JsonErrorItem> errors = null;
		return new JsonDataImportResponseWrapper(errors, response);
	}

	public static JsonDataImportResponseWrapper error(@NonNull final List<JsonErrorItem> errors)
	{
		final JsonDataImportResponse response = null;
		return new JsonDataImportResponseWrapper(errors, response);
	}

	public static JsonDataImportResponseWrapper error(@NonNull final JsonDataImportResponse response)
	{
		final List<JsonErrorItem> errors = null;
		return new JsonDataImportResponseWrapper(errors, response);
	}

	public static JsonDataImportResponseWrapper error(final Throwable throwable, final String adLanguage)
	{
		final List<JsonErrorItem> errors = ImmutableList.of(JsonErrors.ofThrowable(throwable, adLanguage));
		final JsonDataImportResponse response = null;
		return new JsonDataImportResponseWrapper(errors, response);
	}
}
