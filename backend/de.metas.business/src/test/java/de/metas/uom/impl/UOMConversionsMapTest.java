package de.metas.uom.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.adempiere.exceptions.NoUOMConversionException;
import org.compiere.Adempiere;
import org.junit.Before;
import org.junit.Test;

import de.metas.uom.UOMConversionRate;
import de.metas.uom.UOMConversionsMap;
import de.metas.uom.UomId;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */

public class UOMConversionsMapTest
{
	private final UomId uomId1 = UomId.ofRepoId(1);
	private final UomId uomId2 = UomId.ofRepoId(2);
	private final UomId uomId3 = UomId.ofRepoId(3);

	@Before
	public void init()
	{
		Adempiere.enableUnitTestMode();
	}

	@Test
	public void test_getRate()
	{
		final UOMConversionRate rate = UOMConversionRate.builder()
				.fromUomId(uomId1)
				.toUomId(uomId2)
				.fromToMultiplier(new BigDecimal("100"))
				.toFromMultiplier(new BigDecimal("0.01"))
				.build();

		final UOMConversionsMap conversions = UOMConversionsMap.builder()
				.rate(rate)
				.build();

		assertThat(conversions.getRate(uomId1, uomId2)).isEqualTo(rate);
		assertThat(conversions.getRate(uomId2, uomId1)).isEqualTo(rate.invert());

		assertThat(conversions.getRate(uomId1, uomId1)).isEqualTo(UOMConversionRate.one(uomId1));
		assertThat(conversions.getRate(uomId2, uomId2)).isEqualTo(UOMConversionRate.one(uomId2));

		assertThrows(NoUOMConversionException.class, () -> conversions.getRate(uomId1, uomId3));
	}

}
