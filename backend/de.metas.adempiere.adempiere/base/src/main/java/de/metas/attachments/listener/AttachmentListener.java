/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

package de.metas.attachments.listener;

import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.listener.AttachmentListenerConstants.ListenerWorkStatus;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Table_AttachmentListener;

/**
 * Listeners subscribed to attaching files to tables should implement this interface.
 * Implementations need to be registered in the table {@link I_AD_Table_AttachmentListener#Table_Name}.
 */
public interface AttachmentListener
{
	ListenerWorkStatus afterRecordLinked(AttachmentEntry attachmentEntry, TableRecordReference tableRecordReference);

	default ListenerWorkStatus beforeRecordLinked(AttachmentEntry attachmentEntry, TableRecordReference tableRecordReference)
	{
		return ListenerWorkStatus.NOT_APPLIED;
	}
}
