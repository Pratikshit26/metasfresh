package de.metas.handlingunits.expiry.process;

import java.time.LocalDate;

import de.metas.common.util.time.SystemTime;
import org.compiere.SpringContextHolder;

import de.metas.handlingunits.expiry.HUWithExpiryDatesService;
import de.metas.handlingunits.expiry.UpdateMonthsResult;
import de.metas.process.JavaProcess;
import de.metas.process.RunOutOfTrx;

/*
 * #%L
 * de.metas.handlingunits.base
     
 * #L%
 */

public class M_HU_Update_MonthsUntilExpiry_Attribute extends JavaProcess
{
	private final HUWithExpiryDatesService huWithExpiryDatesService = SpringContextHolder.instance.getBean(HUWithExpiryDatesService.class);

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		final LocalDate today = SystemTime.asLocalDate();
		addLog("Today is: " + today);

		final UpdateMonthsResult result = huWithExpiryDatesService.updateMonthsUntilExpiry(today);
		addLog("Result: " + result);

		return MSG_OK;
	}
}
