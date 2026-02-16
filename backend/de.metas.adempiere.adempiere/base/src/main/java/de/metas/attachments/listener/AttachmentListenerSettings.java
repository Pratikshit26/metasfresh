/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

package de.metas.attachments.listener;

import de.metas.i18n.AdMessageId;
import de.metas.javaclasses.JavaClassId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@Value
public class AttachmentListenerSettings
{
	@NonNull
	JavaClassId listenerJavaClassId;

	boolean isSendNotification;

	@Nullable
	AdMessageId adMessageId;

	@Builder
	private AttachmentListenerSettings(
			@NonNull final JavaClassId listenerJavaClassId,
			final boolean isSendNotification,
			@Nullable final AdMessageId adMessageId)
	{
		if (isSendNotification && adMessageId == null)
		{
			throw new AdempiereException("An AD_Message_ID must be set if notifications are enabled!");
		}

		this.listenerJavaClassId = listenerJavaClassId;
		this.isSendNotification = isSendNotification;
		this.adMessageId = adMessageId;
	}
}
