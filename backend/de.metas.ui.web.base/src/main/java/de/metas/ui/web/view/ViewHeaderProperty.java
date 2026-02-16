package de.metas.ui.web.view;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

/*
 * #%L
 * metasfresh-webui-api
     
 * #L%
 */

@Value
@Builder
public class ViewHeaderProperty
{
	@Nullable String fieldName;
	@NonNull ITranslatableString caption;
	@NonNull ITranslatableString value;

	@SuppressWarnings("unused")
	public static class ViewHeaderPropertyBuilder
	{
		public ViewHeaderPropertyBuilder value(final ITranslatableString value)
		{
			this.value = value;
			return this;
		}

		public ViewHeaderPropertyBuilder value(final String value)
		{
			return value(TranslatableStrings.anyLanguage(value));
		}

		public ViewHeaderPropertyBuilder value(final ZonedDateTime value)
		{
			return value(TranslatableStrings.dateAndTime(value));
		}

		public ViewHeaderPropertyBuilder value(final int value)
		{
			return value(TranslatableStrings.number(value));
		}
	}
}
