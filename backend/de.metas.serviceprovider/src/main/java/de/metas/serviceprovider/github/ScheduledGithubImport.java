/*
 * #%L
 * de.metas.serviceprovider.base
     
 * #L%
 */

package de.metas.serviceprovider.github;

import de.metas.process.Param;

import java.time.LocalDate;

public class ScheduledGithubImport extends GithubImportProcess
{
	@Param(parameterName = "OffsetDays")
	private int offsetDays;

	@Override
	protected String doIt() throws Exception
	{
		final LocalDate dateFrom = LocalDate.now().minusDays(offsetDays);

		setDateFrom(dateFrom);

		return super.doIt();
	}
}
