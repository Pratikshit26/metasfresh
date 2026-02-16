/*
 * #%L
 * de.metas.shipper.gateway.dpd
     
 * #L%
 */

package de.metas.shipper.gateway.dpd;

import de.metas.shipper.gateway.dpd.model.DpdShipperProduct;
import de.metas.shipper.gateway.spi.exceptions.ShipperGatewayException;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Disabled("Makes ACTUAL calls to DPD api and needs auth")
public class IntegrationDEtoATTest
{
	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	@DisplayName("Delivery Order DE -> AT, DPD E12 - fails as next-day delivery doesn't work outside country")
	void E12()
	{
		Assertions.assertThrows(ShipperGatewayException.class,
				() -> DpdTestHelper.testAllSteps(DpdTestHelper.createDummyDeliveryOrderDEtoAT(DpdShipperProduct.DPD_E12)));
	}

	@Test
	@DisplayName("Delivery Order DE -> AT, DPD Classic")
	void Classic()
	{
		DpdTestHelper.testAllSteps(DpdTestHelper.createDummyDeliveryOrderDEtoAT(DpdShipperProduct.DPD_CLASSIC));
	}

	@Test
	@DisplayName("Delivery Order DE -> AT, DPD Express")
	void Express()
	{
		DpdTestHelper.testAllSteps(DpdTestHelper.createDummyDeliveryOrderDEtoAT(DpdShipperProduct.DPD_EXPRESS));
	}
}
