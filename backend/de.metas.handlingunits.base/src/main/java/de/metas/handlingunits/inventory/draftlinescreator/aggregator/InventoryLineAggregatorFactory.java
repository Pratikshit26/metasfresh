package de.metas.handlingunits.inventory.draftlinescreator.aggregator;

import de.metas.document.DocBaseAndSubType;
import de.metas.inventory.AggregationType;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;

/*
 * #%L
 * de.metas.handlingunits.base
     
 * #L%
 */

@UtilityClass
public class InventoryLineAggregatorFactory
{
	public static InventoryLineAggregator getForDocBaseAndSubType(@NonNull final DocBaseAndSubType docBaseAndSubType)
	{
		final AggregationType aggregationMode = AggregationType.getByDocTypeOrNull(docBaseAndSubType);
		Check.assumeNotNull(aggregationMode, "Unexpected docBaseAndSubType={} with no registered aggregationMode", docBaseAndSubType);

		try
		{
			return getForAggregationMode(aggregationMode);
		}
		catch (final Exception ex)
		{
			throw AdempiereException.wrapIfNeeded(ex)
					.setParameter("docBaseAndSubType", docBaseAndSubType)
					.appendParametersToMessage();
		}
	}

	public static InventoryLineAggregator getForAggregationMode(@NonNull final AggregationType aggregationMode)
	{
		switch (aggregationMode)
		{
			case SINGLE_HU:
				return SingleHUInventoryLineAggregator.INSTANCE;

			case MULTIPLE_HUS:
				return MultipleHUInventoryLineAggregator.INSTANCE;

			default:
				throw new AdempiereException("Unexpected aggregationMode: " + aggregationMode);
		}
	}
}
