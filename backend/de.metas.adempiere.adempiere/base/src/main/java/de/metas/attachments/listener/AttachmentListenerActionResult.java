/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

package de.metas.attachments.listener;

import lombok.NonNull;
import lombok.Value;
import org.adempiere.util.lang.impl.TableRecordReference;

@Value
public class AttachmentListenerActionResult
{
	@NonNull
	AttachmentListener listener;

	@NonNull
	AttachmentListenerConstants.ListenerWorkStatus status;

	@NonNull
	TableRecordReference appliedToRecord;
}
