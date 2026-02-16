package de.metas.contracts.refund;

import de.metas.common.util.time.SystemTime;
import lombok.NonNull;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.contracts
     
 * #L%
 */

public class RefundTestToolsTest
{

	/**
	 * make sure that RefundTestTools#computeInvoiceScheduleDayOfMonth() is always between 1 and 28
	 */
	@Test
	public void computeInvoiceScheduleDayOfMonth()
	{
		assertResultOk("2019-02-22", 28);
		assertResultOk("2019-02-23", 1);
		assertResultOk("2019-02-24", 2);
		assertResultOk("2019-02-25", 3);
		assertResultOk("2019-02-26", 4);
		assertResultOk("2019-02-27", 5);
		assertResultOk("2019-02-28", 6);
		assertResultOk("2019-03-01", 7);

		assertResultOk("2020-02-28", 6);
		assertResultOk("2020-02-29", 6);
		assertResultOk("2020-03-01", 7);
	}

	private void assertResultOk(
			@NonNull final String date,
			final int expected)
	{
		SystemTime.setFixedTimeSource(LocalDate.parse(date)
				.atStartOfDay(ZoneId.systemDefault()));

		final int result = RefundTestTools.computeInvoiceScheduleDayOfMonth();
		assertThat(result).isEqualTo(expected);
	}

}
