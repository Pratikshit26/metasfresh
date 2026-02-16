package de.metas.inventory;

import java.util.stream.Stream;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_C_DocType;

import com.google.common.collect.ImmutableMap;

import de.metas.document.DocBaseAndSubType;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
     
 * #L%
 */

public enum AggregationType
{
	// NOTE to developer: Please keep in sync doc sub types with list reference "C_DocType SubType" {@code AD_Reference_ID=148}

	SINGLE_HU(
			HUAggregationType.SINGLE_HU,
			DocBaseAndSubType.of(
					X_C_DocType.DOCBASETYPE_MaterialPhysicalInventory,
					InventoryDocSubType.SingleHUInventory.getCode())),

	MULTIPLE_HUS(
			HUAggregationType.MULTI_HU,
			DocBaseAndSubType.of(
					X_C_DocType.DOCBASETYPE_MaterialPhysicalInventory,
					InventoryDocSubType.AggregatedHUInventory.getCode()));

	@Getter
	private final HUAggregationType huAggregationType;

	@Getter
	private final DocBaseAndSubType docBaseAndSubType;

	private static ImmutableMap<DocBaseAndSubType, AggregationType> byDocType = Stream.of(values())
			.collect(ImmutableMap.toImmutableMap(AggregationType::getDocBaseAndSubType, t -> t));

	private static ImmutableMap<HUAggregationType, AggregationType> byHUAggregationType = Stream.of(values())
			.collect(ImmutableMap.toImmutableMap(AggregationType::getHuAggregationType, t -> t));

	private AggregationType(
			@NonNull final HUAggregationType huAggregationType,
			@NonNull final DocBaseAndSubType docBaseAndSubType)
	{
		this.huAggregationType = huAggregationType;
		this.docBaseAndSubType = docBaseAndSubType;
	}

	public String getHuAggregationTypeCode()
	{
		return getHuAggregationType().getCode();
	}

	public static AggregationType getByDocTypeOrNull(@NonNull final DocBaseAndSubType docBaseAndSubType)
	{
		return byDocType.get(docBaseAndSubType);
	}

	public static AggregationType getByHUAggregationType(@NonNull final HUAggregationType huAggregationType)
	{
		final AggregationType type = byHUAggregationType.get(huAggregationType);
		if (type == null)
		{
			throw new AdempiereException("No " + AggregationType.class + " found for " + huAggregationType);
		}
		return type;
	}

}
