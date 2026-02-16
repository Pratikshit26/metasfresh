package de.metas.adempiere.gui.search.impl;

import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.quantity.QuantityTU;
import de.metas.uom.X12DE355;

/*
 * #%L
 * de.metas.handlingunits.base
     
 * #L%
 */

class HUPackingAwareBLTest
{

	private HUPackingAwareBL huPackingAwareBL;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		huPackingAwareBL = new HUPackingAwareBL();
	}

	@Test
	void calculateQtyTU()
	{
		final I_C_UOM uomRecord = BusinessTestHelper.createUOM("Coli", X12DE355.COLI);

		final I_C_OrderLine orderLineRecord = newInstance(I_C_OrderLine.class);
		orderLineRecord.setC_UOM_ID(uomRecord.getC_UOM_ID());
		orderLineRecord.setQtyEntered(TEN);

		saveRecord(orderLineRecord);

		final OrderLineHUPackingAware orderLineHUPackingAware = new OrderLineHUPackingAware(orderLineRecord);

		// invoke the method under test
		final QuantityTU result = huPackingAwareBL.calculateQtyTU(orderLineHUPackingAware);

		assertThat(result).isEqualTo(QuantityTU.ofInt(10));
	}

}
