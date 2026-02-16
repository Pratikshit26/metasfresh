/*
 * #%L
 * de.metas.serviceprovider.base
     
 * #L%
 */

package de.metas.serviceprovider.timebooking;

import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;

import static de.metas.serviceprovider.TestConstants.MOCK_BOOKED_SECONDS;
import static de.metas.serviceprovider.TestConstants.MOCK_HOURS_AND_MINUTES;
import static de.metas.serviceprovider.TestConstants.MOCK_INSTANT_FROM_DATE;
import static de.metas.serviceprovider.TestConstants.MOCK_ISSUE_ID;
import static de.metas.serviceprovider.TestConstants.MOCK_ORG_ID;
import static de.metas.serviceprovider.TestConstants.MOCK_USER_ID;
import static org.junit.Assert.assertEquals;

public class TimeBookingRepositoryTest
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final TimeBookingRepository timeBookingRepository = new TimeBookingRepository(queryBL);

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void save()
	{
		//given
		final TimeBooking mockTimeBooking = getMockTimeBooking();

		//when
		final TimeBookingId timeBookingId = timeBookingRepository.save(mockTimeBooking);

		final TimeBooking storedTimeBooking = timeBookingRepository.getByIdOptional(timeBookingId).get();

		//then
		final TimeBooking mockTimeBookingWithId = mockTimeBooking.toBuilder().timeBookingId(timeBookingId).build();

		assertEquals(mockTimeBookingWithId, storedTimeBooking);
	}

	private TimeBooking getMockTimeBooking()
	{
		return TimeBooking
				.builder()
				.bookedDate(MOCK_INSTANT_FROM_DATE)
				.bookedSeconds(MOCK_BOOKED_SECONDS)
				.hoursAndMins(MOCK_HOURS_AND_MINUTES)
				.issueId(MOCK_ISSUE_ID)
				.performingUserId(MOCK_USER_ID)
				.orgId(MOCK_ORG_ID)
				.build();
	}
}
