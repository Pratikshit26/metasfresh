/*
 * #%L
 * de.metas.payment.esr
     
 * #L%
 */

package de.metas.payment.esr.listener;

import ch.qos.logback.classic.Level;
import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.listener.AttachmentListener;
import de.metas.attachments.listener.AttachmentListenerConstants;
import de.metas.javaclasses.model.I_AD_JavaClass;
import de.metas.logging.LogManager;
import de.metas.payment.esr.api.IESRImportBL;
import de.metas.payment.esr.api.RunESRImportRequest;
import de.metas.payment.esr.model.I_ESR_Import;
import de.metas.util.Loggables;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.slf4j.Logger;

import static de.metas.attachments.listener.AttachmentListenerConstants.ListenerWorkStatus.SUCCESS;
import static de.metas.payment.esr.ESRConstants.ESR_ASYNC_BATCH_DESC;
import static de.metas.payment.esr.ESRConstants.ESR_ASYNC_BATCH_NAME;

/**
 * Important: when renaming this class, please make sure to also update its {@link I_AD_JavaClass} record.
 */
public class ESRImportAttachmentListener implements AttachmentListener
{
	private static final Logger logger = LogManager.getLogger(ESRImportAttachmentListener.class);

	private final transient IESRImportBL esrImportBL = Services.get(IESRImportBL.class);

	@Override
	public AttachmentListenerConstants.ListenerWorkStatus afterRecordLinked(
			final AttachmentEntry attachmentEntry,
			final TableRecordReference tableRecordReference)
	{
		final I_ESR_Import esrImport = InterfaceWrapperHelper.load(tableRecordReference.getRecord_ID(), I_ESR_Import.class);

		final boolean isZipAttachment = attachmentEntry.getFilename().endsWith(".zip");

		esrImport.setIsArchiveFile(isZipAttachment);
		final RunESRImportRequest runESRImportRequest = RunESRImportRequest.builder()
				.esrImport(esrImport)
				.attachmentEntryId(attachmentEntry.getId())
				.asyncBatchDescription(ESR_ASYNC_BATCH_DESC)
				.asyncBatchName(ESR_ASYNC_BATCH_NAME)
				.loggable(Loggables.withLogger(logger, Level.DEBUG))
				.build();
		
		esrImportBL.scheduleESRImportFor(runESRImportRequest);

		return SUCCESS;
	}
}
