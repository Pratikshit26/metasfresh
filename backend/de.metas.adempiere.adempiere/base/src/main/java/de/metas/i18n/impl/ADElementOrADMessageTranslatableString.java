package de.metas.i18n.impl;

import de.metas.i18n.ILanguageBL;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.Msg;
import de.metas.util.Services;
import lombok.NonNull;

import java.util.Properties;
import java.util.Set;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

/**
 * Wraps a given <code>text</code> and will call {@link Msg#translate(Properties, String, boolean)}.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@lombok.EqualsAndHashCode
final class ADElementOrADMessageTranslatableString implements ITranslatableString
{
	private final String text;

	ADElementOrADMessageTranslatableString(@NonNull final String text)
	{
		this.text = text;
	}

	@Override
	public String toString()
	{
		return text;
	}

	@Override
	public String translate(final String adLanguage)
	{
		final boolean isSOTrx = true;
		return Msg.translate(adLanguage, isSOTrx, text);
	}

	@Override
	public String getDefaultValue()
	{
		return "@" + text + "@";
	}

	@Override
	public Set<String> getAD_Languages()
	{
		return Services.get(ILanguageBL.class).getAvailableLanguages().getAD_Languages();
	}

}
