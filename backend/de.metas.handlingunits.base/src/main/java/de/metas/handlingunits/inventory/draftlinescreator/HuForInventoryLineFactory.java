package de.metas.handlingunits.inventory.draftlinescreator;

import java.math.BigDecimal;
import java.util.stream.Stream;

import de.metas.i18n.AdMessageKey;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.inventory.draftlinescreator.HuForInventoryLine.HuForInventoryLineBuilder;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.material.event.commons.AttributesKey;
import de.metas.organization.OrgId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;

import java.util.stream.Stream;

/*
 * #%L
 * de.metas.handlingunits.base
     
 * #L%
 */

@Service
public class HuForInventoryLineFactory
{
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private static final AdMessageKey MSG_ERROR_INVENTORY_LINE_NEGATIVE = AdMessageKey.of("InventoryLineQuantityNegativeError");

	public Stream<HuForInventoryLine> ofHURecordWithQtyAdjustment(@NonNull final I_M_HU huRecord, @NonNull final BigDecimal qty)
	{
		final HuForInventoryLineBuilder builder = HuForInventoryLine
				.builder()
				.orgId(OrgId.ofRepoId(huRecord.getAD_Org_ID()))
				.locatorId(IHandlingUnitsBL.extractLocatorId(huRecord));

		return handlingUnitsBL
				.getStorageFactory()
				.streamHUProductStorages(huRecord)
				.filter(huProductStorage -> !huProductStorage.isEmpty())
				.map(huProductStorage -> createHuForInventoryLine(builder, huProductStorage, qty));
	}
	public Stream<HuForInventoryLine> ofHURecord(@NonNull final I_M_HU huRecord)
	{
		return ofHURecordWithQtyAdjustment(huRecord, BigDecimal.ZERO);
	}

	private HuForInventoryLine createHuForInventoryLine(
			@NonNull final HuForInventoryLineBuilder huForInventoryLineBuilder,
			@NonNull final IHUProductStorage huProductStorage,
			@NonNull final BigDecimal qty)
	{
		final AttributesKey attributesKey = handlingUnitsBL.getAttributesKeyForInventory(huProductStorage.getM_HU());
		final Quantity bookedQty = huProductStorage.getQty();
		final Quantity adjustedQty = Quantity.of(bookedQty.toBigDecimal().add(qty), bookedQty.getUOM());

		if(adjustedQty.toBigDecimal().signum() < 0)
		{
			throw new AdempiereException(MSG_ERROR_INVENTORY_LINE_NEGATIVE);
		}
		
		return huForInventoryLineBuilder
				.storageAttributesKey(attributesKey)
				.huId(huProductStorage.getHuId())
				.productId(huProductStorage.getProductId())
				.quantityBooked(bookedQty)
				.quantityCount(adjustedQty)
				.build();
	}
}
