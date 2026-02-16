package de.metas.email.mailboxes;

import de.metas.i18n.ExplainedOptional;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.service.ClientId;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@Value
@Builder
public class ClientEMailConfig
{
	@NonNull ClientId clientId;
	@NonNull ExplainedOptional<Mailbox> mailbox;

	public Mailbox getMailboxNotNull()
	{
		return mailbox.orElseThrow(MailboxNotFoundException::new);
	}
}
