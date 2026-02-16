/*
 * #%L
 * de.metas.shipper.gateway.dpd
     
 * #L%
 */

package de.metas.shipper.gateway.dpd.logger;

import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.logging.LogManager;
import de.metas.shipper.gateway.dpd.model.I_DPD_StoreOrder_Log;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.slf4j.Logger;

public class DpdDatabaseClientLogger
{
	public static final transient DpdDatabaseClientLogger instance = new DpdDatabaseClientLogger();
	private static final Logger logger = LogManager.getLogger(DpdDatabaseClientLogger.class);

	private DpdDatabaseClientLogger()
	{
	}

	public void log(@NonNull final DpdClientLogEvent event)
	{
		try
		{
			createLogRecord(event);
		}
		catch (final Exception ex)
		{
			logger.warn("Failed creating Dpd log record: {}", event, ex);
		}

		normalLogging(event);
	}

	private void normalLogging(final DpdClientLogEvent event)
	{
		if (!logger.isTraceEnabled())
		{
			return;
		}

		if (event.getResponseException() != null)
		{
			logger.trace("Dpd Send/Receive error: {}", event, event.getResponseException());
		}
		else
		{
			logger.trace("Dpd Send/Receive OK: {}", event);
		}
	}

	private void createLogRecord(@NonNull final DpdClientLogEvent event)
	{
		final I_DPD_StoreOrder_Log logRecord = InterfaceWrapperHelper.newInstance(I_DPD_StoreOrder_Log.class);
		logRecord.setConfigSummary(event.getConfigSummary());
		logRecord.setDurationMillis((int)event.getDurationMillis());

		logRecord.setDPD_StoreOrder_ID(event.getDeliveryOrderRepoId());

		//noinspection ConstantConditions
		logRecord.setRequestMessage(event.getRequestAsString());

		if (event.getResponseException() != null)
		{
			logRecord.setIsError(true);

			final AdIssueId issueId = Services.get(IErrorManager.class).createIssue(event.getResponseException());
			logRecord.setAD_Issue_ID(issueId.getRepoId());
		}
		else
		{
			logRecord.setIsError(false);
			//noinspection ConstantConditions
			logRecord.setResponseMessage(event.getResponseAsString());
		}

		InterfaceWrapperHelper.save(logRecord);
	}
}
