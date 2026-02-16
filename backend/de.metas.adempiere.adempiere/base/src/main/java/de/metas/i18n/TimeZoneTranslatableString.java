package de.metas.i18n;

import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import lombok.EqualsAndHashCode;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@EqualsAndHashCode
final class TimeZoneTranslatableString implements ITranslatableString
{
	public static TimeZoneTranslatableString ofZoneId(@NonNull final ZoneId zoneId, @NonNull final TextStyle textStyle)
	{
		return new TimeZoneTranslatableString(zoneId, textStyle);
	}

	private final ZoneId zoneId;
	private final TextStyle textStyle;

	private TimeZoneTranslatableString(@NonNull final ZoneId zoneId, @NonNull final TextStyle textStyle)
	{
		this.zoneId = zoneId;
		this.textStyle = textStyle;
	}

	@Deprecated
	@Override
	public String toString()
	{
		return zoneId.toString();
	}

	@Override
	public String translate(final String adLanguage)
	{
		final Language language = Language.getLanguage(adLanguage);
		final Locale locale = language.getLocale();
		return zoneId.getDisplayName(textStyle, locale);
	}

	@Override
	public String getDefaultValue()
	{
		return zoneId.getId();
	}

	@Override
	public Set<String> getAD_Languages()
	{
		return ImmutableSet.of();
	}

}
