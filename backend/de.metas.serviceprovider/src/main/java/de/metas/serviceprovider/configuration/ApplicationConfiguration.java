/*
 * #%L
 * de.metas.serviceprovider.base
     
 * #L%
 */

package de.metas.serviceprovider.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.externalreference.ExternalReferenceTypes;
import de.metas.externalreference.ExternalSystems;
import de.metas.i18n.IMsgBL;
import de.metas.serviceprovider.ImportQueue;
import de.metas.serviceprovider.external.ExternalSystem;
import de.metas.serviceprovider.external.reference.ExternalServiceReferenceType;
import de.metas.serviceprovider.issue.importer.info.ImportIssueInfo;
import de.metas.serviceprovider.timebooking.importer.ImportTimeBookingInfo;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static de.metas.serviceprovider.issue.importer.ImportConstants.IMPORT_LOG_MESSAGE_PREFIX;
import static de.metas.serviceprovider.issue.importer.ImportConstants.ISSUE_QUEUE_CAPACITY;
import static de.metas.serviceprovider.timebooking.importer.ImportConstants.IMPORT_TIME_BOOKINGS_LOG_MESSAGE_PREFIX;
import static de.metas.serviceprovider.timebooking.importer.ImportConstants.TIME_BOOKING_QUEUE_CAPACITY;

@Configuration
public class ApplicationConfiguration
{
	public ApplicationConfiguration(
			@NonNull final ExternalReferenceTypes externalReferenceTypes,
			@NonNull final ExternalSystems externalSystems)
	{
		externalReferenceTypes.registerType(ExternalServiceReferenceType.ISSUE_ID);
		externalReferenceTypes.registerType(ExternalServiceReferenceType.TIME_BOOKING_ID);
		externalReferenceTypes.registerType(ExternalServiceReferenceType.MILESTONE_ID);

		externalSystems.registerExternalSystem(ExternalSystem.EVERHOUR);
		externalSystems.registerExternalSystem(ExternalSystem.GITHUB);
	}

	@Bean
	public IUserDAO userDAO()
	{
		return Services.get(IUserDAO.class);
	}

	@Bean
	public ITrxManager trxManager()
	{
		return Services.get(ITrxManager.class);
	}

	@Bean
	public IQueryBL queryBL()
	{
		return Services.get(IQueryBL.class);
	}

	@Bean
	public ImportQueue<ImportTimeBookingInfo> timeBookingImportQueue()
	{
		return new ImportQueue<>(TIME_BOOKING_QUEUE_CAPACITY, IMPORT_TIME_BOOKINGS_LOG_MESSAGE_PREFIX);
	}

	@Bean
	public ImportQueue<ImportIssueInfo> importIssuesQueue()
	{
		return new ImportQueue<>(ISSUE_QUEUE_CAPACITY, IMPORT_LOG_MESSAGE_PREFIX);
	}

	@Bean
	public ObjectMapper objectMapper()
	{
		return JsonObjectMapperHolder.sharedJsonObjectMapper();
	}

	@Bean
	public IMsgBL msgBL()
	{
		return Services.get(IMsgBL.class);
	}
}
