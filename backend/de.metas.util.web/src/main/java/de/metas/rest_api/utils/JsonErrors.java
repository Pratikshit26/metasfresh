package de.metas.rest_api.utils;

import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v1.JsonErrorItem;
import de.metas.common.rest_api.v1.JsonErrorItem.JsonErrorItemBuilder;
import de.metas.i18n.ITranslatableString;
import de.metas.util.GuavaCollectors;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Null;
import org.compiere.util.Trace;

import javax.annotation.Nullable;
import java.util.Map;

/*
 * #%L
 * de.metas.business.rest-api-impl
     
 * #L%
 */

@UtilityClass
public class JsonErrors
{
	public static JsonErrorItem ofThrowable(
			@NonNull final Throwable throwable,
			@NonNull final String adLanguage)
	{
		return ofThrowable(throwable, adLanguage, null);
	}

	public static JsonErrorItem ofThrowable(
			@NonNull final Throwable throwable,
			@NonNull final String adLanguage,
			@Nullable final ITranslatableString detail)
	{
		final Throwable cause = AdempiereException.extractCause(throwable);

		JsonMetasfreshId adIssueId = null;
		String issueCategory = null;
		for(Throwable currentException = throwable; currentException != null; currentException = currentException.getCause())
		{
			// Stop if we found what we are looking for
			if(adIssueId != null && issueCategory != null)
			{
				break;
			}
			else if (currentException instanceof AdempiereException)
			{
				final AdempiereException metasfreshException = (AdempiereException)currentException;
				if(adIssueId == null && metasfreshException.getAdIssueId() != null)
				{
					adIssueId = JsonMetasfreshId.of(metasfreshException.getAdIssueId().getRepoId());
				}

				if(issueCategory == null)
				{
					issueCategory = metasfreshException.getIssueCategory().toString();
				}
			}
		}

		final JsonErrorItemBuilder builder = JsonErrorItem.builder()
				.message(AdempiereException.extractMessageTrl(cause).translate(adLanguage))
				.errorCode(AdempiereException.extractErrorCodeOrNull(cause))
				.userFriendlyError(AdempiereException.isUserValidationError(cause))
				.stackTrace(Trace.toOneLineStackTraceString(cause.getStackTrace()))
				.adIssueId(adIssueId)
				.issueCategory(issueCategory)
				.parameters(extractParameters(throwable, adLanguage))
				.throwable(throwable);
		if (detail != null)
		{
			builder.detail(detail.translate(adLanguage));
		}
		return builder.build();
	}

	private static Map<String, String> extractParameters(@NonNull final Throwable throwable, @NonNull final String adLanguage)
	{
		return AdempiereException.extractParameters(throwable)
				.entrySet()
				.stream()
				.map(e -> GuavaCollectors.entry(e.getKey(), convertParameterToJson(e.getValue(), adLanguage)))
				.collect(GuavaCollectors.toImmutableMap());
	}

	@NonNull
	private static String convertParameterToJson(final Object value, final String adLanguage)
	{
		if (Null.isNull(value))
		{
			return "<null>";
		}
		else if (value instanceof ITranslatableString)
		{
			return ((ITranslatableString)value).translate(adLanguage);
		}
		else if (value instanceof RepoIdAware)
		{
			return String.valueOf(((RepoIdAware)value).getRepoId());
		}
		else if (value instanceof ReferenceListAwareEnum)
		{
			return ((ReferenceListAwareEnum)value).getCode();
		}
		else
		{
			return value.toString();
		}
	}
}
