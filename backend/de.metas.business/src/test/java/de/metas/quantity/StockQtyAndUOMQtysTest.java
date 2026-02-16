package de.metas.quantity;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.product.ProductId;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */

public class StockQtyAndUOMQtysTest
{
	private I_C_UOM stockUomRecord;
	private I_M_Product productRecord;

	private I_C_UOM uomRecord;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		stockUomRecord = newInstance(I_C_UOM.class);
		saveRecord(stockUomRecord);

		productRecord = newInstance(I_M_Product.class);
		productRecord.setC_UOM_ID(stockUomRecord.getC_UOM_ID());
		saveRecord(productRecord);

		uomRecord = newInstance(I_C_UOM.class);
		saveRecord(uomRecord);
	}

	@Test
	void validate_inconsistent_stockUom()
	{
		final StockQtyAndUOMQty qtys = StockQtyAndUOMQty
				.builder()
				.productId(ProductId.ofRepoId(productRecord.getM_Product_ID()))
				.stockQty(Quantity.of(ONE, uomRecord))
				.uomQty(Quantity.of(TEN, uomRecord))
				.build();

		assertThatThrownBy(() -> StockQtyAndUOMQtys.validate(qtys))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("Product's stock UOM does not match stockQty's UOM");
	}

	@Test
	void validate_inconsistent_uom()
	{
		final StockQtyAndUOMQty qtys = StockQtyAndUOMQty
				.builder()
				.productId(ProductId.ofRepoId(productRecord.getM_Product_ID()))
				.stockQty(Quantity.of(ONE, stockUomRecord))
				.uomQty(Quantity.of(TEN, stockUomRecord))
				.build();

		// this shall *not* throw an exception
		final StockQtyAndUOMQty result = StockQtyAndUOMQtys.validate(qtys);

		assertThat(result).isSameAs(qtys);
	}
}
