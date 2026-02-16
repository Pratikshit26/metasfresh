package de.metas.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.function.Supplier;

/*
 * #%L
 * de.metas.util
     
 * #L%
 */

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public enum OptionalBoolean
{
	TRUE, FALSE, UNKNOWN;

	public static OptionalBoolean ofBoolean(final boolean value)
	{
		return value ? TRUE : FALSE;
	}

	@JsonCreator
	public static OptionalBoolean ofNullableBoolean(@Nullable final Boolean value)
	{
		return value != null ? ofBoolean(value) : UNKNOWN;
	}

	public static OptionalBoolean ofNullableString(@Nullable final String value)
	{
		return ofNullableBoolean(StringUtils.toBooleanOrNull(value));
	}

	public boolean isTrue()
	{
		return this == TRUE;
	}

	public boolean isFalse()
	{
		return this == FALSE;
	}

	public boolean isPresent()
	{
		return this == TRUE || this == FALSE;
	}

	public boolean isUnknown()
	{
		return this == UNKNOWN;
	}

	public boolean orElseTrue() {return orElse(true);}

	public boolean orElseFalse() {return orElse(false);}

	public boolean orElse(final boolean other)
	{
		if (this == TRUE)
		{
			return true;
		}
		else if (this == FALSE)
		{
			return false;
		}
		else
		{
			return other;
		}
	}

	@NonNull
	public OptionalBoolean ifUnknown(@NonNull final OptionalBoolean other)
	{
		return isPresent() ? this : other;
	}

	@NonNull
	public OptionalBoolean ifUnknown(@NonNull final Supplier<OptionalBoolean> otherSupplier)
	{
		return isPresent() ? this : otherSupplier.get();
	}

	@JsonValue
	@Nullable
	public Boolean toBooleanOrNull()
	{
		switch (this)
		{
			case TRUE:
				return Boolean.TRUE;
			case FALSE:
				return Boolean.FALSE;
			case UNKNOWN:
				return null;
			default:
				throw new IllegalStateException("Type not handled: " + this);
		}
	}

	public void ifPresent(@NonNull final BooleanConsumer action)
	{
		if (this == TRUE)
		{
			action.accept(true);
		}
		else if (this == FALSE)
		{
			action.accept(false);
		}
	}

}
