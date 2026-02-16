/*
 * #%L
 * de.metas.shipper.gateway.dpd
     
 * #L%
 */

package de.metas.shipper.gateway.dpd;

import de.metas.shipper.gateway.dpd.model.DpdShipperProduct;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Disabled("Makes ACTUAL calls to DPD api and needs auth")
public class IntegrationDEtoDETest
{
	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	@DisplayName("Delivery Order DE -> DE, DPD E12")
	void E12()
	{
		DpdTestHelper.testAllSteps(DpdTestHelper.createDummyDeliveryOrderDEtoDE(DpdShipperProduct.DPD_E12));
	}

	@Test
	@DisplayName("Delivery Order DE -> DE, DPD Classic")
	void Classic()
	{
		DpdTestHelper.testAllSteps(DpdTestHelper.createDummyDeliveryOrderDEtoDE(DpdShipperProduct.DPD_CLASSIC));
	}

	@Test
	@DisplayName("Delivery Order DE -> DE, DPD Express")
	void Express()
	{
		DpdTestHelper.testAllSteps(DpdTestHelper.createDummyDeliveryOrderDEtoDE(DpdShipperProduct.DPD_EXPRESS));
	}
}
