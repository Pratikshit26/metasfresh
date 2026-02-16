package de.metas.dataentry.layout;

import com.jgoodies.common.base.Objects;

import de.metas.dataentry.DataEntryListValueId;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
     
 * #L%
 */

@Value
@Builder
public class DataEntryListValue
{
	@NonNull
	DataEntryListValueId id;

	@NonNull
	ITranslatableString name;

	@NonNull
	@Default
	ITranslatableString description = TranslatableStrings.empty();

	public boolean isNameMatching(@NonNull final String pattern)
	{
		final String patternNorm = normalizeString(pattern);
		if (patternNorm == null)
		{
			return false;
		}

		final String defaultNameNorm = normalizeString(name.getDefaultValue());
		if (Objects.equals(defaultNameNorm, patternNorm))
		{
			return true;
		}

		for (final String adLanguage : name.getAD_Languages())
		{
			final String nameNorm = normalizeString(name.translate(adLanguage));
			if (Objects.equals(nameNorm, patternNorm))
			{
				return true;
			}
		}

		return false;
	}

	private static final String normalizeString(String str)
	{
		if (str == null)
		{
			return null;
		}

		final String strNorm = str.trim();
		if (strNorm.isEmpty())
		{
			return null;
		}

		return strNorm;
	}
}
