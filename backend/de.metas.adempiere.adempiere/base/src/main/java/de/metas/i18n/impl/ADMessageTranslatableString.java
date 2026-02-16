package de.metas.i18n.impl;

import com.google.common.collect.ImmutableList;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ILanguageBL;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.Msg;
import de.metas.util.Services;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@EqualsAndHashCode
final class ADMessageTranslatableString implements ITranslatableString
{
	private final AdMessageKey adMessage;
	private final List<Object> msgParameters;

	ADMessageTranslatableString(@NonNull final AdMessageKey adMessage, @Nullable final Object... msgParameters)
	{
		this.adMessage = adMessage;
		if (msgParameters == null || msgParameters.length == 0)
		{
			this.msgParameters = ImmutableList.of();
		}
		else
		{
			// NOTE: avoid using ImmutableList because there might be null parameters
			this.msgParameters = Collections.unmodifiableList(Arrays.asList(msgParameters));
		}
	}

	@Override
	@Deprecated
	public String toString()
	{
		return adMessage.toAD_Message();
	}

	@Override
	public String translate(final String adLanguage)
	{
		return Msg.getMsg(adLanguage, adMessage.toAD_Message(), msgParameters.toArray());
	}

	@Override
	public String getDefaultValue()
	{
		return adMessage.toAD_MessageWithMarkers();
	}

	@Override
	public Set<String> getAD_Languages()
	{
		return Services.get(ILanguageBL.class).getAvailableLanguages().getAD_Languages();
	}

}
