package de.metas;

import org.compiere.model.ModelValidationEngine;
import org.slf4j.Logger;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import de.metas.logging.LogManager;
import de.metas.logging.MetasfreshIssueAppender;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@Component
public class ApplicationReadyListener
{
	private static final Logger logger = LogManager.getLogger(ApplicationReadyListener.class);

	@EventListener(ApplicationReadyEvent.class)
	@Order(Orders.FORCE_MODEL_VALIDATION_ENGINE_INIT)
	public void initModelValidationEngine()
	{
		logger.info("Forcing ModelValidationEngine to be inittialized if it wasn't done yet");
		ModelValidationEngine.get();
	}

	@EventListener(ApplicationReadyEvent.class)
	@Order(Orders.ENABLE_ISSUE_LOG_APPENDER) // make sure this executes before ApplicationReadyEvent listeners that have no explicit order
	public void enableIssueReporting()
	{
		final MetasfreshIssueAppender metasfreshIssueAppender = MetasfreshIssueAppender.get();
		if (metasfreshIssueAppender == null)
		{
			logger.info("MetasfreshIssueAppender is NOT configured => won't create AD_Issue records for error log messages ");
			return;
		}
		logger.info("Enabling MetasfreshIssueAppender to create AD_Issue records for error log messages");
		metasfreshIssueAppender.enableIssueReporting();
	}

}
