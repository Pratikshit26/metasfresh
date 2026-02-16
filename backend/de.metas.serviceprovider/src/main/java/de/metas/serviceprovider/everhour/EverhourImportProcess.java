/*
 * #%L
 * de.metas.serviceprovider.base
     
 * #L%
 */

package de.metas.serviceprovider.everhour;

import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.serviceprovider.timebooking.importer.ImportTimeBookingsRequest;
import de.metas.serviceprovider.timebooking.importer.TimeBookingsImporterService;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ISysConfigBL;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;

import java.time.LocalDate;

import static de.metas.serviceprovider.everhour.EverhourImportConstants.EverhourImporterSysConfig.ACCESS_TOKEN;

public class EverhourImportProcess extends JavaProcess
{
	@Param(parameterName = "DateFrom")
	private LocalDate dateFrom;

	@Param(parameterName = "DateTo")
	private LocalDate dateTo;

	private final EverhourImporterService everhourImporterService = SpringContextHolder.instance.getBean(EverhourImporterService.class);
	private final TimeBookingsImporterService timeBookingsImporterService = SpringContextHolder.instance.getBean(TimeBookingsImporterService.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	@Override protected String doIt() throws Exception
	{
		final ImportTimeBookingsRequest timeBookingsRequest = ImportTimeBookingsRequest
				.builder()
				.orgId(Env.getOrgId())
				.authToken(sysConfigBL.getValue(ACCESS_TOKEN.getName()))
				.startDate(dateFrom)
				.endDate(dateTo)
				.build();

		timeBookingsImporterService.importTimeBookings(everhourImporterService, timeBookingsRequest);

		return MSG_OK;
	}

	protected void overwriteParameters(@NonNull final LocalDate dateFrom, @NonNull final LocalDate dateTo)
	{
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
	}
}
