/*
 * #%L
 * de.metas.handlingunits.base
     
 * #L%
 */

package de.metas.handlingunits.shipping.process;

import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.report.ExecuteReportStrategy;
import de.metas.report.ReportStarter;
import lombok.NonNull;

public class PrintAllShipmentDocuments extends ReportStarter implements IProcessPrecondition
{
	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.accept();
		}
		return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
	}

	@Override
	protected ExecuteReportStrategy getExecuteReportStrategy()
	{
		return new PrintAllShipmentsDocumentsStrategy();
	}
}
