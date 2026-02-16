/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

package de.metas.attachments.listener;

public interface AttachmentListenerConstants
{
	enum ListenerWorkStatus
	{
		SUCCESS("OK"),

		NOT_APPLIED("NOT_APPLIED"),

		FAILURE("ERROR");

		private String value;

		ListenerWorkStatus(final String value)
		{
			this.value = value;
		}

		public String getValue()
		{
			return value;
		}
	}
}
