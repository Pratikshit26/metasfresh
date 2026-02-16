package de.metas.email.mailboxes;

import org.adempiere.exceptions.AdempiereException;

import de.metas.i18n.ITranslatableString;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@SuppressWarnings("serial")
public final class MailboxNotFoundException extends AdempiereException
{
	public MailboxNotFoundException(final ITranslatableString msg)
	{
		super(msg);
	}
}
