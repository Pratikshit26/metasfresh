package de.metas.impexp;

import de.metas.impexp.config.DataImportConfigId;
import de.metas.organization.OrgId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.service.ClientId;
import org.adempiere.util.api.IParams;
import org.adempiere.util.api.Params;
import org.springframework.core.io.Resource;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@Value
@Builder
public class DataImportRequest
{
	@NonNull Resource data;
	@NonNull DataImportConfigId dataImportConfigId;
	@NonNull ClientId clientId;
	@NonNull OrgId orgId;
	@NonNull UserId userId;

	boolean completeDocuments;

	@NonNull @Default IParams additionalParameters = IParams.NULL;

	boolean processImportRecordsSynchronously;
	boolean stopOnFirstError;

	@Nullable Params overrideColumnValues;

	@Nullable ImportRecordsRequest.LogMigrationScriptsSpec logMigrationScriptsSpec;
}
