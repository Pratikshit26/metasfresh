package org.eevolution.api;

import java.math.BigDecimal;

import de.metas.quantity.Quantity;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */

@UtilityClass
public class ProductBOMQtys
{
	/**
	 * Calculates Qty + Scrap
	 *
	 * @param qty          qty (without scrap)
	 * @param scrapPercent scrap percent (between 0..100)
	 * @return qty * (1 + qtyScrap/100)
	 */
	public static BigDecimal computeQtyWithScrap(final BigDecimal qty, @NonNull final Percent scrapPercent)
	{
		if (qty == null || qty.signum() == 0)
		{
			return BigDecimal.ZERO;
		}

		if (scrapPercent.isZero())
		{
			return qty;
		}

		final int precision = 8;
		return scrapPercent.addToBase(qty, precision);
	}

	/**
	 * Calculates Qty + Scrap
	 *
	 * @param qty          qty (without scrap)
	 * @param scrapPercent scrap percent (between 0..100)
	 * @return qty * (1 + qtyScrap/100)
	 */
	public static Quantity computeQtyWithScrap(@NonNull final Quantity qty, @NonNull final Percent scrapPercent)
	{
		if (qty.signum() == 0)
		{
			return qty;
		}

		if (scrapPercent.isZero())
		{
			return qty;
		}

		final int precision = 8;
		return Quantity.of(
				scrapPercent.addToBase(qty.toBigDecimal(), precision),
				qty.getUOM());
	}

}
