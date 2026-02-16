/*
 * #%L
 * de.metas.shipper.gateway.dhl
     
 * #L%
 */

package de.metas.shipper.gateway.dhl;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static de.metas.shipper.gateway.dhl.DhlTestHelper.ACCOUNT_NUMBER_DE;
import static de.metas.shipper.gateway.dhl.DhlTestHelper.createDummyDeliveryOrderDEtoDE;

@Disabled("makes ACTUAL calls to dhl api and needs auth")
class IntegrationDEtoDETest
{
	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	@DisplayName("Delivery Order DE -> DE + test persistence after all steps")
	void testAllSteps()
	{
		DhlTestHelper.testAllSteps(createDummyDeliveryOrderDEtoDE(), ACCOUNT_NUMBER_DE);
	}
}
