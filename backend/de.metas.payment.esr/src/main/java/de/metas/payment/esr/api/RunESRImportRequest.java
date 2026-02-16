/*
 * #%L
 * de.metas.payment.esr
     
 * #L%
 */

package de.metas.payment.esr.api;

import de.metas.attachments.AttachmentEntryId;
import de.metas.payment.esr.model.I_ESR_Import;
import de.metas.process.PInstanceId;
import de.metas.util.ILoggable;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class RunESRImportRequest
{
	@NonNull
	I_ESR_Import esrImport;

	@NonNull
	AttachmentEntryId attachmentEntryId;

	@NonNull
	String asyncBatchName;

	@NonNull
	String asyncBatchDescription;

	@NonNull
	ILoggable loggable;

	@Nullable
	PInstanceId pInstanceId;
}
