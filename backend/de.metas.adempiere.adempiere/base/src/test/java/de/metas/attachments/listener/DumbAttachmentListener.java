/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

package de.metas.attachments.listener;

import de.metas.attachments.AttachmentEntry;
import org.adempiere.util.lang.impl.TableRecordReference;

import static de.metas.attachments.listener.AttachmentListenerConstants.ListenerWorkStatus.SUCCESS;

/**
 *  Dumb attachment listener; created only for test purposes.
 */
public class DumbAttachmentListener implements AttachmentListener
{
	@Override public AttachmentListenerConstants.ListenerWorkStatus afterRecordLinked(AttachmentEntry attachmentEntry, TableRecordReference tableRecordReference)
	{
		return SUCCESS;
	}
}
