package de.metas.storage.spi.hu.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.product.ProductId;
import de.metas.storage.IStorageQuery;

/*
 * #%L
 * de.metas.handlingunits.base
     
 * #L%
 */

class HUStorageQueryTest
{
	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void createHUQueryBuilder()
	{
		final I_C_BPartner bPartnerRecord = newInstance(I_C_BPartner.class);
		saveRecord(bPartnerRecord);
		final BPartnerId bPartnerId = BPartnerId.ofRepoId(bPartnerRecord.getC_BPartner_ID());

		final I_M_Product productRecord = newInstance(I_M_Product.class);
		saveRecord(productRecord);
		final ProductId productId = ProductId.ofRepoId(productRecord.getM_Product_ID());

		final I_M_Warehouse warehouseRecord = newInstance(I_M_Warehouse.class);
		saveRecord(warehouseRecord);
		final WarehouseId warehouseId = WarehouseId.ofRepoId(warehouseRecord.getM_Warehouse_ID());

		final IStorageQuery storageQuery = HUStorageEngine.instance
				.newStorageQuery()
				.addProductId(productId)
				.addWarehouseId(warehouseId)
				.addBPartnerId(bPartnerId)
				.addBPartnerId(null);

		final IHUQueryBuilder queryBuilder = HUStorageQuery
				.cast(storageQuery)
				.createHUQueryBuilder();

		assertThat(queryBuilder.getOnlyWithProductIds()).containsOnly(productId);
		assertThat(queryBuilder.getOnlyInWarehouseIds()).containsOnly(warehouseId);
		assertThat(queryBuilder.getOnlyInBPartnerIds()).containsOnly(bPartnerId, null);
	}

}
