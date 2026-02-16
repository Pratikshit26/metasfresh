/*
 * #%L
 * de.metas.serviceprovider.base
     
 * #L%
 */

package de.metas.serviceprovider.everhour;

import de.metas.process.Param;

import java.time.LocalDate;

public class ScheduledEverhourImport extends EverhourImportProcess
{
	@Param(parameterName = "OffsetDays")
	private int offsetDays;

	@Override protected String doIt() throws Exception
	{
		final LocalDate dateFrom =  LocalDate.now().minusDays(offsetDays);
		final LocalDate dateTo = LocalDate.now();

		overwriteParameters(dateFrom, dateTo);

		return super.doIt();
	}
}
