package de.metas.vertical.pharma.msv3.server.peer.metasfresh.services;

import com.google.common.collect.ImmutableList;
import de.metas.document.sequence.impl.DocumentNoBuilderFactory;
import de.metas.material.cockpit.model.I_MD_Stock_WarehouseAndProduct_v;
import de.metas.material.cockpit.stock.StockRepository;
import de.metas.product.ProductCategoryId;
import de.metas.vertical.pharma.msv3.server.peer.metasfresh.model.MSV3ServerConfig;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3EventVersion;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3StockAvailability;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3StockAvailabilityUpdatedEvent;
import de.metas.vertical.pharma.msv3.server.peer.service.MSV3ServerPeerService;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Product;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static java.math.BigDecimal.ONE;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

/*
 * #%L
 * metasfresh-pharma.msv3.server-peer-metasfresh
     
 * #L%
 */

public class MSV3StockAvailabilityServiceTest
{
	private MSV3StockAvailabilityService msv3StockAvailabilityService;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		msv3StockAvailabilityService = new MSV3StockAvailabilityService(
				new StockRepository(),
				new MSV3ServerConfigService(),
				new MSV3ServerPeerService(Optional.empty(), Optional.empty()),
				new DocumentNoBuilderFactory(Optional.empty()));
	}

	@Test
	public void createMSV3StockAvailabilityUpdateEvent()
	{
		final ProductCategoryId productCategoryId = ProductCategoryId.ofRepoId(10);

		final I_MD_Stock_WarehouseAndProduct_v stockRecordWithValidPZN = createStockRecord(productCategoryId, "12345");
		assertThat(stockRecordWithValidPZN.getQtyOnHand()).isEqualByComparingTo(ONE); // guard

		createStockRecord(productCategoryId, "ABCDE");

		final MSV3ServerConfig msv3ServerConfig = MSV3ServerConfig.builder()
				.productCategoryId(productCategoryId)
				.build();

		// invoke the method under test
		final ImmutableList<MSV3StockAvailabilityUpdatedEvent> result = msv3StockAvailabilityService
				.streamMSV3StockAvailabilityUpdateEvents(msv3ServerConfig, MSV3EventVersion.of(20))
				.collect(ImmutableList.toImmutableList());

		assertThat(result).hasSize(1);

		// we expect that only the valid PZN is present
		assertThat(result.get(0).getItems()).hasSize(1);

		final MSV3StockAvailability msv3StockAvailabilityItem = result.get(0).getItems().get(0);
		assertThat(msv3StockAvailabilityItem.getPzn()).isEqualTo(12345);
		assertThat(msv3StockAvailabilityItem.getQty()).isEqualTo(1);
	}

	@Test
	public void createMSV3StockAvailabilityUpdateEvent_fixedQtyAvailableToPromise()
	{
		final ProductCategoryId productCategoryId = ProductCategoryId.ofRepoId(10);

		createStockRecord(productCategoryId, "12345");
		createStockRecord(productCategoryId, "ABCDE");

		final MSV3ServerConfig msv3ServerConfig = MSV3ServerConfig.builder()
				.productCategoryId(productCategoryId)
				.fixedQtyAvailableToPromise(10)
				.build();

		// invoke the method under test
		final ImmutableList<MSV3StockAvailabilityUpdatedEvent> result = msv3StockAvailabilityService
				.streamMSV3StockAvailabilityUpdateEvents(msv3ServerConfig, MSV3EventVersion.of(20))
				.collect(ImmutableList.toImmutableList());

		assertThat(result).hasSize(1);

		// new expect that only the valid PZN is present, but with the fixedQtyAvailableToPromise of 10 that we set
		assertThat(result.get(0).getItems()).hasSize(1);

		final MSV3StockAvailability msv3StockAvailabilityItem = result.get(0).getItems().get(0);
		assertThat(msv3StockAvailabilityItem.getPzn()).isEqualTo(12345);
		assertThat(msv3StockAvailabilityItem.getQty()).isEqualTo(10);
	}

	private I_MD_Stock_WarehouseAndProduct_v createStockRecord(
			@NonNull final ProductCategoryId productCategoryId,
			@NonNull final String productValue)
	{
		final I_M_Product productRecord = newInstance(I_M_Product.class);
		productRecord.setM_Product_Category_ID(productCategoryId.getRepoId());
		productRecord.setValue(productValue);
		saveRecord(productRecord);

		final I_MD_Stock_WarehouseAndProduct_v stockItemRecord = newInstance(I_MD_Stock_WarehouseAndProduct_v.class);
		stockItemRecord.setM_Product_ID(productRecord.getM_Product_ID());
		stockItemRecord.setProductValue(productRecord.getValue());
		stockItemRecord.setM_Product_Category_ID(productRecord.getM_Product_Category_ID());
		stockItemRecord.setQtyOnHand(ONE);
		saveRecord(stockItemRecord);

		return stockItemRecord;
	}

}
