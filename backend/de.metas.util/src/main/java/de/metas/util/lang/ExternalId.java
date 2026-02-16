package de.metas.util.lang;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.business.rest-api
     
 * #L%
 */

@Value
public class ExternalId implements Comparable<ExternalId>
{
	/**
	 * Can be used to distinguish between an external ID that was not yet set and one that was already set to "can't be used or set".
	 */
	public static final ExternalId INVALID = ExternalId.of("INVALID");

	String value;

	@JsonCreator
	@NonNull
	public static ExternalId of(@NonNull final String value)
	{
		return new ExternalId(value);
	}

	@Nullable
	public static ExternalId ofOrNull(@Nullable final String value)
	{
		if (Check.isBlank(value))
		{
			return null;
		}
		return new ExternalId(value);
	}

	@Nullable
	public static String toValue(@Nullable final ExternalId externalId)
	{
		if (externalId == null)
		{
			return null;
		}
		return externalId.getValue();
	}

	/**
	 * @return {@code true} if the given {@code externalId} is the same as {@link #INVALID}.
	 */
	public static boolean isInvalid(@Nullable final ExternalId externalId)
	{
		if (externalId == null)
		{
			return false;
		}
		return externalId == INVALID;
	}

	@JsonValue
	public String getValue()
	{
		return value;
	}

	@Override
	public int compareTo(@NonNull final ExternalId o)
	{
		return value.compareTo(o.value);
	}
}
